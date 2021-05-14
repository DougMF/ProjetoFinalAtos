package br.dmf.ProjetoFinalRei.Beans;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.dmf.ProjetoFinalRei.Controllers.EncomendaController;
import br.dmf.ProjetoFinalRei.Controllers.Encomenda_insumoController;
import br.dmf.ProjetoFinalRei.Controllers.InsumoController;
import br.dmf.ProjetoFinalRei.Models.Bairro;
import br.dmf.ProjetoFinalRei.Models.Cidade;
import br.dmf.ProjetoFinalRei.Models.Encomenda;
import br.dmf.ProjetoFinalRei.Models.Encomenda_insumo;
import br.dmf.ProjetoFinalRei.Models.Endereco;
import br.dmf.ProjetoFinalRei.Models.Insumo;
import br.dmf.ProjetoFinalRei.Models.Logradouro;


@ManagedBean(name="encomendaBean")
@ViewScoped
public class EncomendaBean{
	private EncomendaController encomendaController = new EncomendaController();
	public List<Encomenda> encomendas;
	public List<Encomenda_insumo> encomendainsumos;
	public List<Insumo> insumos;

	@PostConstruct
	public void init() {
		encomendaController.setup();
		encomendas = encomendaController.listAll();
	}
	
	public List<Encomenda_insumo> getEncomendaInsumos(int encomendaId) {
		Encomenda_insumoController encomenda_insumoController = new Encomenda_insumoController();
		
		encomenda_insumoController.setup();
		return encomenda_insumoController.listByEncomenda(encomendaId);		
	}
	
	//Adiciona um insumo à encomenda
	public void adicionarEncomendaInsumo(int encomendaId, int insumoId, int qtdAdd, int qtdDisponivel) {
		if(qtdAdd > qtdDisponivel)
			exibirMsgErro("Quantidade indisponível");
		else if(qtdAdd <= 0)
			exibirMsgErro("Quantidade inválida!");
		else {
			System.out.println("Encomenda: " + encomendaId+
					"\nInsumo: " + insumoId+
					"\nQtd: " + qtdAdd+
					"\nEncomenda: " + qtdDisponivel);
			encomendaController.session.close();
			encomendaController.sessionFactory.close();
			Encomenda_insumoController encomenda_insumoController = new Encomenda_insumoController();
			//Encomenda_insumo encomenda_insumo = new Encomenda_insumo();
			
			encomenda_insumoController.setup();
			encomenda_insumoController.create(encomendaId, insumoId, qtdAdd);
			
			
			encomendaController = new EncomendaController();
			encomendaController.setup();
			
			getAllEncomendas();
		}
	}
	
	//Remove um insumo da encomenda
	public void removerEncomendaInsumo(int insumoId){		
		Encomenda_insumoController encomenda_insumoController = new Encomenda_insumoController();
		System.out.println(insumoId);
		encomenda_insumoController.setup();
		String resposta = encomenda_insumoController.delete(insumoId);
		
		encomendaController = new EncomendaController();
		encomendaController.setup();			
		
		if(!resposta.equals("ok")) {
			exibirMsgErro(resposta);
			return;
		}
		
		getAllEncomendas();
	}
	
	public void exibirMsgErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(mensagem)); 
	}
	
	//Lista todos os encomendas com nome e telefone semelhantes aos digitados
	public void getEncomenda(String nome, String telefone) {
		encomendas = encomendaController.read(nome.toUpperCase().trim(), telefone.trim());
	}

	public List<Encomenda> getEncomendas() {
		return encomendas;
	}
	
	public void getAllEncomendas() {
		encomendas = encomendaController.listAll();
	}
	
	public void cadastrar(int id, String nome, String cpf, String telefone, String cep, String nomeCidade, 
			String nomeBairro, String nomeLogradouro, int numero, String complemento){
		
		Cidade cidade = new Cidade();
		Bairro bairro = new Bairro();
		Logradouro logradouro = new Logradouro();
		Endereco endereco = new Endereco();
		Encomenda encomenda = new Encomenda();
		
		cidade.setNome(nomeCidade.toUpperCase().trim());
		
		bairro.setNome(nomeBairro.toUpperCase().trim());
		bairro.setCidade(cidade);
		
		logradouro.setNome(nomeLogradouro.toUpperCase().trim());
		logradouro.setBairro(bairro);
		
		endereco.setCep(cep.trim());
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento.toUpperCase().trim());
		
		encomenda.setId(id);
		/*encomenda.setNome(nome.toUpperCase().trim());
		encomenda.setCpf(cpf.toUpperCase().trim());
		encomenda.setTelefone(telefone.trim());
		
		encomendaController.createOrUpdate(encomenda, cidade, bairro, logradouro, endereco);*/
		getAllEncomendas();
	}
	
	public void deletar(int encomendaId) {
		encomendaController.delete(encomendaId);
		getAllEncomendas();
	}
	
	public List<Insumo> getInsumos() {
		InsumoController insumoController = new InsumoController();
		
		insumoController.setup();
		insumos = insumoController.listAll();
		
		return insumos;
	}

	public void setInsumos(List<Insumo> insumos) {
		this.insumos = insumos;
	}
	
	public List<Encomenda_insumo> getEncomendainsumos() {
		return encomendainsumos;
	}

	public void setEncomendainsumos(List<Encomenda_insumo> encomendainsumos) {
		this.encomendainsumos = encomendainsumos;
	}
}