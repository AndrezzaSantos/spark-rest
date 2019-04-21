package fatesg.spark.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Cliente implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
    @NotNull
    @NotEmpty
    @Column(length = 30, nullable = false)
	protected String razaoSocial;
	protected String cnpj;
	
	protected List<Endereco> endereco;

}
