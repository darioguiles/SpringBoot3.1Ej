package org.iesvdm.dao;


import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO<T extends Pedido> extends RepositoryBase<T> {

    /*

    public List<Pedido> getAllIDComercial(int id);

    public List<Pedido> getAllIDCliente(int id);
*/

    public Optional<Cliente> findClienteBy(int pedidoId);

    public Optional<Comercial> findComercialBy(int pedidoId);


    public List<Cliente> getAllClientesByIdPedido(int pedidoId);

    public void updateSinComercial(T t);


}
