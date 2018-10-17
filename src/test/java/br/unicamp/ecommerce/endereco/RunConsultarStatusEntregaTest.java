package br.unicamp.ecommerce.endereco;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.ecommerce.endereco",
        features = "classpath:features/ConsultarStatusEntrega.feature"
)
public class RunConsultarStatusEntregaTest {

}