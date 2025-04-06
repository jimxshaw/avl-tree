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

    public boolean insert(int key) {
        // Part A: make sure the key is within range.
        validateKey(key);

        // Part B: Insert the new node or re-activate the deleted node.
        TreeNode currentNode = root;
        TreeNode parentNode = null;
        boolean isInserted = false;

        while (currentNode != null) {
            parentNode = currentNode;

            if (key < currentNode.getKey()) {
                currentNode = currentNode.getLeftChild();
            }
            else if (key > currentNode.getKey()) {
                currentNode = currentNode.getRightChild();
            }
            else {
                // Getting here means the key already exists.
                // If the key is deleted then re-activate it.
                if (currentNode.isDeleted()) {
                    currentNode.undelete();
                    isInserted = true;
                }

                // Should return false if the key
                // exists and is active.
                return isInserted;
            }
        }

        // If the current node is null then we have reached a Leaf node
        // and should insert the node here.
        TreeNode newTreeNode = new TreeNode(key);

        // If the new node has no parent then it must be the root.
        // By AVL rules, if the key is smaller than the parent's key
        // then the new node must be inserted to the parent's left.
        // If the key is larger than the parent's key then the new node
        // must be inserted to the parent's right.
        if (parentNode == null) {
            this.setRoot(newTreeNode);
        }
        else if (key < parentNode.getKey()) {
            parentNode.setLeftChild(newTreeNode);
        }
        else {
            parentNode.setRightChild(newTreeNode);
        }

        // Mark that a new node has been successfully inserted.
        isInserted = true;

        // Part C: Re-balance the tree and deal with rotations.



        return isInserted;
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

    public String toString() {
        return "";
    }

    private static class TreeNode {
        private int key;
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

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
