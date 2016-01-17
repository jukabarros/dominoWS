package controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import service.PlayerService;
import model.Player;

@ManagedBean(name="loginController")
@SessionScoped
public class LoginController implements Serializable{

	private static final long serialVersionUID = -6983839857205389929L;

	private Player player;
	
	private String login;
	
	private String password;
	
	private PlayerService service;

	public LoginController() {
		super();
		this.player = new Player();
		this.login = null;
		this.password = null;
		this.service = new PlayerService();
	}
	
	//TODO Encrypt Password
	public String doLogin(){
		this.player = this.service.doLogin(this.login, this.password);
		if (this.player.getName() == null){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Login e/ou senha inválidos", ""));
			this.password = "";
			return null;
		}else{
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("player", this.player);
			this.password = "";
			return "login";
		}
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.player = (Player) session.getAttribute("player");
		session.invalidate();
		return "logout";
	}
	
	public String gotoNewPlayer(){
		return "newPlayer";
	}
	
	// GET AND SET
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
