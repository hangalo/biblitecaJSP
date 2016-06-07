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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class LeitorDAO implements DAO<Leitor> {

    private static final String INSERIR = "INSERT INTO leitor(nome_leitor, sobrenome_leitor, data_nascimento, data_inscricao, bairro_leitor, rua_leitor, casa_leitor, id_municipio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE leitor SET nome_leitor = ?, sobrenome_leitor = ?, data_nascimento = ?, data_inscricao = ?, bairro_leitor = ?, rua_leitor = ?, casa_leitor = ?, id_municipio = ? WHERE id_leitor = ?";
    private static final String LISTA_TUDO = "SELECT * FROM leitor";
    private static final String BUSCA_POR_CODIGO = "SELECT l.*, m.* FROM leitor l, municipio m where l.id_municipio = m.id_municipio AND id_leitor = ?";
    private static final String ELIMINAR = "DELETE FROM leitor WHERE id_leitor = ?";


    private Connection con;
    
    public LeitorDAO() {
        con = Conexao.criarConexao();
    }

   
    @Override
    public boolean salvar(Leitor leitor) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            
            ps.setString(1, leitor.getNome());
            ps.setString(2, leitor.getSobrenome());
            ps.setDate(3, new java.sql.Date(leitor.getDataNascimento().getTime()));
             ps.setLong(10, leitor.getMunicipio().getCodigo());
            ps.setString(4, leitor.getBairro());
            ps.setString(5, leitor.getBairro());
            ps.setString(6, leitor.getRua());
            ps.setString(7, leitor.getCasa());
            ps.setDate(8, new java.sql.Date(leitor.getDataInscricao().getTime()));
                 
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
    public boolean editar(Leitor leitor) {
         boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            
            ps.setString(1, leitor.getNome());
            ps.setString(2, leitor.getSobrenome());
            ps.setDate(3, new java.sql.Date(leitor.getDataNascimento().getTime()));
             ps.setLong(10, leitor.getMunicipio().getCodigo());
            ps.setString(4, leitor.getBairro());
            ps.setString(5, leitor.getBairro());
            ps.setString(6, leitor.getRua());
            ps.setString(7, leitor.getCasa());
            ps.setDate(8, new java.sql.Date(leitor.getDataInscricao().getTime()));
            ps.setLong(9, leitor.getCodigo());
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
    public boolean excluir(Leitor leitor) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, leitor.getCodigo());
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
    public Leitor buscarPeloCodigo(Leitor leitor) {
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, leitor.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(leitor, rs);
            
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
        
        return leitor;
    }
    
    public List<Leitor> buscarTudo(){
    List<Leitor> leitores = new ArrayList<>();
    try{
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(LISTA_TUDO);
     while(rs.next()){
     Leitor leitor = new Leitor();
         popularComDados(leitor, rs);
         leitores.add(leitor);
     
     
     }
    st.close();
    rs.close();
    }catch(SQLException ex){
        System.err.println("Erro ao recupera dados da Editora"+ex.getMessage());
    }
    
    return leitores;
    }
    
    
    private void popularComDados(Leitor leitor, ResultSet rs) {
        try {

            leitor.setCodigo(rs.getLong("id_leitor"));
            leitor.setNome(rs.getString("nome_leitor"));
            leitor.setSobrenome(rs.getString("sobrenome_leitor"));
            leitor.setDataNascimento(rs.getDate("data_nascimento"));
            leitor.setDataInscricao(rs.getDate("data_inscricao"));
            leitor.setBairro(rs.getString("bairro_leitor"));
            leitor.setCasa(rs.getString("rua_leitor"));
            leitor.setRua(rs.getString("casa_leitor"));
            Municipio municipio = new Municipio();
            municipio.setNome(rs.getString("m.nome_municipio"));
            leitor.setMunicipio(municipio);

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
}
