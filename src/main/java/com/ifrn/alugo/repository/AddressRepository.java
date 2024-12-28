package com.ifrn.alugo.repository;

import com.ifrn.alugo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
