package br.com.vuga.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.ItemPedido;
import br.com.vuga.entity.Pedido;
import br.com.vuga.entity.Produto;

@ManagedBean(name = "carrinhoMb")
@SessionScoped
public class CarrinhoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private Dao<Produto> daoProduto;
	private Dao<Pedido> daoPedido;
	private Integer idProduto;
	private int qtdProduto;

	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;

	public CarrinhoMb() {
		daoProduto = new Dao<Produto>(Produto.class);
		daoPedido = new Dao<Pedido>(Pedido.class);
		pedido = new Pedido();
		pedido.setItens(new ArrayList<ItemPedido>());
	}

	public String adicionarQtdProdutos() {

		Produto produto = daoProduto.get(idProduto);

		if (produto != null) {

			if (qtdProduto < 1) {
				FacesMessage msg = new FacesMessage("Quantidade Inválida");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return null;
			}

			boolean temPedido = verificarSeExisteProdutoArray(produto);
			if (!temPedido) {
				ItemPedido i = new ItemPedido();
				i.setProduto(produto);
				i.setQuantidade(qtdProduto);
				pedido.getItens().add(i);

			} else {
				for (ItemPedido i : pedido.getItens()) {
					if (idProduto.equals(i.getProduto().getId())) {
						i.setQuantidade(qtdProduto);
					}
				}
			}
		}

		qtdProduto = 0;

		return "carrinho.jsf";
	}

	public String adicionarProduto() {

		Produto produto = daoProduto.get(idProduto);

		if (produto != null) {

			boolean temPedido = verificarSeExisteProdutoArray(produto);
			if (!temPedido) {
				ItemPedido i = new ItemPedido();
				i.setProduto(produto);
				i.setQuantidade(1);
				// setarValor(i);
				pedido.getItens().add(i);

			} else {
				for (ItemPedido i : pedido.getItens()) {
					if (idProduto.equals(i.getProduto().getId())) {
						i.setQuantidade(i.getQuantidade() + 1);
					}
				}
			}
		}

		// setarValor(pedido);

		return "carrinho.jsf";
	}

	// private void setarValor(ItemPedido i) {
	// i.setValorUnitario(i.getProduto().getValor());
	// i.setValorTotal(i.getValorUnitario().multiply(
	// new BigDecimal(i.getQuantidade())));
	// }
	//
	// private void setarValor(Pedido p) {
	// for (ItemPedido i : p.getItens()) {
	// if (p.getValorTotal() == null) {
	// p.setValorTotal(i.getValorTotal());
	// } else {
	// p.setValorTotal(p.getValorTotal().add(i.getValorTotal()));
	// }
	// }
	// }

	public String diminuirQuantidadeDeProduto() {
		Produto produto = daoProduto.get(idProduto);

		for (ItemPedido i : pedido.getItens()) {
			if (i.getProduto() != null
					&& i.getProduto().getId().equals(produto.getId())) {
				if (i.getQuantidade() == 1) {
					pedido.getItens().remove(i);

					if (pedido.getItens().size() == 0) {
						return "index.jsf";
					}

				} else {
					i.setQuantidade(i.getQuantidade() - 1);
				}
			}
		}

		return "carrinho.jsf";
	}

	private boolean verificarSeExisteProdutoArray(Produto produto) {
		for (ItemPedido i : pedido.getItens()) {
			if (i.getProduto() != null
					&& i.getProduto().getId().equals(produto.getId())) {
				return true;
			}
		}
		return false;
	}

	public String continuarComprando() {

		return "index.jsf";
	}

	public String efetuarPedido() {

		if (loginMB.getCliente() != null) {
			pedido.setCliente(loginMB.getCliente());
		} else {
			FacesMessage msg = new FacesMessage("Por favor, logar-se");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		for (ItemPedido i : pedido.getItens()) {
			Produto prod = daoProduto.get(i.getProduto().getId());
			if (prod.getQuantidade() < i.getQuantidade()) {

				FacesMessage msg = new FacesMessage("Produto " + prod.getNome()
						+ " tem apenas " + prod.getQuantidade() + " unidades");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "carrinho.jsf";
			}
		}

		pedido.setDataEfetuarPedido(new Date());

		BigDecimal valorTotalPedido = new BigDecimal(0);
		for (ItemPedido i : pedido.getItens()) {
			i.setPedido(pedido);
			i.setValorUnitario(i.getProduto().getValor());
			i.setValorTotal(i.getValorUnitario().multiply(
					new BigDecimal(i.getQuantidade())));
			valorTotalPedido.add(i.getValorTotal());
		}

		pedido.setValorTotal(valorTotalPedido);

		pedido = daoPedido.salvar(pedido);

		for (ItemPedido i : pedido.getItens()) {
			Produto prod = daoProduto.get(i.getProduto().getId());
			prod.setQuantidade(prod.getQuantidade() - i.getQuantidade());
			daoProduto.salvar(prod);
		}

		logout();

		FacesMessage msg = new FacesMessage(
				"Compra Efetuada com Sucesso | id: " + pedido.getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "historico.jsf";
	}

	private void logout() {
		// ((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
		// .getSession(false)).invalidate();

		pedido = new Pedido();
		pedido.setItens(new ArrayList<ItemPedido>());
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public int getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

}
