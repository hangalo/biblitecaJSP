/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author toshiba
 */
public class AutorLivroCodigo implements Serializable{
    private Autor autor;
    private Livro livro;

    public AutorLivroCodigo() {
    }

    public AutorLivroCodigo(Autor autor, Livro livro) {
        this.autor = autor;
        this.livro = livro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.autor);
        hash = 41 * hash + Objects.hashCode(this.livro);
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
        final AutorLivroCodigo other = (AutorLivroCodigo) obj;
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        return Objects.equals(this.livro, other.livro);
    }
    
}
