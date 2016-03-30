package br.edu.ifpb.pweb.calendario.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.pweb.calendario.model.Feriado;

public class FeriadoDAO extends GenericDAOJPAImpl<Feriado, Long>{
	
	private static Logger logger = Logger.getLogger(FeriadoDAO.class);
	
	public FeriadoDAO(EntityManager em){
		super(em);
	}
	
	public FeriadoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}
	
	public List<Feriado> findAll() throws DAOException{
		List<Feriado> feriados = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Feriado f");
			feriados = (List<Feriado>) q.getResultList();
		} catch (HibernateException e) {
			throw new DAOException("Erro ao tentar pegar Feriados", e);
		}
		return feriados;
	}

}
