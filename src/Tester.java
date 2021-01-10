import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collections;

public class Tester {

    static Heap heap;
    static FibonacciHeap fibonacciHeap = new FibonacciHeap();
    static double grade;
    static double testScore;

    public static void main(String[] args) {
/*        int cuts = 0;
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
        printHeapElad.printHeapFib(heap);*/

//FibonacciHeap Tester

/*        String test = "test10";
        heap = new Heap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        heap.deleteMin();
        fibonacciHeap.deleteMin();
        System.out.println("before for");

        for (int i = 2700; i > 2524; i--) {
            if (heap.findMin() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                return;
            }
            System.out.println("delete: " + i);
            printHeapElad.printHeapFib(fibonacciHeap);
            heap.delete(i);
            fibonacciHeap.delete(nodes.get(i - 2000));
        }
        int i = 2524;
        heap.delete(i);
        fibonacciHeap.delete(nodes.get(i - 2000));
        printHeapElad.printHeapFib(fibonacciHeap);

        while (!heap.empty()) {
            if (heap.findMin() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }*/
/*        System.out.println("before if isEmpty");
        if (!fibonacciHeap.isEmpty())*/
/*            System.out.println("not good 2 -------------------------------------");
System.out.println("heap min: " + heap.findMin() + "fiboHeap min: " + fibonacciHeap.findMin().getKey());
System.out.println("heap size: " + heap.size() + "fiboHeap size: " + fibonacciHeap.size());*/

/*        FibonacciHeap.HeapNode[] arr = new FibonacciHeap.HeapNode[8];
        int j = 0;
        for (int i : new int[] {2945, 2946, 2947, 2948, 2949, 2950, 2951}){
            FibonacciHeap.HeapNode node = fibonacciHeap.insert(i);
            arr[j] = node;
            j += 1;
        }

        FiboHeapPrinter.printHeap(fibonacciHeap);
        System.out.println("delete: " + arr[0].getKey());
        fibonacciHeap.delete(arr[0]);
        FiboHeapPrinter.printHeap(fibonacciHeap);
        System.out.println("delete: " + arr[1].getKey());
        fibonacciHeap.delete(arr[1]);
        FiboHeapPrinter.printHeap(fibonacciHeap);
        System.out.println("delete: " + arr[2].getKey());
        fibonacciHeap.delete(arr[2]);
        FiboHeapPrinter.printHeap(fibonacciHeap);*/

        String test = "test17";
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        System.out.println("potential: " + fibonacciHeap.potential());
        System.out.println("cuts: " + FibonacciHeap.totalCuts);
        System.out.println("links: " + FibonacciHeap.totalLinks());
        System.out.println("counters Rep 1: " + fibonacciHeap.countersRep()[0]);
        System.out.println(Arrays.toString(fibonacciHeap.countersRep()));
        //System.out.println("counters Rep 1: " + fibonacciHeap.countersRep()[1]);
        if (fibonacciHeap.potential() != 1 ||
                FibonacciHeap.totalCuts() - cuts != 0 ||
                FibonacciHeap.totalLinks() - links != 1 ||
                fibonacciHeap.countersRep()[0] != 0 ||
                fibonacciHeap.countersRep()[1] != 1)
            bugFound(test);


    }
//delete: 2524

    static void bugFound(String test) {
        System.out.println("Bug found in " + test);
        grade -= testScore;
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





