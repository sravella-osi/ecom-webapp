package com.fs.ecom.ecom_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private float quantity;


    public InventoryDTO(float quantity) {
        this.quantity = quantity;
    }
}
