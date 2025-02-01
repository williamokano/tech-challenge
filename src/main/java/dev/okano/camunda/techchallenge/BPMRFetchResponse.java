package dev.okano.camunda.techchallenge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public record BPMRFetchResponse(
        @JsonProperty("id") String id,
        @JsonProperty("bpmn20Xml") String bpmn20Xml
) {
    public InputStream bpmnAsStream() {
        return new ByteArrayInputStream(bpmn20Xml.getBytes(StandardCharsets.UTF_8));
    }
}
