package com.accela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accela.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

}
