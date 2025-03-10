package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
}
