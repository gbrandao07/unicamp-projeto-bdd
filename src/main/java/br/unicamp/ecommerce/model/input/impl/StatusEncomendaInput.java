package br.unicamp.ecommerce.model.input.impl;

import br.unicamp.ecommerce.model.input.RequestBodyInput;

/**
 * Armazena as informacoes necessarias da encomenda para requisicao no sistema
 * dos Correios
 * 
 * @author gustavo
 *
 */
public class StatusEncomendaInput implements RequestBodyInput {

	private String usuario;
	private String identificador;

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.unicamp.ecommerce.model.input.RequestBodyInput#toRequestBody()
	 */
	@Override
	public String toRequestBody() {
		StringBuilder str = new StringBuilder();
		str.append("\"&nVlUsuario\":");
		if (usuario != null && !usuario.isEmpty()) {
			str.append("\"" + usuario + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		str.append("\"&nVlIdentificador\":");
		if (identificador != null && !identificador.isEmpty()) {
			str.append("\"" + identificador + "\"");
		} else {
			str.append("\"" + "null" + "\"");
		}
		return str.toString();
	}
}
