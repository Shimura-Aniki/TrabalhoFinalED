package cliente;

public class Nodo {
	public Cliente cliente;
	public Nodo seguinte;
	
	public Nodo(Cliente ponto){
		cliente = ponto;
		seguinte = null;
	}
}
