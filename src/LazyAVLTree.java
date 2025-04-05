public class LazyAVLTree {
    private TreeNode root;
    private static final int KEY_LOWER_LIMIT = 1;
    private static final int KEY_UPPER_LIMIT = 99;

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

    public boolean delete(int key) {
        if (key < KEY_LOWER_LIMIT || key > KEY_UPPER_LIMIT) {
            throw new IllegalArgumentException("Key must be between " + KEY_LOWER_LIMIT + " and " + KEY_UPPER_LIMIT);
        }

        TreeNode treeNode = findTreeNode(key);

        // To delete a node it has to exist and be active.
        if (treeNode != null && !treeNode.isDeleted()) {
            treeNode.delete();

            // Successful lazy deletion.
            return true;
        }

        // Either not found or is already deleted.
        return false;
    }

    public boolean contains(int key) {
        if (key < KEY_LOWER_LIMIT || key > KEY_UPPER_LIMIT) {
            throw new IllegalArgumentException("Key must be between " + KEY_LOWER_LIMIT + " and " + KEY_UPPER_LIMIT);
        }

        TreeNode treeNode = findTreeNode(key);

        // A found node must exist and also be active.
        return treeNode != null && !treeNode.isDeleted();
    }

    private TreeNode findTreeNode(int key) {
        // Start traversing from the root.
        TreeNode currentNode = this.getRoot();

        while (currentNode != null) {
            // By AVL definition a child node smaller than the
            // parent node will be a left child.
            if (key < currentNode.getKey()) {
                currentNode = currentNode.getLeftChild();
            }
            // By AVL definition a child node larger than the
            // parent node will be a right child.
            else if (key > currentNode.getKey()) {
                currentNode = currentNode.getRightChild();
            }
            else {
                // Found the node.
                return currentNode;
            }
        }

        // Node not found.
        return null;
    }

    // AVLs are BSTs so the max value is always the very right-most tree node.
    public int findMax() {
        TreeNode max = findMax(this.getRoot());

        return (max != null) ? max.getKey() : -1;
    }

    private TreeNode findMax(TreeNode treeNode) {
        TreeNode max = null;

        while (treeNode != null) {
            if (!treeNode.isDeleted()) {
                max = treeNode;
            }

            // Continue going down the right side of the tree.
            treeNode = treeNode.getRightChild();
        }

        return max;
    }

    // AVLs are BSTs so the min value is always the very left-most tree node.
    public int findMin() {
        TreeNode min = findMin(this.getRoot());

        return (min != null) ? min.getKey() : -1;
    }

    private TreeNode findMin(TreeNode treeNode) {
        TreeNode min = null;

        while (treeNode != null) {
            if (!treeNode.isDeleted()) {
                min = treeNode;
            }

            // Continue going down the left side of the tree.
            treeNode = treeNode.getLeftChild();
        }

        return min;
    }

    public int height() {
        return height(this.getRoot());
    }

    private int height(TreeNode treeNode) {
        // Empty tree has the lowest integer for height.
        if (treeNode == null) {
            return Integer.MIN_VALUE;
        }

        try {
            int heightLeft = height(treeNode.getLeftChild());
            int heightRight = height(treeNode.getRightChild());

            // Add 1 to include the current node in this path.
            return 1 + Math.max(heightLeft, heightRight);
        }
        catch(StackOverflowError e) {
            System.err.println("Stack Overflow: Tree is too deep: " + e);
            return Integer.MIN_VALUE;
        }
    }

    public int size() {
        return size(this.getRoot());
    }

    private int size(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }

        try {
            return 1 + size(treeNode.getLeftChild()) + size(treeNode.getRightChild());
        }
        catch(StackOverflowError e) {
            System.err.println("Stack Overflow: Tree is too deep: " + e);
            return 0;
        }
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
