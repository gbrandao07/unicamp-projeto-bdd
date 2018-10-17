package br.unicamp.ecommerce.model.xml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Representa uma estrutura com informacoes do status da encomenda na estrutura xml
 * 
 * @author gustavo
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "versao", "qtd", "tipoPesquisa", "tipoResultado", "objeto" })
@XmlRootElement(name = "sroxml")
public class StatusEncomenda {

	@XmlElement(required = true)
	private BigDecimal versao;

	private int qtd;

	@XmlElement(name = "TipoPesquisa", required = true)
	private String tipoPesquisa;

	@XmlElement(name = "TipoResultado", required = true)

	private String tipoResultado;

	@XmlElement(required = true)
	private StatusEncomenda.Objeto objeto;

	/**
	 * Obtém o valor da propriedade versao.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getVersao() {
		return versao;
	}

	/**
	 * Obtém o valor da propriedade qtd.
	 * 
	 */
	public int getQtd() {
		return qtd;
	}

	/**
	 * Obtém o valor da propriedade tipoPesquisa.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoPesquisa() {
		return tipoPesquisa;
	}

	/**
	 * Obtém o valor da propriedade tipoResultado.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoResultado() {
		return tipoResultado;
	}

	/**
	 * Obtém o valor da propriedade objeto.
	 * 
	 * @return possible object is {@link Sroxml.Objeto }
	 * 
	 */
	public StatusEncomenda.Objeto getObjeto() {
		return objeto;
	}

	/**
	 * <p>
	 * Classe Java de anonymous complex type.
	 * 
	 * <p>
	 * O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro
	 * desta classe.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="evento" maxOccurs="unbounded">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                   &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
	 *                   &lt;element name="hora" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
	 *                   &lt;element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="local" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                   &lt;element name="cidade" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="uf" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "numero", "evento" })
	public static class Objeto {

		@XmlElement(required = true)
		protected String numero;
		@XmlElement(required = true)
		protected List<StatusEncomenda.Objeto.Evento> evento;

		/**
		 * Obtém o valor da propriedade numero.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getNumero() {
			return numero;
		}

		/**
		 * Gets the value of the evento property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot.
		 * Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
		 * for the evento property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getEvento().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Sroxml.Objeto.Evento }
		 * 
		 * 
		 */
		public List<StatusEncomenda.Objeto.Evento> getEvento() {
			if (evento == null) {
				evento = new ArrayList<StatusEncomenda.Objeto.Evento>();
			}
			return this.evento;
		}

		/**
		 * <p>
		 * Classe Java de anonymous complex type.
		 * 
		 * <p>
		 * O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro
		 * desta classe.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
		 *         &lt;element name="hora" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
		 *         &lt;element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="local" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *         &lt;element name="cidade" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="uf" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "tipo", "status", "data", "hora", "descricao", "local", "codigo", "cidade",
				"uf" })
		public static class Evento {

			@XmlElement(required = true)
			protected String tipo;
			protected int status;
			@XmlElement(required = true)
			@XmlSchemaType(name = "dateTime")
			protected XMLGregorianCalendar data;
			@XmlElement(required = true)
			@XmlSchemaType(name = "dateTime")
			protected XMLGregorianCalendar hora;
			@XmlElement(required = true)
			protected String descricao;
			@XmlElement(required = true)
			protected String local;
			protected int codigo;
			@XmlElement(required = true)
			protected String cidade;
			@XmlElement(required = true)
			protected String uf;

			/**
			 * Obtém o valor da propriedade tipo.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getTipo() {
				return tipo;
			}

			/**
			 * Obtém o valor da propriedade status.
			 * 
			 */
			public int getStatus() {
				return status;
			}

			/**
			 * Obtém o valor da propriedade data.
			 * 
			 * @return possible object is {@link XMLGregorianCalendar }
			 * 
			 */
			public XMLGregorianCalendar getData() {
				return data;
			}

			/**
			 * Obtém o valor da propriedade hora.
			 * 
			 * @return possible object is {@link XMLGregorianCalendar }
			 * 
			 */
			public XMLGregorianCalendar getHora() {
				return hora;
			}

			/**
			 * Obtém o valor da propriedade descricao.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getDescricao() {
				return descricao;
			}

			/**
			 * Obtém o valor da propriedade local.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getLocal() {
				return local;
			}

			/**
			 * Obtém o valor da propriedade codigo.
			 * 
			 */
			public int getCodigo() {
				return codigo;
			}

			/**
			 * Obtém o valor da propriedade cidade.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCidade() {
				return cidade;
			}

			/**
			 * Obtém o valor da propriedade uf.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getUf() {
				return uf;
			}
		}
	}
}
