package service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import model.Player;
import dao.PlayerDAO;

public class PlayerService implements Serializable{

	private static final long serialVersionUID = -1165364489920182176L;
	
	private PlayerDAO dao;

	public PlayerService() {
		super();
		this.dao = new PlayerDAO();
	}
	
	public List<Player> getAll(){
		try{
			return this.dao.getAll();
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Player doLogin(String login, String password) {
		try {
			return this.dao.doLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Player findByLogin(String login) {
		try {
			return this.dao.findByLogin(login);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Player p) {
		try {
			return this.dao.insert(p);
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	public void delete(Player p){
		try {
			this.dao.delete(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Player p){
		try {
			this.dao.update(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
