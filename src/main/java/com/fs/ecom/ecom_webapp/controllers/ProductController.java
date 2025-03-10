package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.ProductDTO;
import com.fs.ecom.ecom_webapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(originPatterns = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProducts(productDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ProductDTO getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ProductDTO> getAllProducts(@RequestParam(name = "search") String searchQuery){
        return productService.getProducts(searchQuery);
    }

    @GetMapping("/searchList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<String> getSearchSuggestions(@RequestParam(name = "search") String searchQuery){
        return productService.getSearchSuggestions(searchQuery);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateProduct(@PathVariable("id") Long id){
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable("id") Long id){
    }

}
