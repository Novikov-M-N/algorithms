import java.util.ArrayList;
import java.util.List;

public class BinaryTree<E extends Comparable<E>> {
    private Node<E> root;
    private int nodeMaxStringLength;    // Максимальная длина toString для узла - нужна для корректной работы display()
    private int depth;                  // Максимальная глубина дерева
    private boolean isBalanced;         // Флаг: сбалансировано ли дерево.

    public void add(E element) {
        Node<E> current = root;
        Node<E> parent = null;
        int currentDepth = 1;
        int compare = -1;
        boolean isLeft = true;

        // Ищем место для добавления
        while (current != null && compare != 0) {
            parent = current;
            compare = element.compareTo(current.payload);
            if (compare != 0) {
                if (compare > 0) {
                    current = current.right;
                    isLeft = false;
                } else {
                    current = current.left;
                    isLeft = true;
                }
                currentDepth++;
            }
        }
        if (current == null) {
            Node<E> newNode = new Node<>(element);
            if (root == null) {
                root = newNode;
            } else {
                if (isLeft) {
                    parent.left = newNode;
                } else {
                    parent.right = newNode;
                }
            }
        } else {
            current.count++;    // Если узел с таким содержимым уже имеется, увеличиваем его счётчик
        }
        if (currentDepth > depth) {
            depth = currentDepth;
        }
    }

    public int depth() { return depth; }

    public boolean checkBalance() {
        isBalanced = true;
        if (root != null) {
            maxDepth(root); // В ходе выполнения этой функции может измениться поле isBalanced
        }
        return isBalanced;
    }

    private int maxDepth(Node<E> node) {
        int result;
        if (node == null) {
            result = 0;
        } else {
            int maxLeft = maxDepth(node.left);
            int maxRight = maxDepth(node.right);
            result = Math.max(maxLeft, maxRight) + 1;
            if (Math.abs(maxLeft - maxRight) > 1) {
                isBalanced = false;
            }
        }
        return result;
    }

    public void display() {
        List<List<Node<E>>> levels = treeToNodeList();
        displayNodeList(levels);

    }

    // Конвертирует содержимое дерева в листы узлов по уровням
    private List<List<Node<E>>> treeToNodeList() {
        List<List<Node<E>>> result = new ArrayList<>();

        if (root != null) {
            List<Node<E>> level = new ArrayList<>();
            level.add(root);
            result.add(level);
            boolean isNextLevel;
            do {
                isNextLevel = false;
                List<Node<E>> nextLevel = new ArrayList<>();
                for (Node<E> node : level) {
                    if (node == null) {
                        nextLevel.add(null);
                        nextLevel.add(null);
                    } else {
                        if (!isNextLevel && (node.left != null || node.right != null)) {
                            isNextLevel = true;
                        }
                        nextLevel.add(node.left);
                        nextLevel.add(node.right);
                    }
                }
                if (isNextLevel) {
                    result.add(nextLevel);
                    level = nextLevel;
                }
            } while (isNextLevel);
        }

        return result;
    }

    private void displayNodeList(List<List<Node<E>>> nodeList) {
        int levelIndex = nodeList.size() - 1;
        List<List<String>> levels = nodeListToStringList(nodeList);
        for (List<String> level : levels) {
            StringBuilder levelSb = new StringBuilder();
            int space = ((1 << levelIndex) - 1) * nodeMaxStringLength;
            int halfSpace = space >> 1;
            levelSb.append(" ".repeat(halfSpace));

            for (String s : level) {
                if (levelIndex == 0) {
                    levelSb.append(cell(s, " ", nodeMaxStringLength + space));
                } else {
                    levelSb.append(cell(s, ".", nodeMaxStringLength + space));
                }
                levelSb.append(" ".repeat(space + nodeMaxStringLength));
            }
            levelSb.setLength(levelSb.length() - space - nodeMaxStringLength);
            levelIndex--;
            System.out.println(levelSb);
        }
    }

    // Дополняет строку до заданной длины указанными символами справа и слева
    private String cell(String s, String placeholder, int length) {
        int leftSpace = (length - s.length()) >> 1;
        int rightSpace = length - s.length() - leftSpace;
        return placeholder.repeat(leftSpace) + s + placeholder.repeat(rightSpace);
    }

    // Конвертирует листы узлов в листы строк на основе их toString(). Попутно находит максимальную длину строки узла
    private List<List<String>> nodeListToStringList(List<List<Node<E>>> nodeList) {
        List<List<String>> result = new ArrayList<>();

        String emptyString = "##";
        nodeMaxStringLength = emptyString.length();
        for (List<Node<E>> level : nodeList) {
            List<String> levelStrings = new ArrayList<>();
            result.add(levelStrings);
            for (Node<E> node : level) {
                if (node == null) {
                    levelStrings.add(emptyString);
                } else {
                    String s = node.toString();
                    levelStrings.add(s);
                    if (s.length() > nodeMaxStringLength) {
                        nodeMaxStringLength = s.length();
                    }
                }
            }
        }

        return result;
    }

    private class Node<T> {
        private T payload;
        private Node<T> left;
        private Node<T> right;
        private int count = 1;

        private Node(T payload) {
            this.payload = payload;

        }

        public String toString() {
            return payload.toString() + "(" + count + ")";
        }
    }
}
