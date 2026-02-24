# language: pt
@order-timeline
Funcionalidade: API de Timeline de Ordens

  Como um sistema consumidor,
  Eu quero gerenciar os steps de formalização da timeline.

  Cenário: Criar steps de formalização com sucesso
    Dado que o sistema está configurado para criar steps de formalização
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-timeline/api/v1/formalization/steps"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Steps criados com sucesso"

  Cenário: Buscar um step de formalização com sucesso
    Dado que o sistema está configurado para buscar o step da ordem "3"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-timeline/api/v1/formalization/step/3"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "numeroPedido"
