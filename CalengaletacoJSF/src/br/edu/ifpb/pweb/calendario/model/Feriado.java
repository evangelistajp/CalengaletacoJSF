package br.edu.ifpb.pweb.calendario.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Feriado {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Type(type="true_false")
	private Boolean fixo=false;
	@OneToMany(mappedBy="feriadoFixo", cascade = CascadeType.ALL)
	private List<FeriadoSubstituto> substitutos = new ArrayList<FeriadoSubstituto>();
	
	public Feriado(){}
	
	public Feriado(String nome, Date data,Boolean fixo){
		this.nome = nome;
		this.data = data;
		this.fixo = fixo;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getFixo() {
		return fixo;
	}

	public void setFixo(Boolean fixo) {
		this.fixo = fixo;
	}

	public List<FeriadoSubstituto> getSubstitutos() {
		return substitutos;
	}

	public void setSubstitutos(List<FeriadoSubstituto> substitutos) {
		this.substitutos = substitutos;
	}

	public void addSubstituto(FeriadoSubstituto sub){
		this.substitutos.add(sub);
	}

	public void removerSubstituto(FeriadoSubstituto sub){
		this.substitutos.remove(sub);
	}

	public FeriadoSubstituto getSubstituto(long id){
		for (FeriadoSubstituto sub : substitutos) {
			if (sub.getId() == id) {
				return sub;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Feriado [id=" + id + ", nome=" + nome + ", data=" + data
				+ ", fixo=" + fixo + "]";
	}
}
