# language: pt
@recontratacao-formalizacao-v1
Funcionalidade: API de Recontratação e Formalização V1

  Como um sistema consumidor,
  Eu quero criar e gerenciar ordens de recontratação.

  Cenário: Criar uma ordem de recontratação com sucesso
    Dado que o sistema está configurado para criar uma ordem de recontratação V1
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao/api/v1/order"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Ordem criada com sucesso"
