package com.fs.ecom.ecom_webapp.mapper;

import com.fs.ecom.ecom_webapp.dto.CategoryDTO;
import com.fs.ecom.ecom_webapp.dto.DiscountDTO;
import com.fs.ecom.ecom_webapp.dto.InventoryDTO;
import com.fs.ecom.ecom_webapp.dto.ProductDTO;
import com.fs.ecom.ecom_webapp.models.Category;
import com.fs.ecom.ecom_webapp.models.Discount;
import com.fs.ecom.ecom_webapp.models.Inventory;
import com.fs.ecom.ecom_webapp.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    UserMapper userMapper;

    public Product newProduct(Product product){
        Product newProduct = new Product();

        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setStatus(product.getStatus());
        newProduct.setPrice(product.getPrice());
        newProduct.setDiscount(product.getDiscount());
        newProduct.setActive(product.isActive());
        newProduct.setCategory(product.getCategory());
        newProduct.setInventory(product.getInventory());
        newProduct.setSeller(product.getSeller());
        newProduct.setCreatedAt(product.getCreatedAt());
        newProduct.setModifiedAt(product.getModifiedAt());

        return newProduct;
    }

    public Product toProduct(ProductDTO productDTO){
        Product product = new Product();
        if(productDTO.getId()!=null){
            product.setId(productDTO.getId());
        }
        product.setActive(productDTO.isActive());
        product.setDescription(productDTO.getDescription());
        product.setCategory(toCategory(productDTO.getCategoryDTO()));
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(toDiscount(productDTO.getDiscountDTO()));
        product.setSeller(userMapper.toUser(productDTO.getSeller()));
        product.setInventory(toInventory(productDTO.getInventoryDTO()));

        return product;
    }

    public List<ProductDTO> toProductDTOList(List<Product> productList){
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product : productList){
            productDTOList.add(toProductDTO(product));
        }
        return productDTOList;
    }

    public List<Product> toProductList(List<ProductDTO> productDTOList){
        List<Product> productList = new ArrayList<>();
        for(ProductDTO productDTO : productDTOList){
            productList.add(toProduct(productDTO));
        }
        return productList;
    }

    public ProductDTO toProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        if(product.getId()!=null){
            productDTO.setId(product.getId());
        }
        productDTO.setActive(product.isActive());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryDTO(toCategoryDTO(product.getCategory()));
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDiscountDTO(toDiscountDTO(product.getDiscount()));
        productDTO.setSeller(userMapper.getUserDetailsDTO(product.getSeller()));
        productDTO.setInventoryDTO(toInventoryDTO(product.getInventory()));

        return productDTO;
    }

    public Inventory toInventory(InventoryDTO inventoryDTO){
        Inventory inventory = new Inventory();
        if(inventoryDTO.getId()!=null) inventory.setId(inventoryDTO.getId());
        inventory.setQuantity(inventoryDTO.getQuantity());

        return inventory;
    }

    public InventoryDTO toInventoryDTO(Inventory inventory){
        InventoryDTO inventoryDTO = new InventoryDTO();
        if(inventory.getId()!=null) inventoryDTO.setId(inventory.getId());
        inventoryDTO.setQuantity(inventory.getQuantity());

        return inventoryDTO;
    }

    public Category toCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        if(categoryDTO.getId()!=null) category.setId(categoryDTO.getId());
        category.setDescription(categoryDTO.getDescription());
        category.setName(categoryDTO.getName());
        return category;
    }

    public CategoryDTO toCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        if(category.getId()!=null) categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public Discount toDiscount(DiscountDTO discountDTO){
        Discount discount = new Discount();
        if(discountDTO.getId()!=null) discount.setId(discountDTO.getId());
        discount.setValue(discountDTO.getValue());
        return discount;
    }

    public DiscountDTO toDiscountDTO(Discount discount){
        DiscountDTO discountDTO = new DiscountDTO();
        if(discount.getId()!=null) discountDTO.setId(discount.getId());
        discountDTO.setValue(discount.getValue());
        return discountDTO;
    }
}
