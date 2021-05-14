package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Encomenda;


public class EncomendaController {
    public SessionFactory sessionFactory;
    public Session session;
    
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	    session = sessionFactory.openSession();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    public void create(Encomenda encomenda) {
        session.beginTransaction();
     
        session.save(encomenda);
        
        session.getTransaction().commit();
    }
 
    public List<Encomenda> read(String nome, String telefone) {
    	session.beginTransaction();

        List<Encomenda> encomendas = session.createQuery("select e from Encomenda e "
        											   + "inner join Cliente c on c.id = e.cliente "
        											   + "where c.nome like :nome and c.telefone like :telefone", Encomenda.class)
        												.setParameter("nome", "%" + nome + "%")
        												.setParameter("telefone", "%" + telefone + "%")
        												.getResultList();        
        session.getTransaction().commit();
        
        return encomendas;
    }

    public void update(Encomenda encomenda) {
    	session.beginTransaction();

    	session.update(encomenda);

    	session.getTransaction().commit();
    }
 
    public void delete(int encomenda_id) {
    	Encomenda encomenda = new Encomenda();
    	encomenda.setId(encomenda_id);
     
        session.beginTransaction();
     
        session.delete(encomenda);
     
        session.getTransaction().commit();
    }    

    
    public List<Encomenda> listAll() {
        session.beginTransaction();

        List<Encomenda> encomendas = session.createQuery("from Encomenda", Encomenda.class).getResultList();
        
        session.getTransaction().commit();
        
        return encomendas;
    }
    
    public Encomenda queryWhere(int encomenda_id) {
    	session.beginTransaction();

    	Encomenda encomenda = session.createQuery("from Encomenda where id = " + encomenda_id, Encomenda.class).getSingleResult();
        
        return encomenda;
    }
}