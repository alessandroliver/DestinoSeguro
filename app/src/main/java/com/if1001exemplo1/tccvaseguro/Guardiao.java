package com.if1001exemplo1.tccvaseguro;


public class Guardiao {
    private String nome;
    private String telefone;
    private boolean avisar;
    public Guardiao(){}

    public String getNome(){
        return this.nome;
    }

    public void setAvisar(boolean b){
        avisar = b;
    }

    public boolean getAvisar(){
        return avisar;
    }

    public void setNome(String s){
        this.nome = s;
    }

    public String getTelefone(){
        return this.telefone;
    }

    public void setTelefone(String s){
        this.telefone = s;
    }
}
