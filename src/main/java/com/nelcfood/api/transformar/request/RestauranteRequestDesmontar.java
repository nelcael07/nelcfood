package com.nelcfood.api.transformar.request;

import com.nelcfood.api.dto.request.RestauranteDTORequest;
import com.nelcfood.model.entities.Cidade;
import com.nelcfood.model.entities.Cozinha;
import com.nelcfood.model.entities.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteRequestDesmontar {

  @Autowired
  private ModelMapper modelMapper;

  public Restaurante transformarRequestEmEntidade(RestauranteDTORequest restauranteDTOInput) {
    return modelMapper.map(restauranteDTOInput, Restaurante.class);
  }

  public void copiarRequestParaEntidade(RestauranteDTORequest restauranteDTORequest, Restaurante restauranteAtual) {
    //para evitar erro ao alterar id cozinha na atualização de restaurante
    restauranteAtual.setCozinha(new Cozinha());
    if (restauranteAtual.getEndereco() != null) {
      restauranteAtual.getEndereco().setCidade(new Cidade());
    }
    modelMapper.map(restauranteDTORequest, restauranteAtual);
  }
}
