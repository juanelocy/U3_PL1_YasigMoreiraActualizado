package grafos;

public class GrafoPeso {
	int[][] matPeso;

	public static final int INFINITO = Integer.MAX_VALUE;

	public GrafoPeso(int vertices) {
		matPeso = new int[vertices][vertices];
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				matPeso[i][j] = INFINITO;
			}
		}
	}

	public void agregarArista(int origen, int destino, int peso) {
		matPeso[origen][destino] = peso;
		matPeso[destino][origen] = peso; // Si el grafo es no dirigido
	}

	public int numeroDeVertices() {
		return matPeso.length;
	}

	// Otros métodos y atributos relevantes...

	public int[][] getMatPeso() {
		return matPeso;
	}
}