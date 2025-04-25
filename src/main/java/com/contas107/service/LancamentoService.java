package com.contas107.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.DTO.BancoDTO;
import com.contas107.DTO.LancamentoRequestDTO;
import com.contas107.DTO.LancamentoResponseDTO;
import com.contas107.DTO.TotalEmpresaDTO;
import com.contas107.mapstruct.LancamentoMapper;
import com.contas107.model.Banco;
import com.contas107.model.Lancamento;
import com.contas107.repository.BancoRepository;
import com.contas107.repository.LancamentoRepository;
import com.mysql.cj.util.StringUtils;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private LancamentoMapper lancamentoMapper;

    public List<LancamentoResponseDTO> obterTodosLancamentos(){
        List <LancamentoResponseDTO> retorno = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.findAll());
        if (retorno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado. 🙁");
        }
        return retorno;
    }

    public LancamentoResponseDTO obterLancamentoPorId(Long id) {
        LancamentoResponseDTO retorno = lancamentoMapper.paraLancamentoResponseDTO(lancamentoRepository.findById(id).orElse(null));
        if (retorno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + id + " não encontrado.");
        }
        return retorno;
    }   

    public LancamentoResponseDTO salvarLancamento(@RequestBody LancamentoRequestDTO lancamento) {
        if (lancamento.getBanco() == null 
            || lancamento.getBanco().getId() == null 
            || bancoRepository.findById(lancamento.getBanco().getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Banco não encontrado. ⛔");
        }

        if (lancamento.getDataLancamento() == null || StringUtils.isNullOrEmpty(lancamento.getDataLancamento().toString())) {
            lancamento.setDataLancamento(LocalDate.now(ZoneId.of("UTC"))); 
        }

        LancamentoResponseDTO retorno = lancamentoMapper.paraLancamentoResponseDTO(lancamentoRepository.save(lancamentoMapper.paraLancamentoEntity(lancamento)));
        if (retorno == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar o Lançamento. 🙁");
        }
        return retorno;

    }

    public LancamentoResponseDTO atualizarLancamento(LancamentoRequestDTO lancamento) {
       
        LancamentoResponseDTO lancamentoExistente = lancamentoRepository.findById(lancamento.getId())
                .map(lancamentoMapper::paraLancamentoResponseDTO)
                .orElse(null);
        if (lancamentoExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + lancamento.getId() + " não encontrado.");
        }

        lancamentoExistente.setValor(lancamento.getValor());
        lancamentoExistente.setObservacao(lancamento.getObservacao());
        lancamentoExistente.setEmpresa(lancamento.getEmpresa());
        lancamentoExistente.setBanco(lancamento.getBanco());
        lancamentoExistente.setDataLancamento(lancamento.getDataLancamento());
        lancamentoExistente.setDataVencimento(lancamento.getDataVencimento());
        lancamentoExistente.setAtraso(lancamento.getAtraso());
        lancamentoExistente.setId(lancamento.getId());

        LancamentoResponseDTO retorno = lancamentoMapper.paraLancamentoResponseDTO(lancamentoRepository.save(lancamentoMapper.paraLancamentoEntityRespDTO(lancamentoExistente)));    
        if (retorno == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar o Lançamento. 🙁");
        }
        return retorno;
                   
    }
    
    public List<LancamentoResponseDTO> obterLancamentosAtrasados() {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.findAll());
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado.");
        }
        List<LancamentoResponseDTO> retorno = lancamentos.stream()
                .filter(lancamento -> lancamento.getAtraso())
                .toList();
        if (retorno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado com atraso.");
        }
        return retorno;
    }

    public List<LancamentoResponseDTO> obterLancamentosPorBanco(Long idBanco) {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.findAll());
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
        }
        return lancamentos.stream()
                .filter(lancamento -> lancamento.getBanco().getId().equals(idBanco))
                .toList();  
    }

    public List<LancamentoResponseDTO> obterLancamentosAtrasadosPorBanco(Long idBanco) {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.findAll());
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
        }
        List<LancamentoResponseDTO> retorno = lancamentos.stream()
                .filter(lancamento -> lancamento.getBanco().getId().equals(idBanco) && lancamento.getAtraso())
                .toList();
        if (retorno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco + " com atraso.");
        }
        return retorno;
    }

    public void deletarLancamento(Long id) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + id + " não encontrado.");
        }   
        if (!lancamentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar o Lançamento. 🙁");
        }
        lancamentoRepository.delete(lancamento);
    }

    public List<LancamentoResponseDTO> obterLancamentosPorMes(int mes) {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.lancamentosNoMes(mes));
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
        }
        return lancamentos;
    }

    public BigDecimal obterTotalGastoNoMes(int mes) {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.lancamentosNoMes(mes));
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
        }
        BigDecimal totalMes = lancamentos.stream()
            .map(LancamentoResponseDTO::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalMes;
    }

    public BigDecimal obterTotalGastoPorBancoNoMes(List<LancamentoResponseDTO> lancamento, Long idBanco, int mes) {
        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.lancamentosNoMes(mes));
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
        }
        List<LancamentoResponseDTO> lancamentosBanco = lancamentos.stream()
            .filter(lancamentoBanco -> lancamentoBanco.getBanco().getId().equals(idBanco))
            .toList();
        if (lancamentosBanco.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco + " no mês: " + mes);
        }
        BigDecimal totalBanco = lancamentosBanco.stream()
            .map(LancamentoResponseDTO::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalBanco;
        
    }

    public TotalEmpresaDTO obterTotalGastoPorEmpresa(Long idEmpresa) {

        List<LancamentoResponseDTO> lancamentos = lancamentoMapper.paraLISTLancamentoResponseDTO(lancamentoRepository.findAll());
		
		// Compoe lista com empresas iguais a passada por parametro
		lancamentos = lancamentos.stream()
				.filter(lancamento -> lancamento.getEmpresa().getId().equals(idEmpresa))
				.toList();

		// Se a lista "lancamentos" estiver vazia, lança uma exceção
		if (lancamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado. ⛔");
		}

		// Se a lista "lancamentos" não estiver vazia, retorna o total gasto por empresa
	    		BigDecimal totalGasto = lancamentos.stream()
				.map(LancamentoResponseDTO::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
        
        TotalEmpresaDTO retorno = new TotalEmpresaDTO();
        
        retorno.setNome(lancamentos.get(0).getEmpresa().getNome());
        retorno.setTotal(totalGasto);
		
		return retorno;	  

    }

}
