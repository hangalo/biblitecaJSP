package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Municipio;
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
public class MunicipioDAO implements DAO<Municipio>{
    //private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    Connection conexao;

    
    public MunicipioDAO(){
        conexao = Conexao.criarConexao();
    }
    
    /**************************************************************************************
    * Objectivo: Inserir Dados na tabela Municipio                                        *
    * Parametros: O Objecto Municipio carregado                                           *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean salvar(Municipio municipio) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO municipio(nome_municipio, id_provincia) VALUES (?, ?)";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, municipio.getNome());
        prepareStatement.setLong(2, municipio.getProvincia().getCodigo());
        return prepareStatement.executeUpdate() > 0;

    }

    /**************************************************************************************
    * Objectivo: Atualizar Dados na tabela Municipio                                      *
    * Parametros: O Objecto Municipio carregado                                           *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean editar(Municipio municipio) throws ClassNotFoundException, SQLException {

        String sql = "UPDATE municipio SET nome_municipio = ?, id_provincia = ? WHERE id_municipio = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, municipio.getNome());
        prepareStatement.setLong(2, municipio.getProvincia().getCodigo());
        prepareStatement.setLong(3, municipio.getCodigo());
        return prepareStatement.executeUpdate() > 0;

    }

    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Municipio                                     *
    * Parametros: O Objecto provincia carregado                                           *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Municipio municipio) throws ClassNotFoundException, SQLException {
        return excluir(municipio.getCodigo());
    }

    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Municipio                                     *
    * Parametros: O código da provincia a ser eliminado                                   *
    * Devolve: Verdadeiro caso execute com sucesso e Falso caso contrário                 *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM municipio WHERE id_municipio = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long)codigo);
        return prepareStatement.executeUpdate() > 0;

    }
    
    /**************************************************************************************
    * Objectivo: Carregar os dados da tabela Municipio                                    *
    * Parametros: O ResultSet                                                             *
    * Devolve: Objecto Municipio carregado                                                *
    * Autor: Adelino Eduardo                                                              *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    private Municipio carregarDados(ResultSet resultSet) throws SQLException{
        Municipio municipio = new Municipio();
        Provincia provincia = new Provincia();
               
        municipio.setCodigo(resultSet.getLong("id_municipio"));
        municipio.setNome(resultSet.getString("nome_municipio"));
                              
        provincia.setCodigo(resultSet.getLong("id_provincia"));
        provincia.setNome(resultSet.getString("nome_provincia"));
        
        municipio.setProvincia(provincia);
        return municipio;
    }
      
    /**************************************************************************************
    * Objectivo: Eliminar um Dado na tabela Municipio                                     *
    * Parametros: O código do Municipio a ser localizado                                  *
    * Devolve: Objecto Municipio carregado ou vazio                                       *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public Municipio buscarPeloCodigo(Serializable codigo) throws SQLException, ClassNotFoundException {

        String sql = "SELECT m.*, p.* FROM municipio m, provincia p WHERE m.id_provincia = p.id_provincia and id_municipio = ?";
        
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long)codigo);

        resultSet = prepareStatement.executeQuery();
        Municipio municipio = null;

        if (resultSet.next())
            municipio = carregarDados(resultSet);

        return municipio;

    }

    /**************************************************************************************
    * Objectivo: Localizar todos os dados da tabela Municipio                             *
    * Parametros: Nenhum                                                                  *
    * Devolve: A lista de Municipio carregado ou vazio                                    *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    @Override
    public List<Municipio> buscarTudo() throws SQLException, ClassNotFoundException {
        return aplicarFiltro(0L, 0L, false);
    }
    
    /**************************************************************************************
    * Objectivo: Filtrar os dados da tabela Municipio                                     *
    * Parametros: A linha inicial e o total de linhas a ser devolvido                     *
    * Devolve: Objecto Municipio carregado ou vazio                                       *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    private List<Municipio> aplicarFiltro(Long linhaInicial, Long totalLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM municipio";
        if (filtroActivo)
            sql = sql + " LIMIT ?, ?";
        
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        if (filtroActivo){
            preparedStatement.setLong(1, linhaInicial);
            preparedStatement.setLong(2, totalLinhas);
        }
        
        resultSet = preparedStatement.executeQuery();
        List<Municipio> municipios = new ArrayList<>();
        Municipio municipio = null;
        while (resultSet.next()) { 
            municipio = carregarDados(resultSet);
            municipios.add(municipio);
        }

        resultSet.close();
        return municipios;
    }    
    
    /**************************************************************************************
    * Objectivo: Filtrar os dados da tabela Municipio                                     *
    * Parametros: A linha inicial e o total de linhas a ser devolvido                     *
    * Devolve: Objecto Municipio carregado ou vazio                                       *
    * Autor: Chandimba                                                                    *
    * Alteração: Adelino Eduardo                                                          *
    * Data Alteração: 28/05/2016                                                          *
    **************************************************************************************/
    public List<Municipio> filtrar(Long linhaInicial, Long totalLinhas) throws ClassNotFoundException, SQLException{
        return aplicarFiltro(linhaInicial, totalLinhas, true);
    }

}
