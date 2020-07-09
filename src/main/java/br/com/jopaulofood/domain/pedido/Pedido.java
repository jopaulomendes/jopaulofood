package br.com.jopaulofood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.jopaulofood.domain.cliente.Cliente;
import br.com.jopaulofood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
	
	public enum Status{
		Producao(1, "Seu pedido está sendo preparado", false),
		Entrega(2, "Seu pedido já está indo até você", false),
		Concluido(3, "Pedido finalizado", true);		
		
	 Status(int ordem, String descricao, boolean ultimo) {
			this.ordem = ordem;
			this.descricao = descricao;
			this.ultimo = ultimo;
		}
		
		int ordem;
		String descricao;
		boolean ultimo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	private Status status;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Restaurante restaurante;
	
	@NotNull
	private BigDecimal subtotal;
	
	@NotNull
	private BigDecimal total;
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens;
}
