package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Categoria;
import com.cpj.biblioteca.modelo.CategoriaLivro;
import com.cpj.biblioteca.modelo.CategoriaLivroCodigo;
import com.cpj.biblioteca.modelo.Livro;
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
public class CategoriaLivroDAO implements DAO<CategoriaLivro> {

    private static final String INSERIR = "INSERT INTO livro_categoria(livro_id_livro, categoria_id_categoria, data_registo) VALUES (?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE livro_categoria SET data_registo = ? WHERE livro_id_livro = ? AND categoria_id_categoria = ?";
    private static final String LISTA_TUDO = "SELECT livro.* FROM livro_categoria, livro "
            + " WHERE categoria_id_categoria = id_categoria AND livro_id_livro = id_livro AND id_categoria = ? ";
    private static final String BUSCA_POR_CODIGO = "SELECT livro.* FROM livro_categoria, livro "
            + " WHERE categoria_id_categoria = id_categoria AND livro_id_livro = id_livro AND id_categoria = ? ";
    private static final String ELIMINAR = "DELETE FROM livro_categoria WHERE categoria_id_categoria = ? AND livro_id_livro = ?";

    private Connection con;

    public CategoriaLivroDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(CategoriaLivro categoriaLivro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            ps.setLong(1, categoriaLivro.getCodigo().getLivro().getCodigo());
            ps.setLong(2, categoriaLivro.getCodigo().getCategoria().getCodigo());
            ps.setDate(3, new Date(categoriaLivro.getDataRegisto().getTime()));

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
    public boolean editar(CategoriaLivro categoriaLivro) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setDate(1, new Date(categoriaLivro.getDataRegisto().getTime()));
            ps.setLong(2, categoriaLivro.getCodigo().getLivro().getCodigo());
            ps.setLong(3, categoriaLivro.getCodigo().getCategoria().getCodigo());
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
    public boolean excluir(CategoriaLivro categoriaLivro) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, categoriaLivro.getIdRegisto());
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
    public CategoriaLivro buscarPeloCodigo(CategoriaLivro categoriaLivro) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, categoriaLivro.getIdRegisto());

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            popularComDados(categoriaLivro, rs);

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

        return categoriaLivro;
    }

    @Override
    public List<CategoriaLivro> buscarTudo() {
        List<CategoriaLivro> categorias = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                CategoriaLivro categoria = new CategoriaLivro();
                popularComDados(categoria, rs);
                categorias.add(categoria);

            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao recupera dados da Editora" + ex.getMessage());
        }

        return categorias;
    }

    private void popularComDados(CategoriaLivro categoriaLivro, ResultSet resultSet) {
        try {

            Categoria categoria = new Categoria();
            categoria.setCodigo(resultSet.getLong("categoria.id_categoria"));
            categoria.setNome(resultSet.getString("categoria.nome_categoria"));

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

            categoriaLivro.setCodigo(new CategoriaLivroCodigo(livro, categoria));
            categoriaLivro.setDataRegisto(resultSet.getDate("livro_categoria.data_registo"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
}
