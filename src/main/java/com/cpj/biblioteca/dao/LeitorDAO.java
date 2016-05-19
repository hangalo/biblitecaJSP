/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.Leitor;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class LeitorDAO implements DAO<Leitor> {

    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    @Override
    public boolean salvar(Leitor leitor) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO leitor(nome_leitor, sobrenome_leitor, data_nascimento, data_inscricao, bairro_leitor, rua_leitor, casa_leitor, id_municipio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, leitor.getNome());
            prepareStatement.setString(2, leitor.getSobrenome());
            prepareStatement.setDate(3, new Date(leitor.getDataNascimento().getTime()));
            prepareStatement.setDate(4, new Date(leitor.getDataInscricao().getTime()));
            prepareStatement.setString(5, leitor.getBairro());
            prepareStatement.setString(6, leitor.getRua());
            prepareStatement.setString(7, leitor.getCasa());
            prepareStatement.setLong(8, leitor.getMunicipio().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Leitor leitor) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE leitor SET nome_leitor = ?, sobrenome_leitor = ?, data_nascimento = ?, data_inscricao = ?, bairro_leitor = ?, rua_leitor = ?, casa_leitor = ?, id_municipio = ? "
                    + "WHERE id_leitor = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, leitor.getNome());
            prepareStatement.setString(2, leitor.getSobrenome());
            prepareStatement.setDate(3, new Date(leitor.getDataNascimento().getTime()));
            prepareStatement.setDate(4, new Date(leitor.getDataInscricao().getTime()));
            prepareStatement.setString(5, leitor.getBairro());
            prepareStatement.setString(6, leitor.getRua());
            prepareStatement.setString(7, leitor.getCasa());
            prepareStatement.setLong(8, leitor.getMunicipio().getCodigo());
            prepareStatement.setLong(8, leitor.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Leitor leitor) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM leitor WHERE id_leitor = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, leitor.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Leitor buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT l.*, m.* FROM leitor l, municipio m where l.id_municipio = m.id_municipio AND id_leitor = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long) codigo);

            resultSet = prepareStatement.executeQuery();
            Leitor leitor = new Leitor();
            if (resultSet.next()) {
                Municipio municipio = new Municipio();

                leitor.setCodigo(resultSet.getLong("id_leitor"));
                leitor.setNome(resultSet.getString("nome_leitor"));
                leitor.setSobrenome(resultSet.getString("sobrenome_leitor"));
                leitor.setDataNascimento(resultSet.getDate("data_nascimento"));
                leitor.setDataInscricao(resultSet.getDate("data_inscricao"));
                leitor.setBairro(resultSet.getString("bairro_leitor"));
                leitor.setCasa(resultSet.getString("rua_leitor"));
                leitor.setRua(resultSet.getString("casa_leitor"));

                municipio.setCodigo(resultSet.getLong("m.id_municipio"));
                municipio.setNome(resultSet.getString("nome_municipio"));
                leitor.setMunicipio(municipio);

            }
            return leitor;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Leitor> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    public List<Leitor> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<Leitor> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM leitor ";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<Leitor> autores = new ArrayList<>();
            if (resultSet.next()) {
                Leitor leitor = new Leitor();
                leitor.setCodigo(resultSet.getLong("id_leitor"));
                leitor.setNome(resultSet.getString("nome_leitor"));
                leitor.setSobrenome(resultSet.getString("sobrenome_leitor"));
                leitor.setDataNascimento(resultSet.getDate("data_nascimento"));
                leitor.setDataInscricao(resultSet.getDate("data_inscricao"));
                leitor.setBairro(resultSet.getString("bairro_leitor"));
                leitor.setCasa(resultSet.getString("rua_leitor"));
                leitor.setRua(resultSet.getString("casa_leitor"));
                leitor.setMunicipio(null);

                autores.add(leitor);
            }
            return autores;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
