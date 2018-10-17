package br.unicamp.ecommerce.endereco.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.WireMockServer;

import br.unicamp.ecommerce.Configuracao;
import br.unicamp.ecommerce.dao.DadosDeEntregaDAO;
import br.unicamp.ecommerce.model.input.impl.ProdutoInput;
import br.unicamp.ecommerce.model.xml.PrecoPrazo;
import br.unicamp.ecommerce.service.CorreiosService;
import br.unicamp.ecommerce.util.TipoEntregaEnum;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class CalcularFreteComTempoEntregaSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@Mock
	DadosDeEntregaDAO dao;
	
	@InjectMocks
	private CorreiosService correiosService;

	private ProdutoInput produto;

	private String cepOrigem;
	
	private String cepDestino;

	private PrecoPrazo precoPrazo;
	
	private TipoEntregaEnum codServico;

	private Throwable throwable;

	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9877);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9877/ws/correios");
		produto = new ProdutoInput();
		cepOrigem = null;
		cepDestino = null;
		precoPrazo = null;
		codServico = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}

	@Dado("^um produto com peso \"([^\"]*)\" kg$")
	public void dado_um_produto_com_peso_kg(String peso) throws Throwable {
		produto.setPeso(peso);
	}

	@Dado("^largura \"([^\"]*)\" cm$")
	public void e_largura_cm(String largura) throws Throwable {
		produto.setLargura(largura);
	}

	@Dado("^altura \"([^\"]*)\" cm$")
	public void e_altura_cm(String altura) throws Throwable {
		produto.setAltura(altura);
	}

	@Dado("^comprimento \"([^\"]*)\" cm$")
	public void e_comprimento_cm(String comprimento) throws Throwable {
	    produto.setComprimento(comprimento);
	}

	@Dado("^o CEP origem seja \"([^\"]*)\"$")
	public void o_CEP_origem_seja(String cepOrigem) throws Throwable {
		if (cepOrigem != null && !cepOrigem.isEmpty()) {
			this.cepOrigem = cepOrigem;
		} else {
			this.cepOrigem = "null";
		}
	}

	@Dado("^o CEP destino seja \"([^\"]*)\"$")
	public void e_o_CEP_destino_seja(String cepDestino) throws Throwable {
		if (cepDestino != null && !cepDestino.isEmpty()) {
			this.cepDestino = cepDestino;
		} else {
			this.cepDestino = "null";
		}
	}

	@Dado("^o Codigo do Serviço seja \"([^\"]*)\"$")
	public void e_o_Codigo_do_Serviço_seja(String codServico) throws Throwable {
		if (codServico != null && !codServico.isEmpty()) {
			this.codServico = TipoEntregaEnum.fromString(codServico);
		} else {
			this.codServico = TipoEntregaEnum.fromString("null");
		}
	}

	@Quando("^eu executar a funcionalidade de frete$")
	public void quando_eu_executar_a_funcionalidade_de_frete() throws Throwable {
		setupWireMockServer();

		Mockito.when(dao.saveDadosDeEntrega(Mockito.anyDouble(), Mockito.anyInt())).thenReturn(true);
		
		throwable = catchThrowable(() -> this.precoPrazo = correiosService.buscarCalculoTempoFrete(produto, cepOrigem, cepDestino, codServico.getCodigo()));
	}

	@Quando("^eu executar a funcionalidade de frete com o sistema dos Correios fora$")
	public void quando_eu_executar_a_funcionalidade_de_frete_com_o_sistema_dos_correios_fora() throws Throwable {
		
		wireMockServer.stubFor(post(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-BuscaEndereco_out.xml")));
		
		throwable = catchThrowable(() -> this.precoPrazo = correiosService.buscarCalculoTempoFrete(produto, cepOrigem, cepDestino, codServico.getCodigo()));
	}
	
	@Quando("^eu executar a funcionalidade de frete ocorrendo erro ao acessar a base do ecommerce$")
	public void quando_eu_executar_a_funcionalidade_de_frete_ocorrendo_erro_ao_acessar_a_base_do_ecommerce() throws Throwable {
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-"+codServico.getCodigo()+".xml")));
		
		Mockito.when(dao.saveDadosDeEntrega(Mockito.anyDouble(), Mockito.anyInt())).thenThrow(new Exception("Ocorreu um erro interno. Problemas ao acessar o banco de dados."));
		
		throwable = catchThrowable(() -> this.precoPrazo = correiosService.buscarCalculoTempoFrete(produto, cepOrigem, cepDestino, codServico.getCodigo()));
	}
	
	@Entao("^o sistema deve retornar o preco \"([^\"]*)\" e o tempo \"([^\"]*)\"$")
	public void entao_o_sistema_deve_retornar_o_preco_e_o_tempo(String preco, String tempo) throws Throwable {
		assertEquals("Erro", preco, precoPrazo.getServicos().getCServico().getValor());
		assertThat(throwable).isNull();
	}

	@Entao("^o sistema deve retornar um erro com código \"([^\"]*)\" e mensagem \"([^\"]*)\"$")
	public void o_sistema_deve_retornar_um_erro_com_código_e_mensagem(String codErro, String msgErro) throws Throwable {
		assertEquals("Erro", codErro, String.valueOf(precoPrazo.getServicos().getCServico().getErro()));
		assertEquals("Erro", msgErro, precoPrazo.getServicos().getCServico().getMsgErro().trim());
		assertThat(throwable).isNull();
	}

	@Entao("^o sistema deve retornar um erro com a mensagem contendo:$")
	public void o_sistema_deve_retornar_um_erro_com_a_mensagem_contendo(String msgErro) throws Throwable {
		assertThat(throwable).hasMessage(msgErro);
	}
	
	@Entao("^o sistema deve armazenar o preco e o tempo no banco de dados$")
	public void entao_o_sistema_deve_armazenar_o_preco_e_o_tempo_no_banco_de_dados() throws Throwable {
		Mockito.verify(dao, Mockito.times(1)).saveDadosDeEntrega(Double.valueOf(precoPrazo.getServicos().getCServico().getValor()), (int) precoPrazo.getServicos().getCServico().getPrazoEntrega());
	}

	@E("^ocorrer erro ao acessar a base do ecommerce$")
	public void e_ocorrer_erro_ao_acessar_a_base_do_ecommerce() throws Throwable {
		assertThat(throwable).isNotNull();
	}
	
	@Entao("^solicitar ao usuario a correcao do valores$")
	public void solicitar_ao_usuario_a_correcao_do_valores() throws Throwable {
	    // nothing to do here
	}
	
	private void setupWireMockServer() {
	
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-"+codServico.getCodigo()+".xml")));
		
		
		/////////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_PESO_NAO_INFORMADO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_PESO_INVALIDO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_PESO_INVALIDO.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
	   					  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_LARGURA_NAO_INFORMADA.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_LARGURA_INVALIDA.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_LARGURA_INVALIDA.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_ALTURA_NAO_INFORMADA.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_ALTURA_INVALIDA.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_ALTURA_INVALIDA.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COMPRIMENTO_NAO_INFORMADO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COMPRIMENTO_INVALIDO.xml")));
		

		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COMPRIMENTO_INVALIDO.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_ORIGEM_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_ORIGEM_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_ORIGEM_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + "null" + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_DESTINO_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + "0" + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_DESTINO_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + "-1" + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + codServico.getCodigo() + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_CEP_DESTINO_INVALIDO_OU_NAO_INFORMADO.xml")));
		
		///////////
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + "null" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COD_SERVICO_NAO_INFORMADO.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + "0" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COD_SERVICO_INVALIDO.xml")));
		
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlPeso\":" + "\"" + produto.getPeso() + "\""))
				  .withRequestBody(containing("\"&nVlComprimento\":" + "\"" + produto.getComprimento() + "\""))
				  .withRequestBody(containing("\"&nVlAltura\":" + "\"" + produto.getAltura() + "\""))
				  .withRequestBody(containing("\"&nVlLargura\":" + "\"" + produto.getLargura() + "\""))
				  .withRequestBody(containing("\"&ncepOrigem\":" + "\"" + cepOrigem + "\""))
				  .withRequestBody(containing("\"&ncepDestino\":" + "\"" + cepDestino + "\""))
				  .withRequestBody(containing("\"&ncodServico\":" + "\"" + "-1" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-calculo-frete-tempo-entrega-ERR_COD_SERVICO_INVALIDO.xml")));
	}
}