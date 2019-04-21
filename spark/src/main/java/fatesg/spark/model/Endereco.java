package fatesg.spark.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
	
	protected Long id;	 
	protected String rua;
	protected String bairro;
	protected String complemento;
	protected String cidade;
	
}
