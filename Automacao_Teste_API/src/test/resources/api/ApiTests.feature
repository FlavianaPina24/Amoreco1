# language: pt
Funcionalidade: Testes da API JSONPlaceholder

  Cenário: Criar um novo post com sucesso
    Dado que o corpo da requisição é:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    Quando uma requisição POST é enviada para "/posts"
    Então o código de status da resposta deve ser 201
    E o corpo da resposta deve conter a propriedade "id"

  Cenário: Atualizar um post existente
    Dado que o corpo da requisição é:
      """
      {
        "id": 1,
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    Quando uma requisição PUT é enviada para "/posts/1"
    Então o código de status da resposta deve ser 200
    E o corpo da resposta deve conter a propriedade "id"

  Cenário: Obter um post existente
    Quando uma requisição GET é enviada para "/posts/1"
    Então o código de status da resposta deve ser 200
    E o corpo da resposta deve conter a propriedade "userId"

  Cenário: Tentar criar um post com corpo inválido
    Dado que o corpo da requisição é:
      """
      {invalid_json}
      """
    Quando uma requisição POST é enviada para "/posts"
    Então o código de status da resposta deve ser 500

  Cenário: Tentar acessar um endpoint protegido/inexistente
    Quando uma requisição GET é enviada para "/protected"
    Então o código de status da resposta deve ser 404

  Cenário: Tentar acessar um endpoint que simula erro interno
    Quando uma requisição GET é enviada para "/erro500"
    Então o código de status da resposta deve ser 404

  Cenário: Tentar acessar um endpoint que simula serviço indisponível
    Quando uma requisição GET é enviada para "/erro503"
    Então o código de status da resposta deve ser 404
