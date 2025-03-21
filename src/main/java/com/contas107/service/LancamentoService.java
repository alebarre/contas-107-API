package com.contas107.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contas107.model.Lancamento;
import com.contas107.repository.LancamentoRepository;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Lancamento> obterTodosLancamentos(){
        return lancamentoRepository.findAll();
    }

}
