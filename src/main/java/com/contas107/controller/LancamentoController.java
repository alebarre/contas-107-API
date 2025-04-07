package com.contas107.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.model.Lancamento;
import com.contas107.service.LancamentoService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
public class LancamentoController {
	
	@Autowired
	private final LancamentoService lancamentoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Lancamento salvar(@RequestBody Lancamento lancamento) {
        return lancamentoService.salvarLancamento(lancamento);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Lancamento putMethodName(@PathVariable Long id, @RequestBody Lancamento lancamento) {
		// Verifica se o lançamento existe antes de tentar atualizá-lo
		if (lancamentoService.obterLancamentoPorId(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + lancamento.getId() + " não encontrado.");
		}

		// Atualiza o lançamento
		lancamento.setId(id); // Define o ID do lançamento a ser atualizado
		lancamentoService.atualizarLancamento(lancamento);
		
		// Retorna o lançamento atualizado
		return lancamentoService.obterLancamentoPorId(id);
		
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> listarTodos () {
		return lancamentoService.obterTodosLancamentos();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Lancamento obterLancamentoPorId(@PathVariable Long id) {
		return lancamentoService.obterLancamentoPorId(id);
	}

	@GetMapping("/atrasados")
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> obterLancamentosAtrasados() {
		List<Lancamento> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado");
		}
		return lancamentoService.obterLancamentosAtrasados();
	}

	@GetMapping("/banco/{idBanco}")
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> obterLancamentosPorBanco(@PathVariable Long idBanco) {
		List<Lancamento> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
		}
		return lancamentoService.obterLancamentosPorBanco(idBanco);
	}

	@GetMapping("/banco/{idBanco}/atrasados")
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> obterLancamentosAtrasadosPorBanco(@PathVariable Long idBanco) {
		List<Lancamento> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
		}
		return lancamentoService.obterLancamentosAtrasadosPorBanco(idBanco); 
	}

	@GetMapping("/mes/{mes}")
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> obterLancamentosPorMes(@PathVariable int mes) {
		List<Lancamento> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
		}
		return lancamentoService.obterLancamentosPorMes(mes);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarLancamento(@PathVariable Long id) {
		lancamentoService.deletarLancamento(id);
	}
	
}
