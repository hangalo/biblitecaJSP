/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author toshiba
 */
public interface DAO<T> {
    public boolean salvar(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean editar(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean excluir(T entidade) throws ClassNotFoundException, SQLException ;
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException ;
    public T buscarPeloCodigo(Serializable codigo)throws ClassNotFoundException, SQLException ;
    public List<T> buscarTudo() throws ClassNotFoundException, SQLException ;
}
