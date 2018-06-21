package com.if1001exemplo1.tccvaseguro.basicas;

import java.util.Date;

public class Pessoa {

    private int id;
    private String nome;
    private String sobrenome;
    private String cidade;
    private String uf;
    private String email;
    private String sexo;
    private Date nascimento;
    private int numero_celular;
    private String operadora;
    private String nacionalidade;
    private double cep;

    public Pessoa(int id, String nome, String sobrenome, String email, String cidade, String uf, String sexo,
                  Date nascimento, int numero_celular, String operadora, String nacionalidade, double cep) {
        this.id = id;
        this. nome = nome;
        this. sobrenome =  sobrenome;
        this.email = email;
        this.cidade = cidade;
        this.uf = uf;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.numero_celular = numero_celular;
        this.operadora = operadora;
        this.nacionalidade = nacionalidade;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCep() {
        return cep;
    }

    public void setCep(double cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getSexo() {
        return sexo;
    }


    public String getEmail() {
        return email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public int getNumeroCelular() {
        return numero_celular;
    }

    public String getCidade() {
        return cidade;
    }

    public String getOperadora() {
        return operadora;
    }

    public String getUf() {
        return uf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroCelular(int numero_celular) {
        this.numero_celular = numero_celular;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
