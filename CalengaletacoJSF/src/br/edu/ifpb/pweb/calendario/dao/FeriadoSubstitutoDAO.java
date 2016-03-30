package br.edu.ifpb.pweb.calendario.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.pweb.calendario.model.Feriado;
import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;

public class FeriadoSubstitutoDAO extends GenericDAOJPAImpl<FeriadoSubstituto, Long>{

private static Logger logger = Logger.getLogger(FeriadoSubstituto.class);
	
	public FeriadoSubstitutoDAO(EntityManager em){
		super(em);
	}
	
	public FeriadoSubstitutoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public List<FeriadoSubstituto> findAll() {
		List<FeriadoSubstituto> substitutos = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from FeriadoSubstituto s");
			substitutos = (List<FeriadoSubstituto>) q.getResultList();
		} catch (HibernateException e) {
			throw new DAOException("Erro ao tentar pegar Feriados Substitutos", e);
		}
		return substitutos;

	}
}
