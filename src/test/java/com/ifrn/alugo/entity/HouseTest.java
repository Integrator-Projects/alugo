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

class HouseTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Validar preço do aluguel")
    void validateWhenRentPriceIsNotNegative() {
         House house = Instancio.of(House.class)
                .set(Select.field(House::getRentalPrice), 0.0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Valor do aluguel deve ser positivo")
    void shouldNotValidateWhenRentPriceIsNegative() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getRentalPrice), -1.0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertEquals("Valor do aluguel deve ser positivo", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Número de banheiros deve ser maior que zero")
    void shouldNotValidateNumberOfBathroomsIsLessThanZero() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getNumberOfBathrooms), 0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertEquals("Número de banheiros deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar número de banheiros")
    void validateWhenNumberOfBathroomsIsGreaterThanZero() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getNumberOfBathrooms), 1)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Validar número de quartos")
    void validateWhenNumberOfBedroomsIsGreaterThanZero() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getNumberOfBedrooms), 1)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número de quartos deve ser maior que zero")
    void shouldNotValidateNumberOfBedroomsIsNotPositive() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getNumberOfBedrooms), 0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertEquals("Número de quartos deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar área")
    void validateWhenAreaIsPositive() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getAreaInM2), 1.0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Área deve ser maior que zero")
    void shouldNotValidateAreaIsNotPositive() {
        House house = Instancio.of(House.class)
                .set(Select.field(House::getAreaInM2), 0.0)
                .create();

        Set<ConstraintViolation<House>> constraintViolations = validator.validate(house);
        assertEquals("Área deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

}