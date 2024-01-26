package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilDAO {

    public static Pedido newPedido(ResultSet rs) throws SQLException {
        return new Pedido(rs.getInt("id"),
                rs.getDouble("total"),
                rs.getDate("fecha"),
                rs.getInt("id_cliente"),
                rs.getInt("id_comercial")
        );
    }
    public static Cliente newCliente(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id")
                , rs.getString("nombre")
                , rs.getString("apellido1")
                , rs.getString("apellido2")
                , rs.getString("ciudad")
                , rs.getInt("categoría")
        );
    }

    public static Comercial newComercial(ResultSet rs) throws SQLException {
        return new Comercial(rs.getInt("id")
                , rs.getString("nombre")
                , rs.getString("apellido1")
                , rs.getString("apellido2")
                , rs.getBigDecimal("comisión"));
    }

}
