package com.contas107.controller;

import com.contas107.DTO.BancoDTO;
import com.contas107.DTO.EmpresaDTO;
import com.contas107.service.BancoService;
import com.contas107.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaDTO salvar(@RequestBody EmpresaDTO dto) {
        return empresaService.salvarEmpresa(dto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EmpresaDTO> listarTodasEmpresas () {
        return empresaService.obterTodasEmpresas();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpresaDTO editarempresa(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não pode ser nulo. ⛔");
        }
        return empresaService.editarEmpresa(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
    }

}
