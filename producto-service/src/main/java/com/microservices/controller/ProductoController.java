package com.microservices.controller;

import com.microservices.client.ProductoResponse;
import com.microservices.model.Producto;
import com.microservices.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ----------------------------
    // LISTAR TODOS LOS PRODUCTOS
    // ----------------------------
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    // ----------------------------
    // OBTENER PRODUCTO POR ID (sin categoría)
    // ----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ----------------------------
    // OBTENER PRODUCTO CON CATEGORÍA (comunicación entre microservicios)
    // ----------------------------
    @GetMapping("/{id}/con-categoria")
    public ResponseEntity<ProductoResponse> obtenerConCategoria(@PathVariable Long id) {
        return productoService.obtenerProductoConCategoria(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ----------------------------
    // CREAR PRODUCTO
    // ----------------------------
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // ----------------------------
    // ACTUALIZAR PRODUCTO
    // ----------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        return productoService.actualizarProducto(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ----------------------------
    // ELIMINAR PRODUCTO
    // ----------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
