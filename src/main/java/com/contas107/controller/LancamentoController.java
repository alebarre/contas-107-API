package com.contas107.controller;

import java.math.BigDecimal;
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

import com.contas107.DTO.LancamentoRequestDTO;
import com.contas107.DTO.LancamentoResponseDTO;
import com.contas107.service.LancamentoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
@Tag(name = "Lançamentos", description = "Gerenciamento de Lançamentos")
public class LancamentoController {
	
	@Autowired
	private final LancamentoService lancamentoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Criar um novo lançamento", description = "Adiciona um novo lançamento ao sistema.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Lançamento criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o lançamento"),
		@ApiResponse(responseCode = "500", description = "Erro ao criar o lançamento")
	})
	public LancamentoResponseDTO salvar(@RequestBody LancamentoRequestDTO lancamento) {
        return lancamentoService.salvarLancamento(lancamento);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Atualizar um lançamento", description = "Atualiza os dados de um lançamento existente pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Lançamento atualizado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lançamento não encontrado"),
		@ApiResponse(responseCode = "500", description = "Erro ao atualizar o lançamento")
	})
	public LancamentoResponseDTO atualizar(@PathVariable Long id, @RequestBody LancamentoRequestDTO lancamento) {
		// Verifica se o lançamento existe antes de tentar atualizá-lo
		if (lancamentoService.obterLancamentoPorId(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + id + " não encontrado.");
		}

		// Atualiza o lançamento
		lancamento.setId(id); // Define o ID do lançamento a ser atualizado
		
		LancamentoResponseDTO lancamentoAtualizado = lancamentoService.atualizarLancamento(lancamento);
		if (lancamentoAtualizado == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar o lançamento.");
		}	
		return lancamentoAtualizado;
		
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Listar todos os lançamentos", description = "Retorna uma lista de todos os lançamentos cadastrados.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos retornada com sucesso"),
		@ApiResponse(responseCode = "500", description = "Erro ao listar os lançamentos")
	})
	public List<LancamentoResponseDTO> listarTodos () {
		return lancamentoService.obterTodosLancamentos();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Obter um lançamento por ID", description = "Retorna os detalhes de um lançamento específico pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lançamento encontrado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
	})
	public LancamentoResponseDTO obterLancamentoPorId(@PathVariable Long id) {
		return lancamentoService.obterLancamentoPorId(id);
	}

	@GetMapping("/atrasados")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Listar lançamentos atrasados", description = "Retorna uma lista de lançamentos que estão atrasados.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos atrasados retornada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Nenhum lançamento atrasado encontrado")
	})
	public List<LancamentoResponseDTO> obterLancamentosAtrasados() {
		List<LancamentoResponseDTO> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado");
		}
		return lancamentoService.obterLancamentosAtrasados();
	}

	@GetMapping("/banco/{idBanco}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Listar lançamentos por banco", description = "Retorna uma lista de lançamentos associados a um banco específico pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos retornada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Nenhum lançamento encontrado para o banco especificado")
	})
	public List<LancamentoResponseDTO> obterLancamentosPorBanco(@PathVariable Long idBanco) {
		List<LancamentoResponseDTO> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
		}
		List<LancamentoResponseDTO> retorno = lancamentoService.obterLancamentosPorBanco(idBanco);
		if (retorno.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
		}
		return retorno;
	}

	@GetMapping("/banco/{idBanco}/atrasados")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Listar lançamentos atrasados por banco", description = "Retorna uma lista de lançamentos atrasados associados a um banco específico pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos atrasados retornada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Nenhum lançamento atrasado encontrado para o banco especificado")
	})
	public List<LancamentoResponseDTO> obterLancamentosAtrasadosPorBanco(@PathVariable Long idBanco) {
		List<LancamentoResponseDTO> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
		}
		return lancamentoService.obterLancamentosAtrasadosPorBanco(idBanco); 
	}

	@GetMapping("/mes/{mes}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Listar lançamentos por mês", description = "Retorna uma lista de lançamentos associados a um mês específico.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos retornada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Nenhum lançamento encontrado para o mês especificado")
	})
	public List<LancamentoResponseDTO> obterLancamentosPorMes(@PathVariable int mes) {
		List<LancamentoResponseDTO> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
		}
		return lancamentoService.obterLancamentosPorMes(mes);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletar um lançamento", description = "Remove um lançamento existente pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Lançamento deletado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
	})
	public void deletarLancamento(@PathVariable Long id) {
		lancamentoService.deletarLancamento(id);
	}

	@GetMapping("/totalmes/{mes}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Total pago no mês", description = "Retorna uma lista de lançamentos associados a um mês específico e o total do mês.")	
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de lançamentos retornada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Nenhum lançamento encontrado para o mês especificado")
	})
	public BigDecimal obterLancamentosPorMesComTotal(@PathVariable int mes) {
		List<LancamentoResponseDTO> lancamentos = lancamentoService.obterTodosLancamentos();
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
		}
		return lancamentoService.obterTotalGastoNoMes(mes);
	}
	
}
