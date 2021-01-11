import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Measurements {
    static FibonacciHeap fibonacciHeap=new FibonacciHeap();
    static int M10 = (int) Math.pow(2.0, 10.0);
    static int M11 = (int) Math.pow(2.0, 11.0);
    static int M12 = (int) Math.pow(2.0, 12.0);
    public static void main(String[] args) {
        main2(3000);
    }

    private void main1(){
        FibonacciHeap.HeapNode[] m=new FibonacciHeap.HeapNode[13]; //here
        long Start=System.nanoTime();
        int size = M12; //here
        int count = 0;
        List<Integer> list = new LinkedList<>();
        for (int i=0; i<12; i++){ //here
            double sigma = 0;
            for (int j=1; j<i+1; j++){
                sigma += Math.pow(0.5, j);
            }
            sigma = (size*sigma) + 2;
            list.add((int) sigma);
        }
        list.add((int)(size - 1));
        for (int i=(int)size; i>=0; i--){
            if (list.contains(i)){
                m[count] = fibonacciHeap.insert(i);
                count++;
            }
            else {
                fibonacciHeap.insert(i);
            }
        }
        fibonacciHeap.deleteMin();
        for (FibonacciHeap.HeapNode node : m){
            fibonacciHeap.decreaseKey(node, size+1);
        }
        prints(Start);
    }

    public static void main2(int m){
        long Start=System.nanoTime();
        fibonacciHeap = new FibonacciHeap();
        for (int i=m; i>=1; i--){
            fibonacciHeap.insert(i);
        }
        for (int i=0; i<(m/2); i++){
            fibonacciHeap.deleteMin();
        }
        prints(Start);
    }

    private static void prints(long Start) {
        System.out.println((System.nanoTime()-Start)/(double)1000000);
        System.out.println(FibonacciHeap.totalCuts()+" cuts");
        System.out.println(FibonacciHeap.totalLinks()+" links");
        System.out.println(fibonacciHeap.potential()+" potential");

    }
    static void Reverseinsert(FibonacciHeap.HeapNode[] m) {
        for (int i=m.length-1;i>0;i--) {
            m[i]=fibonacciHeap.insert(i);
        }
    }
}


/*    public static void main2(String[] args) {
        long Start=System.nanoTime();
        FibonacciHeap.HeapNode[] m=new FibonacciHeap.HeapNode[1001];
        Reverseinsert(m);
        for (int i=0;i<m.length/2;i++) {
            fibonacciHeap.deleteMin();
        }
        prints(Start);
    }*/

        /*Reverseinsert(m);
        m[0]=fibonacciHeap.insert(0);
        fibonacciHeap.deleteMin();
        for (int i=0;i<Math.log(m.length)-1;i++) {
            fibonacciHeap.decreaseKey(m[(int)((m.length-1)*(1-Math.pow(0.5,i+1)))+2],m.length);
        }
        fibonacciHeap.decreaseKey(m[m.length-2],1);*/