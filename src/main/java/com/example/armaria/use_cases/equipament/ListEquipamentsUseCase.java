package com.example.armaria.use_cases.equipament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.errors.PageSizeOutOfBoundException;
import com.example.armaria.errors.PaginaNaoExistenteException;
import com.example.armaria.errors.TamanhoDaPaginaNegativoException;
import com.example.armaria.repositories.EquipamentRepository;

import jakarta.transaction.Transactional;

@Service
public class ListEquipamentsUseCase {
  private final EquipamentRepository equipamentRepository;
  public static final int TAMANHO_MAXIMO_PAGINA = 30;

  public ListEquipamentsUseCase(EquipamentRepository equipamentRepository) {
    this.equipamentRepository = equipamentRepository;
  }

  @Transactional
  public EquipamentsListViewModel execute(EquipamentsListDTO equipamentsListDTO) {
    int pagina = equipamentsListDTO.page();
    int tamanhoPagina = equipamentsListDTO.itensPerPage();

    if (tamanhoPagina <= 0) {
      throw new TamanhoDaPaginaNegativoException(tamanhoPagina);
    }

    if (tamanhoPagina > TAMANHO_MAXIMO_PAGINA) {
      throw new PageSizeOutOfBoundException();
    }

    Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

    Page<Equipament> equipaments = equipamentRepository.findAll(pageable);

    if (pagina >= equipaments.getTotalPages() || pagina < 0) {
      throw new PaginaNaoExistenteException();
    }

    EquipamentsListViewModel equipamentsListViewModel = new EquipamentsListViewModel(pagina, "",
        "", equipaments.getTotalElements(), equipaments.getTotalPages(),
        equipaments.toList());

    return equipamentsListViewModel;
  }
}
