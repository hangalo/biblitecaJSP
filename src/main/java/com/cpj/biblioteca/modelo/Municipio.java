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
public class Municipio implements Serializable {

    private Long codigo;
    private String nome;
    private Provincia provincia;

    public Municipio() {
    }

    public Municipio(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }

    public Municipio(Long codigo, String nome, Provincia provincia) {
        this.codigo = codigo;
        this.nome = nome;
        this.provincia = provincia;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final Municipio other = (Municipio) obj;
        return Objects.equals(this.codigo, other.codigo);
    }
    
}
