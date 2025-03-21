package com.contas107.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.model.Banco;
import com.contas107.repository.BancoRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/bancos")
@AllArgsConstructor
public class BancoController {

	@Autowired
	private final BancoRepository bancoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Banco salvar(@RequestBody Banco banco) {
            try {
                return bancoRepository.save(banco);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Banco. 🙁", e);
            }
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Banco> listarTodos () {
		return bancoRepository.findAll();
	}
	
}
