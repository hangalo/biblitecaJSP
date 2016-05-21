package com.cpj.biblioteca.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author Chandimba
 */
public interface DAO<T> {
    public boolean salvar(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean editar(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean excluir(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException ;
    public T buscarPeloCodigo(Serializable codigo)throws ClassNotFoundException, SQLException ;
    public List<T> buscarTudo() throws ClassNotFoundException, SQLException ;
}
