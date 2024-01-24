package org.iesvdm.modelo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Min(value=1, message = "{msg.valid.min}")
    private int id;

    @NotNull(message = "{msg.valid.not.null}")
    @DecimalMin(value="0.0", message = "{msg.valid.min}")
    @DecimalMin(value="100.0", message = "{msg.valid.max}")
    private Double total;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

     private int id_cliente;
     private int id_comercial;

}
