package org.org.flightbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "health", description = "Health Check API")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Check service health status")
    public ResponseEntity<?> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Flight Booking Service");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }
}

