package com.if1001exemplo1.tccvaseguro.basicas;

import java.util.Date;
import java.util.List;

public class Guardioes extends Pessoa{
    String parentesco;
    private List<User> pessoasQueGuardam;


    public Guardioes (int id, String nome, String sobrenome, String email, String cidade, String uf, String sexo,
                      Date nascimento, int numero_celular, String operadora, String nacionalidade, double cep,
                      String parentesco, List<User> pessoasQueGuardam) {
        super (id, nome,sobrenome,email,cidade,uf,sexo,nascimento,numero_celular,operadora,nacionalidade,cep);

        this.parentesco = parentesco;
        this.pessoasQueGuardam = pessoasQueGuardam;
    }


    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

     public List<User> getPessoasQueGuardam() {
        return pessoasQueGuardam;
    }

    public void setPessoasQueGuardam(List<User> pessoasQueGuardam) {
        this.pessoasQueGuardam = pessoasQueGuardam;
    }
 }
