package org.iesvdm.dao;


import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {

    public List<Pedido> getAll();

    public void update(Pedido pedido);
    public void create(Pedido pedido);

    public Optional<Pedido> find(int id);

    public void delete(long id);

    public List<Cliente> getAllClientesByIdPedido(int id);

    public List<Pedido> getAllPClientesByID(int id);

    public List<Pedido> getAllPComercialesByID(int id);

    public void updateSinComercial(Pedido p);
}
