package com.if1001exemplo1.tccvaseguro;

import java.util.Date;
import java.util.List;


public class Usuario extends Pessoa{

    private List<Guardioes> guardioes;
    private String senha;
    private String cpf;


    public Usuario (String nome, String sobrenome, String email, String cidade, String uf, String sexo, Date nascimento, int numero_celular, String operadora, String nacionalidade
            , double cep, List<Guardioes> guardioes, String senha, String cpf) {
        super (nome, sobrenome,email, cidade,uf,sexo, nascimento, numero_celular,operadora, nacionalidade, cep);

        this.senha = senha;
        this.guardioes = guardioes;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public List<Guardioes> getGuardioes() {
        return guardioes;
    }

    public void setGuardioes(List<Guardioes> guardioes) {
        this.guardioes = guardioes;
    }

}

