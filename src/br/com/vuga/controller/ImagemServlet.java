package br.com.vuga.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Produto;

@WebServlet("/ImagemServlet")
public class ImagemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Integer idProduto = Integer.valueOf(req.getParameter("idProduto"));
			Dao<Produto> dao = new Dao<Produto>(Produto.class);
			Produto p = dao.get(idProduto);
			if (p.getImagem() != null) {
				resp.getOutputStream().write(p.getImagem());
			}

		} catch (Exception e) {

		}

	}

}
