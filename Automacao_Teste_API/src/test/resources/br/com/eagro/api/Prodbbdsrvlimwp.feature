# language: pt
@prodbbdsrvlimwp
Funcionalidade: prodbbdsrvlimwp

  Como um consumidor da API,
  Eu quero verificar o status do serviço,
  Para garantir que ele está operacional.

  Cenário: Verificar o status do serviço (Health Check)
    Dado que eu tenho as credenciais para o serviço de Limite Climático
    Quando uma requisição "GET" é enviada para o endpoint "/v2/orders/health"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "status"

  Cenário: Cancelar uma ordem via backoffice
    Dado que o sistema está configurado para cancelar uma ordem
    Quando uma requisição "POST" é enviada para o endpoint "/v2/orders/backoffice/cancel"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Ordem cancelada com sucesso"

  Cenário: Consultar o status de uma ordem
    Dado que o sistema está configurado para consultar o status de uma ordem
    Quando uma requisição "GET" é enviada para o endpoint "/v2/orders/backoffice/80834236000162/status"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "status"

  Cenário: Criar uma ordem via backoffice
    Dado que o sistema está configurado para criar uma ordem
    Quando uma requisição "POST" é enviada para o endpoint "/v2/orders/backoffice"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Ordem criada com sucesso"

  Cenário: Cancelar uma ordem V1 via backoffice
    Dado que o sistema está configurado para cancelar uma ordem V1
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-bbd-agro-srv-limite-clim-wrapper/api/v1/order/backoffice/cancel"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Ordem V1 cancelada com sucesso"
