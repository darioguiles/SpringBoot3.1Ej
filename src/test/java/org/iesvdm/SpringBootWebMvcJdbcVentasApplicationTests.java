package org.iesvdm;

import org.iesvdm.dao.ClienteDAOImpl;
import org.iesvdm.dao.ComercialDAOImpl;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class SpringBootWebMvcJdbcVentasApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ClienteDAOImpl clienteDAOImpl;

	@Autowired
	ComercialDAOImpl comercialDAOImpl;
	@Test
	void testIDIncremental_ClienteSimpleJDBC() {

		Cliente cliente = new Cliente(0,
				"Dario"
				, "Guiles"
				, "Cuesta"
				,"Málaga"
				,2);
		this.clienteDAOImpl.create_CON_RECARGA_SIMPLEJDBC(cliente);
		Assertions.assertTrue(cliente.getId()>0);
		System.out.println("ID Cliente AUTO_INCREMENT: " + cliente.getId()); //TEST PASA
	}

	@Test
	void testIDIncremental_ComercialSimpleJDBC() {

		Comercial comercial = new Comercial(0,
				"Javier"
				, "Garcia"
				, "Moreno"
				,20.4f);
		this.comercialDAOImpl.create_CON_RECARGA_SIMPLEJDBC(comercial);
		Assertions.assertTrue(comercial.getId()>0);
		System.out.println("ID Comercial AUTO_INCREMENT: " + comercial.getId()); //TEST PASA 1
	}

	//Todo -> Hacer el resto de test para comprobar y reafirmar la seguridad de la función.
}
