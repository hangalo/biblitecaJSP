package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class AutorDAO implements DAO<Autor> {

    private static final String INSERIR = "INSERT INTO autor(nome_autor, sobrenome_autor, data_nascimento, breve_biografica, id_municipio) VALUES (?, ?, ?, ?, ?)";
    private static final String ACTUALISAR = "UPDATE autor SET nome_autor = ?, sobrenome_autor = ?, data_nascimento = ?, breve_biografica = ?, id_municipio = ? WHERE id_autor = ?";
    private static final String LISTA_TUDO = "SELECT a.id_autor, a.nome_autor, a.sobrenome_autor, a.data_nascimento, a.breve_biografica, m.nome_municipio FROM autor a inner join municipio m on a.id_municipio = m.id_municipio";
    private static final String BUSCA_POR_CODIGO = "SELECT a.*, m.* FROM autor a, municipio m WHERE a.id_municipio = m.id_municipio AND id_autor = ?";
    private static final String ELIMINAR = "DELETE FROM autor WHERE id_autor = ?";

    private Connection con;

    public AutorDAO() {
        con = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(Autor autor) {
        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERIR);
            ps.setString(1, autor.getNome());
            ps.setString(2, autor.getSobrenome());
            ps.setDate(3, new Date(autor.getDataNascimento().getTime()));
            ps.setString(4, autor.getBiografia());
            ps.setLong(5, autor.getMunicipio().getCodigo());

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
    public boolean editar(Autor autor) {

        boolean sucesso = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(ACTUALISAR);
            ps.setString(1, autor.getNome());
            ps.setString(2, autor.getSobrenome());
            ps.setDate(3, new Date(autor.getDataNascimento().getTime()));
            ps.setString(4, autor.getBiografia());
            ps.setLong(5, autor.getMunicipio().getCodigo());
            ps.setLong(6, autor.getCodigo());
            ps.close();
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
    public boolean excluir(Autor autor) {
         PreparedStatement ps = null;
        boolean sucesso = false;
        try {
            ps = con.prepareStatement(ELIMINAR);
            ps.setLong(1, autor.getCodigo());
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
    public Autor buscarPeloCodigo(Autor autor) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(BUSCA_POR_CODIGO);
            ps.setLong(1, autor.getCodigo());

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            popularComDados(autor, rs);

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

        return autor;
    }


    public List<Autor> buscarTudo() {
        List<Autor> autores = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(LISTA_TUDO);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Autor autor = new Autor();

                popularComDados(autor, rs);

                autores.add(autor);
            }
        } catch (SQLException ex) {
            System.out.println("Erro a listar do dados dos autores" + ex);
        }

        return autores;
    }

   

    

    

    private void popularComDados(Autor autor, ResultSet resultSet) {
        try {
            autor.setCodigo(resultSet.getLong("a.id_autor"));
            autor.setNome(resultSet.getString("a.nome_autor"));
            autor.setSobrenome(resultSet.getString("a.sobrenome_autor"));

            autor.setDataNascimento(resultSet.getDate("a.data_nascimento"));
            Municipio municipio = new Municipio();
            municipio.setNome(resultSet.getString("m.nome_municipio"));
            autor.setMunicipio(municipio);
            autor.setBiografia(resultSet.getString("a.breve_biografica"));

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao ler dados " + ex.getMessage());
        }
    }

    

}
