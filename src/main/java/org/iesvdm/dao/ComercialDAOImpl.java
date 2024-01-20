package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//Ponemos Autowired
	@Autowired //Le ponemos autowired para que Lombok nos ayude con la creación del jdbc
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void create(Comercial comerc) {

		String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comisión) 
							VALUES  (     ?,         ?,         ?,         ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comerc.getNombre());
			ps.setString(idx++, comerc.getApellido1());
			ps.setString(idx++, comerc.getApellido2());
			ps.setFloat(idx, comerc.getComision());
			return ps;
		},keyHolder);

		comerc.setId(keyHolder.getKey().intValue());

		//Podemos recuperar el id

		log.info("Insertados {} registros.", rows);

	}

	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getFloat("comisión"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {
		// TODO Auto-generated method stub

		Comercial fab =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getFloat("comisión"))
						, id
				);

		if (fab != null) {
			return Optional.of(fab);}
		else{
			log.info("Comercial no encontrado.");
			return Optional.empty();
		}

	}

	@Override
	public void update(Comercial cliente) {

		int rows = jdbcTemplate.update("""
										UPDATE comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comisión = ?  
												WHERE id = ?
										""", cliente.getNombre()
				, cliente.getApellido1()
				, cliente.getApellido2()
				, cliente.getComision()
				, cliente.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);


	}

	@Override
	public void delete(long id) {

		//jdbcTemplate.update("DELETE  from pedido where id_comercial = ?",id);
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);

	}

	public void create_CON_RECARGA_SIMPLEJDBC(Comercial comercial) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert
					.withTableName("comercial")
					.usingGeneratedKeyColumns();
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("nombre",comercial.getNombre())
					.addValue("apellido1",comercial.getApellido1())
					.addValue("apellido2",comercial.getApellido2())
					.addValue("comisión",comercial.getComision());
			Number number = simpleJdbcInsert.execute(params);
			comercial.setId(number.intValue());
	}
}
