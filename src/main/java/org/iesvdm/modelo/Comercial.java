package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	@Min(value=1, message = "{msg.valid.min}")
	private int id;

	@NotNull(message = "{msg.valid.not.null}")
	@Length(min=1, max=30)
	private String nombre;

	@NotNull(message = "{msg.valid.not.null}")
	@Length(min=1, max=30)
	private String apellido1;

	private String apellido2;

	@NotNull(message = "{msg.valid.not.null}")
	@DecimalMin(value="0.276", message = "{msg.valid.min}")
	@DecimalMax(value="0.946", message = "{msg.valid.max}")
	private BigDecimal comision;
	
}
