package com.if1001exemplo1.tcc;

import java.util.List;

/**
 * Created by Alessandro on 08/05/2017.
 */

public class Boletim {
    private double media1;
    private double media2;
    private double media3;
    private double media4;
    private double media_geral;
    private double recuperacao;
    private List<Disciplina> disciplina;

    public Boletim (double media1, double media2, double media3, double media4, double media_geral, double recuperacao,
                    List<Disciplina> disciplina){
        this.media1 = media1;
        this.media2 = media2;
        this.media3 = media3;
        this.media4 = media4;
        this.media_geral = media_geral;
        this.recuperacao = recuperacao;
        this.disciplina = disciplina;
    }

    public double getMedia1(){
        return media1;
    }
    public void setMedia1(double media1){
        this.media1 = media1;
    }

    public double getMedia2(){
        return media2;
    }
    public void setMedia2(double media2){
        this.media2 = media2;
    }

    public double getMedia3(){
        return media3;
    }
    public void setMedia3(double media3){
        this.media3 = media3;
    }

    public double getMedia4(){
        return media4;
    }
    public void setMedia4(double media4){
        this.media4 = media4;
    }

    public double getMedia_geral(){
        return media_geral;
    }
    public void setMedia_geral(double media_geral){
        this.media_geral = media_geral;
    }

    public double getRecuperacao(){
        return recuperacao;
    }
    public void setRecuperacao(double recuperacao){
        this.recuperacao = recuperacao;
    }

    public List<Disciplina> getDisciplina(){
        return disciplina;
    }
    public void setDisciplina(List<Disciplina> disciplina){
        this.disciplina = disciplina;
    }
}
