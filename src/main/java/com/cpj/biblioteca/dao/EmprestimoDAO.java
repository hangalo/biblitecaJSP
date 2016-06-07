package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Emprestimo;
import com.cpj.biblioteca.modelo.EmprestimoCodigo;
import com.cpj.biblioteca.modelo.Leitor;
import com.cpj.biblioteca.modelo.Livro;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class EmprestimoDAO implements DAO<Emprestimo> {

    private static final String INSERIR = "INSERT INTO emprestimo(id_livro, id_leitor, data_emprestimo, data_devolucao, observacoes) VALUES (?, ?, ?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE emprestimo data_devolucao = ?, observacoes =? WHERE id_livro = ?, id_leitor = ?, data_emprestimo = ? ";
    private static final String LISTA_TUDO = "SELECT * FROM leitor";
    /* A consulta nao devolve os dez livros mais procurados mais os primeiros dez da consulta*/
    private static final String DEZ_MAIS_PROCURADOS="SELECT livro.* FROM emprestimo, livro WHERE emprestimo.id_livro = livro.id_livro LIMIT 10";
    private static final String BUSCA_POR_CODIGO = "SELECT emprestimo.*, livro.*, leitor.* FROM emprestimo, livro, leitor WHERE emprestimo.id_livro = livro.id_livro AND emprestimo.id_leitor = leitor.id_leitor AND id_livro = ? AND id_leitor = ? AND data_emprestimo = ?";
    private static final String ELIMINAR = "DELETE FROM emprestimo WHERE id_livro = ? AND id_leitor = ? AND data_emprestimo = ?";
    private Connection con;

    public EmprestimoDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(Emprestimo emprestimo) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            ps.setString(5, emprestimo.getObservacao());
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
    public boolean editar(Emprestimo emprestimo) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setTimestamp(1, new Timestamp(emprestimo.getDataHoraDevolucao().getTime()));
            ps.setString(2, emprestimo.getObservacao());
           
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
    public boolean excluir(Emprestimo emprestimo) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, emprestimo.getCodigo());
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
    public Emprestimo buscarPeloCodigo(Emprestimo emprestimo) {
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, emprestimo.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(emprestimo, rs);
            
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
        
        return emprestimo;
    }
    
    public List<Emprestimo> buscarTudo(){
    List<Emprestimo> emprestimos = new ArrayList<>();
    try{
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(LISTA_TUDO);
     while(rs.next()){
     Emprestimo emprestimo = new Emprestimo();
         popularComDados(emprestimo, rs);
         emprestimos.add(emprestimo);
     
     
     }
    st.close();
    rs.close();
    }catch(SQLException ex){
        System.err.println("Erro ao recupera dados da Editora"+ex.getMessage());
    }
    
    return emprestimos;
    }
   

    

    private void popularComDados(Emprestimo emprestimo, ResultSet rs) {
        try {

            Livro livro = new Livro();
            Leitor leitor = new Leitor();

            livro.setCodigo(rs.getLong("livro.id_livro"));
            livro.setTitulo(rs.getString("livro.titulo"));
            livro.setIsbn(rs.getString("livro.isbn"));
            livro.setDataPublicacao(rs.getDate("livro.data_publicacao"));
            livro.setEdicao(rs.getString("livro.edicao"));
            livro.setResumo(rs.getString("livro.resumo"));
            livro.setSessao(rs.getString("livro.sessao"));
            livro.setEstante(rs.getInt("livro.estante"));
            livro.setPosicao(rs.getInt("livro.posicao"));

            leitor.setCodigo(rs.getLong("leitor.id_leitor"));
            leitor.setNome(rs.getString("leitor.nome_leitor"));
            leitor.setSobrenome(rs.getString("leitor.sobrenome_leitor"));
            leitor.setDataNascimento(rs.getDate("leitor.data_nascimento"));
            leitor.setDataInscricao(rs.getDate("leitor.data_inscricao"));
            leitor.setBairro(rs.getString("leitor.bairro_leitor"));
            leitor.setCasa(rs.getString("leitor.rua_leitor"));
            leitor.setRua(rs.getString("leitor.casa_leitor"));

            emprestimo.setCodigo(rs.getInt("codigo_emprestimo"));
            emprestimo.setDataHoraDevolucao(rs.getDate("data_devolucao"));
            emprestimo.setObservacao(rs.getString("observacoes"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados" + ex.getMessage());
        }

    }

}
