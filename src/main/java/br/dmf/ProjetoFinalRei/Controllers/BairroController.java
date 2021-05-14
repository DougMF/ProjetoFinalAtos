package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Bairro;


public class BairroController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Bairro bairro) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(bairro);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Bairro read(int bairro_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Bairro bairro = session.get(Bairro.class, bairro_id);
     
        session.close();
        return bairro;
    }

    protected void update(Bairro bairro) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(bairro);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int bairro_id) {     
    	Bairro bairro = new Bairro();
    	bairro.setId(bairro_id);
    	
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(bairro);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Bairro> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Bairro> bairros = session.createQuery("from Bairro", Bairro.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return bairros;
    }
    
    public Bairro queryWhere(int bairro_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Bairro bairro = session.createQuery("from Bairro where id = " + bairro_id, Bairro.class).getSingleResult();
        
        session.close();
        return bairro;
    }
}