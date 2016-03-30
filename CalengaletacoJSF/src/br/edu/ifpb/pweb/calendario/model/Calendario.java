package br.edu.ifpb.pweb.calendario.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Calendario {
	
	private Integer dia, mes, ano, diaDaSemana;
	private String data, datafixo;
	private String strferiados;
	private String strAnotacoes;
 	private List<Feriado> feriados;
	private List<FeriadoSubstituto> substitutos;
	private List<Anotacao> anotacoes;
	
	
	public Calendario(Integer dia, Integer mes, Integer ano){ 
		this.feriados = new ArrayList<Feriado>();
		this.substitutos = new ArrayList<FeriadoSubstituto>();
		this.anotacoes = new ArrayList<Anotacao>();
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		if(dia <= 9){
			this.data = "0"+this.dia+"/";
		}else{
			this.data = ""+this.dia+"/";
		}
		if (mes <= 9) {
			this.data += "0"+this.mes;
		}else{
			this.data += ""+this.mes;
		}
		this.datafixo = this.data;
		this.data += "/"+ this.ano;
	}
	
	
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public List<Feriado> getFeriados() {
		return feriados;
	}
	public void setFeriados(List<Feriado> feriados) {
		this.feriados = feriados;
	}
	
	public void addFeriado(Feriado feriado){
		this.feriados.add(feriado);
	}
	
	public Feriado getFeriado(long id){
		for (Feriado feriado : feriados) {
			if (feriado.getId() == id) {
				return feriado;
			}
		}	
		return null;
				
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
	
	public void addAnotacoes(List<Anotacao> an){
		this.anotacoes.addAll(an);
	}
	
	public List<FeriadoSubstituto> getSubstitutos() {
		return substitutos;
	}
	
	public void setSubstitutos(List<FeriadoSubstituto> substitutos) {
		this.substitutos = substitutos;
	}

	public void addSubstitutos(FeriadoSubstituto sub){
		this.substitutos.add(sub);
	}

	public Integer getDiaDaSemana() {
		return diaDaSemana;
	}


	public void setDiaDaSemana(Integer diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}


	public String getData() {
		return data;
	}

	public String getDataFixo() {
		return this.datafixo;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void setDataFixo(String data) {
		this.datafixo = data;
	}


	public String getStrferiados() {
		return strferiados;
	}


	public void setStrferiados(String strferiados) {
		this.strferiados = strferiados;
	}


	public String getStrAnotacoes() {
		return strAnotacoes;
	}


	public void setStrAnotacoes(String strAnotacoes) {
		this.strAnotacoes = strAnotacoes;
	}

	
		
	
}
