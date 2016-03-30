package br.edu.ifpb.pweb.calendario.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FeriadoSubstituto{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private Feriado feriadoFixo;
	
	public FeriadoSubstituto(){
		super();
	}

	public FeriadoSubstituto(Long id,String nome, Date data, Feriado fe){
		this.id = id;
		this.nome = nome;
		this.data = data;	
		this.feriadoFixo = fe;
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

	public void setFeriadoFixo(Feriado fixo) {
		this.feriadoFixo = fixo;
	}

	public Feriado getFeriadoFixo() {
		return feriadoFixo;
	}

	@Override
	public String toString() {
		return "FeriadoSubstituto [id=" + id + ", nome=" + nome + ", data="
				+ data + ", feriadoFixo=" + feriadoFixo.getData() + "]";
	}
	
}
