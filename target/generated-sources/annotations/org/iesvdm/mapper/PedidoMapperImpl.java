package org.iesvdm.mapper;

import javax.annotation.processing.Generated;
import org.iesvdm.dto.PedidoFormDTO;
import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-26T16:38:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public PedidoFormDTO pedidoAPedidoFormDTO(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoFormDTO pedidoFormDTO = new PedidoFormDTO();

        pedidoFormDTO.setIdCliente( pedido.getId_cliente() );
        pedidoFormDTO.setIdComercial( pedido.getId_comercial() );
        pedidoFormDTO.setId( pedido.getId() );
        pedidoFormDTO.setTotal( pedido.getTotal() );
        pedidoFormDTO.setFecha( pedido.getFecha() );

        return pedidoFormDTO;
    }

    @Override
    public Pedido pedidoFormDTOAPedido(PedidoFormDTO pedidoFormDTO) {
        if ( pedidoFormDTO == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setId_cliente( pedidoFormDTO.getIdCliente() );
        pedido.setId_comercial( pedidoFormDTO.getIdComercial() );
        pedido.setId( pedidoFormDTO.getId() );
        pedido.setTotal( pedidoFormDTO.getTotal() );
        pedido.setFecha( pedidoFormDTO.getFecha() );

        return pedido;
    }
}
