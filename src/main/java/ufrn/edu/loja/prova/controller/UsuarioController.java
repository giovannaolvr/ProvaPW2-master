package ufrn.edu.loja.prova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufrn.edu.loja.prova.model.CamisaModel;
import ufrn.edu.loja.prova.model.UsuarioModel;
import ufrn.edu.loja.prova.service.CamisaService;
import ufrn.edu.loja.prova.service.UsuarioService;

import java.util.List;

@Controller
public class UsuarioController {
    
    UsuarioService service;
    CamisaService serviceCamisa;

    public UsuarioController(UsuarioService service, CamisaService serviceCamisa){
        this.service = service;
        this.serviceCamisa = serviceCamisa;
    }

    @RequestMapping(value = {"/adminPage"}, method = RequestMethod.GET)
    public String getAdmin(Model model){
        List<CamisaModel> camisaList = serviceCamisa.findAll();
        model.addAttribute("camisaList", camisaList);
        return "adminPage";
    }


    @GetMapping("/cadastrarUsuario")
    public String doCadastrarUsuario(Model model){

        UsuarioModel u = new UsuarioModel();
        model.addAttribute("usuario", u);

        return "cadastrarUsuario";
    }

    @PostMapping("/doSalvarUsuario")
    public String doSalvarUsuario(@ModelAttribute UsuarioModel u){
        service.create(u);

        return "redirect:/";
    }

    @GetMapping("/listUsuarios")
    public String listAll(){
        List<UsuarioModel> usuarios = service.listAll();
        for (UsuarioModel u : usuarios){
            System.out.println(u);
        }

        return "listUsuarios";
    }
}