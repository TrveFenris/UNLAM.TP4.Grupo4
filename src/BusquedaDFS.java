package grafos;

public class BusquedaDFS {
	private boolean[] marked;    // marked[v] = is there an s-v path?
	private int count;  
	
	public BusquedaDFS(Grafo g, int s) {
		marked = new boolean[g.getCantNodos()];
		dfs(g,s);
	}
	
	private void dfs(Grafo G, int v) {
       count++;
       marked[v] = true;
       for (int w : G.nodosAdyacentesA(v)) {
           if (!marked[w]) {
               dfs(G, w);
           }
       }
	}	
	
	public boolean marked(int v) {
        return marked[v];
    }
}
