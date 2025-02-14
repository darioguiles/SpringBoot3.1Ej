package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.dto.ComercialDTO;
import jakarta.validation.Valid;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;

	private ComercialService comercialService;

	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService, ComercialService comercialService) {
		this.clienteService = clienteService;
		this.comercialService= comercialService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		//Vamos a meter una lista de Comerciales
		List<ComercialDTO> listaComerciales = comercialService.listAllByIDCliente(id);
		model.addAttribute("listaCom",listaComerciales);
		//Un contador de pedidos x comercial? -> implementar



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
	public String submitCrear(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, Model model) {


		if (bindingResult.hasErrors()) {
			model.addAttribute("cliente", cliente);

			return "crear-cliente";

		}

		clienteService.newCliente(cliente);

		return "redirect:/cliente";
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
