package com.contas107.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.contas107.DTO.LancamentoRequestDTO;
import com.contas107.DTO.LancamentoResponseDTO;
import com.contas107.model.Lancamento;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    @Mapping(target = "id", ignore = true)
    Lancamento paraLancamentoEntity(LancamentoRequestDTO dto);

    Lancamento paraLancamentoEntityRespDTO(LancamentoResponseDTO dto);

    LancamentoResponseDTO paraLancamentoResponseDTO(Lancamento lancamento);

    List<LancamentoResponseDTO> paraLISTLancamentoResponseDTO(List<Lancamento> list);

}
