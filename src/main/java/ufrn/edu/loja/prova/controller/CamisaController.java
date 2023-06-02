package ufrn.edu.loja.prova.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ufrn.edu.loja.prova.model.*;
import ufrn.edu.loja.prova.service.*;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Controller
public class CamisaController {

    private final CamisaService service;
    private UploadFileService uploadFileService;
    

    public CamisaController(CamisaService service, UploadFileService uploadFileService) {
        this.service = service;
        this.uploadFileService = uploadFileService;
    }
   

    @RequestMapping(value = {"/", "/index", "/index.html"}, method = RequestMethod.GET)
    public String getIndex(Model model, HttpServletResponse response, HttpSession session) {
        
        List<CamisaModel> camisaList = service.findAll();
        model.addAttribute("camisaList", camisaList);

        // Adicionar o cookie de visita
        addVisitaCookie(response);

        // Obter o carrinho da sessão
        List<CamisaModel> carrinho = getCarrinhoFromSession(session);
        model.addAttribute("quantidadeCarrinho", carrinho.size());

        return "index.html";
    }

    @GetMapping("/cadastrarPage")
    public String getCadastrarPage(Model model){
        CamisaModel c = new CamisaModel();
        model.addAttribute("camisa", c);
        return "cadastrarPage";
    }

    @PostMapping("/doSalvar")
    public String doSalvar(@ModelAttribute("camisa") @Valid CamisaModel c, Errors errors, @RequestParam(name="file") MultipartFile file) {
        var numeroAleatorio = Math.random(); 

        if (errors.hasErrors()) {
            return "cadastrarPage";
        } else {
            String fileName = numeroAleatorio+"produto"+file.getOriginalFilename();
            c.setImageURI('/'+fileName);
            this.uploadFileService.save(file, fileName);
            service.save(c);
            return "redirect:/adminPage?mensagem=Salvo com sucesso!"; 
        }
         
    }

    @GetMapping("/editarPage/{id}")
    public String getEditarPage(@PathVariable(name = "id") Long id, Model model){
        Optional<CamisaModel> c = service.findById(id);
        if (c.isPresent()){
            model.addAttribute("camisa", c.get());
            return "editarPage";
        }else{
            return "redirect:/adminPage";
        }
    }

    @GetMapping("/deletar/{id}")
    public String doDeletar(@PathVariable(name = "id") Long id) {
    Optional<CamisaModel> camisa = service.findById(id);
        if (camisa.isPresent()) {
            service.delete(camisa);
        }
        return "redirect:/index?mensagem=Remocao realizada com sucesso!";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model, HttpSession session) {
        List<CamisaModel> carrinho = getCarrinhoFromSession(session);

        if (carrinho.isEmpty()) {
            return "redirect:/index?mensagem=Nao existem itens no carrinho.";
        }

        model.addAttribute("carrinho", carrinho);
        return "verCarrinho.html";
    }

   @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session) {
        List<CamisaModel> carrinho = getCarrinhoFromSession(session);
        
        if (!carrinho.isEmpty()) {
            // Carrinho contém itens, então pode finalizar a compra
            carrinho.clear();
            return "redirect:/index?mensagem=Compra finalizada com sucesso!";
        } else {
            // Carrinho está vazio, redireciona de volta para o index com mensagem de erro
            return "redirect:/index?mensagem=Nao ha itens no carrinho!";
        }
    }
    
    // Adicionar o cookie de visita
    private void addVisitaCookie(HttpServletResponse response) {
        LocalDateTime now = LocalDateTime.now();
        String visitaValue = now.toString();

        Cookie visitaCookie = new Cookie("visita", visitaValue);
        visitaCookie.setMaxAge(24 * 60 * 60); // 24 horas
        visitaCookie.setPath("/");
        response.addCookie(visitaCookie);
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarAoCarrinho(@PathVariable(name = "id") Long id, HttpSession session) {
        List<CamisaModel> carrinho = getCarrinhoFromSession(session);
        Optional<CamisaModel> camisaOptional = service.findById(id);
        camisaOptional.ifPresent(carrinho::add);
        session.setAttribute("carrinho", carrinho);
        return "redirect:/index";
    }

    // Obter o carrinho da sessão ou criar um novo se não existir
    @SuppressWarnings("unchecked")
    private List<CamisaModel> getCarrinhoFromSession(HttpSession session) {
        List<CamisaModel> carrinho = (List<CamisaModel>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }
        return carrinho;
    }
}

