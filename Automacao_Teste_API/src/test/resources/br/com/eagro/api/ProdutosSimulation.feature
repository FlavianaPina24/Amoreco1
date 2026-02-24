# language: pt
@produtos-simulation
Funcionalidade: API de Simulação de Produtos

  Como um sistema consumidor,
  Eu quero buscar e gerenciar simulações de produtos.

  Cenário: Buscar todas as simulações com sucesso
    Dado que o sistema está configurado para buscar todas as simulações
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-simulation/api/v2/simulation" com os parâmetros
      | nome_parametro   | valor_parametro   |
      | status-future-use| true              |
      | x-agro-user-id   | 732.265.520-66    |
    Então a resposta deve ter o status 200

  Cenário: Gravar dados de uma simulação com sucesso
    Dado que o sistema está configurado para gravar uma simulação
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-simulation/api/v2/simulation"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Simulação gravada com sucesso"

  Cenário: Buscar uma simulação por ID com sucesso
    Dado que o sistema está configurado para buscar a simulação com ID "25413251"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-simulation/api/v2/simulation/25413251"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "numeroSimulacao"

  Cenário: Atualizar dados de uma simulação com sucesso
    Dado que o sistema está configurado para atualizar a simulação com ID "25412301"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-simulation/api/v2/simulation/25412301"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Simulação atualizada com sucesso"
