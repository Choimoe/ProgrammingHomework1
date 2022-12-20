public class Problem7 {
    public static void main(String[] args) {
        MyHashMap m = new MyHashMap();
        m.put("dog", "Bosco");
        m.put("dog", "Spot");
        m.put("cat", "Rags");
        System.out.println(m);
    }
}

class MyNode {
    Object object;
    MyNode next, prev;

    public MyNode() {
        next = prev = null;
    }

    public MyNode(Object object) {
        this.object = object;
        next = prev = null;
    }

    public MyNode(Object object, MyNode next, MyNode prev) {
        this.object = object;
        this.next = next;
        this.prev = prev;
    }
}

class MyHashMap {
    DoubleLinkList list = new DoubleLinkList();

    public void put(String key, String value) {
        MapEntry entry = new MapEntry(key, value);
        list.insertNew(entry);
    }

    public MapEntry get(String key) {
        MapEntry entry = new MapEntry(key, null);
        return (MapEntry) list.find(entry);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

class MapEntry {
    String key, value;

    public MapEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MapEntry) {
            return key.hashCode() == ((MapEntry) o).key.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}

class DoubleLinkList {
    MyNode head;
    int length;

    public DoubleLinkList() {
        head = new MyNode();
        head.next = head;
        head.prev = head;
    }

    public MyNode nodeAt(int index) {
        MyNode cur = head.next;
        int count = 0;

        while (cur != head) {
            if (count == index) return cur;
            count++;
            cur = cur.next;
        }

        return null;
    }

    public MyNode findAt(Object object) {
        MyNode cur = head.next;
        while (cur != head) {
            if (cur.object.equals(object)) return cur;
            cur = cur.next;
        }
        return null;
    }

    public Object find(Object object) {
        MyNode cur = findAt(object);
        if (cur == null) return null;
        else return cur.object;
    }

    public boolean exists(Object object) {
        return findAt(object) != null;
    }

    public Object at(int index) {
        return nodeAt(index).object;
    }

    public void insert(MyNode curNode, MyNode newNode) {
        length++;

        newNode.next = curNode.next;
        newNode.prev = curNode;

        curNode.next.prev = newNode;
        curNode.next = newNode;
    }

    public void insertFront(MyNode curNode) {
        insert(head, curNode);
    }

    public void insertAt(int index, Object object) {
        MyNode newNode = new MyNode(object);
        MyNode cur = nodeAt(index);

        if (cur == null) return;

        insert(cur, newNode);
    }

    public void insertNew(Object object) {
        MyNode newNode = new MyNode(object);
        MyNode node = findAt(object);
        if (node == null) insertFront(newNode);
        else node.object = object;
    }

    public void insertEnd(Object object) {
        MyNode newNode = new MyNode(object);
        MyNode cur = nodeAt(length);

        if (cur == null) return;

        insert(cur, newNode);
    }

    public void delete (MyNode cur) {
        cur.next.prev = cur.prev;
        cur.prev.next = cur.next;
    }

    public void deleteAt (int index) {
        delete(nodeAt(index));
    }

    public void change (Object oldObj, Object newObj) {
        MyNode cur = findAt(oldObj);
        if (cur == null) return;

        cur.object = newObj;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");

        MyNode cur = head.next;
        while (cur != head) {
            result.append(cur.object.toString());
            result.append(", ");
            cur = cur.next;
        }

        int resultSize = result.length();
        result.delete(resultSize - 2, resultSize);

        result.append("]");

        return result.toString();
    }
}
