package org.iesvdm.controlador;


import java.util.List;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ComercialController {

    private ComercialService comercialService;

    private ClienteService clienteService;


    @Autowired
    public ComercialController(ComercialService comercialService, ClienteService clienteService) {
        this.comercialService = comercialService;
        this.clienteService = clienteService;
    }

    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";
    }

    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        //Añadimos la lista de pedidos asociados al comercial

       List<Pedido> listaPedidos = comercialService.listAllPedidos(id);
        model.addAttribute("listaPedidos", listaPedidos);

        //Añadimos el clienteService para sacar el nombre de los clientes
        model.addAttribute("clienteService", clienteService);
/*
       Aquí hay algo que falla
        */

        return "detalle-comercial";

    }


    //Tenemos que implementar el Create, Update y Delete

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "crear-comercial";

    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute Comercial comercial) {

        comercialService.newComercial(comercial);

        return new RedirectView("/comerciales") ;
    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comercial";

    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }



}
