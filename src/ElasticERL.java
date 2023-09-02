import java.io.*;
import java.util.*;
class ElasticERLAVL{
    Node root;

    class Node{
        int key;
        String value;
        int height;
        Node left;
        Node right;

        Node(int key,String value,int height,Node left, Node right)
        {
            this.key=key;
            this.value=value;
            this.height=height;
            this.left=left;
            this.right=right;
        }
    }

    public ElasticERLAVL(int key,String value)
    {
        this.root = new Node(key,value,0,null,null);
    }
    public ElasticERLAVL()
    {
        this.root=null;
    }

    private int height(Node node) {
        if(node != null )
            return node.height ;

        return -1;
    }

    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private int balanceFactor(Node node) {
        return height(node.right) - height(node.left);
    }

    public boolean containsKey(Node root, int key) {
        if(root==null)
            return false;

        if(root.key==key)
            return true;

        if(root.key>key)
            return containsKey(root.left,key);
        else
            return containsKey(root.right,key);
    }

    LinkedLst result = new LinkedLst();
    public LinkedLst inorderTraversal(Node root) {
        if (root!=null) {
            inorderTraversal(root.left);
            result.add(root.key, root.value);
            inorderTraversal(root.right);
        }
        return result;
    }

    public void insertNode(int key,String value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, int key, String value) {
        if(node==null)
            return new Node(key,value,0,null,null);
        else if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
        } else {
            //node.values.add(value);    //logic for already exists node simply add value in existing list
        }

        updateHeight(node);

        return rebalance(node);
    }

    private Node rebalance(Node node) {
        int balanceFactor = balanceFactor(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (balanceFactor(node.left) <= 0) {
                // Rotate right
                node = rotateRight(node);
            } else {
                // Rotate left-right
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (balanceFactor(node.right) >= 0) {
                // Rotate left
                node = rotateLeft(node);
            } else {
                // Rotate right-left
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }
        return node;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;

        node.left = leftChild.right;
        leftChild.right = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;

        node.right = rightChild.left;
        rightChild.left = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    public void deleteNode(int key) {
        root = delete(key, root);

        updateHeight(root);

        rebalance(root);
    }

    Node delete(int key, Node node) {
        // No node at current position --> go up the recursion
        if (node == null) {
            return null;
        }

        // Traverse the tree to the left or right depending on the key
        if (key < node.key) {
            node.left = delete(key, node.left);
        } else if (key > node.key) {
            node.right = delete(key, node.right);
        }

        // At this point, "node" is the node to be deleted

        // Node has no children --> just delete it
        else if (node.left == null && node.right == null) {
            node = null;
        }

        // Node has only one child --> replace node by its single child
        else if (node.left == null) {
            node = node.right;
        } else if (node.right == null) {
            node = node.left;
        }

        // Node has two children
        else
            deleteNodeWithTwoChildren(node);

        if (node == null)
            return null;

        return node;
    }

    private void deleteNodeWithTwoChildren(Node node) {
        // Find minimum node of right subtree ("inorder successor" of current node)
        Node inOrderSuccessor = findMinimum(node.right);

        // Copy inorder successor's data to current node
        node.key = inOrderSuccessor.key;

        // Delete inorder successor recursively
        node.right = delete(inOrderSuccessor.key, node.right);
    }

    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public String getAllValues(int key) {
        Node node = root;
        while(true)
        {
            if(node.key==key)
            {
                return node.value;
            }
            else if (node.key>key) {
                node = node.left;
            }
            else {
                node=node.right;
            }
        }
    }

    public int findSuccessor(int key) {

        Node current = root;
        Node successor = null;

        while (current != null) {
            if (current.key > key) {
                successor = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (successor == null) {
            return -1; // No successor found
        }
        return successor.key;
    }

    public int findPredecessor(int key) {
        Node node = root;
        Node predecessor = null;

        while (node != null) {
            if (node.key == key) {
                if (node.left != null) {
                    predecessor = node.left;
                    while (predecessor.right != null) {
                        predecessor = predecessor.right;
                    }
                }
                break;
            } else if (node.key > key) {
                node = node.left;
            } else {
                predecessor = node;
                node = node.right;
            }
        }

        if (predecessor != null) {
            return predecessor.key;
        } else {
            return -1;
        }
    }

    public LinkedLst findRange(int key1, int key2)
    {
        LinkedLst inOrderKeys = inorderTraversal(root);
        LinkedLst result = new LinkedLst();

        LinkedLst.Node temp = inOrderKeys.head;

        while(temp.next!=null)
        {
            if(temp.key>=key1 && temp.key<=key2)
                result.add(temp.key,temp.val);

            temp=temp.next;
        }
        return result;
    }
}

class ElasticERLHash{

    LinkedLst[] table = new LinkedLst[19];

    ElasticERLHash(){
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedLst();
        }
    }

    public int hashValue(int value) {
        int result = 17; // arbitrary prime number
        int hashCode = 31 * result + value; // multiply by another prime number and add value
        return hashCode % table.length;
    }

    public void add(int key,String val){
        int index = hashValue(key);
        table[index].add(key, val);
    }

    //direct search for the bucket
    public void removeKey(int ein){
        int bucketIndex = hashValue(ein);
        table[bucketIndex].remove(ein);

    }

    public LinkedLst rangeKeys(int key1, int key2){
        LinkedLst obj = new LinkedLst();
        for (int i = 0; i < table.length; i++) {
            LinkedLst.Node curr = table[i].head;
            while(curr != null){
                if(curr.key >= key1 && curr.key <= key2){
                    System.out.print(curr.key+" ");
                    obj.add(curr.key, curr.val);
                }
                curr = curr.next;
            }
        }
        return obj;
    }

    public int nextKey(int ein) {
        int bucketIndex = hashValue(ein);
        //if list is null
        if (table[bucketIndex].head == null) {
            return -1;
        } else {
            LinkedLst.Node curr = table[bucketIndex].head;
            while (curr.next != null && curr.key != ein) {
                curr = curr.next;
            }
            //if it is lastNode then return 0 otherwise nextKey
            return curr.next != null ? curr.next.key : -1;
        }
    }

    public int prevKey(int ein){
        int bucketIndex = hashValue(ein);
        // if list is null or firstNode match

        if (table[bucketIndex].head == null  || table[bucketIndex].head.key == ein) {
            return 0;
        } else {
            LinkedLst.Node curr = table[bucketIndex].head;
            while ( curr != null && curr.key != ein) {
                curr = curr.next;
            }
            // if ein not in a list return -1 otherwise prevkey;
            return curr != null ? curr.prev.key : -1;
        }
    }

    public String getValues(int ein){
        int bucketIndex = hashValue(ein);
        return table[bucketIndex].get(ein);
    }

    public int noOfKeys(){
        int sum = 0;
        for (int i = 0; i < table.length; i++) {
            sum+=table[i].totalKeys();
        }
        return sum;
    }
    public LinkedLst getAllKeys(){
        int[] keys = new int[noOfKeys()];
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            LinkedLst.Node curr =  table[i].head;
            while(curr != null){
                keys[count] = curr.key;
                curr = curr.next;
                count++;
            }
        }

        mergeSort(keys, 0, keys.length-1);
        LinkedLst obj = new LinkedLst();
        for (int i = 0; i < keys.length; i++) {
            obj.add(keys[i], null);
        }
        return obj;
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = temp[i - left];
        }
    }

    public boolean containsKey(int ein){
        int bucketIndex = hashValue(ein);
        LinkedLst.Node curr = table[bucketIndex].head;
        while(curr != null){
            if(curr.key == ein){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }
}

public class ElasticERL {

    int threshold;
    ElasticERLAVL avl = null;
    ElasticERLHash hash = null;
    public  ElasticERL(int size) {
        this.threshold=size;
        if(size<=1000)
            this.hash = new ElasticERLHash();
        else
            this.avl = new ElasticERLAVL();
    }
    public ElasticERL() {
        //empty constructor
    }
    public ElasticERL setEINThreshold(int size)
    {
        return new ElasticERL(size);
    }

    public int generate() {
        int key;
        do {
            // Generate an 8-digit random number as the key
            key = (int) (Math.random() * 90000000) + 10000000;
            // Check if the key already exists
            if(threshold<=1000)
            {
                if (hash.containsKey(key)) {
                    key = 0;
                }
            }
            else {
                if (avl.containsKey(avl.root, key)) {
                    key = 0;
                }
            }
        } while (key == 0);
        return key;
    }

    public LinkedLst allKeys() {
        if(avl!=null)
            return avl.inorderTraversal(avl.root);
        else
            return hash.getAllKeys();
    }

    public void add(int key, String value) {
        if(avl!=null)
            avl.insertNode(key,value);
        else
            hash.add(key,value);
    }

    public void remove(int key) {
        if(avl!=null)
            avl.deleteNode(key);
        else
            hash.removeKey(key);
    }

    public String getValues(int key) {
        if(avl!=null) {
            if(avl.containsKey(avl.root, key))
                return avl.getAllValues(key);
            else
                return null;
        }
        else
            return hash.getValues(key);
    }

    public int nextKey(int key){

        if(avl!=null) {
            int result = avl.findSuccessor(key);
            if(result!=-1)
                return result;
            else
                return -1;
        }
        else
            return hash.nextKey(key);

    }

    public int prevKey(int key){
        if(avl!=null){
            int result = avl.findPredecessor(key);
            if(result!=-1)
                return result;
            else
                return -1;
        }else
            return hash.prevKey(key);
    }

    public LinkedLst rangeKey(int key1, int key2){
        if(avl!=null)
            return avl.findRange(key1,key2);
        else
            return hash.rangeKeys(key1,key2);
    }

    public static void main(String[] args) {

        System.out.println("Do you wants to take input from File or write Manually. 1) Press 1 for file 2) Press 2 for manually ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        int counter=0;
        boolean isExceed = false;
        String path = "D:\\University\\Assignments\\EHITS_test_files\\";

        if(choice == 1){
            System.out.println("Enter file name: ");
            sc.nextLine();
            String fileName = sc.nextLine();
            BufferedReader reader = null;
            try
            {
                reader = new BufferedReader(new FileReader(path+fileName));
                String line = reader.readLine();
                while (line != null) {
                    line = reader.readLine();
                    counter++;
                    if(counter > 1000){
                        isExceed = true;
                        break;
                    }

                }
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ElasticERL el;
            System.out.println("Does the exceeded 1000: "+isExceed);
            if(isExceed){
                 el = new ElasticERL(1001);
            }
            else{
                 el = new ElasticERL(1);
            }
            try {
                Scanner scanner = new Scanner(new FileInputStream(path + fileName));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    el.add(Integer.parseInt(line),"hello");   //adding key in related datastructure - avl or hashtable
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }

            LinkedLst result;

            result = el.allKeys();  //calling method to print all keys
            System.out.println("ALl the keys: ");
            result.display();

              System.out.println("Does given key is in the list: "+el.avl.containsKey(el.avl.root,33255593));
              el.remove(33255593); //removing key from given list
              System.out.println("Does given key is in the list: "+el.avl.containsKey(el.avl.root, 44799844) );
//            result = el.allKeys();
//            result.display();

//            System.out.println("Value for given key is: " + el.getValues(99869696)); //all values method
//
//            System.out.println("Next(suceesor) of the given key is: "+el.nextKey(99869696)); //next key
//
//            System.out.println("Prev(predecessor) of the given key is: "+el.prevKey(99869696)); //prev key
//
//            result = el.rangeKey(10000000,90000000); //range of the given two key
//            System.out.println("Range of the given two key: ");
//            result.display();
        }
        else{
            System.out.println("Enter the size of total EIN: ");
            int size = sc.nextInt();

            ElasticERL el = new ElasticERL();
            el=el.setEINThreshold(size);
            for(int i=0; i<size; i++)
            {
                int key = el.generate();
                String value = "hello";

                el.add(key,value);
            }
            LinkedLst output;

            output = el.allKeys();  //calling method to print all keys
            System.out.println("ALl the keys: ");
            output.display();
//
//            System.out.println("Does given key is in the list: "+el.avl.containsKey(el.avl.root,33255593));
//            el.remove(33255593); //removing key from given list
//            System.out.println("Does given key is in the list: "+el.avl.containsKey(el.avl.root,33255593));
//
//
//            System.out.println("Value for given key is: " + el.getValues(99869696)); //all values method
//
//            System.out.println("Next(suceesor) of the given key is: "+el.nextKey(99869696)); //next key
//
//            System.out.println("Prev(predecessor) of the given key is: "+el.prevKey(99869696)); //prev key
//
//            output = el.rangeKey(10000000,90000000); //range of the given two key
//            System.out.println("Range of the given two key: ");
//            output.display();
            
        }
    }
}
