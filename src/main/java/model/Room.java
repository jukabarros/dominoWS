package model;

import java.io.Serializable;
import java.sql.Date;

public class Room implements Serializable{
	
	private static final long serialVersionUID = 5210182474779892892L;

	private Integer id;
	
	private String name;
	
	private Date dateOfCreate;
	
	private Player playerOwn;
	
	private int numOfPlayers;

	public Room() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public Player getPlayerOwn() {
		return playerOwn;
	}

	public void setPlayerOwn(Player playerOwn) {
		this.playerOwn = playerOwn;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

}
