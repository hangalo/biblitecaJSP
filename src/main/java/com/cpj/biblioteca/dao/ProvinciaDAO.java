package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.modelo.Provincia;
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
public class ProvinciaDAO implements DAO<Provincia> {

    
    private static final String INSERIR =  "INSERT INTO provincia(nome_provincia) VALUES (?)";
    private static final String ACTUALISAR = "UPDATE provincia SET nome_provincia = ? WHERE id_provincia = ?";
    private static final String LISTA_TUDO = "SELECT * FROM provincia";
    private static final String BUSCA_POR_CODIGO = "SELECT * FROM provincia WHERE id_provincia = ?";
    private static final String ELIMINAR = "DELETE FROM provincia WHERE id_provincia = ?";
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    private Connection con;

    public ProvinciaDAO() {
        con = Conexao.criarConexao();
    }

   @Override
    public boolean salvar(Provincia provincia) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);

            ps.setString(1, provincia.getNome());
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
    public boolean editar(Provincia provincia) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setString(1, provincia.getNome());
             ps.setLong(2, provincia.getCodigo());
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
    public boolean excluir(Provincia provincia) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, provincia.getCodigo());
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
    public Provincia buscarPeloCodigo(Provincia provincia) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, provincia.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(provincia, rs);

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

        return provincia;
    }

    public List<Provincia> buscarTudo() {
        List<Provincia> provincias= new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                Provincia provincia = new Provincia();
                popularComDados(provincia, rs);
                provincias.add(provincia);

            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao recupera dados da Editora" + ex.getMessage());
        }

        return provincias;
    }
    private void popularComDados(Provincia provincia, ResultSet resultSet) {
        try {
            provincia.setCodigo(resultSet.getLong("id_provincia"));
            provincia.setNome(resultSet.getString("nome_provincia"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }
}
