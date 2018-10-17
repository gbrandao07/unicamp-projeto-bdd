package br.unicamp.ecommerce.model.input.impl;

import br.unicamp.ecommerce.model.input.RequestBodyInput;

/**
 * Armazena as informacoes necessarias do produto para requisicao no sistema dos Correios
 * 
 * @author gustavo
 *
 */
public class ProdutoInput implements RequestBodyInput {

	private String peso;
	private String largura;
	private String altura;
	private String comprimento;

	/**
	 * @return the peso
	 */
	public String getPeso() {
		return peso;
	}

	/**
	 * @param peso
	 *            the peso to set
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}

	/**
	 * @return the largura
	 */
	public String getLargura() {
		return largura;
	}

	/**
	 * @param largura
	 *            the largura to set
	 */
	public void setLargura(String largura) {
		this.largura = largura;
	}

	/**
	 * @return the altura
	 */
	public String getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(String altura) {
		this.altura = altura;
	}

	/**
	 * @return the comprimento
	 */
	public String getComprimento() {
		return comprimento;
	}

	/**
	 * @param comprimento
	 *            the comprimento to set
	 */
	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.unicamp.ecommerce.model.input.RequestBodyInput#toRequestBody()
	 */
	@Override
	public String toRequestBody() {
		StringBuilder str = new StringBuilder();
		str.append("\"&nVlPeso\":");
		if (peso != null && !peso.isEmpty()) {
			str.append("\"" + peso + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		str.append("\"&nVlComprimento\":");
		if (comprimento != null && !comprimento.isEmpty()) {
			str.append("\"" + comprimento + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		str.append("\"&nVlAltura\":");
		if (altura != null && !altura.isEmpty()) {
			str.append("\"" + altura + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		str.append("\"&nVlLargura\":");
		if (largura != null && !largura.isEmpty()) {
			str.append("\"" + largura + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		return str.toString();
	}
}
