package br.com.caelum.library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAO<T> {
	
	private final Class<T> classe;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public void add(T t) {

		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
		em.close();
	}

	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
		em.close();
	}

	public void update(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		em.close();
	}

	public List<T> listAll() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

	public T findById(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = em.find(classe, id);
		em.close();
		return instancia;
	}
	
	public int accountEveryone() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> listAllPaged(int firstResult, int maxResults, String columm, String value) {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		Root<T> root = query.from(classe);

		if(value != null) {
			query = query.where(em.getCriteriaBuilder().like(root.<String>get(columm), value + "%"));
		}
		
		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		em.close();
		return lista;
	}
	
	public int quantityOfElements() {
		EntityManager em = new JPAUtil().getEntityManager();
		
		long result = (long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		
		em.close();
		
		return (int) result;
		
	}


}
