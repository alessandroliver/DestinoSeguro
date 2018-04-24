package com.if1001exemplo1.tcc;

import java.util.Date;

/**
 * Created by Alessandro on 08/05/2017.
 */

public class Disciplina {
    private String nome;
    private int codigo;
    private Date data_inicio;
    private Date data_fim;
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;
    private int faltas;
    private double carga_horaria;

    public Disciplina (String nome, int codigo, Date data_inicio, Date data_fim, double nota1, double nota2, double nota3,
                       double nota4, int faltas, double carga_horaria){
        this.nome = nome;
        this.codigo = codigo;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.faltas = faltas;
        this.carga_horaria = carga_horaria;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public Date getData_inicio(){
        return data_inicio;
    }
    public void setData_inicio(Date data_inicio){
        this.data_inicio = data_inicio;
    }

    public Date getData_fim(){
        return data_fim;
    }
    public void setData_fim(Date data_fim){
        this.data_fim = data_fim;
    }

    public double getNota1(){
        return nota1;
    }
    public void setNota1(double nota1){
        this.nota1 = nota1;
    }

    public double getNota2(){
        return nota2;
    }
    public void setNota2(double nota2){
        this.nota2 = nota2;
    }

    public double getNota3(){
        return nota3;
    }
    public void setNota3(double nota3){
        this.nota3 = nota3;
    }

    public double getNota4(){
        return nota4;
    }
    public void setNota4(double nota4){
        this.nota4 = nota4;
    }

    public int getFaltas(){
        return faltas;
    }
    public void setFaltas(int faltas){
        this.faltas = faltas;
    }

    public double getCarga_horaria(){
        return carga_horaria;
    }
    public void setCarga_horaria(double carga_horaria){
        this.carga_horaria = carga_horaria;
    }
}
