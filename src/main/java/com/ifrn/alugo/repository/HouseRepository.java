package com.ifrn.alugo.repository;

import com.ifrn.alugo.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
