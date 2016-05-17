/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.modelo;

import java.util.Objects;

/**
 *
 * @author toshiba
 */
public class AutorLivro {
    private Autor autor;
    private Livro livro;
    private int ano;

    public AutorLivro() {
    }

    public AutorLivro(Autor autor, Livro livro, int ano) {
        this.autor = autor;
        this.livro = livro;
        this.ano = ano;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.autor);
        hash = 97 * hash + Objects.hashCode(this.livro);
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
        final AutorLivro other = (AutorLivro) obj;
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        return Objects.equals(this.livro, other.livro);
    }
       
}