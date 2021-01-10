/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
    static int totalCuts = 0; //total cuts in run
    static int totalLinks = 0; //total links in run

    private HeapNode first = null;
    private HeapNode min = null;
    private int numOfTrees = 0;
    private int totalNumberOfMarked = 0;
    private int size = 0;

    public int getNumOfTrees(){
        return this.numOfTrees;
    }

    public int getNumberOfMarked(){
        return totalNumberOfMarked;
    }

    private void addTreeToCount(){
        this.numOfTrees += 1;
    }

    private void addCutToCount(){
        totalCuts += 1;
    }

    public FibonacciHeap(){}

    public FibonacciHeap(HeapNode first, int size){
        this.first = first;
        this.size = size;
        this.first.setNext(this.first);
        this.first.setPrev(this.first);
        this.min = first;
        this.numOfTrees = 1;
    }

    /**
     * returns the first node in the heap
     * Complexity: O(1)
     * */
    public HeapNode getFirst(){
        return this.first;
    }


   /**
    * public boolean isEmpty()
    *
    * precondition: none
    *
    * The method returns true if and only if the heap
    * is empty.
    *
    */
    public boolean isEmpty() //Complexity: O(1)
    {
    	return (this.first ==null);
    }

   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    *
    * Returns the new node created.
    */
    public HeapNode insert(int key){ //Complexity: O(1)
        HeapNode node = new HeapNode(key);
        this.numOfTrees += 1;
        this.size += 1;
        if (this.first == null){ //if heap is empty
            this.first = node;
            this.min = node;
            node.setPrev(node);
            node.setNext(node);
        }
        else { //heap is not empty
            node.setNext(this.first);
            node.setPrev(this.first.prev);
            this.first.prev.setNext(node);
            this .first.setPrev(node);
            this.first = node;
            if (node.key < this.min.key){
                this.min = node;
            }
        }
        return node;
    }


   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin() { //Complexity: WC - O(n), Amortized - O(logn)
        if (this.min == null) { //heap is empty
            return;
        }
        //heap isn't empty:
        HeapNode minimum = this.min;
        if (this.size == 1) { //heap contains one node
            this.min = null;
            this.first = null;
            this.totalNumberOfMarked = 0;
            minimum.setPrev(null);
            minimum.setNext(null);
            minimum.setChild(null);
        } else { //heap contains more than one node
            if (this.min.equals(this.min.next)) { //heap contains 1 Fibonacci tree and minimum has children
                this.numOfTrees += this.min.rank;
                this.first = this.min.child;
                this.first.setParent(null);
            } else { //minimum has brothers
                if (this.first.equals(this.min)){ //if min is first
                    this.first = this.min.next;
                }
                //this.first = this.min.next;  ??????????
                if (this.min.child == null) { //min has no children
                    minimum.prev.setNext(minimum.next);
                    minimum.next.setPrev(minimum.prev);
                } else { //minimum has children
                    this.numOfTrees += min.rank;
                    HeapNode child = minimum.child;
                    HeapNode lastChild = child.prev;
                    minimum.prev.setNext(child);
                    child.setPrev(minimum.prev);
                    minimum.next.setPrev(lastChild);
                    lastChild.setNext(minimum.next);
                    if (this.min.equals(this.first)) {
                        this.first = child;
                    }
                }
            }
        }
        this.numOfTrees -= 1;
        this.size -= 1;
        this.min = this.first; //just so min isn't null - min is updated after successive linking
        minimum.setPrev(null);
        minimum.setNext(null);
        minimum.setChild(null);
        this.successiveLinking();
    }

    /**
     * Complexity: WC - O(n), amortized - O(logn)
     * needed for deletion
     * @post - maximum of logn trees in the heap
     * */
    private void successiveLinking(){
        if (this.isEmpty() || numOfTrees==1) return; //nothing needs to be done
        int sizeOfArr = (int) Math.floor(logBase2(this.size)) + 1;
        HeapNode[] helpArr = new HeapNode[sizeOfArr];
        HeapNode starterFirst = this.first; //saves the first node to know when full circle is completed
        if (starterFirst.mark){
            starterFirst.setMark(false);
            this.totalNumberOfMarked -= 1;
        }
        helpArr[this.first.getRank()] = this.first;
        HeapNode curr = this.first.next;
        while (!(curr.equals(starterFirst))){ //Didn't finish the full circle
            if (curr.mark){
                curr.setMark(false);
                this.totalNumberOfMarked -= 1;
            }
            int rank = curr.getRank();
            HeapNode nextNode = curr.next;
            //System.out.println(nextNode.getKey());
            if (helpArr[rank] != null){ //if index is occupied
                HeapNode nodeToLink = helpArr[rank];
                helpArr[rank] = null;
                HeapNode linked = this.link(curr, nodeToLink);
                int steps = 1;
                while ((rank+steps < helpArr.length-1) && (helpArr[rank+steps] != null)){ //go forward until index is empty
                    linked = this.link(linked, helpArr[rank+steps]);
                    helpArr[rank+steps] = null;
                    steps += 1;
                }
                helpArr[rank+steps] = linked;
            }
            else { //index is empty
                helpArr[rank] = curr; //occupy
                curr.setParent(null);
                curr.setPrev(null);
                curr.setNext(null);
            }
            curr = nextNode;
        }

        boolean firstFound = false;
        HeapNode minimum;
        for (int i=0; i<helpArr.length; i++){ //goes over the array and connecting the trees
            if (helpArr[i] != null){
                if (helpArr[i].mark){
                    helpArr[i].setMark(false);
                    this.totalNumberOfMarked -= 1;
                }
                if (!firstFound){
                    curr = helpArr[i];
                    this.min = curr;
                    this.first = helpArr[i];
                    firstFound = true;
                    continue;
                }
                if (helpArr[i].getKey() < this.min.getKey()){ //defines the minimum
                    this.min = helpArr[i];
                }
                curr.setNext(helpArr[i]);
                helpArr[i].setPrev(curr);
                curr = helpArr[i];
            }
        }
        this.first.setPrev(curr);
        curr.setNext(this.first);
    }

    /**
     * Links two Fibonacci trees
     * Complexity: O(1)
     * needed for successiveLinking
     * @post - maximum of logn trees in the heap
     * */
    public HeapNode link(HeapNode node1, HeapNode node2){
        if (node1.getKey() > node2.getKey()){  //making sure that node 1 is always smaller
            return link(node2, node1);
        }
        totalLinks += 1;
        this.numOfTrees -= 1;

        if (true){
            if (node1.child==null){ //linking two rank0 trees
                node1.setChild(node2);
                node2.setParent(node1);
                node2.setPrev(node2);
                node2.setNext(node2);
            }
            else {
                node2.setPrev(node1.child.prev);
                node2.setNext(node1.child);
                node1.child.prev.setNext(node2);
                node1.child.setPrev(node2);
                node1.setChild(node2);
                node2.setParent(node1);
            }
        }
        node1.setRank(node1.getRank()+1);
        node1.setPrev(node1);
        node1.setNext(node1);
        node1.setParent(null);
        return node1;
    }


   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal.
    *
    */
    public HeapNode findMin() //Complexity: O(1)
    {
    	return this.min;
    }

   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2) //Complexity: O(1)
    {
        HeapNode heap2First = heap2.first;
        HeapNode heap2Last = heap2.first.prev;
        this.first.prev.setNext(heap2First);
        heap2First.setPrev(this.first.prev);
        this.first.setPrev(heap2Last);
        heap2Last.setNext(this.first);
        if (this.min.getKey() > heap2.min.getKey()){
            this.min = heap2.min;
        }
        this.numOfTrees += heap2.numOfTrees;
        this.size += heap2.size;
        heap2 = this;
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *
    */
    public int size() //Complexity: O(1)
    {
    	return this.size;
    }

    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
    *
    */
    public int[] countersRep() { //Complexity: O(n)
        if (this.size==0){
            int[] res = {};
            return res;
        }
        else {
            int[] arr = new int[(int) Math.floor(logBase2(this.size)) + 1];
            arr[this.first.getRank()] += 1;
            HeapNode tmpNode = this.first.next;
            while (!(tmpNode.equals(this.first))) {
                arr[tmpNode.rank] += 1;
                tmpNode = tmpNode.next;
            }
            return arr;
        }
    }

    /**
     * calculates log base 2
     * Complexity: O(1)
     * */
    public static double logBase2(double d){
        return (Math.log(d)/ Math.log(2));
    }

   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
    *
    */
    public void delete(HeapNode x) //Complexity: WC - O(n), Amortized - O(logn) Todo!!! check
    {
        if (x.mark){
            x.setMark(false);
            this.totalNumberOfMarked -= 1;
        }
        int delta = x.getKey() - Integer.MIN_VALUE;
        decreaseKey(x, delta);
        this.deleteMin();
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta);
        if (x.isRoot()) {
            if (x.getKey() < this.min.getKey()) {
                this.min = x;
                return;
            }
        } else if (x.getParent().getKey() <= x.getKey()) { //Order is uninterrupted
            return;
        } else { //Order interrupted
            cascadingCuts(x);
        }
    }

    public void cascadingCuts(HeapNode node){
        HeapNode parent = node.parent;
        HeapNode next = node;
        while ((!(node.isRoot())) && (node.parent.mark)){
            next = node.parent;
            this.cut(node);
            node = next;
        }

        if (!(node.isRoot()) && !(node.parent.mark)){
            if (!(node.parent.isRoot())){
                node.parent.setMark(true);
                this.totalNumberOfMarked += 1;
            }
            this.cut(node);
        }
    }

    public void cut(HeapNode node){
        node.parent.setRank(node.parent.getRank()-1);
        if (!node.parent.mark && (!node.parent.isRoot())){
            node.parent.setMark(true);
            this.totalNumberOfMarked += 1;
        }
        if (node.parent.child.equals(node)){
            if (node.next.equals(node)){
                node.parent.setChild(null);
            }
            else {
                node.parent.setChild(node.next);
            }
        }
        node.setParent(null);
        node.prev.setNext(node.next);
        node.next.setPrev(node.prev);
        if (node.mark){
            node.setMark(false);
            totalNumberOfMarked -= 1;
        }
        this.first.prev.setNext(node);
        node.setPrev(this.first.prev);
        this.first.setPrev(node);
        node.setNext(this.first);
        this.first = node;
        if (node.getKey() < this.min.getKey()){
            this.min = node;
        }
        this.numOfTrees += 1;
        totalCuts += 1;
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() {
        return (this.numOfTrees + (2 * this.totalNumberOfMarked));
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return totalLinks;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return totalCuts;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k minimal elements in a binomial tree H.
    * The function should run in O(k*deg(H)). 
    * You are not allowed to change H.
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        if (k==0){
            int[] res = {};
            return res;
        }
        FibonacciHeap heapOfMin = new FibonacciHeap();
        int[] arr = new int[k];
        arr[0] = H.min.getKey();
        int counter = 1;
        HeapNode curr = H.first;
        int limit = H.first.getKey();
        while (counter<k){
            HeapNode min1 = findMinBro(curr, limit);
            HeapNode min2 = findMinChild(curr, limit);
            heapOfMin.insert(min1.getKey());
            heapOfMin.insert(min2.getKey());
            curr = heapOfMin.min;
            arr[counter] = curr.getKey();
            counter += 1;
            heapOfMin.deleteMin();
            if (min1.getKey() < min2.getKey()){
                limit = min1.getKey();
            }
            else {
                limit = min2.getKey();
            }
        }
        return arr; // should be replaced by student code
    }

    private static HeapNode findMinBro(HeapNode node, int lim){
        HeapNode minNode = null;
        if (node.getKey() > lim){
            minNode = node;
        }
        HeapNode curr = node.next;
        while (!curr.equals(node)){
            if ((minNode.getKey() > curr.getKey()) && minNode.getKey() > lim){
                minNode = curr;
            }
            curr = curr.next;
        }
        return minNode;
    }

    private static HeapNode findMinChild(HeapNode node, int lim){
        return findMinBro(node.child, lim);
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{

        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;
        private Object val;
        private boolean mark;
        private int rank;
        private int key;

        public HeapNode(int key) {
            this.key = key;
            this.child = null;
            this.next = null;
            this.prev = null;
            this.parent = null;
            this.val = null;
            this.mark = false;
            this.rank = 0;
          }

        public int getKey() {
            return this.key;
          }

        public Object getValue(){
            return this.val;
        }

        public boolean getMark(){
            return this.mark;
        }

        public int getRank(){
            return this.rank;
        }

        public HeapNode getChild(){
            return this.child;
        }

        public HeapNode getNext(){
            return this.next;
        }

        public HeapNode getPrev(){
            return this.prev;
        }

        public HeapNode getParent(){
            return this.parent;
        }

       public void setKey(int key) {
           this.key = key;
       }

       public void setValue(Object val){
           this.val = val;
       }

       public void setMark(boolean mark){
           this.mark = mark;
       }

       public void setRank(int rank){
           this.rank = rank;
       }

       public void setChild(HeapNode child) {
           this.child = child;
       }

       public void setNext(HeapNode next){
           this.next = next;
       }

       public void setPrev(HeapNode prev){
           this.prev = prev;
       }

       public void setParent(HeapNode parent){
           this.parent = parent;
       }

       public boolean isRoot(){
            return (this.parent==null);
       }




    }
}
