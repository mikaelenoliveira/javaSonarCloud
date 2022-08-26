package com.ApiRest.entidades;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Contato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "contato", cascade = CascadeType.REMOVE)
	private List<Compromisso> compromissos;
	
	@NotBlank(message = "Nome é obrigatorio")
	@Column(length = 100, nullable = false)
	private String nome;
	
	@NotBlank(message = "O e-mail é obrigatorio")
	@Email(message = "E-mail invalido")
	@Column(length = 100, nullable = false)
	private String email;
	
	
	@Size(min = 14, max= 14, message = "O telefone deve ter no minimo 14 caracteres")
	private String fone;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updateAt;
	
	

	public Instant getCreatedAt() {
		return createdAt;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = Instant.now();
	}

	public Instant getUpdateAt() {
		return updateAt;
	}

	@PreUpdate
	public void setUpdateAt() {
		this.updateAt =  Instant.now();
	}

	public Contato() {

	}

	public Contato(String nome, String email, String fone) {
		this.nome = nome;
		this.email = email;
		this.fone = fone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

}
