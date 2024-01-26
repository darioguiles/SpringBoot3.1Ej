package org.iesvdm.mapper;

import org.iesvdm.dto.PedidoFormDTO;
import org.iesvdm.modelo.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "id_cliente", target = "idCliente")
    @Mapping(source = "id_comercial", target = "idComercial")
    public PedidoFormDTO pedidoAPedidoFormDTO(Pedido pedido);

    @Mapping(source = "idCliente", target = "id_cliente")
    @Mapping(source = "idComercial", target = "id_comercial")
    public Pedido pedidoFormDTOAPedido(PedidoFormDTO pedidoFormDTO);

    /* No hace falta realmente, pero se puede usar en un futuro
    @Mapping(source = "id_cliente", target = "idCliente")
    @Mapping(source = "id_comercial", target = "idComercial")
    public List<PedidoFormDTO> listaPedidoAlistaPedidoDTO(List<Pedido> lista);

    @Mapping(source = "id_cliente", target = "idCliente")
    @Mapping(source = "id_comercial", target = "idComercial")
    public List<Pedido> listaPedidoDTOAListaPedido(List<PedidoFormDTO> listaDTO);
    */


}