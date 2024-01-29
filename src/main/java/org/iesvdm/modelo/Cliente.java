package org.iesvdm.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

	@Min(value = 1, message = "{msg.valid.min}" )
	private int id;

	@NotNull(message = "{msg.valid.not.null}")
	@Length(min=1, max=30)
	private String nombre;

	@NotNull(message = "{msg.valid.not.null}")
	@Length(min=1, max=30)
	private String apellido1;

	private String apellido2;

	@NotNull(message = "{msg.valid.not.null}")
	@Length(min=1, max=50)
	private String ciudad;

	@Min(value = 100, message = "{msg.valid.min}" )
	@Max(value = 1000, message = "{msg.valid.max}" )
	private int categoria;
	
}
