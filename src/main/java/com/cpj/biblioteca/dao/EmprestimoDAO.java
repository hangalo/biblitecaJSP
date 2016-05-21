/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Emprestimo;
import com.cpj.biblioteca.modelo.EmprestimoCodigo;
import com.cpj.biblioteca.modelo.Leitor;
import com.cpj.biblioteca.modelo.Livro;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class EmprestimoDAO implements DAO<Emprestimo> {

    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    @Override
    public boolean salvar(Emprestimo emprestimo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO emprestimo(id_livro, id_leitor, data_emprestimo, data_devolucao, observacoes) VALUES (?, ?, ?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, emprestimo.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(2, emprestimo.getCodigo().getLeitor().getCodigo());
            prepareStatement.setTimestamp(3, new Timestamp(emprestimo.getCodigo().getDataHoraEmprestimo().getTime()));
            prepareStatement.setTimestamp(4, new Timestamp(emprestimo.getDataHoraDevolucao().getTime()));
            prepareStatement.setString(5, emprestimo.getObservacao());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Emprestimo emprestimo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE emprestimo data_devolucao = ?, observacoes =? "
                    + "WHERE id_livro = ?, id_leitor = ?, data_emprestimo = ? ";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setTimestamp(1, new Timestamp(emprestimo.getDataHoraDevolucao().getTime()));
            prepareStatement.setString(2, emprestimo.getObservacao());
            prepareStatement.setLong(3, emprestimo.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(4, emprestimo.getCodigo().getLeitor().getCodigo());
            prepareStatement.setTimestamp(5, new Timestamp(emprestimo.getCodigo().getDataHoraEmprestimo().getTime()));
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Emprestimo emprestimo) throws ClassNotFoundException, SQLException {
        return excluir(emprestimo.getCodigo());
    }

    @Override
    public boolean excluir(Serializable emprestimo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM emprestimo WHERE id_livro = ? AND id_leitor = ? AND data_emprestimo = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, ((EmprestimoCodigo)emprestimo).getLivro().getCodigo());
            prepareStatement.setLong(2, ((EmprestimoCodigo)emprestimo).getLeitor().getCodigo());
            prepareStatement.setDate(3, new Date(((EmprestimoCodigo)emprestimo).getDataHoraEmprestimo().getTime()));
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Emprestimo buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT emprestimo.*, livro.*, leitor.* "
                    + "FROM emprestimo, livro, leitor "
                    + "WHERE emprestimo.id_livro = livro.id_livro AND emprestimo.id_leitor = leitor.id_leitor AND id_livro = ? AND id_leitor = ? AND data_emprestimo = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, ((EmprestimoCodigo) codigo).getLivro().getCodigo());
            prepareStatement.setLong(2, ((EmprestimoCodigo) codigo).getLeitor().getCodigo());
            prepareStatement.setDate(3, new Date(((EmprestimoCodigo) codigo).getDataHoraEmprestimo().getTime()));

            resultSet = prepareStatement.executeQuery();
            Emprestimo emprestimo = new Emprestimo();

            if (resultSet.next()) {
                Livro livro = new Livro();
                Leitor leitor = new Leitor();

                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));

                leitor.setCodigo(resultSet.getLong("leitor.id_leitor"));
                leitor.setNome(resultSet.getString("leitor.nome_leitor"));
                leitor.setSobrenome(resultSet.getString("leitor.sobrenome_leitor"));
                leitor.setDataNascimento(resultSet.getDate("leitor.data_nascimento"));
                leitor.setDataInscricao(resultSet.getDate("leitor.data_inscricao"));
                leitor.setBairro(resultSet.getString("leitor.bairro_leitor"));
                leitor.setCasa(resultSet.getString("leitor.rua_leitor"));
                leitor.setRua(resultSet.getString("leitor.casa_leitor"));

                emprestimo.setCodigo(new EmprestimoCodigo(leitor, livro, resultSet.getDate("data_emprestimo")));
                emprestimo.setDataHoraDevolucao(resultSet.getDate("data_devolucao"));
                emprestimo.setObservacao(resultSet.getString("observacoes"));
            }

            return emprestimo;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Emprestimo> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    public List<Emprestimo> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<Emprestimo> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * "
                    + "FROM emprestimo, livro, leitor "
                    + "WHERE emprestimo.id_livro = livro.id_livro AND emprestimo.id_leitor = leitor.id_leitor ";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<Emprestimo> emprestimos = new ArrayList<>();
            while (resultSet.next()) {
                Emprestimo emprestimo = new Emprestimo();
                Livro livro = new Livro();
                Leitor leitor = new Leitor();

                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));

                leitor.setCodigo(resultSet.getLong("leitor.id_leitor"));
                leitor.setNome(resultSet.getString("leitor.nome_leitor"));
                leitor.setSobrenome(resultSet.getString("leitor.sobrenome_leitor"));
                leitor.setDataNascimento(resultSet.getDate("leitor.data_nascimento"));
                leitor.setDataInscricao(resultSet.getDate("leitor.data_inscricao"));
                leitor.setBairro(resultSet.getString("leitor.bairro_leitor"));
                leitor.setCasa(resultSet.getString("leitor.rua_leitor"));
                leitor.setRua(resultSet.getString("leitor.casa_leitor"));

                emprestimo.setCodigo(new EmprestimoCodigo(leitor, livro, resultSet.getDate("data_emprestimo")));
                emprestimo.setDataHoraDevolucao(resultSet.getDate("data_devolucao"));
                emprestimo.setObservacao(resultSet.getString("observacoes"));

                emprestimos.add(emprestimo);
            }
            return emprestimos;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    public List<Livro> filtrarOsDezLivrosMaisProcurados() throws ClassNotFoundException, SQLException {
        return aplicarFiltroDosDezLivrosMaisProcurados();
    }

    public List<Livro> aplicarFiltroDosDezLivrosMaisProcurados() throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT livro.* "
                    + "FROM emprestimo, livro "
                    + "WHERE emprestimo.id_livro = livro.id_livro "
                    + "LIMIT 10";

            prepareStatement = conexao.prepareStatement(sql);

            resultSet = prepareStatement.executeQuery();

            List<Livro> livros = new ArrayList<>();
            while (resultSet.next()) {
                Livro livro = new Livro();

                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));

                livros.add(livro);
            }
            return livros;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

}
