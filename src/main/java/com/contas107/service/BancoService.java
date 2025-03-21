package com.contas107.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contas107.model.Banco;
import com.contas107.repository.BancoRepository;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRespository;

    public List<Banco> obterTodosBancos(){
        return bancoRespository.findAll();
    }

}
