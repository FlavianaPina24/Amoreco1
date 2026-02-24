# language: pt
@api-proposta-v1
Funcionalidade: API de Proposta V1 - Testes de Consulta

  Como um sistema consumidor,
  Eu quero consultar propostas existentes na API de Propostas V1 por seu ID,
  Para garantir que a busca de dados está funcionando corretamente.

  Cenário: Consulta de uma proposta existente com sucesso
    Dado que uma proposta base existe no sistema com a chave de cenário "PROPOSTA_V1_CONSULTA_EXISTENTE"
    Quando uma requisição "GET" é enviada para o endpoint da proposta base
    Então a resposta deve ter o status 200
    E o corpo da resposta da proposta deve conter o nome do cliente "CLIENTE DE CONSULTA V1"
