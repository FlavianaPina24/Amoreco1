# language: pt
@user-management-delegation-v1
Funcionalidade: API de Orquestração de Delegação de Usuários V1

  Como um sistema consumidor,
  Eu quero buscar e gerenciar delegações de especialistas.

  Cenário: Buscar delegações por email do especialista com sucesso
    Dado que o sistema está configurado para buscar delegações pelo email "e@e"
    Quando uma requisição "GET" é enviada para o endpoint "/user-management-delegation-orch/api/v1/delegations/by-email-specialist/e@e"
    Então a resposta deve ter o status 200

  Cenário: Delegar usuários especialistas com sucesso
    Dado que o sistema está configurado para delegar usuários especialistas
    Quando uma requisição "POST" é enviada para o endpoint "/user-management-delegation-orch/api/v1/specialists/users/for-delegation"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Delegação realizada com sucesso"

  Cenário: Buscar cliente para delegação com sucesso
    Dado que o sistema está configurado para buscar o cliente "41" para o especialista "41"
    Quando uma requisição "GET" é enviada para o endpoint "/user-management-delegation-orch/api/v1/specialists/client/for-delegation/41/41"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "name"

  Cenário: Buscar ordens para delegação por email do especialista
    Dado que o sistema está configurado para buscar ordens para delegação pelo email "W@e"
    Quando uma requisição "GET" é enviada para o endpoint "/user-management-delegation-orch/api/v1/specialists/orders/for-delegation/by-email-specialist/W@e"
    Então a resposta deve ter o status 200

  Cenário: Revogar delegações em lote com sucesso
    Dado que o sistema está configurado para revogar delegações em lote
    Quando uma requisição "POST" é enviada para o endpoint "/user-management-delegation-orch/api/v1/batch-revokes"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Delegações revogadas com sucesso"

  Cenário: Criar uma delegação com sucesso
    Dado que o sistema está configurado para criar uma delegação
    Quando uma requisição "POST" é enviada para o endpoint "/user-management-delegation-orch/api/v1/delegation"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Delegação criada com sucesso"

  Cenário: Buscar uma delegação por ID com sucesso
    Dado que o sistema está configurado para buscar a delegação "123"
    Quando uma requisição "GET" é enviada para o endpoint "/user-management-delegation-orch/api/v1/delegation/123"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "id"

  Cenário: Atualizar uma delegação por ID com sucesso
    Dado que o sistema está configurado para atualizar a delegação "123"
    Quando uma requisição "PUT" é enviada para o endpoint "/user-management-delegation-orch/api/v1/delegation/123"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Delegação atualizada com sucesso"

  Cenário: Deletar uma delegação por ID com sucesso
    Dado que o sistema está configurado para deletar a delegação "123"
    Quando uma requisição "DELETE" é enviada para o endpoint "/user-management-delegation-orch/api/v1/delegation/123"
    Então a resposta deve ter o status 204
