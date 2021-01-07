import java.util.Arrays;

public class Tester {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        int cuts = 0;
        int links = 0;

        heap.insert(4);
        heap.insert(5);
        FibonacciHeap.HeapNode node = heap.insert(6);
        heap.deleteMin();

        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.deleteMin();

        heap.insert(1);
        heap.deleteMin();

        heap.decreaseKey(node, 2);

        if (heap.potential() != 4 ||
                FibonacciHeap.totalCuts() - cuts != 1 ||
                FibonacciHeap.totalLinks() - links != 3)
            System.out.println("prob");
        System.out.println("Potential: " + heap.potential());
        System.out.println("Cuts: " + FibonacciHeap.totalCuts());
        System.out.println("Links: " + FibonacciHeap.totalLinks());
        System.out.println("Marks: " + heap.getNumberOfMarked());
        System.out.println("Trees: " + heap.getNumOfTrees());
        printHeapElad.printHeapFib(heap);
    }
}

