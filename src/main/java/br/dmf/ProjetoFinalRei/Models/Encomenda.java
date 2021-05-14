package br.dmf.ProjetoFinalRei.Models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "encomenda")
public class Encomenda {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "DATA")
	private String data; 
	
	@Column(name = "HORA")
	private String hora;	
	
	@Column(name = "STATUS_PGTO")
	private String status_pgto;
	
	@Column(name = "ENTREGA")
	private String entrega;
	
	@Column(name = "OBS")
	private String obs;
	
	@Column(name = "TOTAL")
	private float total;
	
	@ManyToOne
    @JoinColumn(name="CLIENTE_ID", nullable=false)
    private Cliente cliente;
	
	@ManyToOne
    @JoinColumn(name="PAGAMENTO_ID", nullable=false)
    private Pagamento pagamento;
	
	@OneToMany(mappedBy = "encomenda")
    private List<Encomenda_insumo> encomenda_insumos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getStatus_pgto() {
		return status_pgto;
	}

	public void setStatus_pgto(String status_pgto) {
		this.status_pgto = status_pgto;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
}
