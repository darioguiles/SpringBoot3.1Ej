package org.iesvdm.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class PedidoDAOImpl implements PedidoDAO<Pedido> {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Cliente> findClienteBy(int pedidoId) {

        Cliente cliente = this.jdbcTemplate.queryForObject("""
                            select C.* from pedido P join cliente C on P.id_cliente = C.id and P.id = '' 
                """
                , (rs, rowNum) -> UtilDAO.newCliente(rs), pedidoId
        );

        return null;
    }

    @Override
    public Optional<Comercial> findComercialBy(int pedidoId) {
        return null;
    }

    @Override
    public List<Cliente> getAllClientesByIdPedido(int pedidoId) {

        List<Cliente> clienteList = this.jdbcTemplate.query("""
                select C.* from pedido P join cliente C on P.id_cliente = C.id  
                and P.id = ?
                """, (rs, rowNum) -> UtilDAO.newCliente(rs)
                , pedidoId);

        return clienteList;
    }

    @Override
    public void create(Pedido pedido) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Con recuperación de id generado
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                        insert into pedido ( total, fecha, id_cliente, id_comercial)
                        values (?, ?, ?, ?);
                        """, new String[] { "id" });
            int idx = 1;
            ps.setDouble(idx++, pedido.getTotal());
            ps.setDate(idx++, new java.sql.Date(pedido.getFecha().getTime()));
            ps.setInt(idx++, pedido.getCliente().getId());
            ps.setInt(idx++, pedido.getComercial().getId());
            return ps;
        },keyHolder);

        log.info("Filas creadas {}", rows);
        log.debug("Pedido con id = {} grabado correctamente",keyHolder.getKey().intValue());

        pedido.setId(keyHolder.getKey().intValue());

    }

    @Override
    public List<Pedido> getAll() {

        List<Pedido> listPedido = this.jdbcTemplate.query("""
                SELECT * FROM  pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                """, (rs, rowNum) -> UtilDAO.newPedido(rs)
        );

        return listPedido;
    }

    @Override
    public Optional<Pedido> find(int id) {

        Pedido pedido= this.jdbcTemplate.queryForObject("""
                    select * from pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                                        WHERE P.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), id);

        if (pedido != null) return Optional.of(pedido);
        log.debug("No encontrado pedido con id {} devolviendo Optional.empty()", id);
        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {

        this.jdbcTemplate.update("""
                      update pedido set total = ?, fecha = ?, id_cliente = ?, id_comercial = ? where id = ?
                    """, pedido.getTotal(), pedido.getFecha(), pedido.getCliente().getId(), pedido.getComercial().getId(), pedido.getId());

    }

    //Un pedido puede estar hecho por un cliente sin la necesidad de tener un Comercial
    public void updateSinComercial(Pedido pedido) {

        this.jdbcTemplate.update("""
                      update pedido set total = ?, fecha = ?, id_cliente = ?, id_comercial = ? where id = ?
                    """, pedido.getTotal(), pedido.getFecha(), pedido.getCliente().getId(), null, pedido.getId());

    }

    @Override
    public void delete(long id) {

        this.jdbcTemplate.update("""
                            delete from pedido where id = ? 
                            """
                , id
        );

    }


    @Override
    public List<Pedido> getAllComercialesByID(int idComercial) {
        //Actualizarse porque estoy usando algo deprecated -> ver diferentes aplicaciones o soluciones
/*
Para esto se debe tener en cuenta que es Sin Pedido y Sin
        List<Pedido> listPedido = jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id_comercial = ?",
                new Object[]{idComercial},
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial") ) ); */
            //Hecho con lo del profe

        List<Pedido> listPedido = this.jdbcTemplate.query("""
                SELECT * FROM pedido P LEFT JOIN comercial CO ON P.id_comercial = CO.id
                        WHERE CO.id = ?

                """, (rs, rowNum) -> UtilDAO.newPedido(rs), idComercial);


        log.info("Devueltos {} registros para id_comercial = {}.", listPedido.size(), idComercial);

        return listPedido;
    }


    @Override
    public List<Pedido> getAllClienteByID(int idCliente) {
        /*
        List<Pedido> listPedido = jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id_cliente = ?",
                new Object[]{idCliente},
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")
                )
        );*/

        List<Pedido> listPedido = this.jdbcTemplate.query("""
                SELECT * FROM pedido P LEFT JOIN cliente C ON P.id_comercial = C.id
                        WHERE C.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), idCliente);

        log.info("Devueltos {} registros para id_cliente = {}.", listPedido.size(), idCliente);

        return listPedido;
    }




    /* * * *                                                               * * * * *
     * * * * *                   SE DEBE INTENTAR                           * * * * *
     * * * * *    un método que llame al getAll y filtre luego por          * * * * *
     * * * * * el ID del cliente/comercial en vez de hacer un getAll entero * * * * *
     * * * * *                                                                * * * */

    /* Codigo previo

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
    public void create(Object o) {

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

*/


}
