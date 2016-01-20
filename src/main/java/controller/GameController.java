package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import service.GameService;
import model.Game;

@ManagedBean(name="gameController")
@ViewScoped
public class GameController implements Serializable{

	private static final long serialVersionUID = -4035954178403357417L;
	
	private Game game;
	
	private List<Game> listGames;
	
	private GameService service;

	private String searchInput;
	
	public GameController() {
		super();
		this.game = new Game();
		this.listGames = new ArrayList<Game>();
		this.service = new GameService();
		this.searchInput = null;
		refreshListRooms();
	}

	public String refreshListRooms(){
		this.listGames = this.service.getAll();
		return null;
	}
	
	/**
	 * Cadastra uma nova sala no jogo
	 * @return null para evitar o erro na VIEW
	 */
	public String insert() {
		try{
			this.game.setDateOfCreate(new java.sql.Date(new Date().getTime()));
			
			int msgCode = this.service.insert(this.game);
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (msgCode == 0){
				// Adicionando o player na sala
				this.service.updateNumOfPlayers(this.game, "ADD");
				
				facesContext.addMessage(null, new FacesMessage("Registro Cadastrado com Sucesso!!")); //Mensagem de validacao 
				this.game = new Game();
				this.listGames = this.service.getAll();
			}else{
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Sala já existe no jogo", "")); 
			}
			
		}catch(Exception e){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao inserir o registro", "")); //Mensagem de erro 
			
		}
		return null;
		
	}
	
	public String findByName(){
		if(this.searchInput.trim().equals("") || this.searchInput.equals(null)){ 
			refreshListRooms(); 	
		}
		else{
			this.listGames.clear();
			this.listGames.add(this.service.findByName(this.searchInput));	
		}
		return null;
	}
	
	/**
	 * Entrando numa sala de jogo
	 * @return view do jogo
	 */
	public String joinRoom(){
		try{
			int numOfPlayers = this.service.getNumOfPlayersGame(this.game);
			
			if (numOfPlayers >= this.game.getNumMaxPlayers()){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Sala cheia :(", "")); 
			}else if (numOfPlayers < 0){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro! :(", ""));
			}else{
				System.out.println("entrando na sala ...");
			}
			
		}catch(Exception e){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro no sistema!", "")); //Mensagem de erro 
			
		}
		return null;
	}
	
	public String leaveRoom(){
		try{
			this.service.updateNumOfPlayers(this.game, "REM");
		}catch(Exception e){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro no sistema!", "")); //Mensagem de erro 
			
		}
		return null;
	}

	// GET and SET

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Game> getListGames() {
		return listGames;
	}

	public void setListGames(List<Game> listGames) {
		this.listGames = listGames;
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}
}
