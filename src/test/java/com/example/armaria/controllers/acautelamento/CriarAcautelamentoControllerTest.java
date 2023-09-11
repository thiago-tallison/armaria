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
import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;
import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

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
    String matriculaGm = "GM123";
    String matriculaArmeiro = "AR123";
    int quantidadeASerCriada = 10;
    int quantidadeASerAcautelada = 5;
    String numSerieEquipamento = "num-serie-existente";

    List<ItemAcauteladoDTO> itensAcauteladosDto = new ArrayList<>();
    itensAcauteladosDto.add(new ItemAcauteladoDTO(1L, 1L, quantidadeASerAcautelada));

    criarArmeiro(matriculaArmeiro);
    criarGm(matriculaGm);
    criarEquipamento(quantidadeASerCriada, numSerieEquipamento);

    CriarAcautelamentoDTO dto = new CriarAcautelamentoDTO(dataAcautelamento,
        matriculaGm, matriculaArmeiro, itensAcauteladosDto);

    // Verificar se um item estoque foi criado com a quantidade
    // "quantidadeASerCriada"
    String getItemEstoqueUrl = "http://localhost:" + port + "/api/item-estoque/num-serie/" + numSerieEquipamento;
    ResponseEntity<ItemEstoque> itemEstoqueAntesAcautelamentoResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        ItemEstoque.class);
    ItemEstoque itemEstoqueAntesAcautelamento = itemEstoqueAntesAcautelamentoResponse.getBody();
    assertNotNull(itemEstoqueAntesAcautelamento);
    assertEquals(HttpStatus.OK, itemEstoqueAntesAcautelamentoResponse.getStatusCode());
    assertEquals(quantidadeASerCriada,
        itemEstoqueAntesAcautelamento.getQuantidadeEmEstoque());

    ResponseEntity<Void> response = restTemplate.postForEntity(criarAcautelamentoUrl, dto, Void.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    // Checar se a quantidade em estoque do item acautelado foi atualizada
    ResponseEntity<ItemEstoque> itemEstoqueDepoisAcautelamentoResponse = restTemplate.getForEntity(getItemEstoqueUrl,
        ItemEstoque.class);
    ItemEstoque itemEstoqueDepoisAcautelamento = itemEstoqueDepoisAcautelamentoResponse.getBody();

    assertNotNull(itemEstoqueDepoisAcautelamento);
    assertEquals(HttpStatus.OK, itemEstoqueDepoisAcautelamentoResponse.getStatusCode());
    assertEquals(quantidadeASerCriada - quantidadeASerAcautelada,
        itemEstoqueDepoisAcautelamento.getQuantidadeEmEstoque());

  }

  public void criarArmeiro(String matricula) {
    String baseUrl = "http://localhost:" + port + "/api/armeiro";

    CriarArmeiroDTO armeiroDTO = new CriarArmeiroDTO(
        matricula, // Matrícula
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(baseUrl, armeiroDTO, String.class);
  }

  public void criarGm(String matricula) {
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/create";

    MunicipalGuardCreateDTO municipalGuardCreateDTO = new MunicipalGuardCreateDTO(
        matricula,
        "João da Silva",
        "joao@example.com",
        "1234567890");

    restTemplate.postForEntity(baseUrl, municipalGuardCreateDTO, String.class);
  }

  public void criarEquipamento(int quantidadeEmEstoque, String numSerieEquipamento) {
    String baseUrl = "http://localhost:" + port + "/api/equipamento";

    CriarEquipamentoComItemDTO equipamentoComItemDto = new CriarEquipamentoComItemDTO("Nome do equipamento",
        numSerieEquipamento, true, quantidadeEmEstoque);

    restTemplate.postForEntity(baseUrl, equipamentoComItemDto, Void.class);
  }
}
