package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {
    List<UserPrivilege> findByUserId(Long userId);
}