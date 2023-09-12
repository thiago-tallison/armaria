package com.example.armaria.controllers.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.controllers.municipal_guard.MunicipalGuardCreateDTO;
import com.example.armaria.dtos.checkout.CheckoutCreateTDO;
import com.example.armaria.entities.StockItem;
import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;
import com.example.armaria.use_cases.checkout.CheckedOutItemDTO;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CreateCheckoutControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testExecute() {
    String createCheckoutUrl = "http://localhost:" + port + "/api/v1/checkout_equipaments";
    LocalDateTime checkoutDate = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    int quantity = 10;
    int checkoutQuantity = 5;
    String equipamentSerialNumber = "serial-number-existente";

    List<CheckedOutItemDTO> itensAcauteladosDto = new ArrayList<>();
    itensAcauteladosDto.add(new CheckedOutItemDTO(1L, 1L, checkoutQuantity));

    getArmoryKeeper(armoryKeeperRegistration);
    getMunicipalGuard(registration);
    createEquipament(quantity, equipamentSerialNumber);

    CheckoutCreateTDO dto = new CheckoutCreateTDO(checkoutDate,
        registration, armoryKeeperRegistration, itensAcauteladosDto);

    // Verificar se um item estoque foi criado com a quantidade
    // "quantidadeASerCriada"
    String getItemEstoqueUrl = "http://localhost:" + port + "/api/v1/stock_items/serial_number/"
        + equipamentSerialNumber;
    ResponseEntity<StockItem> stockItemsBeforeCheckoutResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        StockItem.class);
    StockItem stockItemsBeforeCheckout = stockItemsBeforeCheckoutResponse.getBody();
    assertNotNull(stockItemsBeforeCheckout);
    assertEquals(HttpStatus.OK, stockItemsBeforeCheckoutResponse.getStatusCode());
    assertEquals(quantity,
        stockItemsBeforeCheckout.getQuantityInStock());

    ResponseEntity<Void> response = restTemplate.postForEntity(createCheckoutUrl, dto, Void.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    // Checar se a quantidade em estoque do item acautelado foi atualizada
    ResponseEntity<StockItem> stockItemsAfterCheckoutResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        StockItem.class);
    StockItem stockItemAfterCheckout = stockItemsAfterCheckoutResponse.getBody();

    assertNotNull(stockItemAfterCheckout);
    assertEquals(HttpStatus.OK, stockItemsAfterCheckoutResponse.getStatusCode());
    assertEquals(quantity - checkoutQuantity,
        stockItemAfterCheckout.getQuantityInStock());

  }

  public void getArmoryKeeper(String registration) {
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers";

    ArmoryKeeperCreateDTO armoryKeeperCreateDTO = new ArmoryKeeperCreateDTO(
        registration, // Matrícula
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(baseUrl, armoryKeeperCreateDTO, String.class);
  }

  public void getMunicipalGuard(String registration) {
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/create";

    MunicipalGuardCreateDTO municipalGuardCreateDTO = new MunicipalGuardCreateDTO(
        registration,
        "João da Silva",
        "joao@example.com",
        "1234567890");

    restTemplate.postForEntity(baseUrl, municipalGuardCreateDTO, String.class);
  }

  public void createEquipament(int quantityInStock, String serialNumber) {
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments";

    EquipamentCreateDTO equipamentCreateDTO = new EquipamentCreateDTO("Equipament A",
        serialNumber, true, quantityInStock);

    restTemplate.postForEntity(baseUrl, equipamentCreateDTO, Void.class);
  }
}
