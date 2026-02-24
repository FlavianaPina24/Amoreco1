# language: pt
@specialist-data-v1
Funcionalidade: API de Orquestração de Dados de Especialistas V1

  Como um sistema consumidor,
  Eu quero buscar e gerenciar dados de especialistas e clientes.

  Cenário: Buscar clientes de um especialista por ID de cliente com sucesso
    Dado que o sistema está configurado para buscar clientes por ID de cliente
    Quando uma requisição "GET" é enviada para o endpoint "/specialist-specialist-data-orch/api/v1/specialists/clients/by-client-id"
    Então a resposta deve ter o status 200

  Cenário: Deletar um cliente por ID com sucesso
    Dado que o sistema está configurado para deletar um cliente por ID
    Quando uma requisição "DELETE" é enviada para o endpoint "/specialist-specialist-data-orch/api/v1/specialists/clients/by-client-id"
    Então a resposta deve ter o status 204

  Cenário: Buscar documento de um cliente por agência e conta
    Dado que o sistema está configurado para buscar o documento do cliente da agência "1" e conta "3"
    Quando uma requisição "GET" é enviada para o endpoint "/specialist-specialist-data-orch/api/v1/specialists/clients/1/3"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "cpfCnpj"

  Cenário: Buscar novos clientes por nome
    Dado que o sistema está configurado para buscar novos clientes por nome
    Quando uma requisição "GET" é enviada para o endpoint "/specialist-specialist-data-orch/api/v1/specialists/new-clients/by-name" com os parâmetros
      | nome_parametro   | valor_parametro |
      | name             | Caom            |
      | page             | 1               |
      | perPage          | 10              |
      | includeSavedFlag | true            |
    Então a resposta deve ter o status 200

  Cenário: Buscar ordem de especialista por número da ordem
    Dado que o sistema está configurado para buscar a ordem de especialista "12"
    Quando uma requisição "GET" é enviada para o endpoint "/specialist-specialist-data-orch/api/v1/specialists/order/by-order-number/12"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "orderNumber"
