package com.example.armaria.presenter.rest.api.municipal_guard;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.armaria.core.domain.MunicipalGuard;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetMunicipalGuardByRegistrationControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/" + registration;

    ResponseEntity<MunicipalGuard> response = restTemplate.getForEntity(baseUrl, null, MunicipalGuard.class);
    assertNull(response.getBody());
  }
}
