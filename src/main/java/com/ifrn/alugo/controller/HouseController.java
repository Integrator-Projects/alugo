package com.ifrn.alugo.controller;

import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/houses")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping
    public ResponseEntity<HouseResponseDTO> createHouse(@RequestBody HouseRequestDTO houseRequestDTO) {
        HouseResponseDTO houseResponseDTO = houseService.createHouse(houseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(houseResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<HouseResponseDTO>> getAllHouses(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(houseService.getAllHouses(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponseDTO> findHouseById(@PathVariable Long id) {
        HouseResponseDTO house = houseService.findHouseById(id);
        return ResponseEntity.ok(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseResponseDTO> updateHouse(@PathVariable Long id, @RequestBody HouseRequestDTO houseRequestDTO) {
        HouseResponseDTO house = houseService.updateHouse(id, houseRequestDTO);
        return ResponseEntity.ok(house);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}
