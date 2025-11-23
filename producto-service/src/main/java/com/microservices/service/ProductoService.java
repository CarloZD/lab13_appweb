package com.microservices.service;

import com.microservices.client.CategoriaClient;
import com.microservices.client.ProductoResponse;
import com.microservices.dto.Categoria;
import com.microservices.model.Producto;
import com.microservices.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaClient categoriaClient;

    public ProductoService(ProductoRepository productoRepository, CategoriaClient categoriaClient) {
        this.productoRepository = productoRepository;
        this.categoriaClient = categoriaClient;
    }

    // Listar todos los productos
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Obtener producto con su categoría (comunicación entre microservicios)
    public Optional<ProductoResponse> obtenerProductoConCategoria(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            ProductoResponse response = new ProductoResponse();
            response.setProducto(producto.get());

            // Llamar al microservicio de categorías usando Feign
            try {
                Categoria categoria = categoriaClient.obtenerCategoria(producto.get().getCategoriaId());
                response.setCategoria(categoria);
            } catch (Exception e) {
                // Si no se puede obtener la categoría, dejamos el campo null
                System.err.println("Error al obtener categoría: " + e.getMessage());
            }

            return Optional.of(response);
        }

        return Optional.empty();
    }

    // Crear producto
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Optional<Producto> actualizarProducto(Long id, Producto datosProducto) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(datosProducto.getNombre());
                    productoExistente.setPrecio(datosProducto.getPrecio());
                    productoExistente.setCategoriaId(datosProducto.getCategoriaId());
                    return productoRepository.save(productoExistente);
                });
    }

    // Eliminar producto
    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
