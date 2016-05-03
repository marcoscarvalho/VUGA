package br.com.vuga.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Cliente;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	private Cliente cliente;
	private Dao<Cliente> dao;

	public LoginMB() {
		dao = new Dao<Cliente>(Cliente.class);
	}

	public String logar() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", login);
		map.put("password", password);
		try {
			cliente = dao.getNameQuery(Cliente.GET_LOGIN, map);

			FacesMessage msg = new FacesMessage("Bem vindo "
					+ cliente.getNome());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "index.jsf";

		} catch (Throwable e) {
			FacesMessage msg = new FacesMessage("Cliente não encontrado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
