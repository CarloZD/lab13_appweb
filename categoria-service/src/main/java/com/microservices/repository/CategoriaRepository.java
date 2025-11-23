package com.microservices.repository;

import com.microservices.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository  extends JpaRepository<Categoria,Long>{
}
