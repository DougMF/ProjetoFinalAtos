package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Historico;


public class HistoricoController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Historico historico) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(historico);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Historico read(int historico_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Historico historico = session.get(Historico.class, historico_id);
     
        session.close();
        return historico;
    }

    protected void update(Historico historico) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(historico);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int historico_id) {
    	Historico historico = new Historico();
    	historico.setId(historico_id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(historico);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Historico> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Historico> historicos = session.createQuery("from Historico", Historico.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return historicos;
    }
    
    public Historico queryWhere(int historico_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Historico historico = session.createQuery("from Historico where id = " + historico_id, Historico.class).getSingleResult();
        
        session.close();
        return historico;
    }
}