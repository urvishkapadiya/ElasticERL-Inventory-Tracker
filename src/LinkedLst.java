public class LinkedLst {

    class Node {
        int key;
        Node next;
        Node prev;
        String val;

        Node(int key, String val, Node next, Node prev) {
            this.val = val;
            this.key = key;
            this.next = next;
            this.prev = prev;
        }
    }

    Node head;
    int noOfKeys;

    LinkedLst() {
        head = null;
    }

    public void add(int ein, String val) {
        if (head == null) {
            head = new Node(ein, val, head, head);
        } else {
            Node newNode = new Node(ein, val, head, null);
            head.prev = newNode;
            head = newNode;
        }
        noOfKeys++;
    }

    public void remove(int ein) {
        if (head == null) {
            return;
        }
        else if(head.key == ein){
            head = head.next;
            head.prev = null;
            noOfKeys--;
        }
        else {
            Node curr = head;
            while (curr != null && curr.key != ein) {
                curr = curr.next;
            }

            if(curr!=null){
                // middle node
                if (curr.next != null) {
                    curr.prev.next = curr.next;
                    curr.next.prev = curr.prev;
                }
                //last node
                else
                    curr.prev.next = null;
                noOfKeys--;
            }
        }
    }

    public String get(int ein) {
        if (head == null) {
            return null;
        } else {
            Node curr = head;
            while (curr != null && curr.key != ein) {
                curr = curr.next;
            }
            // if ein found otherwise null;
            return curr != null ? curr.val : null;
        }
    }

    public void display() {
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }

        while(curr!=null)
        {
            System.out.print(curr.key + " ");
            curr=curr.prev;
        }
    }

    public int totalKeys(){
        return noOfKeys;
    }
}
