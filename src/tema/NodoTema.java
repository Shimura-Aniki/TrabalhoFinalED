package tema;

public class NodoTema {
	public Tema tema;
	public NodoTema seguinte;
	public NodoTema anterior;

	public NodoTema(Tema ponto){
		tema = ponto;
		seguinte = null;
		anterior = null;
	}
}
