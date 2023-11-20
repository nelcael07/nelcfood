package com.nelcfood.api.controller;

import com.nelcfood.api.transformar.response.CidadeResponseMontar;
import com.nelcfood.api.transformar.request.CidadeRequestDesmontar;
import com.nelcfood.api.dto.request.CidadeDTORequest;
import com.nelcfood.api.dto.response.CidadeDTOResponse;
import com.nelcfood.model.exception.naoEncontrada.EstadoNaoEncontradoException;
import com.nelcfood.model.exception.NegocioException;
import com.nelcfood.model.entities.Cidade;
import com.nelcfood.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

  CidadeService cidadeService;
  CidadeResponseMontar cidadeResponseAssembler;
  CidadeRequestDesmontar cidadeResquestDisassembler;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CidadeDTOResponse> listar() {
    return cidadeResponseAssembler.
            transformarColecaoEmResponse(cidadeService.listar());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CidadeDTOResponse buscar(@PathVariable Long id) {
    return cidadeResponseAssembler.transformarEntidadeEmResponse(cidadeService.buscar(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CidadeDTOResponse salvar(@RequestBody @Valid CidadeDTORequest cidade) {
    try {
      return cidadeResponseAssembler.transformarEntidadeEmResponse
              (cidadeService.salvar(cidadeResquestDisassembler.transformarRequestEmEntidade(cidade)));
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CidadeDTOResponse atualizar(@RequestBody @Valid CidadeDTORequest cidade, @PathVariable Long id) {
    Cidade cidadeBuscada = cidadeService.buscar(id);
    cidadeResquestDisassembler.copiarRequestParaEntidade(cidade, cidadeBuscada);
    try {
      return cidadeResponseAssembler.transformarEntidadeEmResponse
              (cidadeService.salvar(cidadeBuscada));
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    cidadeService.deletar(id);
  }
}
