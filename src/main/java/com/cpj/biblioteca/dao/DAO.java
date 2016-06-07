package com.cpj.biblioteca.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author Chandimba
 */
public interface DAO<T> {
    public boolean salvar(T entidade) ;
    public boolean editar(T entidade)  ;
    public boolean excluir(T entidade)  ;
    public T buscarPeloCodigo(T entidade) ;
    public List<T> buscarTudo()  ;
}
