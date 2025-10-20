package com.arka.supplying.service;

import com.arka.supplying.dto.ProductDto;
import com.arka.supplying.entity.Supplying;
import com.arka.supplying.repository.SupplyingRepository;
import com.arka.supplying.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SupplyingService {


    @Autowired
    private WebClientUtil webClientUtil;


    private final SupplyingRepository supplyingRepository;

    public Flux<Supplying> getAllAbastecimiento(){
        return supplyingRepository.findAll();
    }

    public Mono<Supplying> getByIdAbastecimiento(Long id){
        return supplyingRepository.findById(id);
    }

    public Mono<Supplying> saveAbastecimiento(Supplying supplying){
        return supplyingRepository.save(supplying);
    }

    public Mono<Supplying> updateAbastecimiento(Long id, Supplying updateSupplying){
        return supplyingRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado inventario con ese ID:"+id)))
                .flatMap(supplying -> {
                    supplying.setCantidad(updateSupplying.getCantidad());
                    return supplyingRepository.save(supplying);
                });
    }

    private Mono<ProductDto> getByIdProduct(Long id){

        return webClientUtil.get("/api/v1/product/{id}", new Object[]{id}, null, ProductDto.class);
    }
}
