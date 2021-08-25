package leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 */
public class _226_InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.right = left;
        root.left = right;
        return root;
    }

    /**
     * 前序遍历
     * @param root
     * @return
     */
    public TreeNode invertTree2(TreeNode root) {
        if(root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree2(root.left);
        invertTree2(root.right);
        return root;
    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public TreeNode invertTree3(TreeNode root) {
        if(root == null) {
            return null;
        }
        invertTree2(root.left);
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree2(root.left);  // 已经交换！
        return root;
    }

    /**
     * 层序遍历
     * @param root
     * @return
     */
    public TreeNode invertTree4(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();

            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
}
