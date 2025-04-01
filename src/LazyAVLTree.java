public class LazyAVLTree {
    private TreeNode root;

    public LazyAVLTree() {
        this.root = null;
    }

    private TreeNode getRoot() {
        return this.root;
    }

    private void setRoot(TreeNode newRoot) {
        root = newRoot;
    }

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
        return height(this.getRoot());
    }

    private int height(TreeNode treeNode) {
        // Empty tree has the lowest integer for height.
        if (treeNode == null) {
            return Integer.MIN_VALUE;
        }

        int heightLeft = height(treeNode.getLeftChild());
        int heightRight = height(treeNode.getRightChild());

        // Adding 1 means to include the current node in this path.
        return 1 + Math.max(heightLeft, heightRight);
    }

    public int size() {
        return size(this.getRoot());
    }

    private int size(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }

        return 1 + size(treeNode.getLeftChild()) + size(treeNode.getRightChild());
    }

    public String toString() {
        return "";
    }

    private static class TreeNode {
        private int key;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private boolean deleted;

        TreeNode(int key) {
            this.key = key;
            this.leftChild = null;
            this.rightChild = null;
            this.deleted = false;
        }

        public int getKey() {
            return this.key;
        }

        public void setKey(int newKey) {
            key = newKey;
        }

        public TreeNode getLeftChild() {
            return this.leftChild;
        }

        public void setLeftChild(TreeNode newLeftChild) {
            leftChild = newLeftChild;
        }

        public TreeNode getRightChild() {
            return this.rightChild;
        }

        public void setRightChild(TreeNode newRightChild) {
            rightChild = newRightChild;
        }

        public boolean isDeleted() {
            return this.deleted;
        }

        public void delete() {
            deleted = true;
        }

        public void undelete() {
            deleted = false;
        }
    }
}
