# language: pt
@order-review-v1
Funcionalidade: API de Orquestração de Revisão de Ordens V1

  Como um sistema consumidor,
  Eu quero buscar e gerenciar revisões de ordens.

  Cenário: Buscar uma revisão de ordem com sucesso
    Dado que o sistema está configurado para buscar a revisão da ordem "902019"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-review-step-orch/api/v1/revision/902019"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "numeroPedido"

  Cenário: Atualizar uma revisão de ordem com sucesso
    Dado que o sistema está configurado para atualizar a revisão da ordem "0000"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-order-review-step-orch/api/v1/revision/0000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Revisão atualizada com sucesso"

  Cenário: Criar uma revisão de ordem com sucesso
    Dado que o sistema está configurado para criar uma revisão de ordem
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-review-step-orch/api/v1/revision"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Revisão criada com sucesso"

  Cenário: Enviar uma notificação com sucesso
    Dado que o sistema está configurado para enviar uma notificação
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-review-step-orch/api/v1/notification"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Notificação enviada com sucesso"
