package com.ifrn.alugo.repository;

import com.ifrn.alugo.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByAddressStreetAndAddressCityAndAddressZipCode(String street, String city, String zipCode);
}
