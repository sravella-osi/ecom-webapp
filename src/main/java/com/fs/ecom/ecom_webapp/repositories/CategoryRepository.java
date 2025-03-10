package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
