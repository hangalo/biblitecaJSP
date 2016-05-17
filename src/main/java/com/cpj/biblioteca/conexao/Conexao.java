/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author toshiba
 */
public class Conexao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String HOST = "localhost";
    private static final String SCHEMA = "biblioteca_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://" + HOST + "/" + SCHEMA;
    
    private static Connection conexao;
    
    public static Connection criarConexao() throws ClassNotFoundException, SQLException {
        if(conexao == null || conexao.isClosed()) {
            Class.forName(DRIVER);            
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conexao;
    }
    
    public static void fecharConexao() throws SQLException {
        if(!conexao.isClosed()) {
            conexao.close();
        }
    }
    
    public static void fecharTudo(PreparedStatement preparedStatement) throws SQLException {
        if(preparedStatement != null && !preparedStatement.isClosed()) {
            preparedStatement.close();
        }
        fecharConexao();
    }
    
    private static void fecharResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }
    
    public static void fecharTudo(PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        fecharResultSet(resultSet);
        fecharTudo(preparedStatement);
    }
}
