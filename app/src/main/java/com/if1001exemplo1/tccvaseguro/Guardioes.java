package com.if1001exemplo1.tccvaseguro;



public class Guardioes {
    private String nome;
    private String sobrenome;
    private double telefone;
    private String parentesco;

    public Guardioes (String nome, String sobrenome, double telefone, String parentesco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.parentesco = parentesco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public double getTelefone() {
        return telefone;
    }

    public void setTelefone(double telefone) {
        this.telefone = telefone;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}

