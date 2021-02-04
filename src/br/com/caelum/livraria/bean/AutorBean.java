package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@ViewScoped
@ManagedBean
public class AutorBean {
	
	private Autor autor = new Autor();
	private Integer autorId;
	
	public Integer getAutorId() {
		System.out.println(autorId);
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		System.out.println(autorId);
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return this.autor;
	}

	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void carregarAutorPorId() {
	    System.out.println(autor.getNome());
	    this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
	
	public RedirectView gravar() {
		System.out.println("Gravando autor");
		
		if(autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(autor);	
		}else {
			new DAO<Autor>(Autor.class).atualiza(autor);
			return new RedirectView("autor");	
		}
		this.autor = new Autor();
		return new RedirectView("livro");
	}
	
	public void remover(Autor autor) {
		try {
			new DAO<Autor>(Autor.class).remove(autor);	
		}catch(PersistenceException pe) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Autor: " + autor.getNome() + ", possui livros vinculados a ele, delete os livros antes!"));
		}
	}
	
	public void alterar(Autor autor) {
		this.autor = autor;
	}
	
}
