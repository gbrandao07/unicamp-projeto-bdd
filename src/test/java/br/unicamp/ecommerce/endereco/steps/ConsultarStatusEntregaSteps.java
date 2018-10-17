package br.unicamp.ecommerce.endereco.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.WireMockServer;

import br.unicamp.ecommerce.Configuracao;
import br.unicamp.ecommerce.dao.DadosDeEntregaDAO;
import br.unicamp.ecommerce.model.input.impl.StatusEncomendaInput;
import br.unicamp.ecommerce.model.xml.StatusEncomenda;
import br.unicamp.ecommerce.service.CorreiosService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class ConsultarStatusEntregaSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@Mock
	DadosDeEntregaDAO dao;
	
	@InjectMocks
	private CorreiosService correiosService;

	private StatusEncomendaInput statusEncomendaInput;
	
	private StatusEncomenda statusEncomenda;
	
	private Throwable throwable;

	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9878);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getStatusEntregaUrl()).thenReturn("http://localhost:9878/ws/correios");
		statusEncomendaInput = new StatusEncomendaInput();
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}

	@Dado("^um usuario logado chamado \"([^\"]*)\"$")
	public void dado_um_usuario_logado_chamado(String usuario) throws Throwable {
		statusEncomendaInput.setUsuario(usuario);
	}

	@Dado("^um pedido composto de um identificador \"([^\"]*)\"$")
	public void e_um_pedido_composto_de_um_identificador(String identificador) throws Throwable {
		statusEncomendaInput.setIdentificador(identificador);
	}

	@Quando("^eu executar a funcionalidade de consulta do status da entrega$")
	public void quando_eu_executar_a_funcionalidade_de_consulta_do_status_da_entrega() throws Throwable {
		setupWireMockServer();
		throwable = catchThrowable(() -> this.statusEncomenda = correiosService.buscarStatusEncomenda(statusEncomendaInput));
	}

	@Entao("^o sistema deve retornar as informações da entrega com \"([^\"]*)\", \"([^\"]*)\" e \"([^\"]*)\"$")
	public void entao_o_sistema_deve_retornar_as_informações_da_entrega_com_e(String tipo, String status, String descricao) throws Throwable {
		assertThat(throwable).isNull();
		assertThat(this.statusEncomenda.getObjeto().getEvento().get(0).getTipo()).isEqualTo(tipo);
		assertThat(String.valueOf(this.statusEncomenda.getObjeto().getEvento().get(0).getStatus())).isEqualTo(status);
		assertThat(this.statusEncomenda.getObjeto().getEvento().get(0).getDescricao()).isEqualTo(descricao);
	}
	
	private void setupWireMockServer() {
		
//		wireMockServer.stubFor(post(urlMatching("/ws/" + (String.format("&nVlUsuario=%s&nVlIdentificador=%s", "Gustavo", "SQ458226057BR")) + ".*"))
//		  .willReturn(aResponse().withStatus(200)
//				  				 .withHeader("Content-Type", "text/xml")
//				  				 .withBodyFile("resultado-status-entrega-pedido-gustavo.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlUsuario\":" + "\"" + "Gustavo" + "\""))
				  .withRequestBody(containing("\"&nVlIdentificador\":" + "\"" + "SQ458226057BR" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-status-entrega-pedido-gustavo.xml")));
		
//		wireMockServer.stubFor(get(urlMatching("/ws/" + (String.format("&nVlUsuario=%s&nVlIdentificador=%s", "Cleomar", "SQ458226058BR")) + ".*"))
//				  .willReturn(aResponse().withStatus(200)
//						  				 .withHeader("Content-Type", "text/xml")
//						  				 .withBodyFile("resultado-status-entrega-pedido-cleomar.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlUsuario\":" + "\"" + "Cleomar" + "\""))
				  .withRequestBody(containing("\"&nVlIdentificador\":" + "\"" + "SQ458226058BR" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-status-entrega-pedido-cleomar.xml")));
		
//		wireMockServer.stubFor(get(urlMatching("/ws/" + (String.format("&nVlUsuario=%s&nVlIdentificador=%s", "Pedro", "SQ458226059BR")) + ".*"))
//				  .willReturn(aResponse().withStatus(200)
//						  				 .withHeader("Content-Type", "text/xml")
//						  				 .withBodyFile("resultado-status-entrega-pedido-pedro.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlUsuario\":" + "\"" + "Pedro" + "\""))
				  .withRequestBody(containing("\"&nVlIdentificador\":" + "\"" + "SQ458226059BR" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-status-entrega-pedido-pedro.xml")));
		
//		wireMockServer.stubFor(get(urlMatching("/ws/" + (String.format("&nVlUsuario=%s&nVlIdentificador=%s", "Tiago", "SQ458226060BR")) + ".*"))
//				  .willReturn(aResponse().withStatus(200)
//						  				 .withHeader("Content-Type", "text/xml")
//						  				 .withBodyFile("resultado-status-entrega-pedido-tiago.xml")));
		
		wireMockServer.stubFor(post(urlMatching("/ws/correios"))
				  .withRequestBody(containing("\"&nVlUsuario\":" + "\"" + "Tiago" + "\""))
				  .withRequestBody(containing("\"&nVlIdentificador\":" + "\"" + "SQ458226060BR" + "\""))
				  .willReturn(aResponse().withStatus(200)
						  				 .withHeader("Content-Type", "text/xml")
						  				 .withBodyFile("resultado-status-entrega-pedido-tiago.xml")));
		
	}
}