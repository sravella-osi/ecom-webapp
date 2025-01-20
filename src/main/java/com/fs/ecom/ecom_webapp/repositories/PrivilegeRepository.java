package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {



}
