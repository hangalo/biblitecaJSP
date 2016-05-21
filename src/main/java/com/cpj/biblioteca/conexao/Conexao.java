package com.cpj.biblioteca.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandimba
 */
public class Conexao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String HOST = "localhost";
    private static final String SCHEMA = "biblioteca_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://" + HOST + "/" + SCHEMA;
    
    private static Connection conexao;
    
    public static Connection criarConexao() {
        try {
            if(conexao == null || conexao.isClosed()) {
                Class.forName(DRIVER);
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException |SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
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
