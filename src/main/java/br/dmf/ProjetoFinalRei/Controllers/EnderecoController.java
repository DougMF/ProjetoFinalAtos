package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Endereco;


public class EnderecoController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Endereco endereco) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(endereco);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Endereco read(int endereco_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Endereco endereco = session.get(Endereco.class, endereco_id);
     
        session.close();
        return endereco;
    }

    protected void update(Endereco endereco) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(endereco);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int endereco_id) {
    	Endereco endereco = new Endereco();
    	endereco.setId(endereco_id);
    	
    	Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(endereco);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Endereco> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Endereco> enderecos = session.createQuery("from Endereco", Endereco.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return enderecos;
    }
    
    public Endereco queryWhere(int endereco_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Endereco endereco = session.createQuery("from Endereco where id = " + endereco_id, Endereco.class).getSingleResult();
        
        session.close();
        return endereco;
    }
}