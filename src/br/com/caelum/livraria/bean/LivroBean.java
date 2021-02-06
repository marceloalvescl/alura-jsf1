package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.ForwardView;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean{
	
	private Livro livro = new Livro();
	private Integer autorId;
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Integer getAutorId() {
		return this.autorId;
	}
	
	public Livro getLivro() {
		return this.livro;
	}
	
	public List<Livro> getLivros(){
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void removeAutor(Autor autor) {
		this.getAutores().remove(autor);
	}
	
	public List<Autor> getAutoresDoLivro(){
		return livro.getAutores();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Gravando autor: " + autor.getNome());
	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}
	
	public ForwardView gravar() throws Exception{
		System.out.println("Gravando autor");
		
		if(livro.getAutores().isEmpty()) {
			//throw new Exception("Livro deve possuir autor");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve possuir autor"));
		}
		
		if(livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(livro);	
		}else {
			new DAO<Livro>(Livro.class).atualiza(livro);
		}
		
		
		
		this.livro = new Livro();
		return new ForwardView("livro");
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);		
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve começar com 1"));
		}
	}
	
	public RedirectView remover(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
		return new RedirectView("livro");
	}
	
	public void alterar(Livro livro) {
		this.livro = livro;
	}
	
}