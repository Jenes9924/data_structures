package data.tree;

/**
 * @author Alex
 * @desc 二叉树递归实现
 * @since 18-6-7
 */

public class BinaryTree<T> {

    Node root;

    private class Node<T> {
        T value;
        Node left;
        Node right;

        public Node(T element) {
            this.value = element;
        }

        public int compareTo(Node o) {
            return this.value.hashCode() - o.value.hashCode();
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();
//        BinaryTree<String> tree2 = new BinaryTree<>();
        int[] addNumbers = {20,10,40,5,15,30,50,4,8,13,18,45,59,7,9,14,17,19,47,16,46,48};
//        String addStrings = {};
        for(int num: addNumbers){
            tree.add(num);
        }
        //遍历成功
//        tree.printSelf();
//        tree.remove(10);
        tree.printSelf(3);

    }

    public void add(T element) {
        Node node = new Node(element);
        if (this.root == null) {
            root = node;
            return;
        } else {
            add(node, root);
        }

    }

    public boolean add(Node target, Node node) {
        if(target.compareTo(node) == 0){
            return false;
        }
        if (target.compareTo(node) > 0) {
            if (node.right == null) {
                node.right = target;
                return true;
            } else {
                return add(target, node.right);
            }
        }
        if (target.compareTo(node) < 0) {
            if (node.left == null) {
                node.left = target;
                return true;
            } else {
                return add(target, node.left);
            }
        }
        return false;
    }

    public void remove(T element) {
        Node node = findNode(element);
        if (node == null){
            System.out.println("要删除的值不存在");
            return;
        }
        remove(node);

    }

    private void remove(Node node) {
        Node father = findFather(node);
        //分为四种情况
        if (node.left == null) {
            //1.无任何子节点
            //2.无左节点有右节点
            if (node.right == null) {
                if(father.left.value.hashCode() == node.value.hashCode()){
                    father.left = null;
                }else {
                    father.right = null;
                }
                return;

            } else {
                if(father.left.value.hashCode() == node.value.hashCode()){
                    father.left = node.right;
                }else {
                    father.right = node.right;
                }
                return;
            }
        } else {
            //3.无右节点有左节点
            //4.左右节点都有
            if (node.right == null) {
                if(father.left.value.hashCode() == node.value.hashCode()){
                    father.left = node.left;
                }else {
                    father.right = node.left;
                }
                return;
            } else {
                //左右节点都有，最复杂的情况
                Node shorts = findShortsNode(node);
                node.value = shorts.value;
                return;
            }
        }


    }

//    public void replaceNode(Node node) {
//
//    }

    public Node findShortsNode(Node removeNode) {
        Node shortNode = findShortsNode(removeNode.left,removeNode.right);
        return shortNode;
    }

    private Node findNode(T element){
        return findNode(root,element);
    }
    private Node findNode(Node node,T element){
        Node value = null;
        if(node.value.hashCode() == element.hashCode()){
            return node;
        }else {
            if(node.left != null){
                value = findNode(node.left,element);
            }
            if(value != null){
                return value;
            }else if(node.right != null){
                return findNode(node.right,element);
            }else {
                return null;
            }
        }

    }

    /**
     * 找到距离被删除节点距离最近的值
     *
     * @param left
     * @param right
     * @return
     */
    public Node findShortsNode(Node left, Node right) {

        if (left.right == null && right.left == null) {
            if (left.left == null) {
                return left;
            }
            if (right.right == null) {
                return right;
            }
        }

        if (left.right == null) {
            remove(left);
            return left;
        }
        if (right.left == null) {
            remove(right);
            return right;
        }
        return findShortsNode(left.right, right.left);
    }

    //调用这个方法之前需要判断被删除节点是否有距离它两层的子节点，否则会出现空指针异常



    public Node findFather(Node node) {
        return findFather(node,root);
    }



    //前序遍历
    //同步骤遍历，同样深度的时候优先选择左节点
    private Node findFather(Node target,Node node){
        Node father = null;
        if(node.right == target || node.left == target){
            return node;
        }
        if(node.left != null){
            father = findFather(target,node.left);
        }
        if(father != null){
            return father;
        }
        if(node.right != null){
            father = findFather(target,node.right);
        }
        return father;
    }
    //前序遍历
    private void traversal(Node node){
        System.out.println(node.value);
        if(node.left != null){
            traversal(node.left);
        }
        if(node.right != null){
            traversal(node.right);
        }
    }
    //中序遍历
    private void middlePrint(Node node){
        if(node.left != null){
            middlePrint(node.left);
        }
        System.out.println(node.value);
        if(node.right != null){
            middlePrint(node.right);
        }
    }

    private void lastPrint(Node node){
        if(node.left != null){
            lastPrint(node.left);
        }
        if(node.right != null){
            lastPrint(node.right);
        }
        System.out.println(node.value);
    }

    public void printSelf(int mode){
        if(mode == 2){
            middlePrint(root);
        }else if (mode == 3){
            lastPrint(root);
        }else {
            traversal(root);
        }
    }


}



