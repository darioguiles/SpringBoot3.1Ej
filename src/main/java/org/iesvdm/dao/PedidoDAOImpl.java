package org.iesvdm.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void create(Pedido pedido) {
        String sqlInsert = """
							INSERT INTO pedido (total, fecha, id_cliente, id_comercial) 
							VALUES  (     ?,         ?,         ?,         ?)
						   """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Con recuperación de id generado
        //Referencia para fechas y documentación posterior
        // https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
            int idx = 1;
            ps.setDouble(idx++, pedido.getTotal());
             //pedido.getFecha();
            Date fecha = new Date(pedido.getFecha().getTime()); //Funcionará??
            ps.setDate(idx++, fecha);
            ps.setInt(idx++, pedido.getId_cliente());
            ps.setInt(idx, pedido.getId_comercial());
            return ps;
        },keyHolder);


        pedido.setId(keyHolder.getKey().intValue());

        //Podemos recuperar el id

        log.info("Insertados {} registros.", rows);
    }

    @Override
    public List<Pedido> getAll() {

        List<Pedido> listPedido = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))

        );

        log.info("Devueltos {} registros.", listPedido.size());

        return listPedido;
    }

    public List<Pedido> getAllID(int id) {

        List<Pedido> listPedido = jdbcTemplate.queryForList(
                "SELECT * FROM pedido where id = ?"
                , (rs, rowNum) -> new Pedido(rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))
                , id
        );

        log.info("Devueltos {} registros.", listPedido.size());

        return listPedido;
    }


    @Override
    public Optional<Pedido> find(int id) {
        Pedido ped =  jdbcTemplate
                .queryForObject("SELECT * FROM pedido WHERE id = ?"
                        , (rs, rowNum) -> new Pedido(rs.getInt("id"),
                                rs.getDouble("total"),
                                rs.getDate("fecha"),
                                rs.getInt("id_cliente"),
                                rs.getInt("id_comercial"))
                        , id
                );

        if (ped != null) {
            return Optional.of(ped);}
        else{
            log.info("Pedido no encontrado.");
            return Optional.empty();
        }
    }



    @Override
    public void update(Pedido pedido) {

        int rows = jdbcTemplate.update("""
										UPDATE comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comisión = ?  
												WHERE id = ?
										""", pedido.getId()
                , pedido.getTotal()
                , pedido.getFecha()
                , pedido.getId_cliente()
                , pedido.getId_comercial());

        log.info("Update de Comercial con {} registros actualizados.", rows);
    }

    @Override
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);

        log.info("Delete de Pedido con {} registros eliminados.", rows);

    }



}
