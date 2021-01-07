/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
    static int totalCuts = 0;
    static int totalLinks = 0;

    HeapNode first = null;
    HeapNode min = null;
    int numOfTrees = 0;
    int totalNumberOfMarked = 0;
    int size = 0;

    public int getNumOfTrees(){
        return this.numOfTrees;
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

    private void addLinkToCount(){
        totalLinks += 1;
    }

    private void addMarkToCount(){
        this.totalNumberOfMarked += 1;
    }

    private void reduceTreeFromCount(){
        this.numOfTrees -= 1;
    }

    private void reduceCutFromCount(){
        totalCuts -= 1;
    }

    private void reduceLinkFromCount(){
        totalLinks -= 1;
    }

    private void reduceMarkFromCount(){
        this.totalNumberOfMarked -= 1;
    }

    private void addToSize(){
        this.size += 1;
    }

    private void reduceFromSize(){
        this.numOfTrees -= 1;
    }

    public HeapNode getFirst(){
        return this.first;
    }

    public void swapMark(HeapNode node) {
        if (!(node.isRoot())) {
            if (node.mark) {
                node.mark = false;
                this.totalNumberOfMarked -= 1;
            }
            else {
                node.mark = true;
                this.totalNumberOfMarked += 1;
            }
        }
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
        this.addTreeToCount();
        this.addToSize();
        if (this.first == null){
            this.first = node;
            this.min = node;
            node.setPrev(node);
            node.setNext(node);
        }
        else {
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
    public void deleteMin() {
        if (this.min == null) { //heap is empty
            return;
        }
        //heap isn't empty:
        this.numOfTrees -= 1;
        this.size -= 1;
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
            } else { //minimum has brothers
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
        minimum.setPrev(null);
        minimum.setNext(null);
        minimum.setChild(null);
        this.successiveLinking();
    }



    /**
     * Removes node from the list its in
     * marks parent
     * add to totalNumberOfMarked
     * connects parent and child if necessary
     * complexity: O(1)
     * */
    private void cutNodeFromList(HeapNode node){
        if (!node.isRoot()) { //node is not root
            if (node.next.equals(node)) { //node has no brothers
                node.parent.setChild(null);
            } else {
                totalNumberOfMarked += 1;
                if (node.parent.child.equals(node)) {
                    node.parent.setChild(node.next);
                }
                node.prev.setNext(node.next);
                node.next.setPrev(node.prev);
                node.setParent(null);
            }
        }
        else { //node is root
            if (node.next.equals(node)) { //node has no brothers
                this.reduceFromSize();
                this.reduceTreeFromCount();
                this.first = null;
                this.min = null;
            } else { //node has brothers
                node.prev.setNext(node.next);
                node.next.setPrev(node.prev);
                node.setParent(null);
            }
        }
    }

    /**
     * Complexity: WC - O(n), amortized - O(logn)
     * needed for deletion
     * @post - maximum of logn trees in the heap
     * */
    private void successiveLinking(){
        if (this.isEmpty()){
            return;
        }
        int sizeOfArr = (int) Math.floor(logBase2(this.size)) + 1;
        HeapNode[] helpArr = new HeapNode[sizeOfArr];
        HeapNode starterFirst = this.first;
        helpArr[this.first.getRank()] = this.first;
        HeapNode curr = this.first.next;
        while (!(curr.equals(starterFirst))){ //Didn't finish the full circle
            int rank = curr.getRank();
            HeapNode nextNode = curr.next;
            if (helpArr[rank] != null){
                HeapNode nodeToLink = helpArr[curr.getRank()];    //how is linking done between trees with deleted nodes?
                helpArr[rank] = null;
                HeapNode linked = this.link(curr, nodeToLink);
                helpArr[rank+1] = linked;
            }
            else {
                helpArr[rank] = curr;
            }
            curr = nextNode;
        }
        boolean firstFound = false;
        HeapNode minimum;
        for (int i=0; i<helpArr.length; i++){
            if (helpArr[i] != null){
                if (!firstFound){
                    curr = helpArr[i];
                    this.min = curr;
                    this.first = helpArr[i];
                    firstFound = true;
                    continue;
                }
                if (helpArr[i].getKey() < this.min.getKey()){
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
        if (node1.mark){
            node1.setMark(false);
            totalNumberOfMarked -= 1;
        }
        node1.setRank(node1.getRank()+1);
        node1.setPrev(node1);
        node1.setNext(node1);
        return node1;
    }


   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return this.min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
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
    public int size()
    {
    	return this.size; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep() {
        if (this.size == 1) {
            int[] res = new int[1];
            res[0] = this.first.getRank();
            return res;
        } else {
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

    public static double logBase2(double d){
        return (Math.log(d)/ Math.log(2));
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {
        int delta = x.getKey() - Integer.MIN_VALUE;
        decreaseKey(x, delta);
        this.min = x;
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
        if (x.isRoot()) {  // x is root
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
        if (!parent.mark){
            this.cut(node);
        }
        else {
            HeapNode next = node;
            while ((!(node.parent.isRoot())) && node.parent.mark){
                next = node.parent;
                this.cut(node);
                node = next;
            }
        }
    }

    public void cut(HeapNode node){
        totalCuts += 1;
        this.addTreeToCount();
        node.parent.setRank(node.parent.getRank()-1);
        cutNodeFromList(node);
        node.setNext(this.first);
        node.setPrev(this.first.prev);
        this.first.prev.setNext(node);
        this.first.setPrev(node);
        this.first = node;
        this.addTreeToCount();
        if (node.getKey() < this.min.getKey()){
            this.min = node;
        }
        node.setMark(false);
        reduceMarkFromCount();
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() {
        return (this.size + (2 * this.totalNumberOfMarked));
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
        public int key;

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
