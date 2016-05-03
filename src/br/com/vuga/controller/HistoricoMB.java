package br.com.vuga.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Pedido;

@ManagedBean(name = "historicoMB")
@RequestScoped
public class HistoricoMB {

	private Dao<Pedido> daoPedido;
	private List<Pedido> pedidos;

	public HistoricoMB() {
		daoPedido = new Dao<Pedido>(Pedido.class);
		pedidos = daoPedido.listar();
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
