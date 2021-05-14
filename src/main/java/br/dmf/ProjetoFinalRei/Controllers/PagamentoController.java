package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.dmf.ProjetoFinalRei.Models.Pagamento;


public class PagamentoController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    protected void create(Pagamento pagamento) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(pagamento);
        
        session.getTransaction().commit();
        session.close();
    }
 
    public Pagamento read(int pagamento_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Pagamento pagamento = session.get(Pagamento.class, pagamento_id);
     
        session.close();
        return pagamento;
    }

    protected void update(Pagamento pagamento) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	session.update(pagamento);

    	session.getTransaction().commit();
    	session.close();
    }
 
    protected void delete(int pagamento_id) {
    	Pagamento pagamento = new Pagamento();
    	pagamento.setId(pagamento_id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(pagamento);
     
        session.getTransaction().commit();
        session.close();
    }    

    
    public List<Pagamento> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Pagamento> pagamentos = session.createQuery("from Pagamento", Pagamento.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return pagamentos;
    }
    
    public Pagamento queryWhere(int pagamento_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	Pagamento pagamento = session.createQuery("from Pagamento where id = " + pagamento_id, Pagamento.class).getSingleResult();
        
        session.close();
        return pagamento;
    }
}