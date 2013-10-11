 /*
  Modell for trafiksimulering
  ===========================

  Foljande klasser skall anvandas: 

     Car representerar fordon
         ankomsttid och destination som satts nar objektet skapas

     Light representerar ljussignaler
         Se nedan
  
     Lane representerar ett vagavsnitt
         En vag representeras av en array dar varje element
	 antingen ar tomt eller innehaller en referens till 
         ett bil-objekt.
         OBS: Klassen Lane paminner om komekanismen i foregaende
         uppgift men den skiljer sig ocksa i flera avseende.
         I denna klass stalls nya bilar i ena anden av ARRAYEN
         och inte narmast efter den som finns dar. I komekanismen        
         var alltid elementen (kunderna) samlade medan bilarna
         i denna klass kan vara utspridda over hela arrayen.
         
 
     TrafficSystem
         Definierar de komponeneter dvs de vagar och signaler
	 som ingar i systemet. Se vidare nedan

     Simulation
         main-metod som driver simuleringen


  Den situation som skall simuleras ser schematiskt ut enligt



         C           B                               A
       s1<----r1-----<---------r0---------------------
       s2<----r2-----< 


  En fil (vagstracka) r0 delar sig vid B i tva filer r1 och r2.
  Signal s1 kontrollerar fil r1 och och signal s2 fil r2.
 
  Bilar uppstar vid A. Sannolikheten att en bil skall komma till A
  vid ett visst tidsteg kallas ankomstintensiteten.

  Vid ett tidssteg ror sig bilarna ett steg framat (om platsen framfor
  ar ledig). Vid C tas bilarna ut fran filerna om repektive
  signal ar gron. Vid B tas bilar ut fran r0 och laggs in pa r1 eller r2
  beroende pa destination (och om platsen ar ledig).

  Anm: Man skulle kunna representera systemet med tva Lane-objekt
  men da maste man ha nagot satt att representera en "avtappning"
  (dar svangfilen borjar). Med den har valda representationen
  blir Lane-klassen enklare.  
    
*/

public class Simulation {

	public static long TimePerStep = 100;
	
	public static void main(String [] args) {
		long    lastTick    = System.currentTimeMillis();
		long    timeElapsed = 0;
		long    stepCntr    = Simulation.TimePerStep;
		boolean running     = true;
		boolean tick        = true;
		TrafficSystem TS    = TrafficSystem.getInstance();
		int stepCnt         = 0;
		while(running)
		{
			timeElapsed = System.currentTimeMillis() - lastTick;
			lastTick = System.currentTimeMillis();
			stepCntr += timeElapsed;
			while(stepCntr >= Simulation.TimePerStep)
			{
				stepCnt++;
				System.out.print("\033[H\033[2J"); // Clear the terminal.
				System.out.flush();
				TS.step(stepCnt);
				TS.print();
				stepCntr -= Simulation.TimePerStep;
			}
		}
	}
}
