# language: pt
@order-timeline-v1
Funcionalidade: API de Orquestração de Timeline de Ordens V1

  Como um sistema consumidor,
  Eu quero construir e gerenciar timelines de ordens.

  Cenário: Construir uma timeline de ordem com sucesso
    Dado que o sistema está configurado para construir a timeline da ordem "0"
    Quando uma requisição "PUT" é enviada para o endpoint "/produtos-order-timeline-orch/api/v1/timeline/build/0"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Timeline construída com sucesso"

  Cenário: Reportar uma tarefa complementar com sucesso
    Dado que o sistema está configurado para reportar uma tarefa complementar
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-timeline-orch/api/v1/timeline/report-complementary-task"
    Então a resposta deve ter o status 200
    E a resposta deve conter a mensagem "Tarefa reportada com sucesso"

  Cenário: Buscar a timeline de uma ordem com sucesso
    Dado que o sistema está configurado para buscar a timeline da ordem "68f14eae3abf8b61f853b177"
    Quando uma requisição "GET" é enviada para o endpoint "/produtos-order-timeline-orch/api/v1/timeline/68f14eae3abf8b61f853b177"
    Então a resposta deve ter o status 200
    E a resposta deve conter a propriedade "id"

  Cenário: Criar um parâmetro de deadline com sucesso
    Dado que o sistema está configurado para criar um parâmetro de deadline
    Quando uma requisição "POST" é enviada para o endpoint "/produtos-order-timeline-orch/api/v1/parameters/deadline"
    Então a resposta deve ter o status 201
    E a resposta deve conter a mensagem "Parâmetro criado com sucesso"
