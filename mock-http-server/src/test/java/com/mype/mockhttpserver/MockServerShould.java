package com.mype.mockhttpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

/**
 * @author Vitaliy Gerya
 */
public class MockServerShould {
    private ClientAndServer mockServer;
    private RestTemplate request;
    public static final String MESSAGE = "body of message.";

    @Before
    public void setUp() throws Exception {
        mockServer = ClientAndServer.startClientAndServer(8080);
        request = new RestTemplate();
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop();
        mockServer = null;
        request = null;
    }

    @Test
    public void mockGetRequestWithoutPath() throws Exception {
        String bodyMessage = "body of message.";
        mockServer
                .when(HttpRequest.request().withMethod("GET"))
                .respond(HttpResponse.response().withBody(bodyMessage));


        String response = request.getForObject("http://localhost:8080", String.class);

        assertThat(response).isNotNull().isEqualTo(bodyMessage);
    }

    @Test
    public void requestPathStartsFromSlash() throws Exception {
        mockServer
                .when(HttpRequest.request().withMethod("GET").withPath("api/latest.json"))
                .respond(HttpResponse.response().withBody(MESSAGE));

        try {
            ResponseEntity<String> response = request.getForEntity("http://localhost:8080/api/latest.json", String.class);
        }
        catch(HttpClientErrorException ex) {
            assertThat(ex.getMessage()).contains("404");

            return;
        }

        fail("Should be response status 404.");
    }

    @Test
    public void mockGetRequestsByPath() throws Exception {
        mockServer
                .dumpToLog()
                .when(HttpRequest.request().withMethod("GET").withPath("/api/latest.json"))
                .respond(HttpResponse.response().withBody(MESSAGE));

        String response = request.getForObject("http://localhost:8080/api/latest.json", String.class);

        assertThat(response).isNotNull().isEqualTo(MESSAGE);
    }

    @Test
    public void mockGetRequestByPathWithQueryParam() throws Exception {
        mockServer.dumpToLog()
                .when(
                    HttpRequest.request()
                        .withMethod("GET")
                        .withPath("api/latest.json")
                        .withQueryStringParameter(new Parameter("app_id", "61f213c43dcf4ac199c031931d3020d1")))
                .respond(
                        HttpResponse.response()
                                .withBody(MESSAGE));

        String response = request.getForObject("http://localhost:8080/api/latest.json?app_id=61f213c43dcf4ac199c031931d3020d1", String.class);

        assertThat(response).isNotNull().isEqualTo(MESSAGE);
    }


}
