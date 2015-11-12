package grafos;

import java.util.Random;

public class MatrizSimetrica {
	private boolean[] matriz;
	private int dimension;
	private int n;//orden de la matriz
	private int t;//tamaÃ±o del vector
	
	public MatrizSimetrica(int dimension){
		this.dimension=dimension;
		Double aux;
		if(dimension<0)
			dimension=1;
		t=(dimension*(dimension-1))/2;
		matriz = new boolean[t];
		aux=((1+ Math.sqrt(1+8*matriz.length))/2);
		n=aux.intValue();
		//generarGrafo(45);
	}
	
	public void generarGrafo(double porcAdy){
		Random r=new Random();
		int rand;
		Double aux=(porcAdy*this.t)/100;;
		int cantAristas=(int)Math.round(aux);
		for(int i=0;i<cantAristas;i++){
			if(matriz[rand=r.nextInt(cantAristas+1)]==true)
				i--;
			else
				matriz[rand]=true;
		}
	}
	
	public boolean getValor(int f, int c){
		if(f==c)
			return true;
		//I = F * N + C â€“ ( F2 + 3 * F + 2) / 2
		//int j=f+c+(f+(f*(f-1))/2)-1; 		<--MartÃ­n
		int i;	
		if(f>c)
			i=(c* n) +f -((c*c)+(3*c)+2)/2;
		else
			i=(f* n) +c -((f*f)+(3*f)+2)/2;
		return matriz[i];
	}
	
	public boolean getValor(int i){
		return matriz[i];
	}
	public int getDimension() {
		return dimension;
	}
	
	public void setValor(int f, int c, boolean val){
		if(f==c)
			return;
		int i;	
		if(f>c)
			i=(c* n) +f -((c*c)+(3*c)+2)/2;
		else
			i=(f* n) +c -((f*f)+(3*f)+2)/2;
		matriz[i]=val;
	}
	
	public void setValor(int i, boolean val){
		matriz[i]=val;
	}
	
	public void mostrarMatriz(){
		System.out.print(" ");
		for(int i=0;i<dimension;i++){
			System.out.print("|"+i+"\t");
		}
		System.out.println("|");
		for(int i=0;i<dimension;i++){
			System.out.print(i);
			for (int j=0;j<dimension;j++) {
				System.out.print("|"+getValor(i, j)+"\t");
			}
			System.out.println("|");
		}
	}
	
	public int getTam(){
		return t;
	}
	
	
	//F = T â€“ (Redondeo hacia arriba con cero decimales de ( 1 + ( 1 + 8 ( T- I ) )1/2  ) / 2 )
//	public int getFila(int i){
//		Double r=(double) (t-(Math.round((1+Math.sqrt( 1+8*(t-i)))/2)));
//		Double k=(double)( t-(Math.round( 1 + (Math.sqrt(1+8*(t-i)) ) ) /2));
//		return k.intValue();
//	}
//	
//	//C = N â€“ ( T â€“ I â€“ (  (N â€“ F â€“ 1)2 â€“ (N â€“ F â€“ 1)  )  / 2 )
//	public int getColumna(int i){
//		//return n-(t-i((n-f-1)*(n-f-1)-(n-f-1)/2)));
//		return 1;
//	}
}
