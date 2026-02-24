# language: pt
@local-validation
Funcionalidade: API de Validação de Documentos

  Esquema do Cenário: <descricao>
    Dado um documento do cenário "<chave_cenario>" a ser validado
    Quando uma requisição de validação "POST" é enviada para "/v1/documento/validar"
    Então a resposta da validação deve ter o status <status>
    E o corpo da resposta da validação deve ter a mensagem "<mensagem>"

  Exemplos:
    | chave_cenario                   | status | mensagem                                                    | descricao                                                 |
    | VALIDACAO_CNPJ_ALFA_VALIDO      | 200    | Documento com formato válido                                | Validar CNPJ com caracteres alfanumericos                 |
    | VALIDACAO_CNPJ_CURTO            | 400    | CNPJ inválido: quantidade de dígitos incorreta              | Validar CNPJ com menos de 14 dígitos                      |
    | VALIDACAO_CNPJ_LONGO            | 400    | CNPJ inválido: quantidade de dígitos incorreta              | Validar CNPJ com mais de 14 dígitos                       |
    | VALIDACAO_CNPJ_COM_ESPECIAIS    | 400    | CNPJ inválido: caracteres especiais não permitidos          | Validar CNPJ com caracteres não alfanumericos             |
    | VALIDACAO_CPF_VALIDO            | 200    | Documento com formato válido                                | Validar CPF Com sucesso                                   |
    | VALIDACAO_CPF_CURTO             | 400    | CPF inválido: quantidade de dígitos incorreta               | Validar CPF com menos de 11 dígitos                       |
    | VALIDACAO_CPF_LONGO             | 400    | CPF inválido: quantidade de dígitos incorreta               | Validar CPF com mais de 11 dígitos                        |
    | VALIDACAO_CPF_COM_ESPECIAIS     | 400    | CPF inválido: caracteres especiais não permitidos           | Validar CPF com caracteres não numericos                  |
    | VALIDACAO_CNPJ_ALFA_DV_VALIDO   | 200    | Documento com formato válido                                | Validar CNPJ Alfanumérico com dígito verificador válido   |
    | VALIDACAO_CNPJ_ALFA_DV_INVALIDO | 400    | CNPJ inválido: dígito verificador não confere               | Validar CNPJ Alfanumérico com dígito verificador inválido |
