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

class BuildingTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Validar prédio com número de andares válido")
    void validateWhenNumberOfFloorsIsValid() {
        Building building = Instancio.of(Building.class)
                .set(Select.field(Building::getNumberOfFloors), 0)
                .create();

        Set<ConstraintViolation<Building>> constraintViolations = validator.validate(building);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número de andares não pode ser negativo")
    void shouldNotValidateWhenNumberOfFloorsIsNotPositive() {
        Building building = Instancio.of(Building.class)
                .set(Select.field(Building::getNumberOfFloors), -1)
                .create();

        Set<ConstraintViolation<Building>> constraintViolations = validator.validate(building);
        assertEquals("Número de andares não pode ser negativo", constraintViolations.iterator().next().getMessage());
    }
}