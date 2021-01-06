import java.util.Arrays;

public class Tester {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        printHeapElad.printHeapFib(heap);
        heap.deleteMin();
        printHeapElad.printHeapFib(heap);
    }
}
