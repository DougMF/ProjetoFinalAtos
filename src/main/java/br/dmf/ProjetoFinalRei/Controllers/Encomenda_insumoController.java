package br.dmf.ProjetoFinalRei.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.GenericJDBCException;

import br.dmf.ProjetoFinalRei.Models.Encomenda;
import br.dmf.ProjetoFinalRei.Models.Encomenda_insumo;
import br.dmf.ProjetoFinalRei.Models.Endereco;
import br.dmf.ProjetoFinalRei.Models.Insumo;

public class Encomenda_insumoController {
    public SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

   
    
    public void create(int encomendaId, int insumoId, int qtd) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        Insumo insumo = session.createQuery("from Insumo where id = " + insumoId, Insumo.class).getSingleResult();
		Encomenda encomenda = session.createQuery("from Encomenda where id = " + encomendaId, Encomenda.class).getSingleResult();        
		Encomenda_insumo encomenda_insumo = new Encomenda_insumo();
		
		int id = 0;
		
		try {
			id = session.createQuery("from Encomenda_insumo where encomenda_id = :encomendaId and insumo_id = :insumoId", Encomenda_insumo.class)
					.setParameter("encomendaId", encomendaId)
					.setParameter("insumoId", insumoId)
					.getSingleResult().getId();
		}catch(NoResultException nre) {			
		}
		
		session.clear();
		
		encomenda_insumo.setId(id);
		encomenda_insumo.setQtd(qtd);
		encomenda_insumo.setInsumo(insumo);
		encomenda_insumo.setEncomenda(encomenda);
		
		session.saveOrUpdate(encomenda_insumo);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Encomenda_insumo> listByEncomenda(int encomendaId) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	List<Encomenda_insumo> encomenda_insumos = session.createQuery("from Encomenda_insumo where encomenda_id = " + encomendaId, Encomenda_insumo.class).getResultList();
     
        session.close();
        return encomenda_insumos;
    }
 
    public Encomenda_insumo read(int encomenda_insumo_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Encomenda_insumo encomenda_insumo = session.get(Encomenda_insumo.class, encomenda_insumo_id);
     
        session.close();
        return encomenda_insumo;
    }

    public void update(Encomenda_insumo encomenda_insumo) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(encomenda_insumo);

    	session.getTransaction().commit();
    	session.close();
    }
 
    public String delete(int encomenda_insumo_id) {
    	Encomenda_insumo encomenda_insumo = new Encomenda_insumo();
    	encomenda_insumo.setId(encomenda_insumo_id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        try {
        	session.delete(encomenda_insumo);
            session.getTransaction().commit();
        }catch(PersistenceException pe) {        	
        	System.out.println("Localized: " + pe.getLocalizedMessage()+"\nMessage: " + pe.getMessage() + "\nStack trace" + pe.getStackTrace());
        	session.close();
        	return getSQLException(pe).getMessage();
        }
        
        session.close();
        
        return "ok";
    }    

    public SQLException getSQLException(RuntimeException e) {
        SQLException sqlException = null;        
        Throwable throwable = e;
        
        while (throwable != null && !(throwable instanceof SQLException)) {
            throwable = throwable.getCause();
        }
        
        if (throwable instanceof SQLException) {
        	sqlException = (SQLException) throwable;
        	return sqlException;
        }
        
        return null;
    }
    
    public List<Encomenda_insumo> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Encomenda_insumo> encomenda_insumos = session.createQuery("from Encomenda_insumo", Encomenda_insumo.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return encomenda_insumos;
    }
    
    public Encomenda_insumo queryWhere(int encomenda_insumo_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Encomenda_insumo encomenda_insumo = session.createQuery("from Encomenda_insumo where id = " + encomenda_insumo_id, Encomenda_insumo.class).getSingleResult();
        
        session.close();
        return encomenda_insumo;
    }
}