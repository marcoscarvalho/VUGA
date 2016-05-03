package br.com.vuga.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Cliente;

@ManagedBean(name = "clienteMB")
@RequestScoped
public class ClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Cliente> clientes;
	private Cliente cliente;
	private Dao<Cliente> dao;

	private String nome;
	private Integer id;

	public ClienteMB() {
		dao = new Dao<Cliente>(Cliente.class);
		cliente = new Cliente();
		listar();
	}

	@PostConstruct
	public void init() {
		System.out.println("CadastrarCliente inicializado!");
	}

	public String salvar() {
		cliente.setLogin(cliente.getEmail());
		dao.salvar(cliente);

		FacesMessage msg = new FacesMessage("Cliente " + cliente.getNome()
				+ " cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "";
	}

	public String retornarCliente() {
		cliente = dao.get(getId());
		return "";
	}

	public String remover() {
		dao.remove(cliente);
		listar();
		return "clientes.jsf";
	}

	private void listar() {
		setClientes(dao.listar());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		if (clientes != null) {
			System.out.println(clientes.size());
		} else {
			System.out.println("clientes == null");
		}
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
