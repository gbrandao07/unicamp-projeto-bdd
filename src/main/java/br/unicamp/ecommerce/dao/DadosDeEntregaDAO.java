package br.unicamp.ecommerce.dao;

/**
 * Camada de persistencia para os dados de entrega providos pelo sistema dos Correios
 * 
 * @author gustavo
 *
 */
public interface DadosDeEntregaDAO {

	/**
	 * 
	 * @param valorFrete
	 * @param diasEntrega
	 * @return true caso tenha salvo com sucesso, false caso tenha ocorrido algum erro na insercao
	 * @throws Exception caso acesso ao banco nao esteja disponivel
	 */
	public boolean saveDadosDeEntrega(Double valorFrete, Integer diasEntrega) throws Exception;
}
