package br.edu.ifpb.pweb.calendario.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.edu.ifpb.pweb.calendario.dao.AnotacaoDAO;
import br.edu.ifpb.pweb.calendario.dao.PersistenceUtil;
import br.edu.ifpb.pweb.calendario.dao.UsuarioDAO;
import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Usuario;

@ManagedBean(name="anotacaoBean")
@SessionScoped
public class AnotacaoBean {

	private Usuario usuario;
	private List<Anotacao> anotacoes;
	private Anotacao anotacao;
	private String descricao;
	private Date data;


	@PostConstruct
	public void init() {
		usuario = new Usuario();

	}

	public String addAnotacao(Usuario us) {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDAO = new AnotacaoDAO(em);
		Anotacao anotacao = new Anotacao();
		anotacao.setDescricao(descricao);
		anotacao.setData(data);
		anotacao.setUsuario(us); 
		usuario = us;
				
		anotacaoDAO.beginTransaction();
		anotacaoDAO.insert(anotacao);
		usuario.addAnotacao(anotacao);
		anotacaoDAO.commit();
		
		return "painelAnotacao?faces-redirect=true";
	}

	public String excluirAnotacao(Anotacao an) {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDAO = new AnotacaoDAO(em);
		Anotacao anotacao = new Anotacao();
		anotacao = an;

		anotacaoDAO.beginTransaction();
		usuario = anotacao.getUsuario();
		anotacaoDAO.delete(anotacao);
		usuario.removerAnotacao(anotacao);

		anotacaoDAO.commit();


		return "painelAnotacao?faces-redirect=true";

	}

	public void editarAnotacao(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDao = new  AnotacaoDAO(em);
		Anotacao an = anotacao;
		
		anotacaoDao.beginTransaction();
		anotacaoDao.update(an);
		anotacaoDao.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Anotação Atualizado!",""));
		this.listarAnotacaoDoUsuario(usuario);
	}
	
	public void listarAnotacao(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDao = new AnotacaoDAO(em);
		this.anotacoes = anotacaoDao.findAll(); 
		System.out.println(this.anotacoes);
	}
	
	public void listarAnotacaoDoUsuario(Usuario us){
		anotacoes = new ArrayList<Anotacao>();
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDao = new AnotacaoDAO(em);
		List<Anotacao> allAnotacoes = new ArrayList<Anotacao>();
		allAnotacoes = anotacaoDao.findAll(); 
		for (Anotacao anotacao : allAnotacoes) {
			if (anotacao.getUsuario().getId() == us.getId()) {
				anotacoes.add(anotacao);
			}
		}
		
	}
	
	public List<Anotacao> pesquisaRetornaAnotacoesDoUsuario(Usuario us){
		List<Anotacao> anotacoes  = new ArrayList<Anotacao>();
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		AnotacaoDAO anotacaoDao = new AnotacaoDAO(em);
		List<Anotacao> allAnotacoes = new ArrayList<Anotacao>();
		allAnotacoes = anotacaoDao.findAll();
		for (Anotacao anotacao : allAnotacoes) {
			if (anotacao.getUsuario().getId() == us.getId()) {
				anotacoes.add(anotacao);
			}
		}
		return anotacoes;
	}
	
	
	public String goToPainelAnotacao(){
		this.data = null;
		this.descricao = null;
		
		return "painelAnotacao?faces-redirect=true";
	}
	
	public String goToEditarAnotacao(Anotacao an){
		this.anotacao = an;
		
		return "editarAnotacao";
	}
	
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Anotacao getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(Anotacao anotacao) {
		this.anotacao = anotacao;
	}

	
	
}
