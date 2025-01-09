package com.ifrn.alugo.repository;

import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByAddressStreetAndAddressCityAndAddressZipCode(String street, String city, String zipCode);
}
