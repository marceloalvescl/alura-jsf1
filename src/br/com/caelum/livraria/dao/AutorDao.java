package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

public class AutorDao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject 
	EntityManager em;
	@Inject
	LivroDao livroDao;
	
	private DAO<Autor> dao;
	
	

	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(Autor.class, this.em);
	}
	
	public void adiciona(Autor autor) {
		dao.adiciona(autor);
	}

	public void remove(Autor autor) {
		dao.remove(autor);
	}

	public void atualiza(Autor autor) {
		dao.atualiza(autor);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	public List<Livro> listaTodosLivros(Integer autorId){
		@SuppressWarnings("unchecked")
		List<Integer> lista = em.createNativeQuery(
				"select Livro_id from livro_autor where autores_id = ".concat(autorId.toString())
				).getResultList();
		List<Livro> listaLivro = new ArrayList<Livro>();
		for (Integer livroId : lista) {
			listaLivro.add(livroDao.buscaPorId(livroId));
		}
		return listaLivro;
	}
	
}
