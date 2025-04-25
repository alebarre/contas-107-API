package com.contas107.service;

import com.contas107.DTO.EmpresaDTO;
import com.contas107.mapstruct.EmpresaMapper;
import com.contas107.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    public List<EmpresaDTO> obterTodasEmpresas() {
        return empresaMapper.paraLISTEmpresaDTO(empresaRepository.findAll());
    }

    public EmpresaDTO salvarEmpresa(@RequestBody EmpresaDTO dto) {
        try {
            return empresaMapper.paraEmpresaDTO(empresaRepository.save(empresaMapper.paraEmpresaEntity(dto)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Empresa. 🙁", e);
        }
    }

    public EmpresaDTO editarEmpresa(@RequestBody EmpresaDTO dto, Long id) {
        if (id == null || String.valueOf(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada. ⛔");
        }
        try {
            dto.setId(id);
            return empresaMapper.paraEmpresaDTO(empresaRepository.save(empresaMapper.paraEmpresaEntity(dto)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao editar a Empresa. 🙁", e);
        }
    }

    public void deletarEmpresa(Long id) {
        if (id == null || String.valueOf(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada. ⛔");
        }
        try {
            empresaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao deletar Empresa. 🙁", e);
        }
    }

}
