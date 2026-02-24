# language: pt
@api-proposta-v1
Funcionalidade: API de Proposta V1 - Testes de Criação

  Como um sistema consumidor,
  Eu quero criar novas propostas na API de Propostas V1,
  Para garantir que as regras de negócio de criação estão sendo aplicadas corretamente.

  Contexto:
    Dado que o sistema está configurado para a API de Proposta V1

  Esquema do Cenário: Validação da criação de propostas
    Quando uma requisição "POST" é enviada para o endpoint "/proposta" com a chave de cenário "<chave_cenario>"
    Então a resposta deve ter o status <status>
    E a resposta deve conter a mensagem "<mensagem>"

    Exemplos:
      | descricao                                       | chave_cenario                | status | mensagem                                         |
      | Criação com sucesso                             | PROPOSTA_V1_CRIACAO_VALIDA   | 201    | Proposta recebida com sucesso                    |
      | Erro ao criar com CPF curto                     | PROPOSTA_V1_CPF_CURTO        | 400    | CPF/CNPJ inválido: quantidade de dígitos incorreta |
      | Erro ao criar com CNPJ com caracteres especiais | PROPOSTA_V1_CNPJ_ESPECIAL    | 400    | CPF/CNPJ inválido: caracteres especiais não permitidos |
