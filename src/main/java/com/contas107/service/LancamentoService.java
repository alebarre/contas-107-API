package com.contas107.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Calendar;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.contas107.model.Lancamento;
import com.contas107.repository.LancamentoRepository;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Lancamento> obterTodosLancamentos(){
        List <Lancamento> retorno = lancamentoRepository.findAll();
        if (retorno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado. 🙁");
        }
        return retorno;
    }

    public Lancamento obterLancamentoPorId(Long id) {
        return lancamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + id + " não encontrado. ⛔"));
    }   

    public Lancamento salvarLancamento(@RequestBody Lancamento lancamento) {
        try {
            lancamento.setDataLancamento(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            return lancamentoRepository.save(lancamento);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar o Lançamento. 🙁", e);
        }
    }

    public Lancamento atualizarLancamento(Lancamento lancamento) {
       
        Lancamento lancamentoExistente = obterLancamentoPorId(lancamento.getId());
        if (lancamentoExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento com o ID: " + lancamento.getId() + " não encontrado.");
        }
        lancamentoExistente.setValor(lancamento.getValor());
        lancamentoExistente.setObservacao(lancamento.getObservacao());
        lancamentoExistente.setEmpresa(lancamento.getEmpresa());
        lancamentoExistente.setBanco(lancamento.getBanco());
        lancamentoExistente.setDataLancamento(lancamento.getDataLancamento());
        lancamentoExistente.setAtraso(lancamento.getAtraso());
        lancamentoExistente.setDataVencimento(lancamento.getDataVencimento());
        return lancamentoRepository.save(lancamentoExistente);
                   
    }
    
    public List<Lancamento> obterLancamentosAtrasados() {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado com atraso.");
        }
        return lancamentos.stream()
                .filter(lancamento -> lancamento.getAtraso())
                .toList();
    }

    public List<Lancamento> obterLancamentosPorBanco(Long idBanco) {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
        }
        return lancamentos.stream()
                .filter(lancamento -> lancamento.getBanco().getId().equals(idBanco))
                .toList();  
    }

    public List<Lancamento> obterLancamentosAtrasadosPorBanco(Long idBanco) {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o banco com ID: " + idBanco);
        }
        return lancamentos.stream()
                .filter(lancamento -> lancamento.getBanco().getId().equals(idBanco) && lancamento.getAtraso())
                .toList();
    }

    public void deletarLancamento(Long id) {
        Lancamento lancamento = obterLancamentoPorId(id);
        if (!lancamentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar o Lançamento. 🙁");
        }
        lancamentoRepository.delete(lancamento);
    }

    public List<Lancamento> obterLancamentosPorMes(int mes) {
        List<Lancamento> lancamentos = lancamentoRepository.lancamentosNoMes(mes);
        if (lancamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum lançamento encontrado para o mês: " + mes);
        }
        return lancamentos;
    }
}
