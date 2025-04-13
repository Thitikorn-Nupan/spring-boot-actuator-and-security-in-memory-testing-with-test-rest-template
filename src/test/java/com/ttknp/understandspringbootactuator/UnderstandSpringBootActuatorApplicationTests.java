package com.ttknp.understandspringbootactuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

/**
     Basic integration test for service demo application.
     When request like below you have to run your app (** Because it's TestRestTemplate class same RestTemplate class)
     It's not including security logic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnderstandSpringBootActuatorApplicationTests {

    @LocalServerPort // Get local server port behind the sense work like this @Value("${local.server.port}").
    private int port;
    private final int portActuator = 8081;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<Map> entity;

    private HttpHeaders getHeaders() {
        /*
         Basic authenticate i set up on security config class
         normally i will use Postman and choose Auth Type -> Basic Auth
         And you can put user/pass quickly if you send value on header like below
         */
        final String base64UsernameAndPassword = Base64.getEncoder().encodeToString("admin:12345".getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic "+base64UsernameAndPassword);
        return headers;
    }

    @Test
    public void shouldReturnFollowAPIWhenSendingRequestToResControllerApiRobot() throws Exception {
        int id = 5000;
        final String uri = "http://localhost:" + port + "/api/robot?id="+id;
        entity = testRestTemplate.exchange(uri , HttpMethod.GET,new HttpEntity<>(getHeaders()),Map.class);  // you have to know what entity.<method name>() returned then mocked it
        then(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        then(entity.getBody()).isEqualTo(Map.of("id",id,"code","XT-23565-TD6-265"));
        then(entity.getHeaders().get("Content-Type")).isEqualTo(List.of("application/json"));
    }


    @Test
    public void shouldReturnFollowAPIWhenSendingRequestToApiActuator() throws Exception {
        final String uri = "http://localhost:" + portActuator + "/api/admin/health";
        // If you wanna pass header use .exchange(...) method instead ** .getForEntity("http://localhost:" + portActuator + "/api/admin/health", Map.class);
        entity = testRestTemplate.exchange(uri,HttpMethod.GET,new HttpEntity<>(getHeaders()), Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().get("status")).isEqualTo("UP");
    }

    @Test
    public void shouldReturnHelloWorldWhenSendingRequestToUnAuthenticate() throws Exception {
        entity = testRestTemplate.getForEntity("http://localhost:" + portActuator + "/api/server", Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().get("data")).isEqualTo("Hello World");
    }


}
