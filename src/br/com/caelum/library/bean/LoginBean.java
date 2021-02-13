package br.com.caelum.library.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.library.dao.UserDao;
import br.com.caelum.library.model.User;

@ManagedBean
@ViewScoped
public class LoginBean {

	private User user = new User();
	
	
	public String submit() {
		System.out.println("Make login user " + this.user.getEmail());
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		boolean exist = new UserDao().exist(this.user);
				
		if(exist) {
			context.getExternalContext().getSessionMap().put("loggedUser", this.user);
			return "book?faces-redirect=true";
		} 
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("User not found"));
		
		return "login?faces-redirect=true";
	
		
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("loggedUser");
		
		return "login?faces-redirect=true";
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	


	
}
