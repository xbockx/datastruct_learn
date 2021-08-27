package tree;

public class Main {
    public static void main(String[] args) {
//        testPreorder();
//        testLevelOrder();
//        testHeight();
//        testIsComplete();
        testAVL();
    }

    static void testPreorder() {
        Integer[] data = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
    }

    static void testLevelOrder() {

    }

    static void testHeight() {
        Integer[] data = new Integer[] {
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println(bst.height());
        System.out.println(bst.height2());
    }

    static void testIsComplete() {
        Integer[] data = new Integer[] {
                7, 4, 9, 2, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println(bst.isComplete());
    }

    static void testAVL() {
        Integer[] data = new Integer[] {
                85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56
        };
        AVLTree<Integer> avlTree = new AVLTree<>();
        for(int i = 0; i < data.length; i++) {
            avlTree.add(data[i]);
        }
        avlTree.levelOrderTraversal(new BinarySearchTree.Visitor<Integer>(){
            @Override
            boolean visit(Integer element) {
                System.out.print(element);
                System.out.print("\t");
                return false;
            }
        });
    }
}
