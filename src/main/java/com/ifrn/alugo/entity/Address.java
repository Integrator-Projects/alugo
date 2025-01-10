package com.ifrn.alugo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A cidade não pode ser vazia")
    private String city;

    @NotBlank(message = "Estado não pode ser vazio")
    private String state;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido")
    private String zipCode;

    @NotBlank(message = "A rua não pode ser vazia")
    private String street;

    @NotBlank(message = "O bairro não pode ser vazio")
    private String neighborhood;

    @NotNull(message = "Número não pode ser nulo")
    @Positive(message = "Número deve ser maior que zero")
    private Integer number;
}
