package br.com.vuga.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vuga.dao.Dao;
import br.com.vuga.entity.Categoria;

@FacesConverter("ConversorCategoria")
public class ConversorCategoria implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		Categoria c = new Dao<Categoria>(Categoria.class).get(Integer
				.parseInt(arg2));
		return c;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Categoria c = (Categoria) arg2;
		return c.getId().toString();
	}

}
