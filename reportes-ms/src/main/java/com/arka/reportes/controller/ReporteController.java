package com.arka.reportes.controller;

import com.arka.reportes.infrastructure.model.InventarioView;
import com.arka.reportes.infrastructure.model.VentasView;
import com.arka.reportes.infrastructure.model.OrdenesView;


import com.arka.reportes.infrastructure.repository.InventarioRepository;
import com.arka.reportes.infrastructure.repository.OrdenesRepository;
import com.arka.reportes.infrastructure.repository.VentasRepository;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final InventarioRepository inventarioRepository;
    private final VentasRepository ventasRepository;
    private final OrdenesRepository ordenesRepository;

    public ReporteController(InventarioRepository inventarioRepository,
                             VentasRepository ventasRepository,
                             OrdenesRepository ordenesRepository) {
        this.inventarioRepository = inventarioRepository;
        this.ventasRepository = ventasRepository;
        this.ordenesRepository = ordenesRepository;
    }

    // ======================================================
    // =============== ENDPOINTS JSON NORMALES ===============
    // ======================================================

    @GetMapping("/inventario")
    public Flux<InventarioView> obtenerInventario() {
        return inventarioRepository.findAll();
    }

    @GetMapping("/ventas")
    public Flux<VentasView> obtenerVentas() {
        return ventasRepository.findAll();
    }

    @GetMapping("/ordenes")
    public Flux<OrdenesView> obtenerOrdenes() {
        return ordenesRepository.findAll();
    }

    // ======================================================
    // =============== ENDPOINTS CSV REACTIVOS ==============
    // ======================================================

    /**
     * Exporta el inventario a CSV en formato streaming reactivo
     */
    @GetMapping(value = "/inventario/csv", produces = "text/csv")
    public Mono<ResponseEntity<Flux<DataBuffer>>> exportInventarioCsv(ServerHttpResponse response) {

        Flux<String> csvFlux = inventarioRepository.findAll()
                .map(i -> String.join(",",
                        String.valueOf(i.getProductoId()),
                        escapeCsv(i.getProducto()),
                        String.valueOf(i.getStockActual()),
                        String.valueOf(i.getUmbralMinimo()),
                        String.valueOf(i.getFaltante()),
                        String.valueOf(i.getFechaActualizacion())
                ))
                .startWith("Producto ID,Producto,Stock Actual,Umbral Mínimo,Faltante,Fecha Actualización");

        Flux<DataBuffer> dataBufferFlux = csvFlux.map(line ->
                response.bufferFactory().wrap((line + "\n").getBytes(StandardCharsets.UTF_8))
        );

        return Mono.just(ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(dataBufferFlux));
    }

    /**
     * Exporta las ventas a CSV en formato streaming reactivo
     */
    @GetMapping(value = "/ventas/csv", produces = "text/csv")
    public Mono<ResponseEntity<Flux<DataBuffer>>> exportVentasCsv(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            ServerHttpResponse response) {

        Flux<String> csvFlux = ventasRepository.findAll()
                .map(v -> String.join(",",
                        String.valueOf(v.getProductoId()),
                        escapeCsv(v.getProducto()),
                        escapeCsv(v.getCliente()),
                        String.valueOf(v.getTotalVendido()),
                        String.valueOf(v.getTotalVentas()),
                        String.valueOf(v.getNumeroCompras()),
                        String.valueOf(v.getTotalGastado()),
                        String.valueOf(v.getFechaVenta())
                ))
                .startWith("Producto ID,Producto,Cliente,Total Vendido,Total Ventas,Número Compras,Total Gastado,Fecha Venta");

        Flux<DataBuffer> dataBufferFlux = csvFlux.map(line ->
                response.bufferFactory().wrap((line + "\n").getBytes(StandardCharsets.UTF_8))
        );

        return Mono.just(ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ventas.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(dataBufferFlux));
    }

    // ======================================================
    // ================== MÉTODO UTILITARIO =================
    // ======================================================

    /**
     * Escapa comas y comillas para evitar romper el CSV
     */
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}
