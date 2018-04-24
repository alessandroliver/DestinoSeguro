package com.if1001exemplo1.tcc;

import java.util.Date;

/**
 * Created by Alessandro on 08/05/2017.
 */

public class Professor extends Funcionario {
    private Disciplina disciplina;

    public Professor(String nome, String cpf, String rg, String nacionalidade, String sexo, String naturalidade, Endereco endereco,
                     Date data_nascimento, Telefone telefone, String email, String senha, double salario, int id,
                     double carga_horaria, String cargo, Disciplina disciplina){
        super(nome, cpf, rg, nacionalidade, sexo, naturalidade, endereco, data_nascimento, telefone, email, senha, salario, id,
        carga_horaria, cargo);

        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina(){
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina){
        this.disciplina = disciplina;
    }
}
