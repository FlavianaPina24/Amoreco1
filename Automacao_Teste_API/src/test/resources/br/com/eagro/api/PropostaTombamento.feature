# language: pt
@proposta-tombamento
Funcionalidade: API de Proposta - Tombamento

  Como um sistema consumidor,
  Eu quero consultar propostas específicas,
  Para garantir que o serviço de tombamento está funcionando.

  Esquema do Cenário: Consultar proposta de tombamento com sucesso
    Dado que o sistema está configurado para o teste de tombamento da proposta <id_proposta>
    Quando uma requisição "GET" é enviada para o endpoint "/proposta/<id_proposta>"
    Então a resposta deve ter o status 200

    Exemplos:
      | nome_teste                | id_proposta |
      | Tombamento TI Hibrida HO  | 413325      |
      | Tombamento TI Hibrida HO3 | 901326      |
