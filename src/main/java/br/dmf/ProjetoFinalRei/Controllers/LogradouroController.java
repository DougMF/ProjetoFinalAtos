package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Logradouro;


public class LogradouroController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Logradouro logradouro) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(logradouro);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Logradouro read(int logradouro_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Logradouro logradouro = session.get(Logradouro.class, logradouro_id);
     
        session.close();
        return logradouro;
    }

    protected void update(Logradouro logradouro) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(logradouro);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int logradouro_id) {
    	Logradouro logradouro =  new Logradouro();
    	logradouro.setId(logradouro_id);
    	
    	Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(logradouro);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Logradouro> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Logradouro> logradouros = session.createQuery("from Logradouro", Logradouro.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return logradouros;
    }
    
    public Logradouro queryWhere(int logradouro_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Logradouro logradouro = session.createQuery("from Logradouro where id = " + logradouro_id, Logradouro.class).getSingleResult();
        
        session.close();
        return logradouro;
    }
}