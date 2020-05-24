public class Test {

    public static void main(String[] args){
        int[][] A = new int[4][2];
        A[0][0] = 0;
        A[0][1] = 2;
        A[1][0] = 5;
        A[1][1] = 10;
        A[2][0] = 13;
        A[2][1] = 23;
        A[3][0] = 24;
        A[3][1] = 25;

        int[] s1 = new int[A.length * 2];

        for(int i = 0; i < A.length; ++i){
            for(int j = 0; j < 2; ++j){
                s1[i + j] = A[i][j];
                System.out.print(s1[i+j] + " ");
            }
        }
        for(int i = 0; i < s1.length; ++i){
            System.out.println("i" + i + " " + s1[i]);
        }

    }
}
