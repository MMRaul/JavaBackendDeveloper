package com.carrito.carrito.application.service;

import com.carrito.carrito.application.dto.*;
import com.carrito.carrito.exception.OrdenAlreadySoldException;
import com.carrito.carrito.exception.ProductLowStockException;
import com.carrito.carrito.infrastructure.entity.Carrito;
import com.carrito.carrito.infrastructure.entity.CarritoItem;
import com.carrito.carrito.exception.OrdenNotFoundException;
import com.carrito.carrito.infrastructure.repository.CarritoItemRepositorio;
import com.carrito.carrito.infrastructure.repository.CarritoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final CarritoRepositorio carritoRepositorio;
    private final CarritoItemRepositorio carritoItemRepositorio;
    private final WebClient webClient;

    public Mono<Void> GuardarCarrito(OrdenDTO dto){

        /*Valida existencia de items*/
        List<OrdenItemDTO> items2 = dto.getItems();
        return validarExistencias(items2)
                .flatMap(existencias -> {
                    if (existencias){
                        Carrito carrito = new Carrito();
                        carrito.setCorreo(dto.getCorreo());
                        carrito.setNombreCliente(dto.getNombreCliente());
                        carrito.setDireccionEntrega(dto.getDireccionEntrega());
                        carrito.setEstado("PENDIENTE");
                        List<OrdenItemDTO> items1 = dto.getItems();
                        Double total = items1.stream()
                                .mapToDouble(subTot -> subTot.getCantidadItems() * subTot.getPrecioUnitario().doubleValue())
                                .sum();
                        carrito.setSubtotal(BigDecimal.valueOf(total));
                        carrito.setFechaCreacion(LocalDate.now());
                        carrito.setFechaVenta(null);


                        return carritoRepositorio.save(carrito)
                                .flatMapMany(carritoguardado -> {
                                    List<CarritoItem> items = dto.getItems().stream().map(itemDTO -> {
                                        CarritoItem carritoItem = new CarritoItem();
                                        /*    carritoItem.setIdCarritoItem(itemDTO.getIdCarritoItem());*/
                                        carritoItem.setIdCarrito(carritoguardado.getIdCarrito());
                                        carritoItem.setIdProducto(itemDTO.getIdProducto());
                                        carritoItem.setCantidadItem(itemDTO.getCantidadItems());
                                        carritoItem.setPrecioUnitario(itemDTO.getPrecioUnitario());
                                        return carritoItem;
                                    }).collect(Collectors.toList());
                                    return carritoItemRepositorio.saveAll(items);
                                }).then();
                    } else{
                        return Mono.error(new ProductLowStockException("Un articulo sin existencias "));
                    }
                });

    }

    public Mono<Void> procesarSiExiste(OrdenDTO dto){

        List<OrdenItemDTO> items2 = dto.getItems();
        return validarExistencias(items2)
                .flatMap(existencias -> {
                    if (existencias){
                        Integer idOrden = dto.getIdOrden();
                        return carritoRepositorio.existsById(idOrden)
                                .flatMap(existe -> {
                                    if(existe){
                                        return carritoRepositorio.findById(idOrden)
                                                .flatMap(upd -> {
                                                    if(upd.getEstado().equals("VENDIDO")){
                                                        return Mono.error(new OrdenAlreadySoldException("Orden " + idOrden + " ya fue vendida"));
                                                    }
                                                    Carrito carrito = new Carrito();
                                                    carrito.setIdCarrito(dto.getIdOrden());
                                                    carrito.setFechaCreacion(upd.getFechaCreacion());
                                                    carrito.setEstado(upd.getEstado());
                                                    carrito.setCorreo(dto.getCorreo());
                                                    carrito.setNombreCliente(dto.getNombreCliente());
                                                    carrito.setDireccionEntrega(dto.getDireccionEntrega());
                                                    List<OrdenItemDTO> items1 = dto.getItems();
                                                    Double total = items1.stream()
                                                            .mapToDouble(subTot -> subTot.getCantidadItems() * subTot.getPrecioUnitario().doubleValue())
                                                            .sum();
                                                    carrito.setSubtotal(BigDecimal.valueOf(total));
                                                    return carritoRepositorio.save(carrito)
                                                            .flatMapMany(carritoguardado -> {
                                                                List<CarritoItem> items = dto.getItems().stream().map(itemDTO -> {
                                                                    CarritoItem carritoItem = new CarritoItem();
                                                                    carritoItem.setIdCarritoItem(itemDTO.getIdDetalleOrden());
                                                                    carritoItem.setIdCarrito(carritoguardado.getIdCarrito());
                                                                    carritoItem.setIdProducto(itemDTO.getIdProducto());
                                                                    carritoItem.setCantidadItem(itemDTO.getCantidadItems());
                                                                    carritoItem.setPrecioUnitario(itemDTO.getPrecioUnitario());
                                                                    return carritoItem;
                                                                }).collect(Collectors.toList());
                                                                return carritoItemRepositorio.saveAll(items);
                                                            }).then();
                                                });

                                    }
                                    else {
                                        return Mono.error(new OrdenNotFoundException("Carrito no encontrado " + idOrden));
                                    }
                                });
                    } else{
                        return Mono.error(new ProductLowStockException("Un articulo sin existencias"));
                    }
                });

    }

    public Flux<ListarOrdenes> retornaOrdenes() {
        return carritoRepositorio.findAll()
                .map(orden -> {
                    ListarOrdenes dto = new ListarOrdenes();
                    dto.setIdOrden(orden.getIdCarrito());
                    dto.setCorreo(orden.getCorreo());
                    dto.setNombreCliente(orden.getNombreCliente());
                    dto.setDireccionEntrega(orden.getDireccionEntrega());
                    dto.setEstado(orden.getEstado());
                    dto.setSubTotal(orden.getSubtotal());
                    dto.setFechaVenta(orden.getFechaCreacion());
                    dto.setFechaVenta(orden.getFechaVenta());

                    return dto;
                }).switchIfEmpty(Mono.error(new OrdenNotFoundException("No se encontró ninguna orden")));
    }

    public Flux<OrdenItemDTO> retornarDetalleOrden(Integer idOrden) {
        return carritoItemRepositorio.findByIdCarrito(idOrden)
                .map( ordenDt -> {
                    OrdenItemDTO item = new OrdenItemDTO();
                    item.setIdDetalleOrden(ordenDt.getIdCarritoItem());
                    item.setIdOrdenC(ordenDt.getIdCarrito());
                    item.setIdProducto(ordenDt.getIdProducto());
                    item.setCantidadItems(ordenDt.getCantidadItem());
                    item.setPrecioUnitario(ordenDt.getPrecioUnitario());

                    return item;
                }).switchIfEmpty(Mono.error(new OrdenNotFoundException("No se encontro la orden id: " + idOrden)));
    }
/*
    public Mono<Void> AplicaOrden(CarritoDTO dto)
    {
        return carritoRepositorio.existsById(dto.getIdOrden())
                .flatMap(existe -> {
                    if(existe){
                        return carritoRepositorio.findById(dto.getIdOrden())
                                .map(ord -> {
                                    Carrito orden = new Carrito();
                                    orden.setIdCarrito(dto.getIdOrden());
                                    orden.setCorreo(orden.getCorreo());
                                    orden.setNombreCliente(orden.getNombreCliente());
                                    orden.setDireccionEntrega(orden.getDireccionEntrega());
                                    orden.setEstado("Pagado");
                                    orden.setSubtotal(orden.getSubtotal());
                                    orden.setFechaCreacion(orden.getFechaCreacion());
                                    orden.setFechaVenta(LocalDate.now());

                                    return carritoRepositorio.save();
                                });
                    }
                    else {
                        return Mono.error(new CarritoItemNotFoundException("Orden no encontrada " + idOrden));
                    }
                }).then();
    }*/
public Mono<Void> AplicarOrden(OrdenDTO dto){

    List<OrdenItemDTO> items2 = dto.getItems();
    return validarExistencias(items2)
            .flatMap(existencias -> {
                if (existencias){
                    Integer idOrden = dto.getIdOrden();
                    return carritoRepositorio.existsById(idOrden)
                            .flatMap(existe -> {
                                if(existe){
                                    return carritoRepositorio.findById(idOrden)
                                            .flatMap(upd -> {
                                                if(upd.getEstado().equals("VENDIDO")){
                                                    return Mono.error(new OrdenAlreadySoldException("Orden " + idOrden + " ya fue vendida"));
                                                }
                                                Carrito carrito = new Carrito();
                                                carrito.setIdCarrito(dto.getIdOrden());
                                                carrito.setFechaCreacion(upd.getFechaCreacion());
                                                carrito.setEstado("VENDIDO");
                                                carrito.setCorreo(upd.getCorreo());
                                                carrito.setNombreCliente(upd.getNombreCliente());
                                                carrito.setDireccionEntrega(upd.getDireccionEntrega());
                                                carrito.setSubtotal(upd.getSubtotal());
                                                carrito.setFechaVenta(LocalDate.now());
                                                return carritoRepositorio.save(carrito)
                                                        .flatMap(carritoGuardado -> ComprarItems(items2));
                                            }).then();
                                }
                                else {
                                    return Mono.error(new OrdenNotFoundException("Carrito no encontrado " + idOrden));
                                }
                            });
                } else{
                    return Mono.error(new ProductLowStockException("Un articulo sin existencias"));
                }
            });

}


    public Mono<Boolean> consultaInventario(OrdenItemDTO item){

        return webClient.get()
                .uri("/api/inventario/{idProducto}" , item.getIdProducto())
                .retrieve()
                .bodyToMono(InvProduct.class)
                .map(response -> response.getStockActual() >= item.getCantidadItems())
                .onErrorReturn(false);
    }

    public Mono<Boolean> validarExistencias(List<OrdenItemDTO> dto){
        return Flux.fromIterable(dto)
                .flatMap(this::consultaInventario)
                .all(existen -> existen);
    }

    public Mono<Boolean> restarInventario(OrdenItemDTO item){
        CompraInventario comprar = new CompraInventario();
        comprar.setAccion("VENTA");
        comprar.setCantidad(item.getCantidadItems());
        comprar.setMotivo("COMPRA");
        return webClient
                .post()
                .uri("/api/inventario/{idProducto}/ajustar" , item.getIdProducto())
                .bodyValue(comprar)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().equals(HttpStatus.OK))
                .onErrorReturn(false);
    }

    public Mono<Boolean> ComprarItems(List<OrdenItemDTO> dto){
        return Flux.fromIterable(dto)
                .flatMap(this::restarInventario)
                .all(compra -> compra);
    }

}
