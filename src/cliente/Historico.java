package cliente;

public class Historico {
	private String historico;

	public Historico(String historico) {
		this.historico = historico;
	}
	
	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
	public static Historico incluirNovoHistorico(String historico2) {
		return new Historico(historico2);
	}
	
	public String toString() {
		return historico;
	}
}
