package model;

import controller.StatusCasa;

public class Casa {

	private StatusCasa status;
	
	public Casa() {
		status = StatusCasa.JOGADA_POSSIVEL;
	}

	public StatusCasa getStatus() {
		return status;
	}

	public void setStatus(StatusCasa status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status.toString();
	}
	
	
	
}
