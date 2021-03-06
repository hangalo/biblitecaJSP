package com.cpj.biblioteca.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Chandimba
 */
public class CategoriaLivroCodigo implements Serializable{
    private Livro livro;
    private Categoria categoria;

    public CategoriaLivroCodigo() {
    }

    public CategoriaLivroCodigo(Livro livro, Categoria categoria) {
        this.livro = livro;
        this.categoria = categoria;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.livro);
        hash = 71 * hash + Objects.hashCode(this.categoria);
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
        final CategoriaLivroCodigo other = (CategoriaLivroCodigo) obj;
        if (!Objects.equals(this.livro, other.livro)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return true;
    }
    
    
}
