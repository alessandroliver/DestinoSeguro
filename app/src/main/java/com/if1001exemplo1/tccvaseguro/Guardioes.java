package com.if1001exemplo1.tccvaseguro;


import java.util.Date;
import java.util.List;

public class Guardioes extends Pessoa{
    private boolean receberSms;
    private List<Usuario> pessoasQueGuardam;


    public Guardioes (String nome, String sobrenome, String email, String cidade, String uf, String sexo, Date nascimento, int numero_celular, String operadora, String nacionalidade
    , double cep, boolean receberSms, List<Usuario> pessoasQueGuardam) {
        super (nome, sobrenome,email, cidade,uf,sexo, nascimento, numero_celular,operadora, nacionalidade, cep);

        this.receberSms = receberSms;
        this.pessoasQueGuardam = pessoasQueGuardam;
    }

    public boolean isReceberSms() {
        return receberSms;
    }

    public void setReceberSms(boolean receberSms) {
        this.receberSms = receberSms;
    }

    public List<Usuario> getPessoasQueGuardam() {
        return pessoasQueGuardam;
    }

    public void setPessoasQueGuardam(List<Usuario> pessoasQueGuardam) {
        this.pessoasQueGuardam = pessoasQueGuardam;
    }
}

