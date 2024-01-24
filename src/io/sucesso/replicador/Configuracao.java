package io.sucesso.replicador;

import java.util.List;

public class Configuracao {

	private String pathOrigem;
	private List<Entidade> entidades;
	
	@Override
	public String toString() {
		return "Configuracao [pathOrigem=" + pathOrigem + ", entidades=" + entidades + "]";
	}
	
	public String getPathOrigem() {
		return pathOrigem;
	}

	public void setPathOrigem(String pathOrigem) {
		this.pathOrigem = pathOrigem;
	}
	public List<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

}
