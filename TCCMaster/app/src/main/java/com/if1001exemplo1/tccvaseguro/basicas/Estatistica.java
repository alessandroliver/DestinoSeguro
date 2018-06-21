package com.if1001exemplo1.tccvaseguro.basicas;

import java.util.Date;

/**
 * Created by Alessandro on 28/11/17.
 */

public class Estatistica {
    private int id;
    private int numeroAlertas;
    private double porcentagemMulheresAlertas;
    private double porcentagemHomensAlertas;
    private String horarioDeMaisAlertas;
    private String cidadeDeMaisAlertas;
    private String bairroDeMaisAlertas;
    private Date dateMaisAlertas;
    private String modoMaisAlerta;
    private String horarioMenosAlertas;
    private int numerosBOfeitos;

    public Estatistica(int id, int numeroAlertas, double porcentagemMulheresAlertas, double porcentagemHomensAlertas,
                       String horarioDeMaisAlertas, String cidadeDeMaisAlertas, String bairroDeMaisAlertas,
                       Date dateMaisAlertas, String modoMaisAlerta, String horarioMenosAlertas,int numerosBOfeitos ){
        this.id = id;
        this.numeroAlertas = numeroAlertas;
        this.porcentagemMulheresAlertas = porcentagemMulheresAlertas;
        this.porcentagemHomensAlertas = porcentagemHomensAlertas;
        this.horarioDeMaisAlertas = horarioDeMaisAlertas;
        this.cidadeDeMaisAlertas = cidadeDeMaisAlertas;
        this.bairroDeMaisAlertas = bairroDeMaisAlertas;
        this.dateMaisAlertas = dateMaisAlertas;
        this.modoMaisAlerta = modoMaisAlerta;
        this.horarioMenosAlertas = horarioMenosAlertas;
        this.numerosBOfeitos = numerosBOfeitos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroAlertas() {
        return numeroAlertas;
    }

    public void setNumeroAlertas(int numeroAlertas) {
        this.numeroAlertas = numeroAlertas;
    }

    public double getPorcentagemMulheresAlertas() {
        return porcentagemMulheresAlertas;
    }

    public void setPorcentagemMulheresAlertas(double porcentagemMulheresAlertas) {
        this.porcentagemMulheresAlertas = porcentagemMulheresAlertas;
    }

    public double getPorcentagemHomensAlertas() {
        return porcentagemHomensAlertas;
    }

    public void setPorcentagemHomensAlertas(double porcentagemHomensAlertas) {
        this.porcentagemHomensAlertas = porcentagemHomensAlertas;
    }

    public String getHorarioDeMaisAlertas() {
        return horarioDeMaisAlertas;
    }

    public String getHorarioMenosAlertas() {
        return horarioMenosAlertas;
    }

    public String getCidadeDeMaisAlertas() {
        return cidadeDeMaisAlertas;
    }

    public void setCidadeDeMaisAlertas(String cidadeDeMaisAlertas) {
        this.cidadeDeMaisAlertas = cidadeDeMaisAlertas;
    }

    public String getBairroDeMaisAlertas() {
        return bairroDeMaisAlertas;
    }

    public void setBairroDeMaisAlertas(String bairroDeMaisAlertas) {
        this.bairroDeMaisAlertas = bairroDeMaisAlertas;
    }

    public Date getDateMaisAlertas() {
        return dateMaisAlertas;
    }

    public void setDateMaisAlertas(Date dateMaisAlertas) {
        this.dateMaisAlertas = dateMaisAlertas;
    }

    public String getModoMaisAlerta() {
        return modoMaisAlerta;
    }

    public void setModoMaisAlerta(String modoMaisAlerta) {
        this.modoMaisAlerta = modoMaisAlerta;
    }

    public int getNumerosBOfeitos() {
        return numerosBOfeitos;
    }

    public void setNumerosBOfeitos(int numerosBOfeitos) {
        this.numerosBOfeitos = numerosBOfeitos;
    }
}
