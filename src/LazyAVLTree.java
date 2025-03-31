public class LazyAVLTree {
    private TreeNode root;

    public LazyAVLTree() {
        this.root = null;
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
        return 0;
    }

    public int size() {
        return 0;
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
