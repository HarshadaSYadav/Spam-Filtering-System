package com.example.Spam.Filtering.System.Controller;

import com.example.Spam.Filtering.System.Entity.Email;
import com.example.Spam.Filtering.System.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.json.JSONObject;



@RestController
@RequestMapping("/api/emails")
public class EmailController {
    @Autowired
    private EmailRepository emailRepository;

    private final String ML_API_URL = "http://localhost:5000/predict";

    @PostMapping("/classify")
    public ResponseEntity<?> classifyEmail(@RequestBody Email email) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>("{\"email\":\"" + email.getEmailText() + "\"}", headers);

            ResponseEntity<String> response = restTemplate.postForEntity(ML_API_URL, request, String.class);
            String classification = new JSONObject(response.getBody()).getString("prediction");
            email.setClassification(classification);
            emailRepository.save(email);

            return ResponseEntity.ok(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
