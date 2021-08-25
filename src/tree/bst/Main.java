package tree.bst;

@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) {
//        testPreorder();
//        testLevelOrder();
//        testHeight();
        testIsComplete();
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
        Integer[] data = new Integer[] {
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        bst.levelOrderTraversal(new BinarySearchTree.Visitor<Integer>(){
            @Override
            boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
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
}
