package com.contas107.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.model.Banco;
import com.contas107.repository.BancoRepository;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    public List<Banco> obterTodosBancos(){
        return bancoRepository.findAll();
    }

    public Banco salvarBanco(@RequestBody Banco banco) {
		try {
			return bancoRepository.save(banco);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Banco. 🙁", e);
		}
	}

}
