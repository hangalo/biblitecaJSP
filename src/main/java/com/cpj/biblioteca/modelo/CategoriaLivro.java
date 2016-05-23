package com.cpj.biblioteca.modelo;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Chandimba
 */
public class CategoriaLivro {
    private CategoriaLivroCodigo codigo;
    private Date dataRegisto;

    public CategoriaLivro() {
        criarDataRegistoAutomaticamente();
    }

    public CategoriaLivro(Livro livro, Categoria categoria) {
        this.codigo = new CategoriaLivroCodigo(livro, categoria);
        criarDataRegistoAutomaticamente();
    }

    public CategoriaLivro(CategoriaLivroCodigo codigo, Date dataRegisto) {
        this.codigo = codigo;
        this.dataRegisto = dataRegisto;
    }

    public CategoriaLivroCodigo getCodigo() {
        return codigo;
    }

    public void setCodigo(CategoriaLivroCodigo codigo) {
        this.codigo = codigo;
    }
    
    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
    
    private void criarDataRegistoAutomaticamente() {
        this.dataRegisto = new Date();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoriaLivro other = (CategoriaLivro) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

}