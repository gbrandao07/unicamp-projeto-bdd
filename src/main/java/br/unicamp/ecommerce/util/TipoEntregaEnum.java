package br.unicamp.ecommerce.util;

/**
 * Enum com os tipos de entrega validos dos Correios
 * 
 * @author gustavo
 *
 */
public enum TipoEntregaEnum {

	PACVAREJO("41106"), 
	SEDEXVAREJO("40010"),
	SEDEX10VAREJO("40215"), 
	NONE_0("0"),
	NONE_NEGATIVE_1("-1"),
	NONE_NULL("null");

	String codigo;

	private TipoEntregaEnum(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * 
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public static TipoEntregaEnum fromString(String codigo) {
		switch (codigo) {
		case "41106":
			return PACVAREJO;
		case "40010":
			return SEDEXVAREJO;
		case "40215":
			return SEDEX10VAREJO;
		case "0":
			return NONE_0;
		case "-1":
			return NONE_NEGATIVE_1;
		default:
			return TipoEntregaEnum.NONE_NULL;
		}
	}
}
