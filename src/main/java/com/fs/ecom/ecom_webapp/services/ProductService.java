package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.ProductDTO;
import com.fs.ecom.ecom_webapp.exceptions.*;
import com.fs.ecom.ecom_webapp.mapper.ProductMapper;
import com.fs.ecom.ecom_webapp.models.Product;
import com.fs.ecom.ecom_webapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    UserRepository userRepository;

    public ProductDTO addProducts(ProductDTO productDTO){
        Product saved = null;
        if (productDTO.getCategoryDTO().getId()==null)
            throw new InvalidProductException("Category Id cannot be null.");
        else if (productDTO.getSeller().getId()==null)
            throw new InvalidProductException("Seller id cannot be null.");
        else if (!categoryRepository.existsById(productDTO.getCategoryDTO().getId()))
            throw new CategoryDoesNotExistException("Category does not exist.");
        else if(!userRepository.existsById(productDTO.getSeller().getId()))
            throw new UserNotFoundException("Seller does not exist.");
        else if(productDTO.getDiscountDTO().getValue()<=0)
            throw new InvalidDiscountException("Discount cannot be zero(0) or negative(-ve).");
        else {
            saved = productRepository.save(productMapper.toProduct(productDTO));
        }

        return productMapper.toProductDTO(saved);
    }

    public ProductDTO getProduct(Long id){
        if(productRepository.existsById(id))
            return productMapper.toProductDTO(productMapper.newProduct(productRepository.getReferenceById(id)));
        else
            throw new ProductNotFoundException("Product with Id : " + id + " not found.");
    }


    public List<ProductDTO> getProducts(String searchQuery) {
        if(productRepository.findByNameContainingIgnoreCase(searchQuery).isEmpty())
            throw new ProductNotFoundException("No products found under the search : "+ searchQuery);
        else 
            return productMapper.toProductDTOList(productRepository.findByNameContainingIgnoreCase(searchQuery));
    }

    public List<String> getSearchSuggestions(String searchQuery) {
        if(productRepository.findNameByNameContainingIgnoreCase(searchQuery).isEmpty())
            return new ArrayList<>();
        else
            return productRepository.findNameByNameContainingIgnoreCase(searchQuery);
    }
}
