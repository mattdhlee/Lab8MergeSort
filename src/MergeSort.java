import java.util.Arrays;
import java.util.Random;

/**
 * Created by Matt on 2017-03-23.
 */
public class MergeSort {

    static Random rand = new Random();

    public enum MergeFunctions {
        SIMPLE, MEMALLOC, ITERATIVE
    }

    static void RandomlyPermute(int[] A) {
        //Random rand = new Random();
        for (int i = 0; i < A.length; i++) {
            int randInt = rand.nextInt(A.length-i) + i;
            int tmp = A[i];
            A[i] = A[randInt];
            A[randInt] = tmp;
        }
    }

    static int[] GenerateA(int n) {
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = i;
        }
        RandomlyPermute(A);
        return A;
    }


    private static void simpleMergeSort(int[] A, int p, int r) {
        //TODO: implement simple recursive mergesort from the book
        if(p<r) {
            int q = (p+r)/2;
            simpleMergeSort(A,p,q);
            simpleMergeSort(A,q+1,r);
            merge(A,p,q,r);
        }
    }

    private static void merge(int[] A, int p, int q, int r) {
        int n = q-p+1;//left size
        int m = r-q;//right size
        //set up the left and right subarrays.
        int[] left = new int[n+1];
        int[] right = new int[m+1];

        for(int i=0;i<n;i++) {
            left[i] = A[p+i];
        }
        for(int j=0;j<m;j++){
            right[j]= A[q+j+1];
        }
        int i=0;
        int j=0;
        left[n] = Integer.MAX_VALUE;
        right[m] = Integer.MAX_VALUE;

        for(int k=p;k<=r;k++){
            if (left[i] <= right[j]) {
                A[k] = left[i++];
            }
            else {
                A[k] = right[j++];
            }
        }
    }

    private static void scratchMergeSort(int[] A, int[] scratchSpace, int p, int r) {
        if(p<r) {
            int q = (p+r)/2;
            scratchMergeSort(A,scratchSpace,p,q);
            scratchMergeSort(A,scratchSpace,q+1,r);
            scratchMerge(A,scratchSpace,p,q,r);
        }
    }

    private static void scratchMerge(int[] A, int[] scratchSpace, int p, int q, int r){
        //move all elements to the scratchspace
        for(int i=p;i<=r;i++) {
            scratchSpace[i] = A[i];
        }
        int n=p;//start of left array
        int m=q+1; //start of right array
        int i = p;
        while(n<=q && m<=r){
            if(scratchSpace[n] <= scratchSpace[m]) {
                A[i++] = scratchSpace[n++];
            }
            else {
                A[i++] = scratchSpace[m++];
            }
        }
        //put the remaining elements into A

        while(n<=q && i<=r) {
            A[i++] = scratchSpace[n++];
        }
        while(m<=r && i<=r) {
            A[i++] = scratchSpace[m++];
        }
    }
    //iterative version
    private static void iterativeMergeSort(int[] A, int[] scratchSpace) {
        int subLength;
        int r;//end of right array

        for(subLength=2;subLength<=A.length;subLength*=2){
            int subMid = subLength/2;
            for(r=subLength;r<=A.length;r+=subLength){
                scratchMerge(A,scratchSpace,r-subLength,r-subMid-1,r-1);//decrement 1 because we want to pass in indexes.
            }

            //edge cases
            if(r>A.length) { //check if there were elements left at the end
                if(r-subLength >= A.length){
                    continue;
                }
                int temp = r;
                r = A.length;
                int start = temp - (subLength*2);
                scratchMerge(A,scratchSpace,start,start+subLength,r-1);
            }
        }
        if(subLength>A.length && subLength/2<A.length) {
            subLength = subLength/2;
            r = A.length;
            scratchMerge(A,scratchSpace,0,subLength-1,r-1);
        }

    }


    //test functions

    static double MergeExperiment(int[] A, MergeFunctions mergeFunction) {
        int numRuns = 5;
        long totalMillis = 0;
        int [] CopyA = new int[A.length];
        for (int i = 0; i < numRuns; ++i)
        {
            long startMillis = 0;
            System.arraycopy(A, 0, CopyA, 0, A.length);
            if(mergeFunction == MergeFunctions.SIMPLE) {
                startMillis = System.currentTimeMillis();
                simpleMergeSort(A,0,A.length-1);
            }
            else if(mergeFunction == MergeFunctions.MEMALLOC) {
                startMillis = System.currentTimeMillis();
                int[] scratchSpace = new int[A.length];
                scratchMergeSort(A,scratchSpace,0,A.length-1);
            }
            else if(mergeFunction == MergeFunctions.ITERATIVE) {
                startMillis = System.currentTimeMillis();
                int[] scratchSpace = new int[A.length];
                iterativeMergeSort(A,scratchSpace);
            }
            totalMillis += (System.currentTimeMillis() - startMillis);
        }

        return (((float)totalMillis)/numRuns)/1000.0;
    }


    static void TestMerge() {
        System.out.println("Timing Select");

        System.out.printf("%15s %8s %8s %8s%n \n", "n", "Simple", "MemAlloc", "Iterative");

        for (int i = 1; i > 0 && i < 200000000; i=i*2) {
            int[] A = GenerateA(i);
            System.out.printf("%15d ", i);
            double timeSimple = MergeExperiment(A, MergeFunctions.SIMPLE);
            double timeMemAlloc = MergeExperiment(A, MergeFunctions.MEMALLOC);
            double timeIterative = MergeExperiment(A, MergeFunctions.ITERATIVE);
            System.out.printf("%8.2f %8.2f %8.2f \n", timeSimple, timeMemAlloc, timeIterative);
        }
    }

    //helper test function to test all merge sort functions
    static boolean test() {
        final int SIZE = rand.nextInt(250) + 1;
        System.out.printf("Testing array with size: %d \n",SIZE);
        int[] testArray = GenerateA(SIZE);

        //test simple merge sort
        simpleMergeSort(testArray,0,SIZE-1);
        for(int i=0;i<SIZE;i++) {
            if(testArray[i] != i) {
                System.out.println("Simple mergesort failed.");
                return false;
            }
        }

        RandomlyPermute(testArray);
        int[] scratchSpace = new int[SIZE];
        //test 1 mem alloc merge sort
        scratchMergeSort(testArray,scratchSpace,0,SIZE-1);
        for(int i=0;i<SIZE;i++) {
            if(testArray[i] != i) {
                System.out.println("MemAlloc mergesort failed.");
                return false;
            }
        }

        RandomlyPermute(testArray);
        scratchSpace = new int[SIZE];
        iterativeMergeSort(testArray,scratchSpace);
        for(int i=0;i<SIZE;i++) {
            if(testArray[i] != i) {
                System.out.println("Iterative mergesort failed.");
                return false;
            }
        }
         return true;
    }

    public static void main(String[] args) {
        //simple merge test

        if (args.length > 0) {
            if (args[0].equals("--test")) {
                boolean testResult = test();
                if(testResult) {
                    System.out.println("All mergesort tests have passed.");
                }
            } else if (args[0].equals("--time")) {
                TestMerge();
            } else {
                System.out.print("Unrecognized command: ");
                System.out.println(args[0]);
            }
        }
    }

}
