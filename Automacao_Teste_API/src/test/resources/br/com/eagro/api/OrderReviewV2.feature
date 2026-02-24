# language: pt
@order-review-v2
Funcionalidade: API de Orquestração de Revisão de Ordens V2

  Como um sistema consumidor,
  Eu quero buscar e gerenciar revisões de ordens na V2.

  Cenário: Buscar uma revisão de ordem V2 com sucesso
    Dado que o sistema está configurado para buscar a revisão V2 da ordem "901067"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-review-step-orch/api/v2/revision/901067"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "numeroPedido"
