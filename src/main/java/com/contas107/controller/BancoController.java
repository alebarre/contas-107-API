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
import com.contas107.service.BancoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/bancos")
@AllArgsConstructor
public class BancoController {

	@Autowired
	private final BancoService bancoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Banco salvar(@RequestBody Banco banco) {
		return bancoService.salvarBanco(banco);
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Banco> listarTodos () {
		return bancoService.obterTodosBancos();
	}
	
}
