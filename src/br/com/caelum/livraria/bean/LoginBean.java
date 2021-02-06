package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@ViewScoped
@ManagedBean
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return this.usuario;
	}

	public RedirectView efetuaLogin() {
		System.out.println("Efetuando login para email: " + usuario.getEmail() + " e senha: " + usuario.getSenha());
		
		boolean existe = new UsuarioDAO().buscaPorEmailESenha(usuario.getEmail(), usuario.getSenha());
		
		if(!existe) {
			FacesContext.getCurrentInstance().addMessage("login", new FacesMessage("Usuário ou senha incorretos"));
			return new RedirectView("");
		}
		
		return new RedirectView("livro");
	}
	
	public RedirectView cadastrarUsuario() {
		new UsuarioDAO().adiciona(usuario);
		return new RedirectView("login");
	}

}
