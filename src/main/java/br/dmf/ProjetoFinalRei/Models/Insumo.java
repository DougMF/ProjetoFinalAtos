package br.dmf.ProjetoFinalRei.Models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "insumo")
public class Insumo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "VALOR")
	private float valor;
	
	@Column(name = "QTDESTOQUE")
	private int qtdestoque;
	
	@OneToMany(mappedBy="insumo")
    private List<Historico> historicos;
	
	@OneToMany(mappedBy = "insumo")
    private List<Encomenda_insumo> encomenda_insumo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public int getQtdestoque() {
		return qtdestoque;
	}

	public void setQtdestoque(int qtdestoque) {
		this.qtdestoque = qtdestoque;
	}
}
