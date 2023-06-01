package ufrn.edu.loja.prova.model;

import java.time.LocalDate;


import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamisaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long softDelete;

    
    private String imageURI;

    @NotBlank
    private String tamanho;

    @NotBlank
    private String cor;

    @NotBlank
    private String tipo;

    @NumberFormat(style = Style.CURRENCY , pattern = "#,##0.00")
    private Double preco;

    public void setSoftDelete() {
        this.softDelete = LocalDate.now().toEpochDay();
    }

}