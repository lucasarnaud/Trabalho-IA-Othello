package model;

import controller.StatusCasa;

public class Casa {

	private StatusCasa status;
	private int i;
	private int j;
	
	public Casa(int i, int j) {
		this.i = i;
		this.j = j;
		this.status = StatusCasa.JOGADA_NAO_POSSIVEL;
	}

	public StatusCasa getStatus() {
		return status;
	}

	public void setStatus(StatusCasa status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format("[ ( %d, %d ) = %s ]", i, j, status.toString());
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Casa other = (Casa) obj;
		if (i != other.i) {
			return false;
		}
		if (j != other.j) {
			return false;
		}
		return true;
	}
	
}
