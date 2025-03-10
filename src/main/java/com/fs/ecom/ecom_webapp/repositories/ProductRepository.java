package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String searchQuery);
    List<String> findNameByNameContainingIgnoreCase(String searchQuery);
}
