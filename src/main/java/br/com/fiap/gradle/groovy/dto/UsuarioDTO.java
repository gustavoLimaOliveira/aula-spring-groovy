package br.com.fiap.gradle.groovy.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        String nome,
        @NotBlank(message = "O e-mail não pode estar em branco.")
        @Email(message = "E-mail inválido.")
        String email,
        @NotBlank(message = "O CPF não pode estar em branco.")
        @CPF(message = "CPF inválido.")
        String cpf,
        LocalDate dataNascimento
) {
}