public class LazyAVLTree {
    private TreeNode root;
    private String lastRotationType;

    private static final int KEY_LOWER_LIMIT = 1;
    private static final int KEY_UPPER_LIMIT = 99;

    private static final String NO_ROTATION = "NoRotation";
    private static final String SINGLE_ROTATION = "SingleRotation";
    private static final String DOUBLE_ROTATION = "DoubleRotation";

    public LazyAVLTree() {
        this.root = null;
    }

    private TreeNode getRoot() {
        return this.root;
    }

    private void setRoot(TreeNode newRootNode) {
        root = newRootNode;
    }

    public String getLastRotationType() {
        return this.lastRotationType;
    }

    public boolean insert(int key) {
        // Part A: make sure the key is within range.
        validateKey(key);


    }

    // Returns the height of the node or -1 if null.
    private int getHeight(TreeNode treeNode) {
        return treeNode == null ? -1 : treeNode.getHeight();
    }

    // According to AVL definition, for every tree node x: -1 <= getBalance(x) <= 1.
    private int getBalance(TreeNode treeNode) {
        int leftChildHeight = (treeNode.getLeftChild() != null) ? treeNode.getLeftChild().getHeight() : -1;
        int rightChildHeight = (treeNode.getRightChild() != null) ? treeNode.getRightChild().getHeight() : -1;

        return leftChildHeight - rightChildHeight;
    }

    private TreeNode rotateLeft(TreeNode oldRootNode) {

    }

    private TreeNode rotateRight(TreeNode oldRootNode) {

    }


    public boolean delete(int key) {
        validateKey(key);

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
        validateKey(key);

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

    private static void validateKey(int key) {
        if (key < KEY_LOWER_LIMIT || key > KEY_UPPER_LIMIT) {
            throw new IllegalArgumentException("Key must be between " + KEY_LOWER_LIMIT + " and " + KEY_UPPER_LIMIT);
        }
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
        // Empty tree has -1 for height by definition.
        if (treeNode == null) {
            return -1;
        }

        try {
            int heightLeft = height(treeNode.getLeftChild());
            int heightRight = height(treeNode.getRightChild());

            // Add 1 to include the current node in this path.
            return 1 + Math.max(heightLeft, heightRight);
        }
        catch(StackOverflowError e) {
            System.err.println("Stack Overflow: Tree is too deep: " + e);
            return -1;
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


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        preOrderToString(this.root, builder);

        return builder.toString().trim();
    }

    // Must be pre-order traversal in this exact order:
    // 1) Check the current node and print the value.
    // 2) Traverse the left subtree.
    // 3) Traverse the right subtree.
    // E.g. if root node 45 has left child 30 and right child 47,
    // 30 has left child 2 and right child 5 (deleted), 47 has
    // right child 50, 50 has right child 60 (deleted) then the
    // pre-order traversal output is 45 30 2 *5 47 50 *60.
    private void preOrderToString(TreeNode treeNode, StringBuilder builder) {
        if (treeNode == null) {
            return;
        }

        // Soft deleted keys are still printed but with a prepended asterisk.
        if (treeNode.isDeleted()) {
            builder.append("*");
        }

        builder.append(treeNode.getKey()).append(" ");

        preOrderToString(treeNode.getLeftChild(), builder);
        preOrderToString(treeNode.getRightChild(), builder);
    }

    private static class TreeNode {
        private final int key;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private boolean deleted;
        private int height;

        TreeNode(int key) {
            this.key = key;
            this.leftChild = null;
            this.rightChild = null;
            this.deleted = false;
            this.height = 0;
        }

        TreeNode(int key, TreeNode leftChild, TreeNode rightChild) {
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.deleted = false;
            this.height = 0;
        }

        public int getKey() {
            return this.key;
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

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
