package br.com.vuga.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Categoria;

@ManagedBean(name = "categoriaMB")
@RequestScoped
public class CategoriaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Categoria> categorias;
	private Categoria categoria;
	private Dao<Categoria> dao;

	private String nome;
	private Integer id;

	public CategoriaMB() {
		dao = new Dao<Categoria>(Categoria.class);
		categoria = new Categoria();
		setCategorias(dao.listar());
	}

	@PostConstruct
	public void init() {
		System.out.println("CadastrarCategoria inicializado!");
	}

	public String editar() {
		System.out.println("editar idCategoria: " + id);
		setCategoria(dao.get(id));
		if (getCategoria() != null) {
			System.out.println("Retornou o produto: "
					+ getCategoria().getNome());
		}
		return "categoria.jsf";
	}

	public String salvar() {
		System.out.println("idCategoria: " + categoria.getId());
		dao.salvar(categoria);
		listar();
		return "categorias.jsf";
	}

	public String remover() {
		dao.remove(categoria);
		listar();
		return "categorias.jsf";
	}

	private void listar() {
		setCategorias(dao.listar());
	}

	public String retornarCategoria() {
		categoria = dao.get(getNome());
		return "";
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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
