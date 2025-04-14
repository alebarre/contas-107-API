package com.contas107.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;

import com.contas107.DTO.BancoDTO;
import com.contas107.model.Banco;

@Mapper(componentModel = "spring")
public interface BancoMapper {

    Banco paraBancoEntity(BancoDTO dto);

    BancoDTO paraBancoDTO(Banco banco);

    List<BancoDTO> paraLISTBancoDTO(List<Banco> list);

}
