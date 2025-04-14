package com.contas107.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.DTO.BancoDTO;
import com.contas107.model.Banco;
import com.contas107.repository.BancoRepository;
import com.contas107.service.BancoService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/bancos")
@AllArgsConstructor
public class BancoController {

	@Autowired
	private final BancoService bancoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BancoDTO salvar(@RequestBody BancoDTO banco) {
		return bancoService.salvarBanco(banco);
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<BancoDTO> listarTodos () {
		return bancoService.obterTodosBancos();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BancoDTO editar(@PathVariable Long id, @RequestBody BancoDTO banco) {
		if (banco.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Banco não encontrado. ⛔");
		}
		return bancoService.editarBancoDTO(banco);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletar(@PathVariable Long id) {
		bancoService.deletarbanco(id);
	}
	
}
