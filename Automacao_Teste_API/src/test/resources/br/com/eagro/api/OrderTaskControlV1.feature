# language: pt
@order-task-control-v1
Funcionalidade: API de Controle de Tarefas de Ordens V1

  Como um sistema consumidor,
  Eu quero buscar análises de tarefas de ordens.

  Cenário: Buscar uma análise de tarefa de ordem com sucesso
    Dado que o sistema está configurado para buscar a análise de tarefa da ordem "900908"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-task-control/api/v1/task-analysis/900908"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "orderId"
