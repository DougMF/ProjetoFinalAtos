package br.dmf.ProjetoFinalRei.Beans;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dmf.ProjetoFinalRei.Controllers.ClienteController;
import br.dmf.ProjetoFinalRei.Models.Bairro;
import br.dmf.ProjetoFinalRei.Models.Cidade;
import br.dmf.ProjetoFinalRei.Models.Cliente;
import br.dmf.ProjetoFinalRei.Models.Endereco;
import br.dmf.ProjetoFinalRei.Models.Logradouro;


@ManagedBean(name="clienteBean")
@ViewScoped
public class ClienteBean{
	private ClienteController clienteController = new ClienteController();
	public List<Cliente> clientes;

	@PostConstruct
	public void init() {
		clienteController.setup();
		clientes = clienteController.listAll();
	}

	//Lista todos os clientes com nome, telefone e/ou cpf semelhantes aos digitados
	public void filtrarClientes(String nome, String telefone, String cpf) {
		clientes = clienteController.read(nome, telefone, cpf);
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public void getAllClientes() {
		clientes = clienteController.listAll();
	}
	
	public void cadastrar(int id, String nome, String cpf, String telefone, String cep, String nomeCidade, 
			String nomeBairro, String nomeLogradouro, int numero, String complemento){
		
		Cidade cidade = new Cidade();
		Bairro bairro = new Bairro();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		Cliente cliente = new Cliente();
		
		cidade.setNome(nomeCidade.toUpperCase().trim());
		
		bairro.setNome(nomeBairro.toUpperCase().trim());
		bairro.setCidade(cidade);
		
		logradouro.setNome(nomeLogradouro.toUpperCase().trim());
		logradouro.setBairro(bairro);
		
		endereco.setCep(cep.trim());
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento.toUpperCase().trim());
		
		cliente.setId(id);
		cliente.setNome(nome.toUpperCase().trim());
		cliente.setCpf(cpf.toUpperCase().trim());
		cliente.setTelefone(telefone.trim());
		
		clienteController.createOrUpdate(cliente, cidade, bairro, logradouro, endereco);
		getAllClientes();
	}
	
	public void deletar(int clienteId) {
		clienteController.delete(clienteId);
		getAllClientes();
	}
}