package com.if1001exemplo1.tccvaseguro.old;


import com.if1001exemplo1.tccvaseguro.basicas.Pessoa;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.util.Date;
import java.util.List;

public class Guard extends Pessoa {
    private boolean receberSms;
    private List<User> pessoasQueGuardam;


    public Guard (int id, String nome, String sobrenome, String email, String cidade, String uf, String sexo,
                  Date nascimento, int numero_celular, String operadora, String nacionalidade, double cep,
                  String parentesco/*, List<User> pessoasQueGuardam*/) {
        super (id, nome,sobrenome,email,cidade,uf,sexo,nascimento,numero_celular,operadora,nacionalidade,cep);

        this.receberSms = receberSms;
        this.pessoasQueGuardam = pessoasQueGuardam;
    }


    public boolean getReceberSms() {
        return receberSms;
    }

    public void setReceberSms(boolean receberSms) {
        this.receberSms = receberSms;
    }

    public List<User> getPessoasQueGuardam() {
        return pessoasQueGuardam;
    }

    public void setPessoasQueGuardam(List<User> pessoasQueGuardam) {
        this.pessoasQueGuardam = pessoasQueGuardam;
    }
}

