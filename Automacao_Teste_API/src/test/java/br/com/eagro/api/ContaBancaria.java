package br.com.eagro.api;

public class ContaBancaria {

    private String agencia;
    private String conta;
    private String digito;

    // Construtor vazio para desserialização
    public ContaBancaria() {
    }

    // Construtor para facilitar a criação
    public ContaBancaria(String agencia, String conta, String digito) {
        this.agencia = agencia;
        this.conta = conta;
        this.digito = digito;
    }

    // Getters e Setters

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDigito() {
        return digito;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }
}
