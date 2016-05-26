package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.Serializable;
import java.sql.Connection;
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
public class EditoraDAO implements DAO<Editora> {
    
    private static final String INSERIR = "INSERT INTO editora(nome_editora, casa_editora, rua_editora, bairro_editora, caixa_postal_editora, telefone_fixo_editora, telefone_movel_editora, email_editora, home_page_editora, id_municipio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String LISTA_TUDO = "SELECT id_editora, nome_editora, casa_editora, rua_editora, bairro_editora, caixa_postal_editora, telefone_fixo_editora, telefone_movel_editora, email_editora, home_page_editora, m.nome_municipio FROM editora e inner join municipio m on e.id_municipio = m.id_municipio";
    private static final String BUSCA_POR_CODIGO = "SELECT id_editora, nome_editora, casa_editora, rua_editora, bairro_editora, caixa_postal_editora, telefone_fixo_editora, telefone_movel_editora, email_editora, home_page_editora, m.nome_municipio FROM editora e inner join municipio m on e.id_municipio = m.id_municipio WHERE id_editora =?";
    private static final String ELIMINAR = "DELETE FROM editora WHERE id_editora =?";
    private static final String ACTUALIZAR = "UPDATE editora SET nome_editora =?, casa_editora=?, rua_editora=?, bairro_editora=?, caixa_postal_editora=?, telefone_fixo_editora=?, telefone_movel_editora=?, email_editora=?, home_page_editora=?, id_municipio=? WHERE id_editora = ?";
    
    private Connection con;
    
    public EditoraDAO() {
        con = Conexao.criarConexao();
    }

   
    @Override
    public boolean salvar(Editora editora) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            
            ps.setString(1, editora.getNome());
            ps.setString(2, editora.getCasa());
            ps.setString(3, editora.getRua());
            ps.setString(4, editora.getBairro());
            ps.setString(5, editora.getCaixaPostal());
            ps.setString(6, editora.getTelefoneFixo());
            ps.setString(7, editora.getTelefoneMovel());
            ps.setString(8, editora.getEmail());
            ps.setString(9, editora.getHomePage());
            ps.setLong(10, editora.getMunicipio().getCodigo());
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
    public boolean editar(Editora editora) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALIZAR);
            
            ps.setString(1, editora.getNome());
            ps.setString(2, editora.getCasa());
            ps.setString(3, editora.getRua());
            ps.setString(4, editora.getBairro());
            ps.setString(5, editora.getCaixaPostal());
            ps.setString(6, editora.getTelefoneFixo());
            ps.setString(7, editora.getTelefoneMovel());
            ps.setString(8, editora.getEmail());
            ps.setString(9, editora.getHomePage());
            ps.setLong(10, editora.getMunicipio().getCodigo());
            ps.setLong(11, editora.getCodigo());
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
    public boolean excluir(Editora editora) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, editora.getCodigo());
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
    public Editora buscarPeloCodigo(Editora editora) {
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, editora.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularEditora(editora, rs);
            
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
        
        return editora;
    }
    
    public List<Editora> bustaTudo(){
    List<Editora> editoras = new ArrayList<>();
    try{
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(LISTA_TUDO);
     while(rs.next()){
     Editora editora = new Editora();
         popularEditora(editora, rs);
         editoras.add(editora);
     
     
     }
    st.close();
    rs.close();
    }catch(SQLException ex){
        System.err.println("Erro ao recupera dados da Editora"+ex.getMessage());
    }
    
    return editoras;
    }
    
    
    private void popularEditora(Editora editora, ResultSet rs) {
        try {
          
            editora.setCodigo(rs.getLong("id_editora"));
            editora.setNome(rs.getString("nome_editora"));
            editora.setCasa(rs.getString("casa_editora"));
            editora.setRua(rs.getString("rua_editora"));
            editora.setBairro(rs.getString("bairro_editora"));
            editora.setCaixaPostal(rs.getString("caixa_postal_editora"));
            editora.setTelefoneFixo(rs.getString("telefone_fixo_editora"));
            editora.setTelefoneMovel(rs.getString("telefone_movel_editora"));
            editora.setEmail(rs.getString("email_editora"));
            editora.setHomePage(rs.getString("home_page_editora"));
            Municipio municipio = new Municipio();
            municipio.setNome(rs.getString("m.nome_municipio"));
            editora.setMunicipio(municipio);
            
        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora"+ex.getMessage());
        }
        
    }
    
    
    
    /**
     * 
     * Metodos criados pelo Nelson que acho(Hangalo) não necessario 
     * olhando para o requisitos da aplicação
     * 
     */
    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {
        return false;
    }
    
    @Override
    public List<Editora> buscarTudo()  {
      List<Editora> editoras = new ArrayList<>();
           
            return editoras;
          
    }
    
    public List<Editora> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }
    
    private List<Editora> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
       
            
            List<Editora> editoras = new ArrayList<>();
           
            return editoras;
        } 
    
}
