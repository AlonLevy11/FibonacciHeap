import java.util.List;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
    HeapNode root = null;
    HeapNode min = null;
    int numOfTrees = 0;
    int totalNumberOfCuts = 0;
    int totalNumberOfMarked = 0;
    int totalNumberOfLinks = 0;
    int size = 0;

    private void addTreeToCount(){
        this.numOfTrees += 1;
    }

    private void addCutToCount(){
        this.totalNumberOfCuts += 1;
    }

    private void addLinkToCount(){
        this.totalNumberOfLinks += 1;
    }

    private void addMarkToCount(){
        this.totalNumberOfMarked += 1;
    }

    private void reduceTreeFromCount(){
        this.numOfTrees -= 1;
    }

    private void reduceCutFromCount(){
        this.totalNumberOfCuts -= 1;
    }

    private void reduceLinkFromCount(){
        this.totalNumberOfLinks -= 1;
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
    	return (this.root==null);
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
        if (this.root == null){
            this.root = node;
        }
        else {
            node.setNext(this.root);
            node.setPrev(this.root.prev);
            this.root.prev.setNext(node);
            this .root.setPrev(node);
            this.root = node;
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
    public void deleteMin()
    {
        if (this.min == null){
        }
     	else {
     	    HeapNode minimum = this.min;
     	    this.reduceTreeFromCount();
            this.reduceFromSize();
            this.numOfTrees += minimum.rank;
            HeapNode child = minimum.child;
            HeapNode lastChild = child.prev;
            minimum.prev.setNext(child);
            child.setPrev(minimum.prev);
            minimum.next.setPrev(lastChild);
            lastChild.setNext(minimum.next);
            if (this.min.equals(this.root)){
                this.root = child;
            }
            minimum.setPrev(null);
            minimum.setNext(null);
     	    this.successiveLinking();
            //Update min done by successive linking
        }
     	
    }

    /**
     * Complexity: O(?????????????????)
     * needed for deletion
     * @post - maximum of logn trees in the heap
     * */
    private void successiveLinking(){
        if (this.isEmpty()){
            return;
        }
        int sizeOfArr = (int) Math.floor(Math.log(this.size)) + 1;
        HeapNode[] helpArr = new HeapNode[sizeOfArr];
        HeapNode starterRoot = this.root;
        helpArr[this.root.getRank()] = this.root;
        HeapNode curr = this.root.next;
        while (!(curr.equals(starterRoot))){
            int rank = curr.getRank();
            HeapNode nextNode = curr.next;
            if (helpArr[rank] != null){
                HeapNode nodeToLink = helpArr[curr.getRank()];    //how is linking done between trees with deleted nodes?
                helpArr[rank] = null;
                HeapNode linked = link(curr, nodeToLink);
                helpArr[rank+1] = linked;
            }
            else {
                helpArr[rank] = curr;
            }
            curr = nextNode;
            //finish successive linking and link

        }
    }


   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return new HeapNode(0);// should be replaced by student code
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	  return; // should be replaced by student code   		
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return 0; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return 0; // should be replaced by student code
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
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return 0; // should be replaced by student code
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
        int[] arr = new int[42];
        return arr; // should be replaced by student code
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
