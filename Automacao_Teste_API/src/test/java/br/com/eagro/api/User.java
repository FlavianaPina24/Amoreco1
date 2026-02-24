package br.com.eagro.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private int id;
    private String nome;
    @JsonProperty("nome_da_mae")
    private String nomeDaMae;
    private int idade;
    @JsonProperty("conta_bancaria")
    private ContaBancaria contaBancaria;
    private String email;
    @JsonProperty("nome_social")
    private String nomeSocial;
    private String cpf;
    private String cnpj;

    // Construtor vazio para desserialização
    public User() {
    }

    // Construtor completo para facilitar a criação da massa de dados
    public User(int id, String nome, String nomeDaMae, int idade, ContaBancaria contaBancaria, String email, String nomeSocial, String cpf, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.nomeDaMae = nomeDaMae;
        this.idade = idade;
        this.contaBancaria = contaBancaria;
        this.email = email;
        this.nomeSocial = nomeSocial;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
