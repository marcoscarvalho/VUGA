package br.com.vuga.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Categoria;
import br.com.vuga.entity.Produto;

@ManagedBean(name = "produtoMB")
@RequestScoped
public class ProdutoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtos;
	private List<Categoria> categorias;
	private Produto produto;
	private Dao<Produto> dao;
	private Dao<Categoria> daoCategoria;
	private Integer idProduto;
	private Integer idCategoria;

	private UploadedFile uploadedFile;

	public ProdutoMB() {
		produtos = new ArrayList<Produto>();
		dao = new Dao<Produto>(Produto.class);
		daoCategoria = new Dao<Categoria>(Categoria.class);
		categorias = daoCategoria.listar();
		produto = new Produto();
		setProdutos(dao.listar());
	}

	@PostConstruct
	public void init() {
		System.out.println("CadastrarProduto inicializado!");
	}

	public String salvar() {

		if (idCategoria != null) {
			Categoria categoria = daoCategoria.get(idCategoria);
			produto.setCategoria(categoria);
		}

		if (uploadedFile != null) {
			System.out.println("upload Files: " + uploadedFile.getContents());
			produto.setImagem(uploadedFile.getContents());
		}

		dao.salvar(produto);

		listar();
		return "produtos.jsf";
	}

	public String infoProduto() {

		setProduto(dao.get(idProduto));

		if (getProduto() != null) {
			return "infoProduto.jsf";

		} else {
			FacesMessage msg = new FacesMessage("Produto não encontrado!");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "";
		}

	}

	public String remover() {
		dao.remove(produto);
		listar();
		return "produtos.jsf";
	}

	public String editar() {
		System.out.println("editar idProduto: " + idProduto);
		setProduto(dao.get(idProduto));
		if (getProduto() != null) {
			System.out.println("Retornou o produto: " + getProduto().getNome());
		}
		return "produto.jsf";
	}

	private void listar() {
		setProdutos(dao.listar());
	}

	public String selecionar() {
		System.out.println(produto);
		System.out.println(produto.getNome());
		return "";
	}

	public String retornarProduto() {
		return "";
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

}
