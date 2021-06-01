package cliente;

public class Cliente {
	private String nome;
	private String sobrenome;
	private String documento;
	private String endereco;
	private String telefone;
	private int numeroVezes;
	private String historicoCliente;
	
	public Cliente(String nome, String sobrenome, String documento, String endereco, String telefone, int numeroVezes) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.endereco = endereco;
		this.telefone = telefone;
		this.numeroVezes = numeroVezes;
		this.historicoCliente = historicoCliente;
	}

	public String getHistoricoCliente() {
		return historicoCliente;
	}

	public void setHistoricoCliente(String novoHistorico) {
		if(historicoCliente == null){
			historicoCliente = novoHistorico;
		} else {
		historicoCliente = historicoCliente + ", " + novoHistorico;
		}
	}

	public String toString() {
		return nome + "§" + sobrenome + "§" + documento + "§" + endereco + "§" + telefone + "§" + numeroVezes + "§" + historicoCliente;
	}

	public String getNome() {
		return nome;
	}

	public String setNome(String nome) {
		return this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public String setSobrenome(String sobrenome) {
		return this.sobrenome = sobrenome;
	}
	
	public String getDocumento() {
		return documento;
	}

	public String setDocumento(String documento) {
		return this.documento = documento;
	}

	public String getEndereco() {
		return endereco;
	}

	public String setEndereco(String endereco) {
		return this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public String setTelefone(String telefone) {
		return this.telefone = telefone;
	}

	public int getNumeroVezes() {
		return numeroVezes;
	}

	public int setNumeroVezes(int numeroVezes) {
		return this.numeroVezes = numeroVezes;
	}

}