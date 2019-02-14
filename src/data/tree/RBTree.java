package data.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Alex
 * @desc 红黑树实现(副本)
 * @since 18-6-11
 */
public class RBTree<T> {
    /**
     * 红黑树与普通二叉树不一样的地方在于
     * 1.染色
     * 2.颜色自检
     * 3.旋转
     */
    Node<T> root;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final boolean RIGHT = true;
    private static final boolean LEFT = false;


    private static class Node<T> {
        T value;
        Node left;
        Node right;
        boolean color = RED;
        Node father;

        public Node(T element) {
            this.value = element;
        }

        public int compareTo(Node o) {
            return this.value.hashCode() - o.value.hashCode();
        }

        public Node<T> getBrother(Node son) {
            if (son.equals(this.left)) {
                return this.right;
            } else if (son.equals(this.right)) {
                return this.left;
            } else {
                return null;
            }
        }

        public boolean hasSon() {
            if (this.left != null || this.right != null) {
                return true;
            } else {
                return false;
            }
        }
    }



    public  List<Integer> result = new ArrayList<>();

    //校验整棵树是否符合规则
    public void checkTree(Node node) {
        int count = 0;
        if (!node.hasSon()) {
            count = checkNode(node, 0);
            result.add(count);
        }
    }

    public void checkTree(){
        checkTree(root);
    }

    public int checkNode(Node node, int count) {
        if (node.color) {
            count = count + 1;
        }
        if (node.equals(root)) {
            return count;
        }
        return checkNode(node.father, count);
    }


    public void add(T element) {
        Node node = new Node(element);
        if (this.root == null) {
            node.color = BLACK;
            root = node;
            return;
        } else {
            add(node, root);
            checkColor(node);
        }

    }

    public void checkColor(Node node) {
        //插入颜色检测判断的逻辑
        if (node.color) {
            return;
        }
        if (node.equals(root)) {
            node.color = BLACK;
            return;
        }
        //  1. 父节点为黑色
        if (node.father.color) {
            return;
        } else {//  2.父节点为红色，颜色冲突
            if (!node.father.color) {
                //判断叔叔节点的颜色
                //  2.1叔节点为黑色时自变色插入，父节点为红色
                Node uncle = node.father.father.getBrother(node.father);
                boolean isLeft = node.father.equals(node.father.father.left);
                if (uncle == null || uncle.color) {
                    if (isLeft) {
                        if (node.equals(node.father.left)) {
                            right(node.father);
                        } else {
                            left(node);
                        }
                    } else {
                        if (node.equals(node.father.right)) {
                            left(node.father);
                        } else {
                            right(node);
                        }
                    }
                } else {
                    //叔节点为红色
                    boolean tmp = node.father.color;
                    node.father.color = node.father.father.color;
                    node.father.father.color = tmp;
                    uncle.color = node.father.color;
                    checkColor(node.father.father);
                }

            }

        }
    }

    /**
     * 左旋
     *
     * @param node 插入节点的父节点
     */
    public void left(Node node) {
//        rotating(node, false);
        changeColorRotate(node, false,null);
    }

    public void right(Node node) {
//        rotating(node, true);
        changeColorRotate(node, true,null);
    }


    public boolean add(Node target, Node node) {
        if (target.compareTo(node) == 0) {
            return false;
        }
        if (target.compareTo(node) > 0) {
            if (node.right == null) {
                node.right = target;
                target.father = node;
                return true;
            } else {
                return add(target, node.right);
            }
        }
        if (target.compareTo(node) < 0) {
            if (node.left == null) {
                node.left = target;
                target.father = node;
                return true;
            } else {
                return add(target, node.left);
            }
        }
        return false;
    }

    public void remove(T element) {
        Node node = findNode(element);
        if (node == null) {
            System.out.println("要删除的值不存在");
            return;
        }
        remove(node);

    }


    /**
     * 删除后颜色修复
     * 删除的节点只会出现有一个子节点或者没有子节点。有两个子节点不会被选择删除后修复颜色
     * 父节点黑色，删除节点黑色，兄弟节点也是黑色，删除的时候不会出问题，但是当再插入一个节点顶替删除节点的位置的时候，会造成路径上黑色节点数量不一致的问题
     * 所以这时候需要从局部逐渐往上进行调整
     * 将兄弟节点变红色，然后将父节点视为红色（实际上未改变颜色）
     *
     * @param father
     * @param brother
     */
    public void fixColor(Node father, Node brother) {
        //删除的节点默认是黑色，红色不会调用此方法进行修复
        //兄弟节点的颜色
        if (father == null) {
            return;
        }

        if (brother == null) {
//            fixColor(father.father, father);
//            System.out.println("ERROR   ：   兄弟节点为null！请检查！！");
            return;
        }
        //兄弟节点颜色为红色
        if (!brother.color) {
            if (brother.equals(father.left)) {
                //测试
                if(brother.right == null){
                    System.out.println("debugger!");
                }
//                brother.color = BLACK;
//                brother.right.color = RED;
                right(brother);
                brother.color = BLACK;
                father.color = RED;
                fixColor(father,father.left);
            } else {
//                brother.color = BLACK;
//                brother.left.color = RED;
                left(brother);
                brother.color = BLACK;
                father.color = RED;
                fixColor(father,father.right);
            }
        } else {
            boolean leftColor = brother.left == null ? BLACK : brother.left.color,
                    rightColor = brother.right == null ? BLACK : brother.right.color;
            if (leftColor && rightColor) {
                brother.color = RED;
                if (!father.color) {//父节点红色就转换为黑色
                    father.color = BLACK;
                } else {
                    if (!father.equals(root)) {
                        fixColor(father.father, father.equals(father.father.left) ? father.father.right : father.father.left);
                    }
                }
                return;
            }
            if (!leftColor && rightColor) {//左边为红色
                if (brother.equals(father.left)) {
                    changeColorRotate(brother, true, false);
                } else {
                    //颜色跟进可能有问题
                    changeColorRotate(brother.left, RIGHT, true);
                    changeColorRotate(father.right, LEFT, false);
                }
            }
            if(!rightColor ) {//右边为红色
                if (brother.equals(father.right)) {
                    changeColorRotate(brother, false, false);
                } else {
                    //颜色跟进可能有问题
                    changeColorRotate(brother.right, LEFT, true);
                    changeColorRotate(father.left, RIGHT, false);
                }
            }
        }

    }

    /**
     * 变色旋转，
     *
     * @param node
     * @param direction 旋转方向，true为右旋，false为左旋
     * @param isTransition 是否变色,如果为null，则表示检查颜色
     */
    public void changeColorRotate(Node node, boolean direction, Boolean isTransition) {
        Node father = node.father;
        boolean tmp = node.color;
        node.father = father.father;
        if (father.equals(root)) {
            root = node;
        } else if (father.equals(father.father.left)) {
            father.father.left = node;
        } else {
            father.father.right = node;
        }
        father.father = node;
        node.color = father.color;
        father.color = tmp;
        if (direction) {
            if (node.right == null) {
                father.left = null;
            } else {
                node.right.father = father;
                father.left = node.right;
            }
            node.right = father;
            //判断是否由插入引起的旋转，如果是，则需要检查颜色
            if(isTransition == null){
                checkColor(node.right);
            }else {
                if (node.left != null && !isTransition) {
                    node.left.color = tmp;
                }
            }
        } else {
            if (node.left == null) {
                father.right = null;
            } else {
                node.left.father = father;
                father.right = node.left;
            }
            node.left = father;
            if(isTransition == null) {
                checkColor(node.left);
            }else {
                if (node.right != null && !isTransition) {
                    node.right.color = tmp;
                }
            }

//            checkColor(node.left);
        }


    }

//    public void changeColorRotateBackup(Node node, boolean direction, boolean isTransition) {
//        Node father = node.father;
//        boolean tmp = node.color;
//        node.father = father.father;
//        if (father.equals(root)) {
//            root = node;
//        } else if (father.equals(father.father.left)) {
//            father.father.left = node;
//        } else {
//            father.father.right = node;
//        }
//        father.father = node;
//        node.color = father.color;
//        father.color = tmp;
//        if (direction) {
//            if (node.right == null) {
//                father.left = null;
//            } else {
//                node.right.father = father;
//                father.left = node.right;
//            }
//            node.right = father;
//            if (node.left != null && !isTransition) {
//                node.left.color = tmp;
//            }
////            checkColor(node.right);
//        } else {
//            if (node.left == null) {
//                father.right = null;
//            } else {
//                node.left.father = father;
//                father.right = node.left;
//            }
//            node.left = father;
//            if (node.right != null && !isTransition) {
//                node.right.color = tmp;
//            }
////            checkColor(node.left);
//        }
//
//
//    }

    private void remove(Node node) {
        Node father = node.father;
        if (node.equals(root) && root.left == null && root.right == null) {
            root = null;
            return;
        }
        if (node.left == null && node.right == null) {
            //1.无任何子节点,红黑树需要进行修复操作
            Node brother = null;
            if (node.equals(father.left)) {
                father.left = null;
                brother = father.right;
            } else {
                father.right = null;
                brother = father.left;
            }
            node.father = null;
            if (node.color) {
                fixColor(father, brother);
            }
        } else {
            Node shorts = findShortsNode(node);
//            remove(shorts);
            node.value = shorts.value;
        }


    }


    public Node findShortsNode(Node removeNode) {
        Node shortNode = removeNode.left == null ? findShort(removeNode.right, true) : findShort(removeNode.left, false);
        Node tmp = new Node(shortNode.value);
        remove(shortNode);
        return tmp;

    }

    private Node findNode(T element) {
        return findNode(root, element);
    }

    private Node findNode(Node node, T element) {
        Node value = null;
        if (node.value.hashCode() == element.hashCode()) {
            return node;
        } else {
            if (node.left != null) {
                value = findNode(node.left, element);
            }
            if (value != null) {
                return value;
            } else if (node.right != null) {
                return findNode(node.right, element);
            } else {
                return null;
            }
        }

    }

    public Node findShort(Node node, boolean direction) {
//        if (node == null) {
//            System.out.println("DEBUG!");
//        }
        if (direction) {
            if (node.left == null) {
                return node;
            } else {
                return findShort(node.left, direction);
            }
        } else {
            if (node.right == null) {
                return node;
            } else {
                return findShort(node.right, direction);
            }
        }

    }


    //前序遍历
    private void traversal(Node node) {
        System.out.println(node.value);
        if (node.left != null) {
            traversal(node.left);
        }
        if (node.right != null) {
            traversal(node.right);
        }
    }

    private int qianxu(Node node,int count){
        if(node.color){
            count ++;
        }
        if(node.left == null && node.right == null){
            return count;
        }
        if (node.left != null) {
            return qianxu(node.left,count);
        }
        if (node.right != null) {
            return qianxu(node.right,count);
        }
        return count;
    }


    public  List<Node> endNodeList = new ArrayList<>();
    /**
     *后序遍历
     * 检测颜色冲突，并且找到所有末端节点
     */
    public void lastTraversal(Node node) throws Exception {
        if(node.left!= null){
            if(!node.color && !node.left.color){
                throw new Exception("颜色冲突！");
            }
            lastTraversal(node.left);
        }
        if(node.right != null){
            if(!node.color && !node.right.color){
                throw new Exception("颜色冲突！");
            }
            lastTraversal(node.right);
        }
        if(node.right == null && node.left == null){
            endNodeList.add(node);
        }
    }
    public int checkEndNode(Node node,int count)  {
//        if(node.left!=null || node.right != null){
//            throw new Exception("错误的Node，非终端Node！");
//        }
        if (node.equals(root)){
            count ++;
            return count;
        }else {
            if(node.color){
                count++;
            }
            return checkEndNode(node.father,count);
        }


    }

    public void checkAllTree() throws Exception {
        if(root == null){
            System.out.println("全部删除，检查完毕");
            return;
        }
        endNodeList = new ArrayList<>();
        //标准黑色节点数量
        int standard = qianxu(root,0);
        int count_tmp = 0;
        lastTraversal(root);
        for (Node node : endNodeList) {
            int count = checkEndNode(node,0);
            if(standard != count){
                throw new Exception("黑色节点数量不一致！");
            }
        }
//        System.out.println("过关");
    }




    //中序遍历
    private void middlePrint(Node node) {
        if (node.left != null) {
            middlePrint(node.left);
        }
        if (!node.color) {
            System.out.println(node.value + "==>>" + (node.color ? "黑色" : "红色"));
//            if (node.father != null && node.father.color) {
//
//            }
        }
        checkTree(node);
        if (node.right != null) {
            middlePrint(node.right);
        }
    }

    private void lastPrint(Node node) {
        if (node.left != null) {
            lastPrint(node.left);
        }
        if (node.right != null) {
            lastPrint(node.right);
        }
        System.out.println(node.value);
    }

    public void printSelf(int mode) {
        if (root == null) {
            System.out.println("这是一颗空的树，请插入后再遍历");
            return;
        }
        if (mode == 2) {
            middlePrint(root);
        } else if (mode == 3) {
            lastPrint(root);
        } else {
            traversal(root);
        }
    }


}
