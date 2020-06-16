package br.com.jopaulofood.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	public Cliente findByEmail(String email);
}
