package org.iesvdm.dto;


import lombok.Data;
import org.iesvdm.modelo.Pedido;

import java.math.BigDecimal;
import java.util.Date;

//Este DTO va a ser con herencia de Pedido
@Data
public class PedidoDTO extends Pedido {

    private float totalComision;

    public PedidoDTO(int id, double total, Date fecha, int id_cliente, int id_comercial, float totalComision) {
        super(id, total, fecha, id_cliente, id_comercial);
        this.totalComision = totalComision;
    }



}
