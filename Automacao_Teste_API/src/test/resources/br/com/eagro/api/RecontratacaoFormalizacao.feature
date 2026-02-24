# language: pt
@recontratacao-formalizacao
Funcionalidade: API de Orquestração de Recontratação e Formalização

  Como um sistema consumidor,
  Eu quero iniciar e gerenciar fluxos de recontratação.

  Cenário: Iniciar o fluxo de preenchimento do pedido com sucesso
    Dado que o sistema está configurado para iniciar um fluxo de preenchimento
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/orderFromOffer/0000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Fluxo iniciado com sucesso"

  Cenário: Buscar recontratação por ID com sucesso
    Dado que o sistema está configurado para buscar a recontratação com ID "000"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byId/000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "id"

  Cenário: Atualizar recontratação por ID com sucesso
    Dado que o sistema está configurado para atualizar a recontratação com ID "0000"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byId/0000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Recontratação atualizada com sucesso"

  Cenário: Buscar recontratação por ID de usuário com sucesso
    Dado que o sistema está configurado para buscar recontratações por ID de usuário
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byUserId"
    Então a resposta deve ter o status 200

  Cenário: Atualizar recontratação com dados do Agrotools
    Dado que o sistema está configurado para atualizar a recontratação com dados do Agrotools para a oferta "0000"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/agrotools/0000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Dados do Agrotools atualizados com sucesso"

  Cenário: Buscar ordem por número de referência com sucesso
    Dado que o sistema está configurado para buscar a ordem com número de referência "000"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/order/byReferenceNumber/000"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "numeroPedido"

  Cenário: Validar uma ordem com sucesso
    Dado que o sistema está configurado para validar a ordem "222"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/order/222/validation"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Ordem válida"

  Cenário: Atualizar custeio de uma ordem com sucesso
    Dado que o sistema está configurado para atualizar o custeio da ordem "000" a partir da oferta "001"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/order/000/fromRehiring/001/costing"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Custeio atualizado com sucesso"

  Cenário: Criar simulação a partir de uma oferta com sucesso
    Dado que o sistema está configurado para criar uma simulação a partir da oferta "0001"
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-recontratacao-formalizacao-orch/api/v1/simulationFromOffer/0001"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Simulação criada com sucesso"
