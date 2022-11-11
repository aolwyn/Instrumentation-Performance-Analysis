import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
    private HashMap<String,Long> times; //hashmap used to track start and stop times. key = comment, value = time.
    private Queue<String> messages; //queue used to keep track of messages in order that will be put into the dump file.
    private HashMap<Integer,String> tabs; //hashmap used to keep track of the 'tabulation' of the messages. key = count, string = tabs.
    private boolean active; // onoff boolean to see if the instrumentation is active 
    private double totalTime; //variable to keep track of total time since activation.
    private long timeDifference; //variable used to keep track of the difference between start + stop of a timing.
    private double startTime; //used to keep track of the staret of the first timing (for use with totalTime.)
    private int count; //counts how many indents / loops we're in - ie, main +1, loop +1, starttiming = +1, stoptiming = -1
    private static Instrumentation ins = new Instrumentation(); //instance used 

    //constructor 
    private Instrumentation(){
        this.times = new HashMap<String,Long>();
        this.messages = new LinkedList<String>();
        this.tabs = new HashMap<Integer,String>();
        this.active = false;
        this.totalTime = 0;
        this.timeDifference = 0;
        this.startTime = 0;
        this.count = 0;
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
        if(totalTime == 0) startTime = System.nanoTime()/1000000; //base case, if it is the first thing being timed
        if(!active) System.out.println("System is not active.");//base case, inactive system

        else{
            times.put(comment,System.nanoTime()); //put the thing into the map.
            String tab = "|   ";//this keeps track of the tabulation
            tabs.put(count,tab.repeat(count)); //we enter the tab into the hashmap
            messages.add(tabs.get(count)+"STARTTIMING: "+ comment);//add the associated message to the queue
            count++;  //increment the count of tabs for the next startTiming call
        }
    }//close startTiming

    //stopTiming method
    public void stopTiming(String comment){
        if(!active) System.out.println("System is not active.");//base case, inactive system
        else{
            count--;//decrement the tab
            totalTime = (System.nanoTime()/1000000)-startTime;//this + next 2 lines gets the difference in time and puts it into the hashmap for the associated comment
            timeDifference = (System.nanoTime() - times.get(comment))/1000000;
            times.put(comment,(long) 0);
            messages.add(tabs.get(count)+"STOPTIMING: "+comment+" "+Long.toString(timeDifference)); //add the stop timing + the tab + the time for the comment
        }

    }//close stopTiming

    //comment method
    public void comment(String comment){
        if(!active) System.out.println("System is not active.");//base case, inactive
        else{
            messages.add(tabs.get(count)+"COMMENT: "+comment);//add the comment to the queue. no tabs should be added / removed.
        }
    }//close comment

    public void dump(String filename){
        //3 cases: inactive, filename given and filename not given.
        //if inactive, do nothing.
        //if case 2 (name given) then we de-queue until empty and send to txt file. if error thrown, do nothing. (see try-catch.)
        //if case 3 (no name) then first create a name for the file based on current date. then do the same as case 2.
        if(!active)System.out.println("System is not active.");
        else if(filename.length() == 0 || filename == null){
            try{
                FileWriter myWriter = new FileWriter(LocalDate.now()+".txt");
                while(!messages.isEmpty()){
                    myWriter.write(messages.remove() + "\n");
                }
                myWriter.write("TOTALTIME: " + Double.toString(totalTime) + "ms");
                myWriter.close();
            }catch(IOException e){
                System.out.println("A File IO Error Has Occurred.");
            }
        }//close else if
        else{
            try{
                FileWriter myWriter = new FileWriter(filename);
                while(!messages.isEmpty()){
                    myWriter.write(messages.remove() + "\n");
                }
                myWriter.write("TOTALTIME: " + Double.toString(totalTime) + "ms");
                myWriter.close();
            }catch(IOException e){
                System.out.println("A File IO Error Has Occurred.");
            }
        }//close else
    }//close dump
}//close class Instrumentation



