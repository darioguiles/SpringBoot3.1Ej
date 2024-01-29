package org.iesvdm.mapper;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ComercialMapper {

//Se ha intendado usar la implementación que viene en la página pero ya sea por estar mal hecha u otro error no funciona
    //Se procede a usar la implementada anteriormente (con componente Spring...)
    @Mapping(source = "id", target = "id")
    ComercialDTO comercialAComercialDTO(Comercial comercial);

    @Mapping(source = "id", target = "id")
    Comercial comercialDTOAComercial(ComercialDTO comercialDTO);



}
