package grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GrafoMatriz {
	
	int numVerts;
	static int MaxVerts = 20;
	
	Vertice[] verts;
	int[][] matAd;
	private int[] distancias;
	private int[] previos;
	
	public GrafoMatriz() {
		this(MaxVerts);
	}
	
	public GrafoMatriz(int mx) {
		matAd = new int[mx][mx];
		verts = new Vertice[mx];
		for (int i = 0; i < mx; i++)
			for (int j = 0; j < mx; j++) // Corrección: j en lugar de i
				matAd[i][j] = 0;
		numVerts = 0;
	}

	// Agregar un nuevo vértice al grafo
	public void nuevoVertice(String nom) {
		boolean esta = numVertice(nom) >= 0;
		if (!esta) {
			Vertice v = new Vertice(nom);
			v.asigVert(numVerts);
			verts[numVerts++] = v;
		}
	}

	// Obtener el índice de un vértice en el arreglo "verts" según su nombre
	int numVertice(String vs) {
		Vertice v = new Vertice(vs);
		boolean encontrado = false;
		int i = 0;
		for (; (i < numVerts) && !encontrado;) {
			encontrado = verts[i].equals(v);
			if (!encontrado)
				i++;
		}
		return (i < numVerts) ? i : -1;
	}

	// Agregar una nueva arista entre dos vértices por su nombre
	public void nuevoArco(String a, String b) throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		matAd[va][vb] = 1;
	}

	// Agregar una nueva arista entre dos vértices por su índice
	public void nuevoArco(int va, int vb) throws Exception {
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		matAd[va][vb] = 1;
	}

	// Verificar si dos vértices por su nombre son adyacentes
	public boolean adyacente(String a, String b) throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		return matAd[va][vb] == 1;
	}

	// Verificar si dos vértices por su índice son adyacentes
	public boolean adyacente(int va, int vb) throws Exception {
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		return matAd[va][vb] == 1;
	}

	// Obtener la lista de vértices conectados a un vértice por su nombre
	public List<Vertice> nodosConectados(String a) throws Exception {
		int va = numVertice(a);
		if (va < 0) {
			throw new Exception("Vértice no existe");
		}

		List<Vertice> nodosConectados = new ArrayList<>();
		for (int i = 0; i < numVerts; i++) {
			if (matAd[va][i] == 1) {
				nodosConectados.add(verts[i]);
			}
		}
		return nodosConectados;
	}
	public void recorridoAnchura(String inicio) throws Exception {
		int vInicio = numVertice(inicio);
		if (vInicio < 0) {
			throw new Exception("Vértice no existe");
		}

		boolean[] visitado = new boolean[numVerts];
		Queue<Integer> cola = new LinkedList<>();

		visitado[vInicio] = true;
		cola.add(vInicio);

		while (!cola.isEmpty()) {
			int vActual = cola.poll();
			System.out.print(verts[vActual].nombre + " ");

			List<Vertice> nodosConectados = nodosConectados(verts[vActual].nombre);
			for (Vertice v : nodosConectados) {
				int vIdx = numVertice(v.nombre);
				if (!visitado[vIdx]) {
					visitado[vIdx] = true;
					cola.add(vIdx);
				}
			}
		}
	}

	// Recorrido en profundidad (DFS)
	public void recorridoProfundidad(String inicio) throws Exception {
		int vInicio = numVertice(inicio);
		if (vInicio < 0) {
			throw new Exception("Vértice no existe");
		}

		boolean[] visitado = new boolean[numVerts];
		recorridoProfundidadAux(vInicio, visitado);
		System.out.println(); // Salto de línea para separar el resultado del menú
	}

	private void recorridoProfundidadAux(int v, boolean[] visitado) {
		visitado[v] = true;
		System.out.print(verts[v].nombre + " ");

		List<Vertice> nodosConectados = null;
		try {
			nodosConectados = nodosConectados(verts[v].nombre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Vertice vertice : nodosConectados) {
			int vIdx = numVertice(vertice.nombre);
			if (!visitado[vIdx]) {
				recorridoProfundidadAux(vIdx, visitado);
			}
		}
	}

	public void dijkstra(String origen) throws Exception {
		int origenIdx = numVertice(origen);
		if (origenIdx < 0) {
			throw new Exception("Vértice de origen no existe");
		}

		distancias = new int[numVerts];
		previos = new int[numVerts];
		boolean[] visitado = new boolean[numVerts];

		// Inicializar las distancias y los vértices previos
		for (int i = 0; i < numVerts; i++) {
			distancias[i] = Integer.MAX_VALUE;
			previos[i] = -1;
		}

		distancias[origenIdx] = 0;

		for (int i = 0; i < numVerts - 1; i++) {
			int v = minDistanciaNoVisitado(distancias, visitado);
			visitado[v] = true;

			List<Vertice> nodosConectados = nodosConectados(verts[v].nombre);
			for (Vertice vertice : nodosConectados) {
				int vIdx = numVertice(vertice.nombre);
				if (!visitado[vIdx] && adyacente(v, vIdx)) {
					int distanciaNueva = distancias[v] + 1; // Considerando distancias unitarias, pero puede ajustarse según el peso de las aristas
					if (distanciaNueva < distancias[vIdx]) {
						distancias[vIdx] = distanciaNueva;
						previos[vIdx] = v;
					}
				}
			}
		}
	}

	private int minDistanciaNoVisitado(int[] distancias, boolean[] visitado) {
		int minDistancia = Integer.MAX_VALUE;
		int minVertice = -1;

		for (int v = 0; v < numVerts; v++) {
			if (!visitado[v] && distancias[v] < minDistancia) {
				minDistancia = distancias[v];
				minVertice = v;
			}
		}
		return minVertice;
	}

	public void mostrarCaminoMinimo(String origen) throws Exception {
		int origenIdx = numVertice(origen);
		if (origenIdx < 0) {
			throw new Exception("Vértice de origen no existe");
		}

		for (int i = 0; i < numVerts; i++) {
			if (i != origenIdx) {
				System.out.print("Desde " + origen + " hacia " + verts[i].nombre + ": ");
				mostrarCamino(previos, i);
				System.out.println(" (Distancia: " + distancias[i] + ")");
			}
		}
	}

	private void mostrarCamino(int[] previos, int v) {
		if (v != -1) {
			mostrarCamino(previos, previos[v]);
			System.out.print(verts[v].nombre + " ");
		}
	}
}
