import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
//import Java.time.Instant;
import java.util.Queue;
//import Java.time.Duration;

public class Instrumentation {
    
/*TO DO:
 * TEST FILE
 * METHODS 
 * 
 * 
 * 
 */
    private HashMap<String,Long> times;
    private Queue<String> messages;
    private boolean active;
    private double totalTime;   
    private long timeDifference;

    private static Instrumentation ins = new Instrumentation();

     /*TO DO:
         * ENTER INFO FOR CONSTRUCTOR
         * GET TOTAL TIME CALCULATION.
         * ADD TRY-CATCH-THROWS.
         * THE 'TABULATION' AT THE BEGINNING.
         */

    /*COMPLETED :
     * Activate 
     * Getter
     * 
     */

    //constructor 
    private Instrumentation(){
        this.times = new HashMap<String,Long>();
        this.messages = new LinkedList<String>();
        this.active = false;
        this.totalTime = 0;
        this.timeDifference = 0;
    }//close constructor

    //getter method
    public static Instrumentation Instance(){
        return ins;
    }

    //activate method
    public void activate(boolean state){
        active = state;
    }//close activate

    //startTiming method
    public void startTiming(String comment){
        if(totalTime == 0) totalTime = System.nanoTime()/1000000;
        if(!active) System.out.println("System is not active.");

        else{
            times.put(comment,System.nanoTime());
            messages.add("STARTTIMING: "+ comment);
        }
    }//close startTiming

    //stopTiming method
    public void stopTiming(String comment){
        if(!active) System.out.println("System is not active.");
        else{
            totalTime = System.nanoTime()/1000000;
            timeDifference = (System.nanoTime() - times.get(comment))/1000000;
            times.put(comment,(long) 0);
            messages.add("STOPTIMING: "+comment+" "+Long.toString(timeDifference));
        }

    }//close stopTiming

    //comment method
    public void comment(String comment){
        if(!active) System.out.println("System is not active.");
        else{
            messages.add("COMMENT: "+comment);
        }
    }//close comment

    public void dump(String filename){
        if(!active)System.out.println("System is not active.");
        else{
            try{
                FileWriter myWriter = new FileWriter(filename);
                while(!messages.isEmpty()){
                    myWriter.write(messages.remove() + "\n");
                }
                myWriter.write("TOTALTIME: " + String.format("%.3f",totalTime) + "ms");
                myWriter.close();
            }catch(IOException e){
                System.out.println("A File IO Error Has Occurred.");
            }
        }//close else
    }//close dump




    }//close class Instrumentation



