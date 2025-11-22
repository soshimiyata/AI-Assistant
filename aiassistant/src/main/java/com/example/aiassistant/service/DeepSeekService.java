package com.example.aiassistant.service;

import com.example.aiassistant.config.DeepSeekConfig;
import okhttp3.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

@Service
public class DeepSeekService {

  private final OkHttpClient client;
  private final DeepSeekConfig config;
  private final ObjectMapper mapper = new ObjectMapper();

  public DeepSeekService(OkHttpClient client, DeepSeekConfig config) {
    this.client = client;
    this.config = config;
  }

  public String chat(String userMessage) throws IOException {

    // --- JSON構築（Jackson） ---
    ObjectNode root = mapper.createObjectNode();
    root.put("model", "deepseek-chat");

    ArrayNode messages = mapper.createArrayNode();
    ObjectNode userMsg = mapper.createObjectNode();
    userMsg.put("role", "user");
    userMsg.put("content", userMessage);

    messages.add(userMsg);
    root.set("messages", messages);

    String jsonBody = mapper.writeValueAsString(root);

    RequestBody body = RequestBody.create(
        jsonBody,
        MediaType.get("application/json; charset=utf-8"));

    Request request = new Request.Builder()
        .url(config.getBaseUrl() + "/chat/completions")
        .addHeader("Authorization", "Bearer " + config.getApiKey())
        .post(body)
        .build();

    Response response = client.newCall(request).execute();
    String responseBody = response.body().string();

    // --- レスポンス解析 ---
    ObjectNode res = (ObjectNode) mapper.readTree(responseBody);

    return res.get("choices")
        .get(0)
        .get("message")
        .get("content")
        .asText();
  }
}
