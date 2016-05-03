package br.com.vuga.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {

	public final static EntityManagerFactory emf = 
		Persistence.createEntityManagerFactory("vuga");
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
