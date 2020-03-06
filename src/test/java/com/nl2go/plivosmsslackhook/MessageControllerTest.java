package com.nl2go.plivosmsslackhook;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.text.MessageFormat.format;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(
    properties = { "plivo-sms-slack-hook.webhook-url=http://localhost:8999/hook" },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class MessageControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8999));

    @Test
    public void shouldCreateMessage(){
        UriComponentsBuilder urlBuilder = fromHttpUrl(format("http://localhost:{0}/messages", port))
            .queryParam("From", "123")
            .queryParam("To", "321")
            .queryParam("Text", "Hello World!");

        stubFor(post("/hook")
            .withRequestBody(equalToJson("{\"text\":\"Message from `123` to `321`:\\n```Hello%20World!```\"}"))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())));

        restTemplate.postForEntity(urlBuilder.toUriString(), null, Void.class);
    }
    @Test
    public void shouldIgnoreMessageIfToNumberNotMatching(){
        UriComponentsBuilder urlBuilder = fromHttpUrl(format("http://localhost:{0}/messages", port))
            .queryParam("From", "123")
            .queryParam("To", "123")
            .queryParam("Text", "Hello World!");

        restTemplate.postForEntity(urlBuilder.toUriString(), null, Void.class);
    }
}
