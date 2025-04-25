package com.contas107.mapstruct;

import com.contas107.DTO.EmpresaDTO;
import com.contas107.model.Empresa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    Empresa paraEmpresaEntity(EmpresaDTO dto);

    EmpresaDTO paraEmpresaDTO(Empresa empresa);

    List<EmpresaDTO> paraLISTEmpresaDTO(List<Empresa> list);

}
