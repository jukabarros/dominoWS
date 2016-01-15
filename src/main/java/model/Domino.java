package model;

import java.io.Serializable;

public class Domino implements Serializable{
	
	private static final long serialVersionUID = -1119403700804437339L;

	private Integer id;
	
	private String partOne;
	
	private String partTwo;

	public Domino() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartOne() {
		return partOne;
	}

	public void setPartOne(String partOne) {
		this.partOne = partOne;
	}

	public String getPartTwo() {
		return partTwo;
	}

	public void setPartTwo(String partTwo) {
		this.partTwo = partTwo;
	}

}
