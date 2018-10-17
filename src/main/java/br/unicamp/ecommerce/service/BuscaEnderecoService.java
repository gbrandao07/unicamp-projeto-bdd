package br.unicamp.ecommerce.service;

import br.unicamp.ecommerce.Configuracao;
import br.unicamp.ecommerce.model.xml.Endereco;

/**
 * Camada de acesso ao servico externo do sistema de busca de endereco
 * 
 * @author gustavo
 *
 */
public class BuscaEnderecoService {

	private Configuracao configuracao;

	/**
	 * A partir do CEP recupera o endereco a partir do sistema externo
	 * 
	 * @param cep
	 * @return
	 * @throws Exception
	 */
	public Endereco buscar(String cep) throws Exception {
		String url = String.format("%s/%s/xml", configuracao.getBuscarEnderecoUrl(), cep);
		return new RemoteService().getAndParseXml(url, Endereco.class);
	}
}
