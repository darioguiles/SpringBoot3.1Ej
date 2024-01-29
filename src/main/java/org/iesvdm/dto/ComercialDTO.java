package org.iesvdm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.modelo.Pedido;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComercialDTO {

    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private BigDecimal comision;
    private int cantidadPedidos;

}
