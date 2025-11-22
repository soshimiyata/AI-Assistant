package com.example.aiassistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class DeepSeekConfig {

  @Value("${deepseek.api.key}")
  private String apiKey;

  @Value("${deepseek.base-url}")
  private String baseUrl;

  @Bean
  public OkHttpClient deepSeekHttpClient() {
    return new OkHttpClient.Builder().build();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getBaseUrl() {
    return baseUrl;
  }
}
