package ufrn.edu.loja.prova.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ufrn.edu.loja.prova.model.*;
import ufrn.edu.loja.prova.service.*;

import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
public class CamisaController {

    private final CamisaService service;

    public CamisaController(CamisaService service) {
        this.service = service;
    }
   

    @RequestMapping(value = {"/", "/index", "/index.html"}, method = RequestMethod.GET)
    public String getIndex(Model model){

        List<CamisaModel> camisaList = service.findAll();
        model.addAttribute("camisaList", camisaList);

        return "index.html";
    }

    @RequestMapping(value = {"/formatada"}, method = RequestMethod.GET)
    public String getFormatada(Model model){

        List<CamisaModel> camisaList = service.findAll();
        model.addAttribute("camisaList", camisaList);

        return "formatada2.0";
    }


    @GetMapping("/mostrarPage")
    public String getMostrarPage(Model model){

        return "mostrarPage";
    }



    @GetMapping("/cadastrarPage")
    public String getCadastrarPage(Model model){
        CamisaModel c = new CamisaModel();
        model.addAttribute("camisa", c);
        return "cadastrarPage";
    }

    @PostMapping("/doSalvar")
    public String doSalvar(@ModelAttribute("camisa") @Valid CamisaModel c, Errors errors) {
    if (errors.hasErrors()) {
        return "cadastrarPage";
    } else {
        service.save(c);
        return "redirect:/adminPage";
    }
}


    @GetMapping("/editarPage/{id}")
    public String getEditarPage(@PathVariable(name = "id") Long id, Model model){
        Optional<CamisaModel> c = service.findById(id);
        if (c.isPresent()){
            model.addAttribute("camisa", c.get());
            return "editarPage";
        }else{
            return "redirect:/index";
        }
    }

    @GetMapping("/deletar/{id}")
    public String doDeletar(@PathVariable(name = "id") Long id){
        Optional<CamisaModel> camisa = service.findById(id);
        if (camisa.isPresent()) {
            service.delete(id);
        }
        return "redirect:/index";
    }

    
    //cookie
    public void addVisitaCookie(HttpServletResponse response){
        LocalDateTime now = LocalDateTime.now();
        String visitaValue = now.toString();

        Cookie visitaCookie = new Cookie("visita", visitaValue);
        visitaCookie.setMaxAge(24 * 60 * 60); // 24 horas
        visitaCookie.setPath("/");
        response.addCookie(visitaCookie);
    }
}

