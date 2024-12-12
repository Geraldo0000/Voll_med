package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;


//para fazer validações usamos o Bean Validation use anotações nos campos para validar
public record DadosCadastroMedico(

        @NotBlank // já verifica se o campo não é nulo e nem vazio e só para strings
        String nome,

        @NotBlank
        @Email// padrão de email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")// padrão expressão regular d= é um digito de 4 a 6 digitos
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        @Valid // para validar os dados do DTO Dados endereço e tambem validar a bean do DTO
        DadosEndereco endereco) {
}
