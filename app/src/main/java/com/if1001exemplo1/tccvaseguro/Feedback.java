package com.if1001exemplo1.tccvaseguro;

import java.util.Date;

/**
 * Created by thayonara on 28/11/17.
 */

public class Feedback {

    private String origem;
    private String destino;
    private String modo;
    private boolean emitiuAlerta;
    private int quantidadeAlerta;
    private double tempoPercurso;
    private Date dateFeedback;
    private DangerousLocations dangerousLocations;
    private String horarioAlerta;
    private String descricaoAlerta;
    private Usuario usuario;


    public Feedback(String origem, String destino, String modo, boolean emitiuAlerta, int quantidadeAlerta, double tempoPercurso,
             Date dateFeedback, DangerousLocations dangerousLocations, String horarioAlerta, String descricaoAlerta, Usuario usuario){
        this.origem = origem;
        this.destino = destino;
        this.modo = modo;
        this.emitiuAlerta = emitiuAlerta;
        this.quantidadeAlerta = quantidadeAlerta;
        this.tempoPercurso = tempoPercurso;
        this.dateFeedback = dateFeedback;
        this.dangerousLocations = dangerousLocations;
        this.horarioAlerta = horarioAlerta;
        this.descricaoAlerta = descricaoAlerta;
        this.usuario = usuario;
    }

    public int getQuantidadeAlerta() {
        return quantidadeAlerta;
    }

    public void setQuantidadeAlerta(int quantidadeAlerta) {
        this.quantidadeAlerta = quantidadeAlerta;
    }

    public double getTempoPercurso() {
        return tempoPercurso;
    }

    public void setTempoPercurso(double tempoPercurso) {
        this.tempoPercurso = tempoPercurso;
    }

    public Date getDateFeedback() {
        return dateFeedback;
    }

    public void setDateFeedback(Date dateFeedback) {
        this.dateFeedback = dateFeedback;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getModo() {
        return modo;
    }

    public boolean isEmitiuAlerta() {
        return emitiuAlerta;
    }

    public void setEmitiuAlerta(boolean emitiuAlerta) {
        this.emitiuAlerta = emitiuAlerta;
    }

    public DangerousLocations getDangerousLocations() {
        return dangerousLocations;
    }

    public void setDangerousLocations(DangerousLocations dangerousLocations) {
        this.dangerousLocations = dangerousLocations;
    }

    public String getHorarioAlerta() {
        return horarioAlerta;
    }

    public void setHorarioAlerta(String horarioAlerta) {
        this.horarioAlerta = horarioAlerta;
    }

    public String getDescricaoAlerta() {
        return descricaoAlerta;
    }

    public void setDescricaoAlerta(String descricaoAlerta) {
        this.descricaoAlerta = descricaoAlerta;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
