public class RBT<E extends Comparable<E>> {
    private Node<E> root;
    private Node<E> leaf; //nil node

    public RBT(){
        root = new Node<E>(null);
        //initialize nil node to null and color to black
        leaf = new Node<E>(null);
        leaf.setColor('b');
    }

    public Node<E> getRoot(){
        return root;
    }

    public void insert(E data){
        // Preform a regular insert
        // Check to make sure the tree remains an RBT tree
        //data = z
        Node<E> y = leaf;
        Node<E> x = root;
        Node<E> z = new Node<>(data);

        //check if root is null before entering while loop to prevent comparison error
        if(x.getData()!= null) {
            while(x != leaf) {
                y = x;
                if(z.getData().compareTo(x.getData()) < 0) {
                    x = x.getLeftChild();
                }
                else {
                    x = x.getRightChild();
                }
            }
        }
        z.setParent(y);
        if(y == leaf) {
            root = z;
        }

        else if(z.getData().compareTo(y.getData()) <= 0) {
            y.setLeftChild(z);
        }
        else {
            y.setRightChild(z);
        }

        z.setLeftChild(leaf);
        z.setRightChild(leaf);
        z.setColor('r');
        rbInsertFixup(z);
    }

    public Node<E> search(E data){
        // Return the node that corresponds with the given data
        // Note: No need to worry about duplicate values added to the tree
        Node<E> x = root;
        while(x != leaf) {
            if(x.getData() == data) {
                return x;
            }
            else if(data.compareTo(x.getData()) <= 0) {
                x = x.getLeftChild();
            }
            else {
                x = x.getRightChild();
            }
        }
        return null;
    }

    public void delete(E data){
        // Preform a regular delete
        // Check to make sure the tree remains an RBT tree
        Node<E> z = search(data);
        Node<E> y = search(data);
        Node<E> x;
        if(y == null) {
            return;
        }

        char originalColor = y.getColor();
        if(z.getLeftChild() == leaf) {
            x = z.getRightChild();
            rbTransplant(z, z.getRightChild());
        }
        else if(z.getRightChild() == leaf) {
            x = z.getLeftChild();
            rbTransplant(z, z.getLeftChild());
        }
        //neither child == leaf, get successor from right subtree
        else {
            y = getMin(z.getRightChild()); //leftmost node in right subtree
            originalColor = y.getColor();
            x = y.getRightChild();
            if(y.getParent() == z) {
                x.setParent(y);
            }
            else {
                rbTransplant(y, y.getRightChild());
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
            }
            rbTransplant(z, y);
            y.setLeftChild(z.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(z.getColor());
        }
        //if original node to be deleted was black, need to recolor and or rotate
        if(originalColor == 'b') {
            rbDeleteFixup(x);
        }

    }

    public void traverse(Node<E> top) {
        // preorder traversal
        if(top.getData()!= null) {
            System.out.print(top.getData() + " ");// + top.getColor() + " ");
        }

        if(top.getLeftChild()!= null) {
            traverse(top.getLeftChild());
        }
        if(top.getRightChild()!=null) {
            traverse(top.getRightChild());
        }
    }


    public void rightRotate(Node<E> x){

        /*
        If x is the root of the tree to rotate with right child subtree T3 and left child y,
        where T1 and T2 are the left and right children of y:
            x becomes right child of y and T1 as its left child of y
            T2 becomes left child of x and T3 becomes right child of x
        */
        Node<E> y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if(y.getRightChild() != leaf) {
            y.getRightChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent() == leaf) {
            root = y;
        }
        else if(x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);
        }
        y.setRightChild(x);
        x.setParent(y);

    }

    public void leftRotate(Node<E> x){

    	/*
        If x is the root of the tree to rotate with left child subtree T1 and right child y,
        where T2 and T3 are the left and right children of y:
            x becomes left child of y and T3 as its right child of y
            T1 becomes left child of x and T2 becomes right child of x
        */
        Node<E> y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if(y.getLeftChild() != leaf) {
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent() == leaf) {
            root = y;
        }
        else if(x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);
        }
        y.setLeftChild(x);
        x.setParent(y);


    }


    public void rbInsertFixup(Node<E> z) {
        if(z.getParent() != leaf) {
            while(z.getParent() != leaf && z.getParent().getColor() == 'r'){
                if(z.getParent() == z.getParent().getParent().getLeftChild()) {
                    Node<E> y = z.getParent().getParent().getRightChild();
                    if(y != leaf && y.getColor() == 'r') { //z's uncle is red
                        z.getParent().setColor('b');
                        y.setColor('b');
                        z.getParent().getParent().setColor('r');
                        z = z.getParent().getParent();
                    }
                    else { //z's uncle is black
                        if(z == z.getParent().getRightChild()) {
                            z = z.getParent();
                            leftRotate(z);
                        }
                        z.getParent().setColor('b');
                        z.getParent().getParent().setColor('r');
                        rightRotate(z.getParent().getParent());
                    }
                }
                else {
                    Node<E> y = z.getParent().getParent().getLeftChild();
                    if(y != leaf && y.getColor() == 'r') {
                        z.getParent().setColor('b');
                        y.setColor('b');
                        z.getParent().getParent().setColor('r');
                        z = z.getParent().getParent();
                    }
                    else {
                        if(z == z.getParent().getLeftChild()) {
                            z = z.getParent();
                            rightRotate(z);
                        }
                        z.getParent().setColor('b');
                        z.getParent().getParent().setColor('r');
                        leftRotate(z.getParent().getParent());
                    }
                }
            }
        }
        root.setColor('b');
    }

    public void rbTransplant(Node<E> u, Node<E> v) {
        //update childs parent and parents left or right child
        if(u.getParent() == leaf) {
            root = v;
        }
        else if(u == u.getParent().getLeftChild()) {
            u.getParent().setLeftChild(v);
        }
        else {
            u.getParent().setRightChild(v);
        }
        v.setParent(u.getParent());


    }

    public Node<E> getMin(Node<E> top){
        // Return the min node(left-most node) of the right subtree
        if(top == null) {
            return null;
        }
        Node<E> node = top;
        while(node.getLeftChild()!= leaf) {
            node = node.getLeftChild();
        }
        return node;
    }

    public void rbDeleteFixup(Node<E> x) {
        Node<E> w;
        while(x != root && x.getColor() == 'b') {
            if(x == x.getParent().getLeftChild()) {
                w = x.getParent().getRightChild();
                if(w.getColor() == 'r') {                   //uncle is red
                    w.setColor('b');                        //case 1
                    x.getParent().setColor('r');            //case 1
                    leftRotate(x.getParent());              //case 1
                    w = x.getParent().getRightChild();      //case 1
                }
                if(w.getLeftChild().getColor() == 'b' && w.getRightChild().getColor() == 'b') {
                    w.setColor('r');                        //case 2
                    x = x.getParent();                      //case 2
                }
                else {
                    if(w.getRightChild().getColor() == 'b') {
                        w.getLeftChild().setColor('b');     //case 3
                        w.setColor('r');                    //case 3
                        rightRotate(w);                     //case 3
                        w = x.getParent().getRightChild();  //case 3
                    }
                    w.setColor(x.getParent().getColor());   //case 4
                    x.getParent().setColor('b');            //case 4
                    w.getRightChild().setColor('b');        //case 4
                    leftRotate(x.getParent());              //case 4
                    x = root;                               //case 4
                }
            }
            else {
                w = x.getParent().getLeftChild();
                if(w.getColor() == 'r') {                   //uncle is red
                    w.setColor('b');                        //case 1
                    x.getParent().setColor('r');            //case 1
                    rightRotate(x.getParent());             //case 1
                    w = x.getParent().getLeftChild();       //case 1
                }
                if(w.getLeftChild().getColor() == 'b' && w.getRightChild().getColor() == 'b') {
                    w.setColor('r');                        //case 2
                    x = x.getParent();                      //case 2
                }
                else {
                    if(w.getLeftChild().getColor() == 'b') {
                        w.getRightChild().setColor('b');    //case 3
                        w.setColor('r');                    //case 3
                        leftRotate(w);                      //case 3
                        w = x.getParent().getLeftChild();   //case 3
                    }
                    w.setColor(x.getParent().getColor());   //case 4
                    x.getParent().setColor('b');            //case 4
                    w.getLeftChild().setColor('b');         //case 4
                    rightRotate(x.getParent());             //case 4
                    x = root;                               //case 4
                }
            }

        }
        //set color to black at the end
        x.setColor('b');
    }
}

