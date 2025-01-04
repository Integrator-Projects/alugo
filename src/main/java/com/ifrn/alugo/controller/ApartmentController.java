package com.ifrn.alugo.controller;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.service.ApartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<ApartmentResponseDTO> createApartment(@RequestBody ApartmentRequestDTO apartmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.createApartment(apartmentRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ApartmentResponseDTO>> getAllApartments(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getAllApartments(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponseDTO> getApartmentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getApartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponseDTO> updateApartment(@PathVariable Long id, @RequestBody ApartmentRequestDTO apartmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.updateApartment(id, apartmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
