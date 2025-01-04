package com.ifrn.alugo.controller;

import com.ifrn.alugo.dto.BuildingRequestDTO;
import com.ifrn.alugo.dto.BuildingResponseDTO;
import com.ifrn.alugo.service.BuildingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping
    public ResponseEntity<BuildingResponseDTO> createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createBuilding(buildingRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<BuildingResponseDTO>> getAllBuildings(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.getAllBuildings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponseDTO> getBuildingById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.getBuildingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingResponseDTO> updateBuilding(@PathVariable Long id, @RequestBody BuildingRequestDTO buildingRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.updateBuilding(id, buildingRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BuildingResponseDTO> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
