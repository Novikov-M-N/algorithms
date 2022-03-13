import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int TREE_COUNT = 20;

    public static void main(String[] args) {

        // 1. Создать и запустить программу для построения двоичного дерева
        List<BinaryTree<Integer>> treeList = new ArrayList<>();
        for (int i = 0; i < TREE_COUNT; i++) {
            treeList.add(IntegerBinaryTreeGenerator.generate(4));
        }

        // 2. Проанализировать, какой процент созданных деревьев являются несбалансированными
        int nonBalancedTrees = 0;
        for (BinaryTree<Integer> bt : treeList) {
            if (!bt.checkBalance()) {
                nonBalancedTrees++;
            }
            bt.display();
            System.out.println();
        }

        double percentOfNonBalanced = 1.0 * nonBalancedTrees / TREE_COUNT * 100;
        System.out.println("Percent of non-balanced trees = " + percentOfNonBalanced);

    }
}
