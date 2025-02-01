package dev.okano.camunda.techchallenge;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BPMNFetcher {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public BPMRFetchResponse fetch(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Server returned HTTP " + httpResponse.statusCode() + " - Expecting 200");
            }

            return mapper.readValue(httpResponse.body(), BPMRFetchResponse.class);

            // this part would benefit from a better exception handler
            // but for simplicity I'm just converting to a runtime
        } catch (Throwable e) {
            throw new RuntimeException("Failed to fetch " + url);
        }
    }
}
