package com.example.armaria.controllers.acautelamento;

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
import com.example.armaria.dtos.acautelamento.CriarAcautelamentoDTO;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.acautelamento.ItemAcauteladoDTO;
import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CriarAcautelamentoControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testExecute() {
    String criarAcautelamentoUrl = "http://localhost:" + port + "/api/acautelamento";
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    int quantidadeASerCriada = 10;
    int quantidadeASerAcautelada = 5;
    String equipamentSerialNumber = "serial-number-existente";

    List<ItemAcauteladoDTO> itensAcauteladosDto = new ArrayList<>();
    itensAcauteladosDto.add(new ItemAcauteladoDTO(1L, 1L, quantidadeASerAcautelada));

    getArmoryKeeper(armoryKeeperRegistration);
    getMunicipalGuard(registration);
    createEquipament(quantidadeASerCriada, equipamentSerialNumber);

    CriarAcautelamentoDTO dto = new CriarAcautelamentoDTO(dataAcautelamento,
        registration, armoryKeeperRegistration, itensAcauteladosDto);

    // Verificar se um item estoque foi criado com a quantidade
    // "quantidadeASerCriada"
    String getItemEstoqueUrl = "http://localhost:" + port + "/api/v1/stock_items/serial_number/"
        + equipamentSerialNumber;
    ResponseEntity<ItemEstoque> itemEstoqueAntesAcautelamentoResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        ItemEstoque.class);
    ItemEstoque itemEstoqueAntesAcautelamento = itemEstoqueAntesAcautelamentoResponse.getBody();
    assertNotNull(itemEstoqueAntesAcautelamento);
    assertEquals(HttpStatus.OK, itemEstoqueAntesAcautelamentoResponse.getStatusCode());
    assertEquals(quantidadeASerCriada,
        itemEstoqueAntesAcautelamento.getQuantityInStock());

    ResponseEntity<Void> response = restTemplate.postForEntity(criarAcautelamentoUrl, dto, Void.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    // Checar se a quantidade em estoque do item acautelado foi atualizada
    ResponseEntity<ItemEstoque> itemEstoqueDepoisAcautelamentoResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        ItemEstoque.class);
    ItemEstoque itemEstoqueDepoisAcautelamento = itemEstoqueDepoisAcautelamentoResponse.getBody();

    assertNotNull(itemEstoqueDepoisAcautelamento);
    assertEquals(HttpStatus.OK, itemEstoqueDepoisAcautelamentoResponse.getStatusCode());
    assertEquals(quantidadeASerCriada - quantidadeASerAcautelada,
        itemEstoqueDepoisAcautelamento.getQuantityInStock());

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
