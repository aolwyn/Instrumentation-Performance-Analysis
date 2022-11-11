import java.util.Random;
import java.util.stream.IntStream;

public class Test {

    
    static Instrumentation ins = Instrumentation.Instance();

    public static void main(String[] args) {

        //testing main:
        ins.activate(true);
        ins.startTiming("main");
        ins.startTiming("loop");
        for(int i = 0; i < 5; i++){
            doSomeStuff();
        }
        ins.stopTiming("loop");
        ins.startTiming("array10000");
        int[] temp = populateArray(10000);
        ins.stopTiming("array10000");
        ins.comment("bubble and quick sort, 10000 next");
        testBBSI();
        testQSI();
        ins.stopTiming("main");
        ins.dump("maintest.txt");

        //isolated tests below:
        //testFunction();
        //testArray();
        //testBBSI();
        //testQSI();
        //testQSNI();

    };

    public static void testFunction(){    //this method is used to test the overhead of start and stop timing. 

        ins.activate(true);
        //long start1 = System.nanoTime();
        ins.startTiming("main");
        //long stop1 = System.nanoTime();
        ins.startTiming("loop");

        for (int i = 0; i < 5; i++){
            doSomeStuff();
        }

        ins.stopTiming("loop");
        ins.comment("this is an example of a comment!");
        //long start2 = System.nanoTime();
        ins.stopTiming("main");
        //long stop2 = System.nanoTime();

        ins.dump("dump_file.txt");
        //ins.dump("");
        //System.out.printf("\nStart Timing Overhead: %f", (float)(stop1-start1)/1000000);
        //System.out.printf("\nStop Timing Overhead: %f", (float)(stop2-start2)/1000000);
    }

    public static void doSomeStuff() {//method provided in the assignment instructions; used for testing overhead. 
        ins.startTiming("doSomeStuff()");
        for (int i = 0; i < 50; i++)
            System.out.println("Hello there!");
        ins.stopTiming("doSomeStuff()");
    }

    public static int[] populateArray(int length){ //create a new array of random numbers with max 99999 of size limit 'size' and then send the stream into an array.
        return IntStream.generate(()-> new Random().nextInt(99999)).limit(length).toArray(); 
       
    }//close populate aray

    public static void testArray(){//tests the creation of different populateArray() sizes.
        /* 
        long starta1 = System.nanoTime();
        int[] temp1 = populateArray(100);
        long stopa1 = System.nanoTime();

        long starta2 = System.nanoTime();
        int[] temp2 = populateArray(500);
        long stopa2 = System.nanoTime();
        
        long starta3 = System.nanoTime();
        int[] temp3 = populateArray(1000);
        long stopa3 = System.nanoTime();
        */
       // System.out.println("Populate Array Test time for size 100: "+ Long.toString((stopa1-starta1)/1000000)+"ms");
      //  System.out.println("Populate Array Test time for size 500: "+ Long.toString((stopa2-starta2)/1000000)+"ms");
        //System.out.println("Populate Array Test time for size 1000: "+ Long.toString((stopa3-starta3)/1000000)+"ms");

    }

    public static void testBBSI(){
        BubbleSort bubbleSort = new BubbleSort();
        int[] temp = populateArray(10000);
        int[] temp2 = temp.clone(); //keep the array (original) private.
        bubbleSort.bubbleSort(temp2);
        //ins.comment("10000 size");
        

    }
    public static void testBBSNI(){ //bubblesort without instrumentation testing; to run, comment out instrumentation from BubbleSort.java
        BubbleSort bubbleSort = new BubbleSort();
        int[] temp = populateArray(10000);
        int[] temp2 = temp.clone(); //keep the array (original) private.
        long time1 = System.nanoTime();
        bubbleSort.bubbleSort(temp2);
        long time2 = System.nanoTime();
        System.out.println("Bubble sort time for 100 without insturumentation: "+Long.toString((time2-time1)/1000000));

    }
    
    public static void testQSI(){//quick sort without instrumentation testing; to run, comment out instrumentation from QuickSort.java
        int size = 10000;
        QuickSort quickSort = new QuickSort();
        int [] temp = populateArray(size);
        int [] temp2 = temp.clone();
        quickSort.getQuickSort(temp2,0, size-1);
      
    }//close testQSI

    public static void testQSNI(){//quick sort without instrumentation testing; to run, comment out instrumentation from QuickSort.java
        int size = 10000;

        QuickSort quickSort = new QuickSort();
        int [] temp = populateArray(size);
        int [] temp2 = temp.clone();

        long time1 = System.nanoTime();
        quickSort.getQuickSort(temp2,0, size-1);
        long time2 = System.nanoTime();
        System.out.println("Quick sort time for 10000 without insturumentation (ms): "+Long.toString((time2-time1)/1000000));
    }
}//close class

