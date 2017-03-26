import java.util.Arrays;

/**
 * Created by Matt on 2017-03-23.
 */
public class MergeSort {


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

    private static void iterativeMergeSort(int[] A, int[] scratchSpace) {
        
    }

    public static void main(String[] args) {
        //simple merge test
        int[] testArray = {4, 3, 7, 8, 1, 0, 9, 2, 6, 5};
        System.out.println(Arrays.toString(testArray));
        simpleMergeSort(testArray, 0, 9);
        System.out.println(Arrays.toString(testArray));
        //one mem alloc merge test
        int[] testArray2 = {4, 3, 7, 8, 1, 0, 9, 2, 6, 5};
        int[] scratchSpace = new int[testArray2.length];
        System.out.println(Arrays.toString(testArray2));
        scratchMergeSort(testArray2,scratchSpace,0,9);
        System.out.println(Arrays.toString(testArray2));

    }

}
