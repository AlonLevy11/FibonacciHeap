import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NewTest {

    static Heap heap;
    static FibonacciHeap fibonacciHeap = new FibonacciHeap();
    FibonacciHeap.HeapNode node;

    public static void main(String[] args) {
/*        String test = "test9";
        heap = new Heap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 2700; i > 2200; i--) {
            System.out.println("i= " + i);
            System.out.println("fibo " + fibonacciHeap.findMin().getKey() + ", heap: " + heap.findMin());
            System.out.println("fibo " + fibonacciHeap.size() + ", heap: " + heap.size());
            if (i==2556){
                System.out.println("Here");
            }
            if (heap.findMin() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                return;
            }
            heap.delete(i);
            fibonacciHeap.delete(nodes.get(i - 2000));
        }*/
/*        FibonacciHeap.HeapNode[] arrOfNodes = new FibonacciHeap.HeapNode[7];
        for (int i=1; i<6; i++){
            arrOfNodes[i] = fibonacciHeap.insert(i);
        }
        printHeapElad.printHeapFib(fibonacciHeap);*/

        for (int i=0; i<17; i++){
            fibonacciHeap.insert(i);
        }
        printHeapElad.printHeapFib(fibonacciHeap);
        fibonacciHeap.deleteMin();
        printHeapElad.printHeapFib(fibonacciHeap);
        int[] result = FibonacciHeap.kMin(fibonacciHeap, 6);
    }


    static void addKeys(int start) {
        for (int i = 0; i < 1000; i++) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }
    }

    static void addKeysReverse(int start) {
        for (int i = 999; i >= 0; i--) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }
    }
}


