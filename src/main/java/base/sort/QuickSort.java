package base.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by mwjsolar on 17/3/15.
 */
public class QuickSort {
    public void sort(int[] beSortArray) {
        if (beSortArray == null || beSortArray.length == 0)
            return;

        sort(beSortArray,0,beSortArray.length-1);
    }

    private void  sort(int[] beSortArray,int start,int end) {
        if (start >= end)
            return;

        int i = start;
        int j = end;
        //选择基准对象
        int selectedBase = beSortArray[i];
        while (i < j) {
            while (beSortArray[i] <= selectedBase
                    && i < j) {i++;}

            selectedBase = beSortArray[i];
            while (beSortArray[j] >= selectedBase
                    && j > i) {j--;}
            beSortArray[i]  = beSortArray[j];
        }

        beSortArray[j] = selectedBase;
        sort(beSortArray,start,i-1);
        sort(beSortArray,i+1,end);
    }

    private void swap(int[] besort,int a,int b) {
        int temp = besort[a];
        besort[a] = besort[b];
        besort[b] = temp;
    }

    @Test
    public void testSort() {
        int[] array = new int[]{1,7,2,3,8};
        sort(array);
        System.out.println(Arrays.asList(1,7,2,3,8));
    }
}
