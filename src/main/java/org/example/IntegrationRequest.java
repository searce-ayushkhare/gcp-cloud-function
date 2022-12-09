package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationRequest {
    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, List<String>> multiValueHeaders;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> pathParameters;
    private Map<String, String> queryStringParameters;
    private Map<String, String> StageVariables;
    private boolean isBase64Encoded;
}
