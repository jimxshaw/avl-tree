public class LazyAVLTree {
    TreeNode root;

    public boolean insert(int key) throws IllegalArgumentException {
        return false;
    }

    public boolean delete(int key) throws IllegalArgumentException {
        return false;
    }

    public boolean contains(int key) throws IllegalArgumentException {
        return false;
    }

    public int findMax() {
        return 0;
    }

    public int findMin() {
        return 0;
    }

    public int height() {
        return 0;
    }

    public int size() {
        return 0;
    }

    public String toString() {
        return "";
    }

    private static class TreeNode {
        int key;
        TreeNode leftChild;
        TreeNode rightChild;
        boolean deleted;


    }
}
