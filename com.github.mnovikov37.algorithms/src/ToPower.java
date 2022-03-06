public class ToPower {
    public static double toPower(double base, int power) {
        double result = 1;
        if (power > 0) {
            result = base * toPower(base, power - 1);
        }
        if (power < 0) {
            result = 1 / toPower(base, -power);
        }
        return result;
    }
}
