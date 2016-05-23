package com.cpj.biblioteca.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Chandimba
 */
public class Lingua implements Serializable {

    private Long codigo;
    private String nome;

    public Lingua() {
    }

    public Lingua(String nome) {
        this.nome = nome;
    }
    
    public Lingua(Long codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
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

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Lingua other = (Lingua) obj;
        return Objects.equals(this.codigo, other.codigo);
    }
    
    
}
