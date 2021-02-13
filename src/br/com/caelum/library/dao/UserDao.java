package br.com.caelum.library.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.library.model.User;

public class UserDao {

	public boolean exist(User user) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		TypedQuery<User> query = em.createQuery("select u from User u where u.email = :pEmail "
				+ "and u.password = :pPassword", User.class);
		
		query.setParameter("pEmail", user.getEmail());
		query.setParameter("pPassword", user.getPassword());
		
		try {
			User result = query.getSingleResult();
		}catch (NoResultException e) {
			return false;
		}
		
		em.close();
		return true;
	}

}
