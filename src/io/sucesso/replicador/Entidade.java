package io.sucesso.replicador;

import java.util.Map;

public class Entidade {

	private Map<String, String> replaces;
	private String files;
	private Boolean ignore;
	
	@Override
	public String toString() {
		return "Configuracao [replaces=" + replaces + "]";
	}

	public Map<String, String> getReplaces() {
		return replaces;
	}

	public void setReplaces(Map<String, String> replaces) {
		this.replaces = replaces;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Boolean getIgnore() {
		return ignore==null?false:ignore;
	}

	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}
	
	
}
