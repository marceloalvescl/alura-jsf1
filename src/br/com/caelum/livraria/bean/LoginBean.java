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
	
	public RedirectView logout() {
		if(usuario == null) {
			return new RedirectView("");
		}

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		
		return new RedirectView("login");
	}

	public RedirectView efetuaLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDAO().buscaPorEmailESenha(usuario.getEmail(), usuario.getSenha());
		
		if(existe) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
			return new RedirectView("livro");
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário ou senha incorretos"));
		return new RedirectView("");
	}
	
	public RedirectView cadastrarUsuario() {
		new UsuarioDAO().adiciona(usuario);
		return new RedirectView("login");
	}

}
