package br.unicamp.ecommerce.model.input;

/**
 * Interface que define os métodos necessários para uma requisição post
 * 
 * @author gustavo
 *
 */
public interface RequestBodyInput {

	/**
	 * A partir dos atributos do objeto, retorna uma String com os parametros a serem 
	 * inseridos no body da requisicao post 
	 * 
	 * @return a String com os parametros
	 */
	String toRequestBody();
}
