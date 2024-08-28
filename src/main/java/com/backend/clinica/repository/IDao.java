package com.backend.clinica.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDao<T>{
    T registrar(T t);
    T buscarPorId(Long id);
    List<T> listarTodos();

}