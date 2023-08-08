package grafos;
import java.util.List;
import java.util.Scanner;

public class Menu {

	int contV = 0;
	String nombre;
	private int[] distancias;
	private int[] previos;

	public void menu(GrafoMatriz grafo) {
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------------");
        System.out.println("1) INGRESO DE NODOS");
        System.out.println("2) CONEXION DE VERTICES");
        System.out.println("3) VERIFICADOR DE ADYACENCIA DE VERTICES");
        System.out.println("4) MOSTRAR ADYCENCIAS DE VERTICES");    
		System.out.println("5) RECORRIDO DE ANCHURA");
		System.out.println("6) RECORRIDO DE PROFUNDIDAD");
		System.out.println("7) DIJKSTRA");
		System.out.println("8) SALIR");
		System.out.println("------------------------------------------------");

		int opcion = sc.nextInt();
		sc.nextLine(); 
		
		switch (opcion) {
		case 1:
            System.out.println("***CREACION DE NODOS***");
            System.out.println("Ingrese los nodos a crear");
			int nVertices = sc.nextInt();
			sc.nextLine(); // Consumimos el salto de línea después de leer el entero.
			int totalVertices = nVertices + contV;
			
			if (nVertices < 1) {
                System.out.println("Error, ingrese numeros validos:");                    
			} else {
				if (totalVertices > 20) {
                    System.out.println("Error, ingrese la cantidad de nodos permitidos");                       
				} else {
					for (int i = 0; i < nVertices; i++) {
						int e=i+1;
                        System.out.println("Ingrese el nombre del vertice "+ e +" :");
						nombre = sc.nextLine();
						grafo.nuevoVertice(nombre.trim());
						contV = contV + 1;
					}                        
				}
			}
			
			menu(grafo);
			
			break;
		case 2:
			System.out.println("***CONEXION DE VERTICES***");
            System.out.println("Ingrese el nombre del vertice origen:");
            String origen = sc.nextLine();
            System.out.println("Ingrese el nombre del vertice destino " );
            String destino = sc.nextLine();
            
			try {
				grafo.nuevoArco(origen, destino);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			menu(grafo);
			
			break;
		case 3:
			System.out.println("***VERIFICADOR DE ADYACENCIA DE VERTICES***");
            System.out.println("Ingrese el nombre del vertice origen :");
            String vertice = sc.nextLine();
            System.out.println("Ingrese el nombre del vertice a destino: ");
            String verticeVerificar = sc.nextLine();
            
			try {
				System.out.println(grafo.adyacente(vertice, verticeVerificar));                  
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			menu(grafo);
			
			break;
		case 4:
            System.out.println("***MOSTRAR ADYCENCIAS DE VERTICES***");
            System.out.println("Ingrese el nombre del vertice a buscar:");
			String verticeBuscar = sc.nextLine();
			
			try {
				List<Vertice> nodosConectadosA = grafo.nodosConectados(verticeBuscar);
				System.out.println("Nodos conectados a ("+verticeBuscar+")");
				for (Vertice v : nodosConectadosA) {
					System.out.println(v.nomVertice());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			menu(grafo);
			
			break;
		case 5:
			System.out.println("***RECORRER EL GRAFO DE ANCHURA***");
			System.out.println("Ingrese el nombre del vértice desde donde iniciar el recorrido:");
			String verticeInicio = sc.nextLine();
			
			try {
				grafo.recorridoAnchura(verticeInicio);
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			menu(grafo);
			
			break;
		case 6:
			System.out.println("***RECORRER EL GRAFO DE PROFUNDIDAD***");
			System.out.println("Ingrese el nombre del vértice desde donde iniciar el recorrido:");
			String verticeInicioDFS = sc.nextLine();
			
			try {
				grafo.recorridoProfundidad(verticeInicioDFS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			menu(grafo);
			
			break;
		case 7:
			System.out.println("***DIJKSTRA***");
		    System.out.print("Ingrese el vértice de origen: ");
		    String origenD = sc.nextLine();

		    try {
		        grafo.dijkstra(origenD);
		        System.out.println("Ingrese las aristas (destino peso), o -1 para terminar:");
		        while (true) {
		            String input = sc.nextLine();
		            if (input.equals("-1")) {
		                break;
		            }
		            String[] parts = input.split(" ");
		            int origenDInt = Integer.parseInt(origenD);
		            int destinoD = Integer.parseInt(parts[0]);
		            int pesoD = Integer.parseInt(parts[1]);
		            GrafoPeso pesoGrafo = new GrafoPeso(contV);
		            pesoGrafo.agregarArista(origenDInt, destinoD, pesoD);
		        }

		        grafo.mostrarCaminoMinimo(origenD);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    menu(grafo);
		
			break;
		case 8:
            System.out.println("***ACABA DE SALIR DEL MENU***");
			break;
		}
		
	}
}
