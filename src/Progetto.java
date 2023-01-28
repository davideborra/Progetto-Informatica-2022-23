// Scheletro di codice per il progetto 2022

// In questo file NON ci deve essere nessuna dichiarazione "package"
// come per esempio
//
//    package progetto;

// Potete aggiungere qui altri "import" per usare le librerie
// standard di Java (ad es. lo Scanner).

import java.io.*;
import java.util.Scanner;

// NON modificate in nessun modo la linea seguente
public class Progetto {

	// Qui potete liberamente aggiungere altre funzioni / procedure.
	
	// Se volete, qui potete anche inserire variabili globali.
	// Se decidete di inserirle, dovete fare in modo che la funzione
	//     determinante
	// inizializzi tali variabili **ogni volta** che viene chiamata. 
	// Inizializzarle una volta sola nel main NON basta, visto che la
	// funzione verra` chiamate piu` volte.
	
	//Funzioni di debug-----------------------------------------------------------------------
	
	public static void stampa (long[] a) {
		System.out.println(a[0]+"/"+a[1]);
	}
	
	public static boolean uguale(long[] a, long[] b) { 
		a=semplifica(a);
		b=semplifica(b);	
		if (a[0]==b[0] && a[1]==b[1]) {
			return true;
		}else {
			return false;
		}
	}
	
		static void matPrint(long[][][] mat) {
			System.out.println("---------------");
			int n = mat[0].length;
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					System.out.print(mat[i][j][0]+"/"+mat[i][j][1]+"\t");
				}
				System.out.print("\n");
			}
			System.out.println("---------------");
		}
	
	//---------------------------------------------------------------------------------------
	
	
	public static boolean zero(long[] a) { 	
		return (a[0]==0);
	}
	
	public static long abs(long a) {
		if(a>=0) {
			return a;
		}
		else {
			return -a;
		}
	}
	
	public static long mcd(long a, long b) {
		if (b==0)
			return a; 
		else 
			return mcd(abs(b),abs(a) % abs(b)); //l'algoritmo funziona sugli interi positivi, e a me non interessano eventuali segni
	}
	
	public static long mcm(long a, long b) {
		return a*b/mcd(a,b);
	}
	
	public static long[] semplifica(long[] a) {
		long [] b = new long[2];
		b[0]=0;
		b[1]=1;
		if(zero(a)) {
			return b;
		}
		long mcd = mcd(a[1],a[0]); //metto prima il denominatore così il controllo ==0 viene fatto sul numeratore, l'unico che può essere 0
		b[0]=a[0]/mcd;
		b[1]=a[1]/mcd;
		return b;
	}
	
	public static long[] prodotto(long[] a, long[] b) {
		//assumo che a e b siano stati precedentemente semplificati
		long [] n = new long[2];
		long [] m = new long[2];
		n[0]=a[0];
		n[1]=b[1];
		n=semplifica(n); //semplifico incrociato
		m[0]=b[0];
		m[1]=a[1];
		m=semplifica(m); //semplifico incrociato
		n[0]*=m[0]; //prodotto numeratori
		n[1]*=m[1]; //prodotto denominarori
		n=semplifica(n);		//altrimemnti rischio di avere 0/2
		return n;
	}
	
	public static long[] reciproco (long[] a) {
		long [] reciproco = new long[2];
		if(a[0]==0) {
			return null;
			//errore
		}else if (a[0]>0){
			reciproco[0]=a[1];
			reciproco[1]=a[0];
		}else {
			reciproco[0]=-a[1];
			reciproco[1]=abs(a[0]);
		}
		return reciproco;
	}
	
	public static long[] opposto (long[] a) {
		long [] opposto = new long[2];
		opposto[0]=-a[0];
		opposto[1]=a[1];
		return opposto;
	}

	public static long[] somma(long[] a, long[] b) {
		long [] somma = new long[2];
		somma[1]=mcm(a[1],b[1]);
		somma[0]=a[0]*somma[1]/a[1]+b[0]*somma[1]/b[1];
		somma=semplifica(somma);
		return somma;
	}
	
	
	//I seguenti tre metodi ritornano un long[] che è il reciproco la modifica che fanno al determinante
	
	public static long[] scambiaRighe(long [][][] mat, int r1, int r2){
		//System.out.println("R"+r1+" <-> R"+r2);
		int n = mat[0].length;
		long[] temp = new long[2];
		for (int i=0; i<n; i++) {
			temp[0]=mat[r1][i][0];
			temp[1]=mat[r1][i][1];
			mat[r1][i][0]=mat[r2][i][0];
			mat[r1][i][1]=mat[r2][i][1];
			mat[r2][i][0]=temp[0];
			mat[r2][i][1]=temp[1];
		}
		long[] menouno = new long[2];
		menouno[0]=-1;
		menouno[1]=1;
		return menouno;
	}
	
	
	public static long[] sommaRighe(long [][][] mat, int r1, int r2, long[] k) { //mette il risultato di r1+k*r2 in r1
		//System.out.println("R"+r1+" <- R"+r1+" + R"+r2);
		int n = mat[0].length;
		for (int i=0; i<n; i++) {
			//System.out.print("\t "+mat[r1][i][0]+"/"+mat[r1][i][1]+" + "+mat[r2][i][0]+"/"+mat[r2][i][1]+" = ");
			mat[r1][i]=somma(mat[r1][i],prodotto(k,mat[r2][i]));
			//System.out.println("\t "+mat[r1][i][0]+"/"+mat[r1][i][1]);
		}
		long[] uno = new long[2];
		uno[0]=1;
		uno[1]=1;
		return uno;
	}
	
	//Non utilizzata
	/*public static long[] moltiplicaRiga(long [][][] mat, int r, long[] k) {
		int n = mat[0].length;
		for (int i=0; i<n; i++) {
			mat[r][i]=prodotto(mat[r][i], k);
		}
		return reciproco(k);
	}*/
	
	public static long[] gauss(long[][][] mat){
		long[] detMod = new long[2];
		detMod[0]=1;
		detMod[1]=1;
		int n = mat[0].length;
		
		for(int i=0; i<n-1; i++) {
			//-------------------------
			if (zero(mat[i][i])) {
				//Questo blocco controlla che il pivot sia diverso da 0
				boolean flag = false;
				for (int j=i+1; j<n; j++) {
					if (!zero(mat[j][i])) {	
						detMod=prodotto(scambiaRighe(mat, i,j), detMod);
						flag=!flag;
						break;
					}
				}
				if (!flag)
					return new long[2];	//se il pivot è 0 la matrice non ha rango massimo quindi il determinante è 0. Non mi serve fare Gauss fino alla fine
			}
			//-------------------------
			//Questo blocco fa 0 sotto al pivot
			long[] pivot = mat[i][i];		
			for (int j=i+1; j<n; j++) {
				if (!zero(mat[j][i])) {
					long[] sotto = mat[j][i];		//coeff del termine che devo rendere zero
					detMod=prodotto(detMod,sommaRighe(mat, j, i, opposto(prodotto(sotto, reciproco(pivot)))));			//Rj-sotto/pivot Ri e metto la modifica al determinante in detmod
					//System.out.println("j: "+j);
					//matPrint(mat);
				}
			//-------------------------
			}
		}
		return detMod;
	}
	
	public static long[][][] load(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			int n = in.nextInt();
			in.nextLine();		//non avanza automaticamante
			long[][][] mat = new long[n][n][2];
			for(int i=0; i<n; i++) {
				String input = in.nextLine();
				String[] rowStr = input.split(" ");
				for(int j = 0; j<n; j++) {
					String[] num = rowStr[j].split("/");
					mat[i][j][0]=Long.parseLong(num[0]);
					mat[i][j][1]=Long.parseLong(num[1]);
				}
			}
			//matPrint(mat);
			return mat;
		} catch (IOException e) {
			System.out.println("Errore di IO: " + e);
			return null;
		}
	}
	
	
	
	
	
	// NON modificate in nessun modo la linea seguente
	public static long[] determinante(String fileName) {
		// Qui potete inserire il vostro codice, modificando lo stub esistente.
		long[] det = new long[2]; 				//determinante
		long[][][] mat = load(fileName);
		int n = mat[0].length;
		det = gauss(mat);					//faccio Gauss e registro il cofficiente di modifica del determinante 
		if(zero(det)) {						//se ho un pivot zero ho interrotto gauss quindi il determinante fa zero
			det[1]=1;						//per sicurezza aggiusto il denominatore, in modo da non rischiare di ritornare 0/2
			return det;
		}
		for(int i=0; i<n; i++) {				//faccio il prodotto degli elementi della diagonale
			det=prodotto(det, mat[i][i]);
		}
		return det;
	}

	// Potete modificare il main liberamente. Potete per esempio eseguire
	// qualche test sulla funzione sopra, come quelli suggeriti sotto.
	// Ricordatevi che chi corregge il progetto NON eseguira` il vostro
	// main, ma i propri test.
	// 
	public static void main(String[] args) {
		
        // Eseguiamo tutti i test
        int counter=0;				//conta gli errori
        long[] det = new long[2];
        String s;					//nome del file
        for (int i=1; i<=100; i++) {			//costruisco il nome del file di input
            s = "matrice";
            if(i<10) {
                s+="-00"+i;
            }else if(i<100) {
                s+="-0"+i;
            }else {
                s+="-"+i;
            }
            s+=".txt";
            long[] check = new long[2];
            try{
                det = determinante("test/" + s);
            }catch(ArithmeticException err){
                System.out.println("\n\n "+err+"\n\n");
            }
            //controllo che il risultato sia corretto
            try {
                Scanner in = new Scanner(new File("test/det_" + s));		
                String input = in.nextLine();
                String[] checkStr = input.split("/");
                check[0]=Long.parseLong(checkStr[0]);
                check[1]=Long.parseLong(checkStr[1]);
                in.close();
            } catch (IOException e) {
                System.out.println("Errore di IO: " + e);
                return;
            }
            System.out.print(s + ": determinante = " + det[0] + "/" + det[1] + "\t" + check[0] + "/" + check[1] + "\t\t\t");
            try{
                if(!uguale(det, check)) {
                    System.out.println("ERRORE");
                    counter++;
                }else {
                    //System.out.print("GIUSTO");
                    System.out.println();
                }
            }catch(Exception e){

            }
        }
        System.out.println("\n ho trovato " + counter +" errori");
        
    }
}

