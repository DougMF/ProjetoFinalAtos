package br.dmf.ProjetoFinalRei.Beans;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dmf.ProjetoFinalRei.Controllers.InsumoController;
import br.dmf.ProjetoFinalRei.Models.Insumo;


@ManagedBean(name="insumoBean")
@ViewScoped
public class InsumoBean{
	private InsumoController insumoController = new InsumoController();
	public List<Insumo> insumos;
	
	@PostConstruct
	public void init() {
		insumoController.setup();
		insumos = insumoController.listAll();
	}

	//Lista insumos com nome semelhante ao digitado
	public void getInsumo(String nome) {
		insumos = insumoController.read(nome);
	}

	public List<Insumo> getInsumos() {
		return insumos;
	}
	
	//Lista todos os insumos
	public void getAllClientes() {
		insumos = insumoController.listAll();
	}
	
	public void cadastrar(int id, String nome, float valor, int qtdEstoque){
		
		Insumo insumo = new Insumo();
		
		insumo.setId(id);
		insumo.setNome(nome.toUpperCase().trim());
		insumo.setQtdestoque(qtdEstoque);
		insumo.setValor(valor);
		
		insumoController.createOrUpdate(insumo);
		getAllClientes();
	}
	
	public void deletar(int insumoId) {
		insumoController.delete(insumoId);
		getAllClientes();
	}
}