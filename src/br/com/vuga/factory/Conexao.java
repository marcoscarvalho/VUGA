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
	
	public static void main(String[] args) {
		// Use persistence.xml configuration
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("vuga");
		EntityManager em = emf.createEntityManager();
		System.out.println(em);
		System.out.println("---------------------------------------------------");
	}
}
