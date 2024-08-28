package com.backend.clinica.repository.impl;

import com.backend.clinica.entity.Turno;
import com.backend.clinica.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TurnoDaoH2 implements IDao <Turno> {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoDaoH2.class);

    @Override
    public Turno registrar(Turno turno) {
        return null;
    }

    @Override
    public Turno buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<Turno> listarTodos() {
        return List.of();
    }
}
