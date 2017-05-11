package multithread;
import java.util.concurrent.TimeUnit;
import static multithread.TicTacToe.contatore; //importo il pacchetto per visualizzare il contatore
import static java.lang.Math.random;

/**
 *
 * @author Davide Tudisco
 */
public class multithread {
    /**
     * @param args the command line arguments
     */
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis(); //assegno a start il tempo iniziale in cui e'¨ stato eseguito il programma

        Thread tic = new Thread (new TicTacToe("TIC")); //creo il primo thread di nome TIC
        tic.start(); //Avvio il thread
        
        Thread tac = new Thread(new TicTacToe("TAC")); //creo il secondo thread di nome TAC
        tac.start(); //Avvio il thread
        
        Thread toe = new Thread (new TicTacToe("TOE")); //creo il terzo thread di nome TOE
        toe.start(); //Avvio il thread
       
        long end = System.currentTimeMillis(); //assegno a end il tempo finale in cui e'¨ stato eseguito il programma
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
        try{
            tic.join(); //verifico quando tic ha concluso il thread
            
        }catch(InterruptedException e)
        {System.out.println(e);} //possibile eccezione
        try{
            tac.join(); //verifico quando tac ha concluso il thread
            
        }catch(InterruptedException e)
     
        {System.out.println(e);} //possibile eccezione
        try{
            toe.join(); //verifico quando toe ha concluso il thread
            
        } catch (InterruptedException e) 
              {System.out.println(e);} //possibile eccezione

        System.out.println ("il punteggio e' " + contatore); //stampo quante volte TOE è capitato dopo TAC
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
    public static int contatore = 0; //contatore conta quante volte il thread TOE viene dopo TAC, lo impostiamo inizialmente a 0
    public static boolean c = false; //se il thread ÃƒÂ¨ TAC diventa true mentre se ÃƒÂ¨ altro ÃƒÂ¨ False
    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s) {
        this.t = s; //assegno la stringa s a t (thread)
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html

    public void run() 
    {
        for (int i = 10; i > 0; i--) 
        {           
            if("TAC".equals(t)) //verifico che TAC sia uguale a t
                c = true; // se e vero, imposto c a true (vero)
                
            msg = "<" + t + "> "; //assegno a msg il testo
            int casuale=100+(int)(Math.random()*300); //Tempo casuale tra 100 e 300 millisecondi
             try {
                TimeUnit.MILLISECONDS.sleep(casuale); 
            } catch (InterruptedException e) {} //possibile eccezione
            if("TOE".equals(t) && c == true) //verifico che TOE sia uguale a t e che c sia true (vero)
                contatore++; //incremento di 1 il contatore
            else
                c = false; //imposto c a false (falso)
            msg += t + ": " + i; //sommo t (thread) a msg ogni volta che ciclo
            
            System.out.println(msg); //visualizzo il messaggio di output (nome del thread)
        }
    
    }
}
