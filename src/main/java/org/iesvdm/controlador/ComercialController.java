package org.iesvdm.controlador;


import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ComercialController {

    private ComercialService comercialService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialController( ComercialService comercialService) {this.comercialService = comercialService;}

    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";
    }

    @GetMapping("/clientes/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        return "detalle-cliente";

    }


    //Tenemos que implementar el Create, Update y Delete

    @GetMapping("/clientes/crear")
    public String crear(Model model) {

        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);

        return "crear-cliente";

    }

    @PostMapping("/clientes/crear")
    public RedirectView submitCrear(@ModelAttribute Cliente cliente) {

        clienteService.newCliente(cliente);

        return new RedirectView("/clientes") ;
    }

    @GetMapping("/clientes/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        return "editar-cliente";

    }

    @PostMapping("/clientes/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("cliente") Cliente cliente) {

        clienteService.replaceCliente(cliente);

        return new RedirectView("/clientes");
    }

    @PostMapping("/clientes/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        clienteService.deleteCliente(id);

        return new RedirectView("/clientes");
    }

}
