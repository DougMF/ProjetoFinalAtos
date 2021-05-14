package br.dmf.ProjetoFinalRei.Controllers;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;

import br.dmf.ProjetoFinalRei.Models.Bairro;
import br.dmf.ProjetoFinalRei.Models.Cidade;
import br.dmf.ProjetoFinalRei.Models.Cliente;
import br.dmf.ProjetoFinalRei.Models.Endereco;
import br.dmf.ProjetoFinalRei.Models.Logradouro;


public class ClienteController {
    protected SessionFactory sessionFactory;
 
    public void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    	
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	   StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    public void createOrUpdate(Cliente cliente, Cidade cidade, Bairro bairro,
    					Logradouro logradouro, Endereco endereco) {
        Session session = sessionFactory.openSession();        
        
        session.beginTransaction();
        
        /* Método para verificar se o endereço já está cadastrado no banco,
           criando o mesmo caso não esteja */
        endereco =  
        		verificarEndereco(session, cidade, bairro, logradouro, endereco);
        
        cliente.setEndereco(endereco);
        
        try {
        	session.saveOrUpdate(cliente);        
        }catch(ConstraintViolationException cve) {
        	System.out.println("O cliente já está cadastrado!");
        	session.getTransaction().rollback();
        	session.close();
        	return;
        }
        
        session.getTransaction().commit();
        session.close();        
    }
    
    public Endereco verificarEndereco(Session session, Cidade cidade, Bairro bairro,
			Logradouro logradouro, Endereco endereco) {

        try {
	        Cidade cidadeBanco = session.createQuery("from Cidade where nome = :nome", Cidade.class)
	        		.setParameter("nome", cidade.getNome())
	        		.getSingleResult();

        	bairro.setCidade(cidadeBanco);
        }catch(NoResultException nre) {
	        session.save(cidade);
        }
    	
        try {
	        Bairro bairroBanco = session
	        		.createQuery("from Bairro where nome = :nome and cidade_id = :cidade_id", Bairro.class)
	        		.setParameter("nome", bairro.getNome())
	        		.setParameter("cidade_id", bairro.getCidade().getId())
	        		.getSingleResult();
	        
	        logradouro.setBairro(bairroBanco);
        }catch(NoResultException nre) {      	        	
        	session.save(bairro);
        }
        
        try {
	        Logradouro logradouroBanco = session
	        		.createQuery("from Logradouro where nome = :nome and bairro_id = :bairro_id", Logradouro.class)
	        		.setParameter("nome", logradouro.getNome())
	        		.setParameter("bairro_id", logradouro.getBairro().getId())
	        		.getSingleResult();
	        
	        endereco.setLogradouro(logradouroBanco);
        }catch(NoResultException nre) {
        	session.save(logradouro);
        }
	     
        try {
	        Endereco enderecoBanco = session
	        		.createQuery("from Endereco where cep = :cep and numero = :numero and complemento = :complemento", Endereco.class)
	        		.setParameter("cep", endereco.getCep())
	        		.setParameter("numero", endereco.getNumero())
	        		.setParameter("complemento", endereco.getComplemento())
	        		.getSingleResult();
	        
	        return enderecoBanco;
	    }catch(NoResultException nre) {
	    	session.save(endereco);
	    }
        
        return endereco;
    }
 
    public Cliente read(int cliente_id) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

        Cliente cliente = session.get(Cliente.class, cliente_id);
     
        session.close();
        return cliente;
    }
    
    public List<Cliente> read(String nome, String telefone, String cpf) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();

    	List<Cliente> clientes = session.createQuery("from Cliente where nome like '%" + nome + "%' "
    			+ "and telefone like '%" + telefone + "%' and cpf like '%" + cpf + "%'", Cliente.class).getResultList();
        
        session.close();
        return clientes;
    }
 
    public void delete(int cliente_id) {
    	Cliente cliente = new Cliente();
    	cliente.setId(cliente_id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        try {
        	session.delete(cliente);
        	session.getTransaction().commit();
        }catch(PersistenceException pe) {
        	System.out.println("O cliente possui uma encomenda em aberto!");
        	session.close();
        	return;        	
        }

        session.close();
    }    

    
    public List<Cliente> listAll() {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Cliente> clientes = session.createQuery("from Cliente", Cliente.class).getResultList();
        
        session.getTransaction().commit();
        session.close();
        
        return clientes;
    }   
    
}