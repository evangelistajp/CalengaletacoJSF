package br.edu.ifpb.pweb.calendario.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.pweb.calendario.model.Anotacao;

public class AnotacaoDAO extends GenericDAOJPAImpl<Anotacao, Long>{
	
private static Logger logger = Logger.getLogger(AnotacaoDAO.class);
	
	public AnotacaoDAO(EntityManager em){
		super(em);
	}
	
	public AnotacaoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public List<Anotacao> findAll() throws DAOException{
		 List<Anotacao> anotacoes = null;
		 try {
			  Query q = this.getEntityManager()
			     .createQuery("from Anotacao a");
			   anotacoes = (List<Anotacao>) q.getResultList();
		 }catch (HibernateException e) {
			 throw new DAOException("Erro ao tentar pegar Anotacoes", e);
		 }
		 return anotacoes;
	}
	
}
