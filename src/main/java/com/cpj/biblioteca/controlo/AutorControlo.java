/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.controlo;

import com.cpj.biblioteca.dao.AutorDAO;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.util.DataUtil;
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
@WebServlet(name = "AutorControlo", urlPatterns = {"/autorControlo"})
public class AutorControlo extends HttpServlet {

    AutorDAO autorDAO = new AutorDAO();
    DataUtil dataUtil = new DataUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accao = request.getParameter("accao");
            if (accao.equalsIgnoreCase("guardar")) {
                Autor autor = new Autor();
                autor.setNome(request.getParameter("nome"));
                autor.setSobrenome(request.getParameter("sobrenome"));
                autor.setDataNascimento(dataUtil.stringToData(request.getParameter("data_nascimento")));
                Municipio municipio = new Municipio();
                municipio.setCodigo(Long.parseLong(request.getParameter("selectMunicipio")));
                autor.setMunicipio(municipio);
                autor.setBiografia(request.getParameter("biografica"));
                autorDAO.salvar(autor);
                RequestDispatcher rd = request.getRequestDispatcher("autor_guardar.jsp");
                rd.forward(request, response);
                
            } else if (accao.equalsIgnoreCase("editar")) {

                Autor autor = new Autor();
                autor.setCodigo(Long.parseLong(request.getParameter("codigo")));
                autor.setNome(request.getParameter("nome"));
                autor.setSobrenome(request.getParameter("sobrenome"));
                autor.setDataNascimento(dataUtil.stringToData(request.getParameter("data_nascimento")));
                Municipio municipio = new Municipio();
                municipio.setCodigo(Long.parseLong(request.getParameter("selectMunicipio")));
                autor.setMunicipio(municipio);
                autor.setBiografia(request.getParameter("biografica"));
                autorDAO.editar(autor);

                RequestDispatcher rd = request.getRequestDispatcher("autores_lista.jsp");
                rd.forward(request, response);
            } else if (accao.equalsIgnoreCase("buscar_por_id")) {
                Autor autor = new Autor();
                autor.setCodigo(Long.parseLong(request.getParameter("codigo")));
                autor = autorDAO.buscarPeloCodigo(autor);
                request.setAttribute("autor", autor);
                RequestDispatcher rd = request.getRequestDispatcher("autor_editar.jsp");
                rd.forward(request, response);
            } else if (accao.equalsIgnoreCase("eliminar")) {
                Autor autor = new Autor();
                autor.setCodigo(Long.parseLong(request.getParameter("codigo")));
                autorDAO.excluir(autor);
                RequestDispatcher rd = request.getRequestDispatcher("autores_lista.jsp");
                rd.forward(request, response);

            } else if (accao.equalsIgnoreCase("listar")) {
                response.sendRedirect("autores_lista.jsp");
            }else if(accao.equalsIgnoreCase("imprimir_lista")){
            
                //Imprime relatorio a implementar com JasperReport
                System.out.println("Lista dos autores");
            
            }else if(accao.equalsIgnoreCase("ficha_autor")){
            //impre ficha do autor - a implementar com JasperRepor
                System.out.println("Ficha do autor");
            
            }
            
            

        } catch (Exception ex) {

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
