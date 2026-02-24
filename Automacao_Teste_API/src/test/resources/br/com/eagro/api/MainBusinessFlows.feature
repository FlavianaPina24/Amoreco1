# language: pt
@main-flow
Funcionalidade: Fluxos de Negócio Legados

  Como um sistema consumidor,
  Eu quero realizar as operações de CRUD em clientes de forma independente,
  Para garantir que a lógica de negócio principal é robusta e não depende da ordem dos testes.

  Cenário: Criação de um novo cliente com sucesso
    Given que o sistema está pronto para criar um novo cliente
    When uma requisição "POST" é enviada para o endpoint "/v1/clientes" com a chave de dados "novo_cliente_valido"
    Then a resposta deve ter o status 201
    And a resposta deve conter a mensagem "Cliente criado com sucesso"

  Cenário: Busca de um cliente existente
    Given que o cliente com ID 1 existe no sistema
    When uma requisição "GET" é enviada para o endpoint "/v1/clientes/1" com a chave de dados "VAZIO"
    Then a resposta deve ter o status 200
    And o corpo da resposta do cliente deve conter o nome "João Silva"

  Cenário: Alteração de um cliente existente
    Given que o cliente com ID 1 existe no sistema
    When uma requisição "PUT" é enviada para o endpoint "/v1/clientes/1" com a chave de dados "ATUALIZACAO_NOME_VALIDA"
    Then a resposta deve ter o status 200
    And a resposta deve conter a mensagem "Cliente atualizado"

  Cenário: Deleção de um cliente existente
    Given que o cliente com ID 1 existe no sistema
    When uma requisição "DELETE" é enviada para o endpoint "/v1/clientes/1" com a chave de dados "VAZIO"
    Then a resposta deve ter o status 204

  Cenário: Busca de um cliente que foi deletado
    Given que o cliente com ID 1 foi deletado do sistema
    When uma requisição "GET" é enviada para o endpoint "/v1/clientes/1" com a chave de dados "VAZIO"
    Then a resposta deve ter o status 404
    And a resposta deve conter a mensagem "Cliente não encontrado"

  Cenário: Busca por cliente com ID inexistente
    Given que o sistema está pronto para criar um novo cliente
    When uma requisição "GET" é enviada para o endpoint "/v1/clientes/999" com a chave de dados "VAZIO"
    Then a resposta deve ter o status 404
    And a resposta deve conter a mensagem "Cliente não encontrado"

  Cenário: Criação de cliente com CPF preenchido com zeros
    Given que o sistema está pronto para criar um novo cliente
    When uma requisição "POST" é enviada para o endpoint "/v1/clientes" com a chave de dados "CPF_9_DIGITOS" e a regra "COMPLETAR_CPF_COM_ZEROS"
    Then a resposta deve ter o status 201
    And a resposta deve conter a mensagem "Cliente criado com sucesso"

  Cenário: Criação de cliente com CNPJ numérico preenchido com zeros
    Given que o sistema está pronto para criar um novo cliente
    When uma requisição "POST" é enviada para o endpoint "/v1/clientes" com a chave de dados "CNPJ_12_DIGITOS" e a regra "COMPLETAR_CNPJ_COM_ZEROS"
    Then a resposta deve ter o status 201
    And a resposta deve conter a mensagem "Cliente criado com sucesso"

  Cenário: Criação de cliente com CNPJ Alfanumérico preenchido com zeros
    Given que o sistema está pronto para criar um novo cliente
    When uma requisição "POST" é enviada para o endpoint "/v1/clientes" com a chave de dados "CNPJ_ALFA_10_DIGITOS" e a regra "COMPLETAR_CNPJ_ALFA_COM_ZEROS"
    Then a resposta deve ter o status 201
    And a resposta deve conter a mensagem "Cliente criado com sucesso"
