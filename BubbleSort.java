

class BubbleSort {
    
    //bubblesort method, retrieved from geeksforgeeks
    public void bubbleSort(int arr[]){
        Instrumentation inst = Instrumentation.Instance();
        inst.startTiming("Bubble Sort");
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
        inst.stopTiming("Bubble Sort");
    }
}//close class
