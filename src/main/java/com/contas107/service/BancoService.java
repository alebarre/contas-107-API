package com.contas107.service;

import java.util.List;

import org.antlr.v4.runtime.misc.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.DTO.BancoDTO;
import com.contas107.mapstruct.BancoMapper;
import com.contas107.model.Banco;
import com.contas107.repository.BancoRepository;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private BancoMapper bancoMapper;

    public List<BancoDTO> obterTodosBancos(){
        return bancoMapper.paraLISTBancoDTO(bancoRepository.findAll());
    }

    public BancoDTO salvarBanco(@RequestBody BancoDTO banco) {
		try {
			return bancoMapper.paraBancoDTO(bancoRepository.save(bancoMapper.paraBancoEntity(banco)));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Banco. 🙁", e);
		}
	}

    public BancoDTO editarBancoDTO(@RequestBody BancoDTO banco) {
        if (banco.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Banco não encontrado. ⛔");
        }
        try {
            return bancoMapper.paraBancoDTO(bancoRepository.save(bancoMapper.paraBancoEntity(banco)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao editar o Banco. 🙁", e);
        }
    }

    public void deletarbanco(Long id) {
        if (id == null || String.valueOf(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Banco não encontrado. ⛔");
        }
        try {
            bancoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao deletar o Banco. 🙁", e);
        }
    }
}
