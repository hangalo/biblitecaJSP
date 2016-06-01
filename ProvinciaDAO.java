package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Provincia;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class ProvinciaDAO implements DAO<Provincia>{
    private ResultSet resultSet;
    Connection conexao;
    
    public ProvinciaDAO()
    {
        conexao = Conexao.criarConexao();
    }
       
    /**************************************************************************************
    * Objectivo: Inserir uma nova provincia                                               *
    * Parametros: A provincia carregada                                                   *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean salvar(Provincia provincia) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO provincia(nome_provincia) VALUES (?)";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, provincia.getNome());
        return prepareStatement.executeUpdate() > 0;
    }
    
    /**************************************************************************************
    * Objectivo: Atualizar os Dados na tabela Provincia                                   *
    * Parametros: A provincia alterada                                                    *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean editar(Provincia provincia) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE provincia SET nome_provincia = ? WHERE id_provincia = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, provincia.getNome());
        prepareStatement.setLong(2, provincia.getCodigo());
        return prepareStatement.executeUpdate() > 0;
    }
    
    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Provincia                                     *
    * Parametros: O Objecto provincia carregado                                           *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Provincia provincia) throws ClassNotFoundException, SQLException {
        return excluir(provincia.getCodigo());
    }
 
    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Provincia                                     *
    * Parametros: O código da provincia a ser eliminado                                   *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM provincia WHERE id_provincia = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long)codigo);
        return prepareStatement.executeUpdate() > 0;
            
    }
    
    /**************************************************************************************
    * Objectivo: Carregar os dados da tabela Provincia                                    *
    * Parametros: O ResultSet                                                             *
    * Devolve: A Provincia carregada                                                      *
    * Autor: Adelino Eduardo                                                              *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    private Provincia carregarDados(ResultSet resultSet) throws SQLException{
        Provincia provincia = new Provincia();
               
        provincia.setCodigo(resultSet.getLong("id_provincia"));
        provincia.setNome(resultSet.getString("nome_provincia"));
        
        return provincia;
    }
    
    /**************************************************************************************
    * Objectivo: Pesquisar um Dado na tabela Provincia                                    *
    * Parametros: O código da provincia a ser localizado                                  *
    * Devolve: A Provincia solicitada, carregado ou vazio                                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public Provincia buscarPeloCodigo(Serializable codigo) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM provincia WHERE id_provincia = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long)codigo);

        resultSet = prepareStatement.executeQuery();
        Provincia provincia = null;

        if (resultSet.next())
            provincia = carregarDados(resultSet);
        
        resultSet.close();
        return provincia;

    }

    /**************************************************************************************
    * Objectivo: Filtrar os dados da tabela Provincia                                     *
    * Parametros: A linha inicial e o total de linhas a ser devolvido                     *
    * Devolve: A Provincia pesquisada caso exista ou null caso contrario                  *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    private List<Provincia> aplicarFiltro(Long linhaInicial, Long totalLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM provincia";
        if (filtroActivo)
            sql = sql + " LIMIT ?, ?";
        
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        if (filtroActivo){
            preparedStatement.setLong(1, linhaInicial);
            preparedStatement.setLong(2, totalLinhas);
        }
        
        resultSet = preparedStatement.executeQuery();
        List<Provincia> provincias = new ArrayList<>();
        while(resultSet.next())
        {
            provincias.add(carregarDados(resultSet));
        }
        resultSet.close();
        return provincias;
    }    
    
    /**************************************************************************************
    * Objectivo: Filtrar os dados da tabela Provincia                                     *
    * Parametros: A linha inicial e o total de linhas a ser devolvido                     *
    * Devolve: A lista de Provincias, carregado ou vazio                                  *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    public List<Provincia> filtrar(Long linhaInicial, Long totalLinhas) throws ClassNotFoundException, SQLException{
        return aplicarFiltro(linhaInicial, totalLinhas, true);
    }

    /**************************************************************************************
    * Objectivo: Localizar todos os dados da tabela Provincia                             *
    * Parametros: Nenhum                                                                  *
    * Devolve: A lista de Provincias, carregado ou vazio                                  *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public List<Provincia> buscarTudo() throws SQLException, ClassNotFoundException {
        return aplicarFiltro(0L, 0L, false);
    }
}
