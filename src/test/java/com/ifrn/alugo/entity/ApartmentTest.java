package com.ifrn.alugo.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Validar apartamento com número válido")
    void validateWhenNumberIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumber), 1)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número deve ser maior que zero")
    void shouldNotValidateWhenNumberIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumber), 0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Número deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar apartamento com andar válido")
    void validateWhenFloorIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getFloor), 0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Andar não pode ser negativo")
    void shouldNotValidateWhenFloorIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getFloor), -1)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Andar não pode ser negativo", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar apartamento com número de quartos válido")
    void validateWhenNumberOfBedroomsIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumberOfRooms), 0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número de quartos não pode ser negativo")
    void shouldNotValidateWhenNumberOfBedroomsIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumberOfRooms), -1)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Número de quartos não pode ser negativo", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar apartamento com número de banheiros válido")
    void validateWhenNumberOfBathroomsIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumberOfBathrooms), 1)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número de banheiros deve ser maior que zero")
    void shouldNotValidateWhenNumberOfBathroomsIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getNumberOfBathrooms), 0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Número de banheiros deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar apartamento com área válida")
    void validateWhenAreaIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getAreaInM2), 1.0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Área deve ser maior que zero")
    void shouldNotValidateWhenAreaIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getAreaInM2), -1.0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Área deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar apartamento com aluguel válido")
    void validateWhenRentalPriceIsValid() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getPrice), 1.0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Preço do aluguel não pode ser negativo")
    void shouldNotValidateWhenRentalPriceIsNotPositive() {
        Apartment apartment = Instancio.of(Apartment.class)
                .set(Select.field(Apartment::getPrice), -1.0)
                .create();

        Set<ConstraintViolation<Apartment>> constraintViolations = validator.validate(apartment);
        assertEquals("Preço do aluguel não pode ser negativo", constraintViolations.iterator().next().getMessage());
    }
}