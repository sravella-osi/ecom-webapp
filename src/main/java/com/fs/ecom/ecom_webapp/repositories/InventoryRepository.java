package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
