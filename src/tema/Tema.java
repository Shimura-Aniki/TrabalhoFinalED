package tema;

public class Tema {
	private String tipo;
	private String cenario;
	private int valorDiaria;
	
	public Tema(String tipo, String cenario, int valorDiaria) {
		this.tipo = tipo;
		this.cenario = cenario;
		this.valorDiaria = valorDiaria;
		
	}

	public String toString() {
		return tipo + "§" + cenario + "§" + valorDiaria;
	}

	public String getTipo() {
		return tipo;
	}

	public String setTipo(String tipo) {
		return this.tipo = tipo;
	}

	public String getCenario() {
		return cenario;
	}

	public String setCenario(String cenario) {
		return this.cenario = cenario;
	}
	
	public int getValorDiaria() {
		return valorDiaria;
	}

	public int setValorDiaria(int valorDiaria) {
		return this.valorDiaria = valorDiaria;
	}
}
