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
    private AutorLivroCodigo codigo;
    private int ano;

    public AutorLivro() {
    }

    public AutorLivro(AutorLivroCodigo codigo, int ano) {
        this.codigo = codigo;
        this.ano = ano;
    }

    public AutorLivroCodigo getCodigo() {
        return codigo;
    }

    public void setCodigo(AutorLivroCodigo codigo) {
        this.codigo = codigo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        return Objects.equals(this.codigo, other.codigo);
    }
    
}