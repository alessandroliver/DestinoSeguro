package com.if1001exemplo1.tccvaseguro.basicas;

import java.util.Date;
import java.util.List;


public class User extends Pessoa{

    private String cpf;
    private String senha;
    private List<Guardioes> guardioes;

    public User(int id, String nome, String sobrenome, String email, String cidade, String uf, String sexo,
                Date nascimento, int numero_celular, String operadora, String nacionalidade, double cep, String cpf,
                String senha, List<Guardioes> guardioes) {
        super (id, nome, sobrenome,email, cidade,uf,sexo, nascimento, numero_celular,operadora, nacionalidade, cep);

        this.cpf = cpf;
        this.senha = senha;
        this.guardioes = guardioes;
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
