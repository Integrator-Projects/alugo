package com.ifrn.domusmanager.repository;

import com.ifrn.domusmanager.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
