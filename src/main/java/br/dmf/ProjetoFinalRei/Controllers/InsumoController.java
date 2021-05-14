package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;

import br.dmf.ProjetoFinalRei.Models.Cliente;
import br.dmf.ProjetoFinalRei.Models.Insumo;


public class InsumoController {
    public SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    public void createOrUpdate(Insumo insumo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        try {
        	session.saveOrUpdate(insumo);
        }catch(ConstraintViolationException cve) {
        	System.out.println("O insumo já está cadastrado!");
        }
        
        session.getTransaction().commit();
        session.close();
    }
 
    public List<Insumo> read(String nome) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	List<Insumo> insumos = session.createQuery("from Insumo where nome like '%" + nome + "%'", Insumo.class).getResultList();
     
        session.close();
        return insumos;
    }
 
    public void delete(int insumo_id) {
    	Insumo insumo = new Insumo();
    	insumo.setId(insumo_id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(insumo);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Insumo> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Insumo> insumos = session.createQuery("from Insumo where qtdestoque > 0", Insumo.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return insumos;
    }
    
    public Insumo queryWhere(int insumo_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Insumo insumo = session.createQuery("from Insumo where id = " + insumo_id, Insumo.class).getSingleResult();
        
        session.close();
        return insumo;
    }
}