package br.unicamp.ecommerce.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Representa uma estrutura com informacoes do preco e prazo no xml
 * 
 * @author gustavo
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "servicos"
})
@XmlRootElement(name = "PrecoPrazo")
public class PrecoPrazo {

	@XmlElement(name = "Servicos", required = true)
    protected PrecoPrazo.Servicos servicos;

    /**
     * Obtém o valor da propriedade servicos.
     * 
     * @return
     *     possible object is
     *     {@link PrecoPrazo.Servicos }
     *     
     */
    public PrecoPrazo.Servicos getServicos() {
        return servicos;
    }

    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="cServico">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="Valor" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PrazoEntrega" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="ValorMaoPropria" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ValorAvisoRecebimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ValorValorDeclarado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="EntregaDomiciliar" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="EntregaSabado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Erro" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="MsgErro" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ValorSemAdicionais" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="obsFim" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    @XmlType(name = "", propOrder = {
        "cServico"
    })
    public static class Servicos {

        @XmlElement(required = true)
        protected PrecoPrazo.Servicos.CServico cServico;

        /**
         * Obtém o valor da propriedade cServico.
         * 
         * @return
         *     possible object is
         *     {@link PrecoPrazo.Servicos.CServico }
         *     
         */
        public PrecoPrazo.Servicos.CServico getCServico() {
            return cServico;
        }

        /**
         * <p>Classe Java de anonymous complex type.
         * 
         * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="Valor" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="PrazoEntrega" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="ValorMaoPropria" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ValorAvisoRecebimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ValorValorDeclarado" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="EntregaDomiciliar" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="EntregaSabado" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Erro" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="MsgErro" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ValorSemAdicionais" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="obsFim" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "codigo",
            "valor",
            "prazoEntrega",
            "valorMaoPropria",
            "valorAvisoRecebimento",
            "valorValorDeclarado",
            "entregaDomiciliar",
            "entregaSabado",
            "erro",
            "msgErro",
            "valorSemAdicionais",
            "obsFim"
        })
        public static class CServico {

            @XmlElement(name = "Codigo")
            protected int codigo;
            @XmlElement(name = "Valor", required = true)
            protected String valor;
            @XmlElement(name = "PrazoEntrega")
            protected int prazoEntrega;
            @XmlElement(name = "ValorMaoPropria", required = true)
            protected String valorMaoPropria;
            @XmlElement(name = "ValorAvisoRecebimento", required = true)
            protected String valorAvisoRecebimento;
            @XmlElement(name = "ValorValorDeclarado", required = true)
            protected String valorValorDeclarado;
            @XmlElement(name = "EntregaDomiciliar", required = true)
            protected String entregaDomiciliar;
            @XmlElement(name = "EntregaSabado", required = true)
            protected String entregaSabado;
            @XmlElement(name = "Erro")
            protected String erro;
            @XmlElement(name = "MsgErro", required = true)
            protected String msgErro;
            @XmlElement(name = "ValorSemAdicionais", required = true)
            protected String valorSemAdicionais;
            @XmlElement(required = true)
            protected String obsFim;

            /**
             * Obtém o valor da propriedade codigo.
             * 
             */
            public int getCodigo() {
                return codigo;
            }

            /**
             * Obtém o valor da propriedade valor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValor() {
                return valor;
            }

            /**
             * Obtém o valor da propriedade prazoEntrega.
             * 
             */
            public int getPrazoEntrega() {
                return prazoEntrega;
            }

            /**
             * Obtém o valor da propriedade valorMaoPropria.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValorMaoPropria() {
                return valorMaoPropria;
            }

            /**
             * Obtém o valor da propriedade valorAvisoRecebimento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValorAvisoRecebimento() {
                return valorAvisoRecebimento;
            }

            /**
             * Obtém o valor da propriedade valorValorDeclarado.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValorValorDeclarado() {
                return valorValorDeclarado;
            }

            /**
             * Obtém o valor da propriedade entregaDomiciliar.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEntregaDomiciliar() {
                return entregaDomiciliar;
            }

            /**
             * Obtém o valor da propriedade entregaSabado.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEntregaSabado() {
                return entregaSabado;
            }

            /**
             * Obtém o valor da propriedade erro.
             * 
             */
            public String getErro() {
                return erro;
            }

            /**
             * Obtém o valor da propriedade msgErro.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMsgErro() {
                return msgErro;
            }

            /**
             * Obtém o valor da propriedade valorSemAdicionais.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValorSemAdicionais() {
                return valorSemAdicionais;
            }

            /**
             * Obtém o valor da propriedade obsFim.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getObsFim() {
                return obsFim;
            }

            /**
             * 
             * @return
             */
            public boolean hasError() {
        		return !"0".equals(erro);
        	}
        }

    }
}
