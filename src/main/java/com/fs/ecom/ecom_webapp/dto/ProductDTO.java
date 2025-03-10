package com.fs.ecom.ecom_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String description;
    private CategoryDTO categoryDTO;
    private InventoryDTO inventoryDTO;
    private String name;
    private float price;
    private DiscountDTO discountDTO;
    private UserDetailsDTO seller;
    private boolean active;
    private String status;

    public ProductDTO(String description, CategoryDTO categoryDTO, InventoryDTO inventoryDTO, String name, float price, DiscountDTO discountDTO, UserDetailsDTO seller, boolean active, String status) {
        this.description = description;
        this.categoryDTO = categoryDTO;
        this.inventoryDTO = inventoryDTO;
        this.name = name;
        this.price = price;
        this.discountDTO = discountDTO;
        this.seller = seller;
        this.active = active;
        this.status = status;
    }
}
