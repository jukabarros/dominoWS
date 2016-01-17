package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import service.PlayerService;
import model.Player;

@ManagedBean(name="playerController")
@ViewScoped
public class PlayerController implements Serializable{

	private static final long serialVersionUID = 67164389915317270L;
	
	private Player player;
	
	private List<Player> listPlayers;
	
	private PlayerService service;

	public PlayerController() {
		super();
		this.player = new Player();
		this.listPlayers = new ArrayList<>();
		this.service = new PlayerService();
//		this.refreshListPlayers();
	}
	
	public void refreshListPlayers(){
		this.listPlayers = this.service.getAll();
	}
	
	/**
	 * Cadastra um novo player no sistema
	 * @return null para evitar o erro na VIEW
	 */
	public String insert() {
		try{
			//Colocando em caixa alta
			this.player.setName(this.player.getName().toUpperCase());
			
			this.player.setDateOfCreate(new java.sql.Date(new Date().getTime()));
			
			int msgCode = this.service.insert(this.player);
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (msgCode == 0){
				facesContext.addMessage(null, new FacesMessage("Registro Cadastrado com Sucesso!!")); //Mensagem de validacao 
				this.player = new Player();
			}else{
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Player já existe no sistema", "")); 
			}
			
		}catch(Exception e){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao inserir o registro", "")); //Mensagem de erro 
			
		}
		return null;
		
	}
	
	/**
	 * Exclui um player do sistema
	 */
	public String deleteCategoria(){
		try{
			this.service.delete(this.player);
			refreshListPlayers();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("Registro excluído com Sucesso!!")); //Mensagem de validacao 

		}catch(Exception e){
			System.err.println("** Erro ao deletar: "+e.getMessage());
			refreshListPlayers();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao excluir o registro", "")); //Mensagem de erro
		}
		return null;
	}

	/**
	 * Edita um player no sistema
	 * @return null para evitar o erro na VIEW
	 */
	public String update() {
		try{
			this.player.setName(this.player.getName().toUpperCase());
			this.service.update(this.player);
			refreshListPlayers();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("Registro editado com Sucesso!!")); //Mensagem de validacao 

		}catch(Exception e){
			refreshListPlayers();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao editar o registro", "")); //Mensagem de erro 

		}
		return null;

	}

	// Get and Set
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Player> getListPlayers() {
		return listPlayers;
	}

	public void setListPlayers(List<Player> listPlayers) {
		this.listPlayers = listPlayers;
	}

}
