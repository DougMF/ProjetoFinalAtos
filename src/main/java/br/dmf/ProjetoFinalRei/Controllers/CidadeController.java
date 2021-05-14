package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Cidade;


public class CidadeController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Cidade cidade) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(cidade);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Cidade read(int cidade_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Cidade cidade = session.get(Cidade.class, cidade_id);
     
        session.close();
        return cidade;
    }

    protected void update(Cidade cidade) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(cidade);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int cidade_id) {
    	Cidade cidade = new Cidade();
    	cidade.setId(cidade_id);
    	
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(cidade);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Cidade> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Cidade> cidades = session.createQuery("from Cidade", Cidade.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return cidades;
    }
    
    public Cidade queryWhere(int cidade_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Cidade cidade = session.createQuery("from Cidade where id = " + cidade_id, Cidade.class).getSingleResult();
        
        session.close();
        return cidade;
    }
}