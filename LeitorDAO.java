package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
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
 * @author Chandimba
 */
public class LeitorDAO implements DAO<Leitor> {

    //private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    Connection conexao;

    public LeitorDAO(){
        conexao = Conexao.criarConexao();
    }
    
    /**************************************************************************************
    * Objectivo: Inserir um novo  Leitor                                                  *
    * Parametros: O Leitor carregado                                                      *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean salvar(Leitor leitor) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO leitor(nome_leitor, sobrenome_leitor, data_nascimento, data_inscricao, bairro_leitor, rua_leitor, casa_leitor, id_municipio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, leitor.getNome());
        prepareStatement.setString(2, leitor.getSobrenome());
        prepareStatement.setDate(3, new Date(leitor.getDataNascimento().getTime()));
        prepareStatement.setDate(4, new Date(leitor.getDataInscricao().getTime()));
        prepareStatement.setString(5, leitor.getBairro());
        prepareStatement.setString(6, leitor.getRua());
        prepareStatement.setString(7, leitor.getCasa());
        prepareStatement.setLong(8, leitor.getMunicipio().getCodigo());
        return prepareStatement.executeUpdate() > 0;

    }

    /**************************************************************************************
    * Objectivo: Atualizar Dados na tabela Leitor                                         *
    * Parametros: O Objecto Leitor carregado                                              *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean editar(Leitor leitor) throws ClassNotFoundException, SQLException {

        String sql = "UPDATE leitor SET nome_leitor = ?, sobrenome_leitor = ?, data_nascimento = ?, data_inscricao = ?, bairro_leitor = ?, rua_leitor = ?, casa_leitor = ?, id_municipio = ? "
                + "WHERE id_leitor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
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

    }

    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Leitor                                        *
    * Parametros: O Objecto Leitor carregado                                              *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Leitor leitor) throws ClassNotFoundException, SQLException {
        return excluir(leitor.getCodigo());
    }

    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Leitor                                        *
    * Parametros: O codigo do leitor que se deseja eliminar                               *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM leitor WHERE id_leitor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long)codigo);
        return prepareStatement.executeUpdate() > 0;

    }

    /**************************************************************************************
    * Objectivo: Carreegar os dados da tabela Leitor                                      *
    * Parametros: O ResultSet                                                             *
    * Devolve: Objecto Leitor carregado                                                   *
    * Autor: Adelino Eduardo                                                              *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
      private Leitor carregarDados(ResultSet resultSet) throws SQLException{
        Municipio municipio = new Municipio();
        Leitor leitor = new Leitor();
               
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
        
        return leitor;
    }
      
    /**************************************************************************************
    * Objectivo: Pesquisar um Dado na tabela Leitor                                       *
    * Parametros: O código do Leitor a ser localizado                                     *
    * Devolve: O Leitor solicitada, carregado ou vazio                                    *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public Leitor buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {

        String sql = "SELECT l.*, m.* FROM leitor l, municipio m where l.id_municipio = m.id_municipio AND id_leitor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long) codigo);

        resultSet = prepareStatement.executeQuery();
        Leitor leitor = null;
        if (resultSet.next()) 
            leitor = carregarDados(resultSet);

        resultSet.close();
        return leitor;

    }

    /**************************************************************************************
    * Objectivo: Localizar todos os dados da tabela Leitor                                *
    * Parametros: Nenhum                                                                  *
    * Devolve: A lista de Leitores carregado ou vazio                                     *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public List<Leitor> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    /**************************************************************************************
    * Objectivo: Filtrar os dados da tabela Leitor                                        *
    * Parametros: A linha inicial e o total de linhas a ser devolvido                     *
    * Devolve: A lista de Leitores, carregado ou vazio                                       *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    public List<Leitor> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<Leitor> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM leitor ";
        if (filtroActivo) {
            sql = sql + " LIMIT ?, ?";
        }

        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        if (filtroActivo) {
            prepareStatement.setLong(1, linhaInicial);
            prepareStatement.setLong(2, totalDeLinhas);
        }

        resultSet = prepareStatement.executeQuery();

        List<Leitor> autores = new ArrayList<>();
        if (resultSet.next()) 
            autores.add(carregarDados(resultSet));

        resultSet.close();
        return autores;

    }
}
