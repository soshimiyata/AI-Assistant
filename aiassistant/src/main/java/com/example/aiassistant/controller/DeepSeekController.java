package com.example.aiassistant.controller;

import com.example.aiassistant.service.DeepSeekService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class DeepSeekController {

  private final DeepSeekService service;

  public DeepSeekController(DeepSeekService service) {
    this.service = service;
  }

  @GetMapping
  public String chat(@RequestParam String message) {
    try {
      return service.chat(message);
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }
}
