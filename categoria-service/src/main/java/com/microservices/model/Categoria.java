package com.microservices.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
