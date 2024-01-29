package org.iesvdm.mapper;

import javax.annotation.processing.Generated;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-28T21:22:35+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ComercialMapperImpl implements ComercialMapper {

    @Override
    public ComercialDTO comercialAComercialDTO(Comercial comercial) {
        if ( comercial == null ) {
            return null;
        }

        ComercialDTO comercialDTO = new ComercialDTO();

        comercialDTO.setId( comercial.getId() );
        comercialDTO.setNombre( comercial.getNombre() );
        comercialDTO.setApellido1( comercial.getApellido1() );
        comercialDTO.setApellido2( comercial.getApellido2() );
        comercialDTO.setComision( comercial.getComision() );

        return comercialDTO;
    }

    @Override
    public Comercial comercialDTOAComercial(ComercialDTO comercialDTO) {
        if ( comercialDTO == null ) {
            return null;
        }

        Comercial comercial = new Comercial();

        comercial.setId( comercialDTO.getId() );
        comercial.setNombre( comercialDTO.getNombre() );
        comercial.setApellido1( comercialDTO.getApellido1() );
        comercial.setApellido2( comercialDTO.getApellido2() );
        comercial.setComision( comercialDTO.getComision() );

        return comercial;
    }
}
