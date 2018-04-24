package com.if1001exemplo1.tcc;

import java.util.Date;

/**
 * Created by Alessandro on 08/05/2017.
 */

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String rg;
    private String nacionalidade;
    private String sexo;
    private String naturalidade;
    private Endereco endereco;
    private Date data_nascimento;
    private Telefone telefone;
    private String email;
    private String senha;

    public Pessoa(String nome, String cpf, String rg, String nacionalidade, String sexo, String naturalidade, Endereco endereco,
                  Date data_nascimento, Telefone telefone, String email, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.nacionalidade = nacionalidade;
        this.sexo = sexo;
        this.naturalidade = naturalidade;
        this.endereco = endereco;
        this.data_nascimento = data_nascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getRg(){
        return rg;
    }
    public void setRg(String rg){
        this.rg = rg;
    }

    public String getNacionalidade(){
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade){
        this.nacionalidade = nacionalidade;
    }

    public String getSexo(){
        return sexo;
    }
    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    public String getNaturalidade(){
        return naturalidade;
    }
    public void setNaturalidade(String naturalidade){
        this.naturalidade = naturalidade;
    }

    public Endereco getEndereco(){
        return endereco;
    }
    public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }

    public Date getData_nascimento(){
        return data_nascimento;
    }
    public void setData_nacimento(Date data_nascimento){
        this.data_nascimento = data_nascimento;
    }

    public Telefone getTelefone(){
        return telefone;
    }
    public void setTelefone(Telefone telefone){
        this.telefone = telefone;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
}
