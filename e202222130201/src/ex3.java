class LinkNode {
    String data;
    LinkNode next;

    public LinkNode(String data) {
        this.data = data;
    }
}

class LinkList {
    private LinkNode head;

    public void add(String data) {
        LinkNode newNode = new LinkNode(data);
        if (head == null) {
            head = newNode;
            head.next = head;
            return;
        }

        LinkNode curr = head;
        while (curr.next != head) curr = curr.next;
        curr.next = newNode;
        newNode.next = head;
    }

    public void print() {
        if (head == null) return;
        LinkNode curr = head;
        while (curr.next != head) {
            System.out.println(curr.data);
            curr = curr.next;
        }
        System.out.println(curr.data);
    }
}

public class ex3 {
    public static void main(String[] args) {
        LinkList list = new LinkList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.print();
    }
}