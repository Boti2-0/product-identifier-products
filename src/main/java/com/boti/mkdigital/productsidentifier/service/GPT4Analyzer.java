package com.boti.mkdigital.productsidentifier.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GPT4Analyzer {
    private static final String API_KEY = "sk-proj-rFSfSPbzJ27Nrt8tv82xT3BlbkFJ2VOl6V6lpLX2OZOrIVBG";

    public static String analyzeContent(String content) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        String prompt = String.format("Analyze the following content to determine if it has any restriction by producer to ads on Google Ads, Bing Ads, and Facebook Ads. Provide specific reasons if it violates any advertising policies. Additionally, suggest 5 keyword options for each platform based on the content.\\n\\nContent: %s\\n\\nResponse format:\\n\\n```json\n%s\n```", content, generateResponseFormat());

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("model", "gpt-4o-mini");
        requestBodyMap.put("messages", new Object[]{message});
        requestBodyMap.put("max_tokens", 1500);

        String json = objectMapper.writeValueAsString(requestBodyMap);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String responseBody = response.body().string();
        JsonNode responseJson = objectMapper.readTree(responseBody);

        return responseJson.get("choices").get(0).get("message").get("content").asText();
    }

    private static String generateResponseFormat() {
        return "{\n" +
                "  \"googleAds\": {\n" +
                "    \"viability\": \"string\",\n" +
                "    \"policyCompliance\": \"string\",\n" +
                "    \"potentialViolations\": {\n" +
                "      \"misleadingClaims\": \"string\",\n" +
                "      \"weightLossClaims\": \"string\",\n" +
                "      \"producerRestrictions\": \"string\"\n" +
                "    },\n" +
                "    \"keywords\": [\"keyword1\", \"keyword2\", \"keyword3\", \"keyword4\", \"keyword5\"]\n" +
                "  },\n" +
                "  \"bingAds\": {\n" +
                "    \"viability\": \"string\",\n" +
                "    \"policyCompliance\": \"string\",\n" +
                "    \"potentialViolations\": {\n" +
                "      \"misleadingHealthClaims\": \"string\",\n" +
                "      \"producerRestrictions\": \"string\"\n" +
                "    },\n" +
                "    \"keywords\": [\"keyword1\", \"keyword2\", \"keyword3\", \"keyword4\", \"keyword5\"]\n" +
                "  },\n" +
                "  \"facebookAds\": {\n" +
                "    \"viability\": \"string\",\n" +
                "    \"policyCompliance\": \"string\",\n" +
                "    \"potentialViolations\": {\n" +
                "      \"exaggeratedClaims\": \"string\",\n" +
                "      \"producerRestrictions\": \"string\"\n" +
                "    },\n" +
                "    \"keywords\": [\"keyword1\", \"keyword2\", \"keyword3\", \"keyword4\", \"keyword5\"]\n" +
                "  },\n" +
                "  \"conclusion\": \"string\"\n" +
                "}";
    }

}
