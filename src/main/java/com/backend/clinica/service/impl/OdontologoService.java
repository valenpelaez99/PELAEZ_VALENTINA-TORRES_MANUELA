package com.backend.clinica.service.impl;

import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.service.IOdontologoService;
import com.backend.clinica.repository.IDao;

import java.util.List;

public class OdontologoService implements IOdontologoService {

    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {

        this.odontologoIDao = odontologoIDao;
    }
    @Override
    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoIDao.registrar(odontologo);
    }

    @Override
    public Odontologo buscarOdontologoPorId(Long id) {
        return odontologoIDao.buscarPorId(id);
    }
}
