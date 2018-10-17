package br.unicamp.ecommerce.service;

import br.unicamp.ecommerce.Configuracao;
import br.unicamp.ecommerce.dao.DadosDeEntregaDAO;
import br.unicamp.ecommerce.model.input.impl.ProdutoInput;
import br.unicamp.ecommerce.model.input.impl.StatusEncomendaInput;
import br.unicamp.ecommerce.model.xml.PrecoPrazo;
import br.unicamp.ecommerce.model.xml.StatusEncomenda;

/**
 * Camada de acesso ao servico externo do sistema dos Correios
 * 
 * @author gustavo
 *
 */
public class CorreiosService {

	private Configuracao configuracao;
	private DadosDeEntregaDAO dao;
	
	/**
	 * 
	 * @param produto
	 * @param cepOrigem
	 * @param cepDestino
	 * @param codServico
	 * @return
	 * @throws Exception
	 */
	public PrecoPrazo buscarCalculoTempoFrete(ProdutoInput produto, String cepOrigem, String cepDestino, String codServico) throws Exception {
		
		String url = configuracao.getConsultaPrecoPrazoUrl();
		String requestBody = produto.toRequestBody() +
		  					  "\"&ncepOrigem\":" + "\"" + cepOrigem + "\"" + 
		  					  "\"&ncepDestino\":" + "\"" + cepDestino + "\"" +  
		  					  "\"&ncodServico\":" + "\"" + codServico + "\"";
		
		PrecoPrazo resultEntity = new RemoteService().postAndParseXml(url, requestBody, PrecoPrazo.class);

	    // salva no banco apenas se a requisicao voltar os dados com sucesso
	    if (!resultEntity.getServicos().getCServico().hasError()) {
	    	dao.saveDadosDeEntrega(Double.valueOf(resultEntity.getServicos().getCServico().getValor()), (int) resultEntity.getServicos().getCServico().getPrazoEntrega());
	    }
	    
		return resultEntity;
	}
	
	/**
	 * 
	 * @param statusEncomendaInput
	 * @return
	 * @throws Exception
	 */
	public StatusEncomenda buscarStatusEncomenda(StatusEncomendaInput statusEncomendaInput) throws Exception {
		
	    String url = configuracao.getStatusEntregaUrl();
	   
		StatusEncomenda resultEntity = new RemoteService().postAndParseXml(url, statusEncomendaInput.toRequestBody(), StatusEncomenda.class);

		return resultEntity;
	}
}
