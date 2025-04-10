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

                    this.lastRotationType = NO_ROTATION;
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
        // Begin from the parent node of the newly inserted/re-activated node.
        TreeNode treeNode = parentNode;

        // No Rotation is the default.
        this.lastRotationType = NO_ROTATION;

        while (treeNode != null) {
            updateHeight(treeNode);
            int balance = getBalance(treeNode);

            // Heavy on the left side.
            if (balance > 1) {
                if (key < treeNode.getLeftChild().getKey()) {
                    treeNode = rotateRight(treeNode);
                    this.lastRotationType = SINGLE_ROTATION;
                }
                else {
                    treeNode.setLeftChild(rotateLeft(treeNode.getLeftChild()));
                    treeNode = rotateRight(treeNode);
                    this.lastRotationType = DOUBLE_ROTATION;
                }
            }
            // Heavy on the right side.
            else if (balance < -1) {
                if (key > treeNode.getRightChild().getKey()) {
                    treeNode = rotateLeft(treeNode);
                    this.lastRotationType = SINGLE_ROTATION;
                }
                else {
                    treeNode.setRightChild(rotateRight(treeNode.getRightChild()));
                    treeNode = rotateLeft(treeNode);
                    this.lastRotationType = DOUBLE_ROTATION;
                }
            }

            // Re-balance by moving back up the tree.
            treeNode = treeNode.getParent();
        }

        // Reaching the end of this method means the new node has been
        // successfully inserted and isInserted will always be true.
        return isInserted;
    }

    private void updateHeight(TreeNode treeNode) {
        int leftChildHeight = (treeNode.getLeftChild() != null) ? treeNode.getLeftChild().getHeight() : -1;
        int rightChildHeight = (treeNode.getRightChild() != null) ? treeNode.getRightChild().getHeight() : -1;

        treeNode.setHeight(1 + Math.max(leftChildHeight, rightChildHeight));
    }

    // According to AVL definition, for every tree node x: -1 <= getBalance(x) <= 1.
    private int getBalance(TreeNode treeNode) {
        int leftChildHeight = (treeNode.getLeftChild() != null) ? treeNode.getLeftChild().getHeight() : -1;
        int rightChildHeight = (treeNode.getRightChild() != null) ? treeNode.getRightChild().getHeight() : -1;

        return leftChildHeight - rightChildHeight;
    }

    private TreeNode rotateLeft(TreeNode oldRootNode) {
        // The right child becomes the new root of this subtree.
        TreeNode newRootNode = oldRootNode.getRightChild();
        // This is the subtree that will be orphaned during rotation.
        TreeNode orphanSubtree = newRootNode.getLeftChild();

        // Execute rotation.
        // The old root becomes left child of the new root.
        newRootNode.setLeftChild(oldRootNode);
        // Attach the orphaned subtree as the old root's right child.
        oldRootNode.setRightChild(orphanSubtree);

        // Update the parent pointers.
        // The new root adopts the old root's parent.
        newRootNode.setParent(oldRootNode.getParent());
        // The old root becomes the child of new root.
        oldRootNode.setParent(newRootNode);

        if (orphanSubtree != null) {
            // The orphan subtree now has oldRootNode as its parent.
            orphanSubtree.setParent(oldRootNode);
        }

        // Update heights after rotation.
        updateHeight(oldRootNode);
        updateHeight(newRootNode);

        // This is now the root of this subtree.
        return newRootNode;
    }

    private TreeNode rotateRight(TreeNode oldRootNode) {
        // The left child becomes the new root of this subtree.
        TreeNode newRootNode = oldRootNode.getLeftChild();
        // This is the subtree that will be orphaned during rotation.
        TreeNode orphanSubtree = newRootNode.getRightChild();

        // Execute rotation.
        // The old root becomes the right child of new root.
        newRootNode.setRightChild(oldRootNode);
        // Attach the orphaned subtree as the old root's left child.
        oldRootNode.setLeftChild(orphanSubtree);

        // Update parent pointers.
        // The new root adopts the old root's parent.
        newRootNode.setParent(oldRootNode.getParent());
        // The old root becomes the child of new root.
        oldRootNode.setParent(newRootNode);

        // The orphan subtree now has oldRootNode as its parent.
        if (orphanSubtree != null) {
            orphanSubtree.setParent(oldRootNode);
        }

        // Update heights after rotation.
        updateHeight(oldRootNode);
        updateHeight(newRootNode);

        // This is now the root of this subtree.
        return newRootNode;
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
        private TreeNode parent;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private boolean deleted;
        private int height;

        TreeNode(int key) {
            this.key = key;
            this.parent = null;
            this.leftChild = null;
            this.rightChild = null;
            this.deleted = false;
            this.height = 0;
        }

        public int getKey() {
            return this.key;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeftChild() {
            return this.leftChild;
        }

        public void setLeftChild(TreeNode newLeftChild) {
            leftChild = newLeftChild;

            if (leftChild != null) {
                leftChild.setParent(this);
            }
        }

        public TreeNode getRightChild() {
            return this.rightChild;
        }

        public void setRightChild(TreeNode newRightChild) {
            rightChild = newRightChild;

            if (rightChild != null) {
                rightChild.setParent(this);
            }
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
