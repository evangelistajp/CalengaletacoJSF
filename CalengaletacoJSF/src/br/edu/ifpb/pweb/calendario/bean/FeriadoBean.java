package br.edu.ifpb.pweb.calendario.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.edu.ifpb.pweb.calendario.dao.FeriadoDAO;
import br.edu.ifpb.pweb.calendario.dao.FeriadoSubstitutoDAO;
import br.edu.ifpb.pweb.calendario.dao.PersistenceUtil;
import br.edu.ifpb.pweb.calendario.model.Feriado;
import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;
import br.edu.ifpb.pweb.calendario.model.Usuario;

@ManagedBean(name="feriadoBean")
@SessionScoped
public class FeriadoBean {
	private String nome;
	private Date data;
	private Boolean fixo;
	private Usuario usuario;
	private Feriado feriado, auxFeriado;
	private FeriadoSubstituto substituto;
	private List<Feriado> feriados;
	private List<FeriadoSubstituto> substitutos;
	

	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	
	public String addFeriado(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		FeriadoDAO feriadodao = new FeriadoDAO(em);
        Feriado feriado = new Feriado();
        feriado.setNome(nome);
        feriado.setData(data);
        feriado.setFixo(fixo); 
		
		feriadodao.beginTransaction();
		feriadodao.insert(feriado);
		feriadodao.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Feriado cadastrado!",""));
		return "painelFeriados?faces-redirect=true";

	}
		
	public String addFeriadoSubstituto() {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		FeriadoSubstitutoDAO substitutodao = new FeriadoSubstitutoDAO(em);
		FeriadoSubstituto substituto = new FeriadoSubstituto();
		Feriado feriadofixo = new Feriado();
		feriadofixo = auxFeriado;
		
		substituto.setNome(feriadofixo.getNome());
		substituto.setData(feriadofixo.getData());
		feriadofixo.setData(data);
		substituto.setFeriadoFixo(feriadofixo);
		feriadofixo.addSubstituto(substituto); 
		
		substitutodao.beginTransaction();
		substitutodao.insert(substituto);
		substitutodao.commit();
		
				
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Feriado substituto cadastrado!",""));
		return "painelFeriados?faces-redirect=true";
	} 

	public void listarFeriados() {
		   EntityManager em = PersistenceUtil.getCurrentEntityManager();
		   FeriadoDAO feriadodao = new FeriadoDAO(em);
		   this.feriados = feriadodao.findAll();
	}
	
	public void listarFeriadosSubstitutos() {
		   EntityManager em = PersistenceUtil.getCurrentEntityManager();
		   FeriadoSubstitutoDAO substitutodao = new FeriadoSubstitutoDAO(em);
		   this.substitutos = substitutodao.findAll();
	}
	
	public void excluirFeriado(Feriado fe){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		FeriadoDAO feriadodao = new FeriadoDAO(em);
		Feriado feriado = feriadodao.find(fe.getId());
		
		if (feriado.getFixo()) {
			this.listarFeriadosSubstitutos();
			for (FeriadoSubstituto sub : substitutos) {
				if (feriado.getId() ==  sub.getFeriadoFixo().getId()) {
					this.excluirFeriadoSubstituto(sub);
				}
			}
			
		}
		
		feriadodao.beginTransaction();
		feriadodao.delete(feriado);
		feriadodao.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Feriado Excluido!",""));
		this.listarFeriados();
	}
	
	public void excluirFeriadoSubstituto(FeriadoSubstituto sub){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		FeriadoSubstitutoDAO substitutodao = new FeriadoSubstitutoDAO(em);
		FeriadoSubstituto substituto = substitutodao.find(sub.getId());
		Feriado feriado = substituto.getFeriadoFixo();
		
		feriado.removerSubstituto(substituto); 
		substitutodao.beginTransaction();
		substitutodao.delete(substituto);
		substitutodao.commit();
		
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Feriado Substituto Excluido!",""));
		this.listarFeriadosSubstitutos();
	}

	public void editarFeriado(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		FeriadoDAO feriadodao = new FeriadoDAO(em);
		Feriado fe = feriado;
		
		feriadodao.beginTransaction();
		feriadodao.update(fe);
		feriadodao.commit();
		
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Feriado Atualizado!",""));
		this.listarFeriados();
	}
	
	
	public String goToPainelFeriado(){
		this.nome = null;
		this.data = null;
		this.fixo = null;
		
		return "painelFeriados";
	}
	
	public String goToEditarFeriado(Feriado fe){
		this.feriado = fe;
				
		return "editarFeriado";
	}
	
	public String goToFeriadoSubstituto(Feriado fe){
		this.auxFeriado = fe;
		this.data =fe.getData();
		
		return "addFeriadoSubstituto";
	}
	
	public String goToAlteraSenha(){
		
		return "alteraSenha";
	}
	
	public String goToPainel(){
		
		return "painel";
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



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<Feriado> getFeriados() {
		return feriados;
	}


	public void setFeriados(List<Feriado> feriados) {
		this.feriados = feriados;
	}


	public Feriado getFeriado() {
		return feriado;
	}


	public void setFeriado(Feriado feriado) {
		this.feriado = feriado;
	}


	public FeriadoSubstituto getSubstituto() {
		return substituto;
	}


	public void setSubstituto(FeriadoSubstituto substituto) {
		this.substituto = substituto;
	}


	public List<FeriadoSubstituto> getSubstitutos() {
		return substitutos;
	}


	public void setSubstitutos(List<FeriadoSubstituto> substitutos) {
		this.substitutos = substitutos;
	}


	public Feriado getAuxFeriado() {
		return auxFeriado;
	}


	public void setAuxFeriado(Feriado auxFeriado) {
		this.auxFeriado = auxFeriado;
	}
	
	
	

}
		
