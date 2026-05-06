# language: pt
@erros-api
Funcionalidade: Resiliência e Tratamento de Erros da API
  Como um Especialista em Qualidade (QA),
  Eu quero testar fluxos de exceção (Cenários Negativos),
  Para garantir que a API é resiliente e retorna os HTTP Status Codes adequados (400, 401, 404).

  Contexto:
    Dado que a API de simulação de erros está configurada

  Cenário: 1. Acesso negado por falta de Token de Autorização (401 Unauthorized)
    Quando uma requisição "GET" é enviada para o endpoint "/api/protegida" sem token
    Então a resposta deve ter o status 401
    E a resposta deve conter a mensagem "Não autorizado: Token ausente ou inválido"

  Cenário: 2. Acesso a um recurso inexistente (404 Not Found)
    Quando uma requisição "GET" é enviada para o endpoint "/api/clientes/9999999"
    Então a resposta deve ter o status 404
    E a resposta deve conter a mensagem "Recurso não encontrado no servidor"

  Cenário: 3. Envio de payload JSON malformado (400 Bad Request)
    Quando uma requisição "POST" com payload malformado é enviada para "/api/clientes"
    Então a resposta deve ter o status 400
    E a resposta deve conter a mensagem "Bad Request: Estrutura do JSON inválida"