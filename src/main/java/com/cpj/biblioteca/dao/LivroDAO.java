package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Lingua;
import com.cpj.biblioteca.modelo.Livro;
import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.modelo.Provincia;
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
public class LivroDAO implements DAO<Livro> {

    private static final String INSERIR = "INSERT INTO livro(isbn, titulo, data_publicacao, id_lingua, edicao, resumo, sessao, estante, posicao, id_editora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE livro SER isbn = ?, titulo = ?, data_publicacao = ?, id_lingua = ?, edicao = ?, resumo = ?, sessao = ?, estante = ?, posicao = ?, id_editora = ? WHERE id_livro = ?";
    private static final String LISTA_TUDO = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora";
    private static final String BUSCA_POR_CODIGO = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora AND id_livro = ?";
    private static final String BUSCA_POR_ISBN = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora AND isbn = ?";
    private static final String ELIMINAR = "DELETE FROM livro WHERE id_livro = ?";

    private ResultSet resultSet;

    private Connection con;

    public LivroDAO() {
        con = Conexao.criarConexao();
    }

    //id_livro, isbn, titulo, data_publicacao, id_lingua, edicao, resumo, sessao, estante, posicao, id_editora
    @Override
    public boolean salvar(Livro livro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);

            ps.setString(1, livro.getIsbn());
            ps.setString(2, livro.getTitulo());
            ps.setDate(3, new Date(livro.getDataPublicacao().getTime()));
            ps.setLong(4, livro.getLingua().getCodigo());
            ps.setString(5, livro.getEdicao());
            ps.setString(6, livro.getResumo());
            ps.setString(7, livro.getSessao());
            ps.setLong(8, livro.getEstante());
            ps.setLong(9, livro.getPosicao());
            ps.setLong(10, livro.getEditora().getCodigo());
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
    public boolean editar(Livro livro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);

            ps.setString(1, livro.getIsbn());
            ps.setString(2, livro.getTitulo());
            ps.setDate(3, new Date(livro.getDataPublicacao().getTime()));
            ps.setLong(4, livro.getLingua().getCodigo());
            ps.setString(5, livro.getEdicao());
            ps.setString(6, livro.getResumo());
            ps.setString(7, livro.getSessao());
            ps.setLong(8, livro.getEstante());
            ps.setLong(9, livro.getPosicao());
            ps.setLong(10, livro.getEditora().getCodigo());
            ps.setLong(11, livro.getCodigo());
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
    public boolean excluir(Livro livro) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, livro.getCodigo());
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
    public Livro buscarPeloCodigo(Livro livro) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, livro.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(livro, rs);

        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar dados da editora" + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException e) {
                System.err.println("Erro ao desalocar recursos" + e.getMessage());
            }
        }

        return livro;
    }

    public List<Livro> buscarTudo() {
        List<Livro> livros = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                Livro livro = new Livro();
                popularComDados(livro, rs);
                livros.add(livro);

            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao recupera dados da Editora" + ex.getMessage());
        }

        return livros;
    }

    public Livro buscarPeloISBN(Livro livro) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setString(1, livro.getIsbn());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(livro, rs);

        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar dados da editora" + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException e) {
                System.err.println("Erro ao desalocar recursos" + e.getMessage());
            }
        }

        return livro;
    }

    private void popularComDados(Livro livro, ResultSet resultSet) {
        try {
            Lingua lingua = new Lingua();
            Editora editora = new Editora();
            livro.setCodigo(resultSet.getLong("livro.id_livro"));
            livro.setTitulo(resultSet.getString("livro.titulo"));
            livro.setIsbn(resultSet.getString("livro.isbn"));
            livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
            livro.setEdicao(resultSet.getString("livro.edicao"));
            livro.setResumo(resultSet.getString("livro.resumo"));
            livro.setSessao(resultSet.getString("livro.sessao"));
            livro.setEstante(resultSet.getInt("livro.estante"));
            livro.setPosicao(resultSet.getInt("livro.posicao"));

            lingua.setCodigo(resultSet.getLong("lingua.id_lingua"));
            lingua.setNome(resultSet.getString("lingua.nome_lingua"));

            editora.setCodigo(resultSet.getLong("editora.id_editora"));
            editora.setNome(resultSet.getString("editora.nome_editora"));
            editora.setRua(resultSet.getString("editora.rua_editora"));
            editora.setCasa(resultSet.getString("editora.casa_editora"));

            livro.setLingua(lingua);
            livro.setEditora(editora);

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
}
