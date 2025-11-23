package com.microservices.client;

import com.microservices.model.Producto;
import com.microservices.dto.Categoria;
import lombok.Data;

@Data
public class ProductoResponse {

    private Producto producto;
    private Categoria categoria;  // Debe ser Categoria, no CategoriaClient
}