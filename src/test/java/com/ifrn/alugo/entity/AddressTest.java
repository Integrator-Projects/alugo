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

class AddressTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Validar endereço com cidade válida")
    void validateWhenCityIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-678")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Cidade não pode ser vazia")
    void shoultNotValidateWhenCityIsEmpty() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("A cidade não pode ser vazia", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar endereço com estado válido")
    void validateWhenStateIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Estado não pode ser vazio")
    void validateWhenStateIsEmpty() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("Estado não pode ser vazio", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar endereço com CEP válido")
    void validateWhenZipCodeIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Formato de CEP inválido")
    void validateWhenZipCodeIsInvalid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "123456-78")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("Formato de CEP inválido", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar endereço com bairro válido")
    void validateWhenNeighborhoodIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("O bairro não pode ser vazio")
    void validateWhenNeighborhoodIsEmpty() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "")
                .set(Select.field(Address::getNumber), 123)
                .create();
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("O bairro não pode ser vazio", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar endereço com número válido")
    void validateWhenNumberIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 1)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("Número não pode ser nulo")
    void validateWhenNumberIsEmpty() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), null)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("Número não pode ser nulo", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Número deve ser maior que zero")
    void validateWhenNumberIsLessThanOne() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 0)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("Número deve ser maior que zero", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Validar endereço com rua válida")
    void validateWhenStreetIsValid() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "Rua Exemplo")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("A rua não pode ser vazia")
    void validateWhenStreetIsEmpty() {
        Address address = Instancio.of(Address.class)
                .set(Select.field(Address::getCity), "São Paulo")
                .set(Select.field(Address::getState), "SP")
                .set(Select.field(Address::getZipCode), "12345-789")
                .set(Select.field(Address::getStreet), "")
                .set(Select.field(Address::getNeighborhood), "Centro")
                .set(Select.field(Address::getNumber), 123)
                .create();

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        assertEquals("A rua não pode ser vazia", constraintViolations.iterator().next().getMessage());
    }
}