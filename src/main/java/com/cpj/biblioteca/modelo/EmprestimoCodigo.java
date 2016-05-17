/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author toshiba
 */
public class EmprestimoCodigo implements Serializable{
    private Leitor leitor;
    private Livro livro;
    private Date dataHoraEmprestimo;

    public EmprestimoCodigo() {
    }

    public EmprestimoCodigo(Leitor leitor, Livro livro, Date dataHoraEmprestimo) {
        this.leitor = leitor;
        this.livro = livro;
        this.dataHoraEmprestimo = dataHoraEmprestimo;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getDataHoraEmprestimo() {
        return dataHoraEmprestimo;
    }

    public void setDataHoraEmprestimo(Date dataHoraEmprestimo) {
        this.dataHoraEmprestimo = dataHoraEmprestimo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.leitor);
        hash = 41 * hash + Objects.hashCode(this.livro);
        hash = 41 * hash + Objects.hashCode(this.dataHoraEmprestimo);
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
        final EmprestimoCodigo other = (EmprestimoCodigo) obj;
        if (!Objects.equals(this.leitor, other.leitor)) {
            return false;
        }
        if (!Objects.equals(this.livro, other.livro)) {
            return false;
        }
        return Objects.equals(this.dataHoraEmprestimo, other.dataHoraEmprestimo);
    }
    
}
