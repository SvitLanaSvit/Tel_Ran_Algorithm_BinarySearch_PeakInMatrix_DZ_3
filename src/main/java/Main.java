import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------------1---------------");
        System.out.println("--------O(n)---------");
        //System.out.println(powRecursion(2, 4));
        long starTime1 = System.nanoTime();
        System.out.println(myPowRecursion(2, 4));
        long stopTime1 = System.nanoTime();
        System.out.println("--------O(log n)---------");
        long starTime2 = System.nanoTime();
        System.out.println(myPow(2,4));
        long stopTime2 = System.nanoTime();

        System.out.println("O(n):\t\t" + (stopTime1 - starTime1));
        System.out.println("O(log n):\t" + (stopTime2 - starTime2));

        System.out.println("\n-------------2---------------");
        int[] arr1 = {100, 112, 256, 349, 770};
        int[] arr2 = {72, 86, 113, 119, 265, 445, 892};
        int[] res = merge(arr1, arr2);
        System.out.println(Arrays.toString(res));
        int val = 256;
        int index = binarySearch(res, 256, 0, res.length - 1);
        System.out.println(val + " is " + index + "th element of array");
        showPlaceOfElementOfArray(res, 7);

        System.out.println("\n-------------3---------------");
        int[] arr = {1, 1, 2, 2, 2, 2, 3,};
        int x = 2;
        int count = countOccurrences(arr, arr.length, x);
        System.out.println("The number '" + x + "' occurs in massive: " +count);

        System.out.println("\n-------------4---------------");
        int[][] arr4 = {{10, 20, 15},{21, 30, 14}, {7, 16, 32}};
        int[] res4 = findPeakIn2DMassive(arr4);
        System.out.println("Peak element is at index: " + res4[0] + " , " + res4[1]);
        System.out.println("Peak element is : " + arr4[res4[0]][res4[1]]);
    }

//    static public double powRecursion(int x, int n){
//        if(n >= 0) {
//            if (n != 0)
//                return x * powRecursion(x, n - 1);
//            return 1;
//        }
//        else {
//            if(n == -1) return 1.0 / x;
//            return 1.0 / (x * powRecursion(x, (-n) - 1));
//        }
//    }

    //1-------------------------------------------
    // O(n)--------
    static public double myPowRecursion(int x, int n){
        if(n < 0)
        {
            n = -n;
            return 1 / powRecursion(x,n);
        }
        return powRecursion(x,n);
    }
    static public double powRecursion(int x, int n){
        if (n != 0)
            return x * powRecursion(x, n - 1);
        return 1;
    }

    // O(Log n)-------
    static public double myPow(int x, int n){
        if(n < 0)
        {
            n = -n;
            return 1 / pow(x,n);
        }
        return pow(x,n);
    }
    static public double pow(int x, int n){
        double half;
        if(n == 0)
            return 1;
        half = pow(x, n / 2);
        if(n % 2 == 0)
            return  half * half;
        else return half * half * x;
    }

    //2-------------------------------------------

    public static int[] merge(int[] arr1, int[] arr2){
        int i = 0, j = 0;
        int n = arr1.length;
        int m = arr2.length;
        int[] result = new int[n + m];
        for(; i < n && j < m;){
            if(arr1[i] <= arr2[j]){
                result[i+j] = arr1[i];
                i++;
            }
            else{
                result[i+j] = arr2[j];
                j++;
            }
        }
        for (int k = i; k < n; k++){
            result[k+i] = arr1[k];
        }
        for (int k = j; k < m; k++){
            result[k+i] = arr2[k];
        }
        return  result;
    }
    //O(log n)
    public static int binarySearch(int[] arr, int key, int start, int end){
        int middle = (start + end) / 2;
        if(end < start) return -1;
        if(key < arr[middle]) return binarySearch(arr, key, start, end - 1);
        if(key > arr[middle]) return binarySearch(arr, key, middle + 1, end);
        if(key == arr[middle]) return middle + 1;
        return -1;
    }
    public static void showPlaceOfElementOfArray(int[] arr, int number){
        if(number > 0 && number < arr.length)
            System.out.println(number + "th of array is " + arr[number - 1]);
    }

    //3-------------------------------------------
    //Time Complexity : O(Log n + count) where count is number of occurrences.
    //Space Complexity: O(log n), due to recursive stack space
    public static int binarySearch3(int[] arr, int key, int start, int end){
        int middle = (start + end) / 2;
        if(end < start) return -1;
        if(key < arr[middle]) return binarySearch(arr, key, start, end - 1);
        if(key == arr[middle]) return middle;
        return binarySearch(arr, key, middle + 1, end);
    }
    public static int countOccurrences(int[] arr, int end, int x){
        int index = binarySearch3(arr, x,0, end - 1);
        if(index == -1) return 0;

        int count = 1;

        int left = index - 1;
        while(left >= 0 && arr[left] == x){
            count++;
            left--;
        }

        int right = index + 1;
        while(right < end && arr[right] == x){
            count++;
            right++;
        }
        return count;
    }

    //4-------------------------------------------
    //Time Complexity: O(rows * columns)
    //Auxiliary Space: O(1)
    static int[] findPeakIn2DMassive(int[][] matrix){
        int startCol = 0, endCol = matrix[0].length - 1;
        while(startCol <= endCol){
            int middleCol = (startCol + endCol) / 2;
            
            int ansRow = 0;

            for (int i = 0; i < matrix.length; i++) {
                ansRow = matrix[i][middleCol] >= matrix[ansRow][middleCol] ? i : ansRow;
            }

            boolean validLeft = middleCol - 1 >= startCol && matrix[ansRow][middleCol - 1] > matrix[ansRow][middleCol];
            boolean validRight = middleCol + 1 <= endCol && matrix[ansRow][middleCol + 1] > matrix[ansRow][middleCol];

            if (!validLeft && !validRight) {
                return new int[] {ansRow, middleCol};
            }
            else if(validRight){
                startCol = middleCol + 1;
            }
            else{
                endCol = middleCol - 1;
            }
        }
        return new int[] {-1, -1};
    }
}
