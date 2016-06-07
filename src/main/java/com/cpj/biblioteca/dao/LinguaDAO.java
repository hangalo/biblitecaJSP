package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Leitor;
import com.cpj.biblioteca.modelo.Lingua;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chandimba
 */
public class LinguaDAO implements DAO<Lingua> {

    private static final String INSERIR = "INSERT INTO lingua(nome_lingua) VALUES (?)";
    private static final String ACTUALISAR = "UPDATE lingua SET nome_lingua = ? WHERE id_lingua = ?";
    private static final String LISTA_TUDO = "SELECT * FROM lingua";
    private static final String BUSCA_POR_CODIGO = "SELECT * FROM lingua WHERE id_lingua = ?";
    private static final String ELIMINAR = "DELETE FROM lingua WHERE id_lingua = ?";
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    
    private Connection con;
    
    public LinguaDAO() {
        con = Conexao.criarConexao();
    }

   
    @Override
    public boolean salvar(Lingua lingua) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            
            ps.setString(1, lingua.getNome());
           
                 
            sucesso = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir dados" + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
        }
        return sucesso;
    }
    
    @Override
    public boolean editar(Lingua lingua) {
         boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setString(1, lingua.getNome());
            ps.setLong(2, lingua.getCodigo());
            sucesso = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir dados" + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
        }
        return sucesso;
    }
    
    @Override
    public boolean excluir(Lingua lingua) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, lingua.getCodigo());
            sucesso = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao actualizar dados" + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
        }
        return sucesso;
    }
    
    @Override
    public Lingua buscarPeloCodigo(Lingua lingua) {
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, lingua.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(lingua, rs);
            
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar dados da editora"+ex.getMessage());
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
               
            } catch (SQLException e) {
                System.err.println("Erro ao desalocar recursos"+e.getMessage());
            }
            }
        
        return lingua;
    }
    
    public List<Lingua> buscarTudo(){
    List<Lingua> linguas = new ArrayList<>();
    try{
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(LISTA_TUDO);
     while(rs.next()){
     Lingua lingua = new Lingua();
         popularComDados(lingua, rs);
         linguas.add(lingua);
     
     
     }
    st.close();
    rs.close();
    }catch(SQLException ex){
        System.err.println("Erro ao recupera dados da Editora"+ex.getMessage());
    }
    
    return linguas;
    }
    
    
    private void popularComDados(Lingua lingua, ResultSet rs) {
        try {

            lingua.setCodigo(rs.getLong("id_lingua"));
                lingua.setNome(rs.getString("nome_lingua"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
    
    
}
