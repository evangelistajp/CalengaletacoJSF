package br.edu.ifpb.pweb.calendario.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.pweb.calendario.model.Usuario;


public class UsuarioDAO extends GenericDAOJPAImpl<Usuario, Long>{
	
	private static Logger logger = Logger.getLogger(UsuarioDAO.class);
	
	public UsuarioDAO(EntityManager em){
		super(em);
	}
	
	public UsuarioDAO() {
		//this(PersistenceUtil.getCurrentEntityManager());
	}

	public Usuario findByLogin(String email, String senha) throws DAOException{
		Usuario usuario = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Usuario u where u.email ='"+email+
							"' and u.senha ='" + senha +"'");
			usuario =  (Usuario) q.getSingleResult();
			if (usuario != null) {
				return (Usuario) usuario;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			throw new DAOException("Erro ao tentar Login", e);
		}
		
	}
	
	public List<Usuario> findAll() throws DAOException{
		List<Usuario> usuarios = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Usuario f");
			usuarios = (List<Usuario>) q.getResultList();
		} catch (HibernateException e) {
			throw new DAOException("Erro ao tentar pegar Usuarios", e);
		}
		return usuarios;
	}

}