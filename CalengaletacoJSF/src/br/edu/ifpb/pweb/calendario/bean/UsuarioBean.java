package br.edu.ifpb.pweb.calendario.bean;

import java.io.IOException;


import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.edu.ifpb.pweb.calendario.dao.AnotacaoDAO;
import br.edu.ifpb.pweb.calendario.dao.PersistenceUtil;
import br.edu.ifpb.pweb.calendario.dao.UsuarioDAO;
import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
	private String nome, senha, email, senhaAntiga, senhaNova;
	private Usuario usuarioLogado;
	List<Anotacao> anotacoes;
	@ManagedProperty(value="#{anotacaoBean}")
	private AnotacaoBean anotacaoBean;


	
	@PostConstruct
	public void criarAdmin() {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		if (usuarioDAO.findAll().size() == 0) {
			Usuario admin = new Usuario();
			admin.setNome("gg");
			admin.setSenha("gg");
			admin.setEmail("gg");
			admin.setIsadmin(true);
			
			usuarioDAO.beginTransaction();
			usuarioDAO.insert(admin);
			usuarioDAO.commit();
		}
	}

	public String efetuarLogin() {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		Usuario usuario = new Usuario();
		try {
			usuario = usuarioDAO.findByLogin(this.email, this.senha);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:Login e/ou Senha incorretos!",""));

			return null;
		}
		
		if (usuario != null && usuario.getSenha().equals(this.senha)) {
			this.usuarioLogado = usuario;
			if (usuario.isIsadmin() == true) { 
				return "painel?faces-redirect=true"; 
			} else {
				return "painel?faces-redirect=true";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:Login e/ou Senha incorretos!",""));
		}
		return null;
	}
	
	public String logout() {
		this.usuarioLogado = null;
		this.email = null;
		this.nome = null;
		this.senha = null;
		

		return "index";
	}
	
	public String efetuarCadastro() throws IOException{
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);		
		if(usuarioLogado == null){
			Usuario usuario = new Usuario();
			
			usuario.setIsadmin(false);
			usuario.setNome(nome);
			usuario.setSenha(senha);
			usuario.setEmail(email);
			
			usuarioDAO.beginTransaction();
			usuarioDAO.insert(usuario);
			usuarioDAO.commit();

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:Usuario cadastrado!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:Erro Ao cadastra Usuario",""));
		}
		
		this.email = null;
		this.nome = null;
		this.senha = null;
		
		return "index";
	}
	
	public String alterarSenha() {
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		if (this.senhaAntiga.equals(this.usuarioLogado.getSenha())) {
			this.usuarioLogado.setSenha(this.senhaNova);
			this.senha = this.senhaNova;
			
			usuarioDAO.beginTransaction();
			usuarioDAO.update(usuarioLogado);
			usuarioDAO.commit();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso: Senha alterada!",""));
			return "alteraSenha";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: Senha antiga não Alterada",""));
			return "alteraSenha";
		}
	}
	
	public String excluirUser(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		Usuario usuario = usuarioLogado;
				
		usuarioDAO.beginTransaction();
		usuarioDAO.delete(usuario);
		usuarioDAO.commit();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso: Usuário Excluído",""));
		return this.logout();
	}
	
	public void verificaLogin() throws IOException{
		if (usuarioLogado == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
		}

	}
	
	
	public String goToCadastrar(){
		this.email = null;
		this.nome = null;
		this.senha = null;
		
		return "cadastrarUsuario";
	}
	
	public String goToIndex(){
		this.email = null;
		this.nome = null;
		this.senha = null;
		
		return "index";
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AnotacaoBean getAnotacaoBean() {
		return anotacaoBean;
	}

	public void setAnotacaoBean(AnotacaoBean anotacaoBean) {
		this.anotacaoBean = anotacaoBean;
	}


	
	
}
