package br.com.vuga.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vuga.factory.Conexao;

public class Dao<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String classe;

	@SuppressWarnings("rawtypes")
	public Dao(Class classe) {
		this.classe = classe.getCanonicalName();
	}

	public T salvar(T object) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		object = em.merge(object);
		em.getTransaction().commit();
		return object;
	}

	public boolean remove(T object) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(object));
		em.getTransaction().commit();
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<T> listar() {
		EntityManager em = Conexao.getEntityManager();
		System.out.println(getClass().getCanonicalName());
		System.out.println(getClass().getSimpleName());
		return em.createQuery("Select p from " + classe + " p").getResultList();
	}

	@SuppressWarnings("unchecked")
	public T get(Integer id) {
		EntityManager em = Conexao.getEntityManager();
		Query query = null;
		query = em.createQuery("Select p from " + classe
				+ " p where p.id = :id");
		query.setParameter("id", id);
		return (T) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public T get(String nome) {
		EntityManager em = Conexao.getEntityManager();
		Query query = em.createQuery("Select p from " + classe
				+ " where nome = ?");
		query.setParameter(1, nome);
		return (T) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public T getNameQuery(String namedQuery, Map<String, String> map) {
		EntityManager em = Conexao.getEntityManager();
		Query query = em.createNamedQuery(namedQuery);

		for (String key : map.keySet()) {
			System.out.println("map: " + key + " | " + map.get(key));
			query.setParameter(key, map.get(key));
		}
		return (T) query.getSingleResult();
	}
}
