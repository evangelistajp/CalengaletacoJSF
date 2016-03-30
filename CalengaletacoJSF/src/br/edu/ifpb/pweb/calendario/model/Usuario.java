package br.edu.ifpb.pweb.calendario.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	@Id		
	@GeneratedValue
	private long id;
	private String nome;
	@Column(unique=true)
	private String email;
	private String senha;
	private boolean isadmin;
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	private List<Anotacao> anotacoes = new ArrayList<Anotacao>();
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.isadmin = false;
	}
	
	public Usuario(String nome, String email, String senha, boolean isadmin) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.isadmin = isadmin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isIsadmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}
	
	public void addAnotacao(Anotacao an){
		this.anotacoes.add(an);
	}

	public void removerAnotacao(Anotacao an){
		this.anotacoes.remove(an);
	}

	public Anotacao getAnotacao(long id){
		for (Anotacao anotacao : anotacoes) {
			if (anotacao.getId() == id) {
				return anotacao;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email
				+ ", senha=" + senha + ", isadmin=" + isadmin + ", anotacoes="
				+ anotacoes + "]";
	}

	
	
}
