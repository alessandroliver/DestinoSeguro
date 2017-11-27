package com.if1001exemplo1.tccvaseguro;



public class Preferencias {
    private String modo_rota;
    private String email_para_notificacao;

    public Preferencias (String modo_rota, String email_para_notificacao) {
        this.modo_rota = modo_rota;
        this.email_para_notificacao = email_para_notificacao;

    }

    public String getModo_rota() {
        return modo_rota;
    }

    public void setModo_rota(String modo_rota) {
        this.modo_rota = modo_rota;
    }

    public String getEmail_para_notificacao() {
        return email_para_notificacao;
    }

    public void setEmail_para_notificacao(String email_para_notificacao) {
        this.email_para_notificacao = email_para_notificacao;
    }
}
