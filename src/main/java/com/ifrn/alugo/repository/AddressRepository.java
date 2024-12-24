package com.ifrn.domusmanager.repository;

import com.ifrn.domusmanager.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
