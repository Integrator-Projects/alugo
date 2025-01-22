package com.ifrn.alugo.controller;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Apartamentos", description = "Gerenciamento de apartamentos")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo apartamento")
    public ResponseEntity<ApartmentResponseDTO> createApartment(@RequestBody ApartmentRequestDTO apartmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.createApartment(apartmentRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Listar todos os apartamentos")
    public ResponseEntity<Page<ApartmentResponseDTO>> getAllApartments(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getAllApartments(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um apartamento pelo id")
    public ResponseEntity<ApartmentResponseDTO> getApartmentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getApartmentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um apartamento")
    public ResponseEntity<ApartmentResponseDTO> updateApartment(@PathVariable Long id, @RequestBody ApartmentRequestDTO apartmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.updateApartment(id, apartmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um apartamento")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
