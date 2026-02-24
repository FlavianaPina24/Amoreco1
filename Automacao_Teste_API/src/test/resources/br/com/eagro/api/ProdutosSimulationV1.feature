# language: pt
@produtos-simulation-v1
Funcionalidade: API de Simulação de Produtos V1

  Como um sistema consumidor,
  Eu quero buscar simulações de produtos.

  Cenário: Buscar todas as simulações V1 com sucesso
    Dado que o sistema está configurado para buscar todas as simulações V1
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-simulation/api/v1/simulation"
    Então a resposta deve ter o status 200
