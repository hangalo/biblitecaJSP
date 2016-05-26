/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.controlo;

import com.cpj.biblioteca.dao.EditoraDAO;
import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author praveen
 */
@WebServlet(name = "EditoraControlo", urlPatterns = {"/editoraControlo"})
public class EditoraControlo extends HttpServlet {
    
    EditoraDAO editoraDAO = new EditoraDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String accao = request.getParameter("accao");
            if (accao.equalsIgnoreCase("guardar")) {
                Editora editora = new Editora();
                editora.setNome(request.getParameter("input_nome"));
                editora.setCasa(request.getParameter("input_casa"));
                editora.setRua(request.getParameter("input_rua"));
                editora.setBairro(request.getParameter("input_bairro"));
                editora.setCaixaPostal(request.getParameter("input_caixa_postal"));
                editora.setTelefoneFixo(request.getParameter("input_telefone_fixo"));
                editora.setTelefoneMovel(request.getParameter("input_telefone_movel"));
                editora.setEmail(request.getParameter("input_email"));
                editora.setHomePage(request.getParameter("input_home_page"));
                Municipio municipio = new Municipio();
                municipio.setCodigo(Long.parseLong(request.getParameter("selectMunicipio")));
                editora.setMunicipio(municipio);
                editoraDAO.salvar(editora);
                
                RequestDispatcher rd = request.getRequestDispatcher("editora_guardar.jsp");
                rd.forward(request, response);
                
            } else if (accao.equalsIgnoreCase("editar")) {
                
                Editora editora = new Editora();
                editora.setCodigo(Long.parseLong(request.getParameter("input_codigo")));
                editora.setNome(request.getParameter("input_nome"));
                editora.setCasa(request.getParameter("input_casa"));
                editora.setRua(request.getParameter("input_rua"));
                editora.setBairro(request.getParameter("input_bairro"));
                editora.setCaixaPostal(request.getParameter("input_caixa_postal"));
                editora.setTelefoneFixo(request.getParameter("input_telefone_fixo"));
                editora.setTelefoneMovel(request.getParameter("input_telefone_movel"));
                editora.setEmail(request.getParameter("input_email"));
                editora.setHomePage(request.getParameter("input_home_page"));
                Municipio municipio = new Municipio();
                municipio.setCodigo(Long.parseLong(request.getParameter("selectMunicipio")));
                editora.setMunicipio(municipio);
                editoraDAO.editar(editora);
                
                RequestDispatcher rd = request.getRequestDispatcher("editora_lista.jsp");
                rd.forward(request, response);
                
                
            } else if (accao.equalsIgnoreCase("eliminar")) {
                
                Editora editora = new Editora();
                editora.setCodigo(Long.parseLong(request.getParameter("input_codigo")));
                editoraDAO.excluir(editora);
                RequestDispatcher rd = request.getRequestDispatcher("editora_lista.jsp");
                rd.forward(request, response);
                
                
            } else if (accao.equalsIgnoreCase("buscar_por_id")) {
                Editora editora = new Editora();
                editora.setCodigo(Long.parseLong(request.getParameter("input_codigo")));
                editora = editoraDAO.buscarPeloCodigo(editora);
                request.setAttribute("editora", editora);
                RequestDispatcher rd = request.getRequestDispatcher("editora_editar.jsp");
                rd.forward(request, response);
                
                
            } else if (accao.equalsIgnoreCase("listar")) {
                response.sendRedirect("editora_lista.jsp");
                
            }else if(accao.equalsIgnoreCase("ficha_editora")){
                System.out.println("Imprimir ficha da editora");
            }
        } catch (NumberFormatException | ServletException | IOException ex) {
            System.err.println("Erro ao ler dados do formulario"+ex.getMessage());
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
