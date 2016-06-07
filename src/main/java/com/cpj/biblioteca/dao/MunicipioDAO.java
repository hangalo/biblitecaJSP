package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Lingua;
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
public class MunicipioDAO implements DAO<Municipio> {

    private static final String INSERIR = "INSERT INTO municipio(nome_municipio, id_provincia) VALUES (?, ?)";
    private static final String ACTUALISAR = "UPDATE municipio SET nome_municipio = ?, id_provincia = ? WHERE id_municipio = ?";
    private static final String LISTA_TUDO = "SELECT * FROM municipio";
    private static final String BUSCA_POR_CODIGO = "SELECT * FROM municipio WHERE id_municipio = ?";
    private static final String ELIMINAR = "DELETE FROM municipio WHERE id_municipio = ?";
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    private Connection con;

    public MunicipioDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(Municipio municipio) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);

            ps.setString(1, municipio.getNome());
            ps.setLong(2, municipio.getProvincia().getCodigo());

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
    public boolean editar(Municipio municipio) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setString(1, municipio.getNome());
            ps.setLong(2, municipio.getProvincia().getCodigo());
            ps.setLong(3, municipio.getCodigo());
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
    public boolean excluir(Municipio municipio) {
        PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, municipio.getCodigo());
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
    public Municipio buscarPeloCodigo(Municipio municipio) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, municipio.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            popularComDados(municipio, rs);

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

        return municipio;
    }

    public List<Municipio> buscarTudo() {
        List<Municipio> municipios = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(LISTA_TUDO);
            while (rs.next()) {
                Municipio municipio = new Municipio();
                popularComDados(municipio, rs);
                municipios.add(municipio);

            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao recupera dados da Editora" + ex.getMessage());
        }

        return municipios;
    }

    private void popularComDados(Municipio municipio, ResultSet resultSet) {
        try {
            municipio.setCodigo(resultSet.getLong("id_municipio"));
            municipio.setNome(resultSet.getString("nome_municipio"));

        } catch (SQLException ex) {
            System.err.println("Erro ao ler dados da Editora" + ex.getMessage());
        }

    }

}
