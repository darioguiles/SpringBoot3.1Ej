package org.iesvdm.mapper;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;

public interface PedidoMapper {

    public PedidoDTO libroALibroDTO(Pedido pedido, float totalComision);


    public Pedido libroDTOALibro(PedidoDTO pedidoDTO);
}
