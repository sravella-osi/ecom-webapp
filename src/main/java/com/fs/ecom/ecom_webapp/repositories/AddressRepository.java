package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressBook, Long> {

    List<AddressBook> findByUserId(Long userId);

}
