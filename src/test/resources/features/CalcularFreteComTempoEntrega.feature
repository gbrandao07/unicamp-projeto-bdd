# language: pt
Funcionalidade: Calcular Frete e Tempo de Entrega
  Como um usuário do sistema Ecommerce
  Desejo calcular o valor do frete e o tempo de entrega a partir do meu CEP
  Para que eu possa realizar o pedido já com essas informações

  Esquema do Cenário: Calcular frete e tempo de entrega previsto com endereco e produto valido
    Dado um produto com peso "10" kg
    E largura "20" cm
    E altura "20" cm
    E comprimento "20" cm
    E o CEP origem seja "13087567"
    E o CEP destino seja "13087567"
    E o Codigo do Serviço seja <codServico>
    Quando eu executar a funcionalidade de frete
    Entao o sistema deve retornar o preco <preco> e o tempo <tempo>
    E o sistema deve armazenar o preco e o tempo no banco de dados

    Exemplos: 
      | codServico | preco   | tempo |
      | "40010"    | "49.50" | "2"   |
      | "40215"    | "68.90" | "1"   |
      | "41106"    | "40.25" | "5"   |

  Esquema do Cenário: Atributos obrigatórios não-informados ou inválidos
    Dado um produto com peso <peso> kg
    E largura <largura> cm
    E altura <altura> cm
    E comprimento <comprimento> cm
    E o CEP origem seja <cepOrigem>
    E o CEP destino seja <cepDestino>
    E o Codigo do Serviço seja <codServico>
    Quando eu executar a funcionalidade de frete
    Entao o sistema deve retornar um erro com código <codErro> e mensagem <msgErro>
    E solicitar ao usuario a correcao do valores

    Exemplos: 
      | peso | largura | altura | comprimento | cepOrigem  | cepDestino | codServico | codErro | msgErro                                                                                        |
      | "0"  | "20"    | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "-888"  | "Não foi encontrada precificação. ERP-031: Nao informado o peso, a quantidade ou o valor(-1)." |
      | "-1" | "20"    | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "-888"  | "Não foi encontrada precificação. ERP-031: Nao informado o peso, a quantidade ou o valor(-1)." |
      | ""   | "20"    | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "99"    | "Erro inesperado. Descrição: Input string was not in a correct format."                        |
      | "10" | "0"     | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "-20"   | "A largura nao pode ser inferior a 11 cm."                                                     |
      | "10" | "-1"    | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "-20"   | "A largura nao pode ser inferior a 11 cm."                                                     |
      | "10" | ""      | "20"   | "20"        | "13761446" | "13087567" | "40010"    | "99"    | "Erro inesperado. Descrição: Input string was not in a correct format."                        |
      | "10" | "20"    | "0"    | "20"        | "13761446" | "13087567" | "40010"    | "-18"   | "A altura nao pode ser inferior a 2 cm."                                                       |
      | "10" | "20"    | "-1"   | "20"        | "13761446" | "13087567" | "40010"    | "-18"   | "A altura nao pode ser inferior a 2 cm."                                                       |
      | "10" | "20"    | ""     | "20"        | "13761446" | "13087567" | "40010"    | "99"    | "Erro inesperado. Descrição: Input string was not in a correct format."                        |
      | "10" | "20"    | "20"   | "0"         | "13761446" | "13087567" | "40010"    | "-22"   | "O comprimento nao pode ser inferior a 16 cm."                                                 |
      | "10" | "20"    | "20"   | "-1"        | "13761446" | "13087567" | "40010"    | "-22"   | "O comprimento nao pode ser inferior a 16 cm."                                                 |
      | "10" | "20"    | "20"   | ""          | "13761446" | "13087567" | "40010"    | "99"    | "Erro inesperado. Descrição: Input string was not in a correct format."                        |
      | "10" | "20"    | "20"   | "20"        | "0"        | "13087567" | "40010"    | "-2"    | "CEP de origem invalido."                                                                      |
      | "10" | "20"    | "20"   | "20"        | "-1"       | "13087567" | "40010"    | "-2"    | "CEP de origem invalido."                                                                      |
      | "10" | "20"    | "20"   | "20"        | ""         | "13087567" | "40010"    | "-2"    | "CEP de origem invalido."                                                                      |
      | "10" | "20"    | "20"   | "20"        | "13761446" | "0"        | "40010"    | "-3"    | "CEP de destino invalido."                                                                     |
      | "10" | "20"    | "20"   | "20"        | "13761446" | "-1"       | "40010"    | "-3"    | "CEP de destino invalido."                                                                     |
      | "10" | "20"    | "20"   | "20"        | "13761446" | ""         | "40010"    | "-3"    | "CEP de destino invalido."                                                                     |
      | "10" | "20"    | "20"   | "20"        | "13761446" | "13087567" | "0"        | "001"   | "Codigo de servico invalido."                                                                  |
      | "10" | "20"    | "20"   | "20"        | "13761446" | "13087567" | "-1"       | "001"   | "Codigo de servico invalido."                                                                  |
      | "10" | "20"    | "20"   | "20"        | "13761446" | "13087567" | ""         | "99"    | "Erro inesperado. Descrição: Input string was not in a correct format."                        |

  Cenario: Erro no sistema dos Correios
    Dado um produto com peso "10" kg
    E largura "20" cm
    E altura "20" cm
    E comprimento "20" cm
    E o CEP origem seja "13761446"
    E o CEP destino seja "13087567"
    E o Codigo do Serviço seja "40010"
    Quando eu executar a funcionalidade de frete com o sistema dos Correios fora
    Entao o sistema deve retornar um erro com a mensagem contendo:
      """
      Serviço indisponivel
      """

  Cenario: Erro no acesso banco de dados do ecommerce
    Dado um produto com peso "10" kg
    E largura "20" cm
    E altura "20" cm
    E comprimento "20" cm
    E o CEP origem seja "13761446"
    E o CEP destino seja "13087567"
    E o Codigo do Serviço seja "40010"
    Quando eu executar a funcionalidade de frete ocorrendo erro ao acessar a base do ecommerce
    Entao o sistema deve retornar um erro com a mensagem contendo:
      """
      Ocorreu um erro interno. Problemas ao acessar o banco de dados.
      """
