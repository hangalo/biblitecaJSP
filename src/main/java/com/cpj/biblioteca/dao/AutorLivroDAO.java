package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.AutorLivro;
import com.cpj.biblioteca.modelo.AutorLivroCodigo;
import com.cpj.biblioteca.modelo.Livro;
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
public class AutorLivroDAO implements DAO<AutorLivro> {

    private static final String INSERIR = "INSERT INTO autor_livro(livro_id_livro, autor_id_autor, ano) VALUES (?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE autor_livro SET ano = ? WHERE livro_id_livro = ? AND autor_id_autor = ?";
    private static final String LISTA_TUDO = "SELECT a.id_autor, a.nome_autor, a.sobrenome_autor, a.data_nascimento, a.breve_biografica, m.nome_municipio FROM autor a inner join municipio m on a.id_municipio = m.id_municipio";
    private static final String BUSCA_POR_CODIGO = "SELECT * FROM autor_livro, autor, livro WHERE livro_id_livro = id_livro AND id_livro = ? AND autor_id_autor = id_autor AND id_autor = ?";
    private static final String ELIMINAR = "DELETE FROM autor_livro WHERE livro_id_livro = ? AND autor_id_autor = ?";

    private Connection con;

    public AutorLivroDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(AutorLivro autorLivro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            ps.setLong(1, autorLivro.getCodigo().getLivro().getCodigo());
            ps.setLong(2, autorLivro.getCodigo().getAutor().getCodigo());
            ps.setInt(3, autorLivro.getAno());
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
    public boolean editar(AutorLivro autorLivro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);

            ps.setInt(1, autorLivro.getAno());
            ps.setLong(2, autorLivro.getCodigo().getLivro().getCodigo());
            ps.setLong(3, autorLivro.getCodigo().getAutor().getCodigo());
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
    public boolean excluir(AutorLivro autorLivro) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, autorLivro.getCodigoRegisto());
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
    public AutorLivro buscarPeloCodigo(AutorLivro autorLivro) {

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, autorLivro.getCodigoRegisto());

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            popularComDados(autorLivro, rs);

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

        return autorLivro;
    }

    @Override
    public List<AutorLivro> buscarTudo() {
        List<AutorLivro> autorLivros = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                AutorLivro autorLivro = new AutorLivro();
                popularComDados(autorLivro, rs);
                autorLivros.add(autorLivro);

            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao recupera dados da Editora" + ex.getMessage());
        }

        return autorLivros;
    }

    private void popularComDados(AutorLivro autorLivro, ResultSet resultSet) {
        try {

            Autor autor = new Autor();
            autor.setCodigo(resultSet.getLong("livro.id_autor"));
            autor.setNome(resultSet.getString("livro.nome_autor"));
            autor.setSobrenome(resultSet.getString("livro.sobrenome_autor"));
            autor.setDataNascimento(resultSet.getDate("livro.data_nascimento"));
            autor.setBiografia(resultSet.getString("livro.breve_biografica"));

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

            autorLivro.setCodigo(new AutorLivroCodigo(autor, livro));
            autorLivro.setAno(resultSet.getInt("autor_livro.ano"));
        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }

}
