package com.ifood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void aboutShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/about",
                String.class)).contains("Hello World");
    }
    
    @Test
    public void weatherShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/weather",
                String.class)).contains("Temperatura em campinas");
    }
    
    @Test
    public void valinhosShouldReturnValinhosTemperatureMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/weather?city=valinhos",
                String.class)).contains("Temperatura em valinhos");
    }
    
    @Test
    public void grandeCampinasShouldReturnCampinasTemperatureMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/grandeCampinas",
                String.class)).contains("Temperatura em campinas");
    }
    
    @Test
    public void grandeCampinasShouldReturnValinhosTemperatureMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/grandeCampinas",
                String.class)).contains("Temperatura em valinhos");
    }
    
    @Test
    public void grandeCampinasShouldReturnVinhedoTemperatureMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/grandeCampinas",
                String.class)).contains("Temperatura em vinhedo");
    }
    
    @Test
    public void grandeCampinasShouldReturnPauliniaTemperatureMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/grandeCampinas",
                String.class)).contains("Temperatura em paulinia");
    }
    
    @Test
    public void grammarErrorShouldReturnDidYouMeanMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/weather?city=campinastre",
                String.class)).contains("Você quis dizer");
    }
    
    @Test
    public void nonExistingCityShouldReturnCityNotFoundMeanMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/weather?city=c3dist9",
                String.class)).contains("Cidade não encontrada");
    }
    
    
    
    
    
    
    
}
