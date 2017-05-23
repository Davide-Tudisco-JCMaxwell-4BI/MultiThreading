import static java.lang.Math.random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide Tudisco
 */
public class esercizio2 {
    /**
     * @param args the command line arguments
     */
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri

    public static void main(String[] args) {

        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis(); //assegno a start il tempo iniziale in cui e'Â¨ stato eseguito il programma
 Schermi schermo = new Schermi();    //creo un nuovo schermo (ovvero un monitor)
        Thread tic = new Thread (new TicTacToe("TIC", schermo)); //creo il primo thread di nome TIC
        tic.start(); //Avvio il thread
        
        Thread tac = new Thread(new TicTacToe("TAC", schermo)); //creo il secondo thread di nome TAC
        tac.start(); //Avvio il thread
        
        Thread toe = new Thread (new TicTacToe("TOE", schermo)); //creo il terzo thread di nome TOE
        toe.start(); //Avvio il thread
       
        long end = System.currentTimeMillis(); //assegno a end il tempo finale in cui e'Â¨ stato eseguito il programma
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
         try{
            tic.join(); //verifico quando toe ha concluso il thread
            
        } catch (InterruptedException e) 
              {} //possibile eccezione
        try{
            tac.join(); //verifico quando toe ha concluso il thread
            
        } catch (InterruptedException e) 
              {} //possibile eccezione
        try{
            toe.join(); //verifico quando toe ha concluso il thread
            
        } catch (InterruptedException e) 
              {} //possibile eccezione
         System.out.println("Main THREAD terminata. Punteggio: " + schermo.punteggio());  //stampo il punteggio dello schermo (monitor)
    }
    
}
class Schermi {

  String ultimoTHREAD = ""; //memorizzo l' ultimo thread scritto sullo schermo
  int punteggio = 0; //inizializzo il punteggio a 0

  public int punteggio() {  
    return this.punteggio; // fornisce il punteggio
  }
  public synchronized  void scrivi(String thread, String msg) {
    int casuale=100+(int)(Math.random()*300); //genero un numero casuale tra 100 e 400
    msg += ": " + casuale + " :";
    if( thread.equals("TOE") && ultimoTHREAD.equals("TAC")) { //verifico che il thread attuale sia uguale a TOE e che il thread precedente sia uguale a TAC
        punteggio++; //incremento di 1 il punteggio
        msg += "  <----------------"; //segnalo (conto) con una freccia quando Toe capita dopo Tac
    }
    try {
        TimeUnit.MILLISECONDS.sleep(casuale); //casuale ora diventa un numero rappresentante il tempo il MILLISECONDI
    } catch (InterruptedException e) {} //Richiamo eccezione    this.ultimoTHREAD = thread;
    System.out.println(msg); //stampo i thread
    ultimoTHREAD = thread; //riassegno il thread alla variabile ultimoTHREAD
  }
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    
    // non essesndo "static" c'e' una copia delle seguenti variabili per ogni THREAD 
    private String t; //dichiarazione variabile t (thread)
    private String msg; //dichiarazione variabile msg (nome del thread)
     Schermi schermo; //dichiarazione dello schermo (monitor)
    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s, Schermi schermo) {
        this.t = s; //assegno la stringa s a t (thread)
        this.schermo = schermo; //dichiaro lo schermo 
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html

   public void run() {
        for (int i = 10; i > 0; i--) { //creo un ciclo for da 10 a 0
            msg = "<" + t + "> " + t + ": " + i;
            schermo.scrivi(t, msg); //stampo sullo schermo il thrad ed il valore
        }
    }
    
}
