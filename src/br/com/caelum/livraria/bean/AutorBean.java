package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transactional;

@Named
@javax.faces.view.ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AutorDao dao;
	@Inject
	LivroDao livroDao;
	private Autor autor = new Autor();

	private Integer autorId;

	public AutorBean() {
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void carregarAutorPelaId() {
		this.autor = this.dao.buscaPorId(autorId);
	}

	@Transactional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}

	@Transactional
	public void remover(Autor autor) {
		List<Livro> livrosDoAutor = this.dao.listaTodosLivros(autor.getId());
		if (!livrosDoAutor.isEmpty()) {
			/*
			 * for (Livro livro : livrosDoAutor) { livroDao.remove(livro); }
			 */

			for (Livro livro : livrosDoAutor)
				System.out.println(livro.getTitulo());
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Primeiro remova os livros do autor: ".concat(livrosDoAutor.toString())));
			return;

		}
		System.out.println("Removendo autor " + autor.getNome());
		this.dao.remove(autor);
	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
