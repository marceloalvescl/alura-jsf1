package br.com.caelum.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public boolean buscaPorEmailESenha(String email, String senha) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM usuario WHERE email = :email AND senha = :senha ;")
					.setParameter("email", email).setParameter("senha", senha);
			q.getSingleResult();	
		}catch(Exception e) {
			return false;
		}finally {
			em.close();
		}
		
		return true;
		
	}

}
