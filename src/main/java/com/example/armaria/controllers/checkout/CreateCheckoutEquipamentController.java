package com.example.armaria.controllers.checkout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.dtos.checkout.CheckoutCreateTDO;
import com.example.armaria.use_cases.checkout.CreateCheckoutEquipamentUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/checkout_equipaments")
public class CreateCheckoutEquipamentController {
  private final CreateCheckoutEquipamentUseCase createCheckoutEquipamentUseCase;

  public CreateCheckoutEquipamentController(CreateCheckoutEquipamentUseCase createCheckoutEquipamentUseCase) {
    this.createCheckoutEquipamentUseCase = createCheckoutEquipamentUseCase;
  }

  @PostMapping
  public ResponseEntity<Void> execute(@Valid @RequestBody CheckoutCreateTDO checkoutCreateTDO) {
    createCheckoutEquipamentUseCase.execute(checkoutCreateTDO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
