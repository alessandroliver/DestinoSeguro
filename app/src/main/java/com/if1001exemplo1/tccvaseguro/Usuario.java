package com.if1001exemplo1.tccvaseguro;

import java.util.Date;


public class Usuario {
    private String nome;
    private String sobrenome;
    private String email;
    private String cidade;
    private String uf;
    private String senha;
    private String receber_email;
    private String sexo;
    private Date nascimento;
    private int numero_celular;

    public Usuario (String nome, String sobrenome, String email, String cidade, String uf, String senha, String receber_email, String sexo,
                    Date nascimento, int numero_celular) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cidade = cidade;
        this.uf = uf;
        this.senha = senha;
        this.receber_email = receber_email;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.numero_celular = numero_celular;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getSobrenome(){
        return sobrenome;
    }

    public void setSobrenome(String sobrenome){
        this.sobrenome = sobrenome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getCidade(){
        return cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getUf(){
        return uf;
    }

    public void setUf(String uf){
        this.uf = uf;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getReceber_email(){
        return receber_email;
    }

    public void setReceber_email(String receber_email){
        this.receber_email = receber_email;
    }

    public String getSexo(){
        return sexo;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    public Date getNascimento(){
        return nascimento;
    }

    public void setNascimento(Date nascimento){
        this.nascimento = nascimento;
    }

    public int getNumero_celular(){
        return numero_celular;
    }

    public void setNumero_celular(int numero_celular){
        this.numero_celular = numero_celular;
    }

}

