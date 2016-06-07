package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Categoria;
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
public class CategoriaDAO implements DAO<Categoria> {

    private static final String INSERIR = "INSERT INTO categoria(nome_categoria) VALUES (?)";
    private static final String ACTUALISAR = "UPDATE categoria SET nome_categoria = ? WHERE id_categoria = ?";
    private static final String LISTA_TUDO = "SELECT * FROM categoria";
    private static final String BUSCA_POR_CODIGO = "SELECT * FROM categoria WHERE id_categoria = ?";
    private static final String ELIMINAR = "DELETE FROM categoria WHERE id_categoria = ?";
   
   

    private Connection con;

    public CategoriaDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(Categoria categoria) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
         ps.setString(1, categoria.getNome());

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
    public boolean editar(Categoria categoria) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
           ps.setString(1, categoria.getNome());
            ps.setLong(2, categoria.getCodigo());
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
    public boolean excluir(Categoria categoria) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, categoria.getCodigo());
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
    public Categoria buscarPeloCodigo(Categoria categoria) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, categoria.getCodigo());

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            popularComDados(categoria, rs);

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

        return categoria;
    }

    @Override
    public List<Categoria> buscarTudo() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                Categoria categoria = new Categoria();
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

    private void popularComDados(Categoria categoria, ResultSet rs) {
        try {

            categoria.setCodigo(rs.getLong("id_categoria"));
            categoria.setNome(rs.getString("nome_categoria"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
}
