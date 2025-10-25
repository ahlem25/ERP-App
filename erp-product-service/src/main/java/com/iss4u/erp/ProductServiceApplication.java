package com.iss4u.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return Arrays.asList(
            new Product(1L, "Produit 1", "Description du produit 1", 100.0),
            new Product(2L, "Produit 2", "Description du produit 2", 200.0),
            new Product(3L, "Produit 3", "Description du produit 3", 300.0)
        );
    }

    @GetMapping("/devis")
    public List<Devis> getDevis() {
        return Arrays.asList(
            new Devis(1L, "Devis 001", "Client A", 1500.0),
            new Devis(2L, "Devis 002", "Client B", 2500.0),
            new Devis(3L, "Devis 003", "Client C", 3500.0)
        );
    }

    public static class Product {
        private Long id;
        private String name;
        private String description;
        private Double price;

        public Product(Long id, String name, String description, Double price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    public static class Devis {
        private Long id;
        private String numero;
        private String client;
        private Double montant;

        public Devis(Long id, String numero, String client, Double montant) {
            this.id = id;
            this.numero = numero;
            this.client = client;
            this.montant = montant;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }
        public String getClient() { return client; }
        public void setClient(String client) { this.client = client; }
        public Double getMontant() { return montant; }
        public void setMontant(Double montant) { this.montant = montant; }
    }
}
