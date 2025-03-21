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

import com.contas107.model.Lancamento;
import com.contas107.repository.LancamentoRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
public class LancamentoController {

	@Autowired
	private final LancamentoRepository lancamentoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Lancamento salvar(@RequestBody Lancamento lancamento) {
            try {
                return lancamentoRepository.save(lancamento);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Lançamento. 🙁", e);
            }
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> listarTodos () {
		return lancamentoRepository.findAll();
	}
	
}
