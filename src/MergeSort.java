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
        int n = q-p+1;
        //System.out.printf("this is p: %d",p);
        //System.out.printf("this is q: %d\n",q);
        //System.out.printf("this is r: %d \n",r);
        //System.out.printf("this is n: %d\n", n);
        int m = r-q;
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
            /*System.out.printf("This is i: %d\n",i);
            System.out.printf("This is j: %d\n",j);
            System.out.printf("left: %s \n",Arrays.toString(left));
            System.out.printf("right: %s \n",Arrays.toString(right));*/
            if (left[i] <= right[j]) {
                A[k] = left[i++];
            } else {
                A[k] = right[j++];
            }
        }
    }

    private static void oneArrayMergeSort(int[] A,int p, int r) {
        if(p<r) {
            int q = (p+r)/2;
            oneArrayMergeSort(A,p,q);
            oneArrayMergeSort(A,q+1,r);
            oneArrayMerge(A,p,q,r);
        }
    }

    private static void oneArrayMerge(int[] A, int p, int q, int r){

    }

    public static void main(String[] args){
        int[] testArray = {4,3,7,8,1,0,9,2,6,5};
        System.out.println(Arrays.toString(testArray));
        simpleMergeSort(testArray,0,9);
        System.out.println(Arrays.toString(testArray));
    }

}
