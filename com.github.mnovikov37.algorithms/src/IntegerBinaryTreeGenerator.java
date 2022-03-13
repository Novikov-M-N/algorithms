import java.util.Random;

public class IntegerBinaryTreeGenerator {
    public static BinaryTree<Integer> generate(int depth) {
        BinaryTree<Integer> result = new BinaryTree<>();
        Random rnd = new Random();
        while (result.depth() < depth){
            result.add(rnd.nextInt(51) - 25);
        }
        return result;
    }
}
