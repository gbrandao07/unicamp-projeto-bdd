# language: pt
Funcionalidade: Consultar Status de Entrega
  Como um usuário do sistema Ecommerce
  Desejo consultar o status do meu pedido realizado
  Para que eu possa ver os detalhes de andamento da entrega

  Esquema do Cenário: Consultar status da entrega de um pedido
    Dado um usuario logado chamado <usuario>
    E um pedido composto de um identificador <identificador>
    Quando eu executar a funcionalidade de consulta do status da entrega
    Entao o sistema deve retornar as informações da entrega com <tipo>, <status> e <descricao>

    Exemplos: 
      | usuario   | identificador   | tipo  | status | descricao                                                  |
      | "Gustavo" | "SQ458226057BR" | "BDE" | "0"    | "Objeto entregue ao destinatário"                          |
      | "Cleomar" | "SQ458226058BR" | "BDE" | "2"    | "A entrega não pode ser efetuada - Carteiro não atendido"  |
      | "Pedro"   | "SQ458226059BR" | "BDE" | "24"   | "Objeto disponível para retirada em Caixa Postal"          |
      | "Tiago"   | "SQ458226060BR" | "BDE" | "23"   | "Objeto devolvido ao remetente"                            |
