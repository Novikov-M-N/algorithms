/**
 * Требуется отыскать наибольший делитель числа 600851475143, являющийся простым числом
 */
public class SimpleDivisor {

    // Зафиксируем исходные данные в константе N
    private final static long N = 600851475143L;

    public static void main(String[] args) {
        // Введём переменную для искомого значения. В самом худшем случае
        // наибольшим простым делителем числа будет само это число.
        long result = N;

        // Введём вспомогательную переменную n. Пригодится в цикле.
        long n = N;

        // Из математики известно, что наибольший простой делитель числа не может быть
        // больше квадратного корня из этого числа.
        // Поэтому имеет смысл проверять числа только до корня из N.
        // Вводим предел счётчика цикла
        double counterLimit = Math.sqrt(n);

        // Начинаем с двойки - она является наименьшим простым числом.
        for (long i = 2; i <= counterLimit; i++) {
            if (n % i == 0) { // Условие нахождения делителя - целочисленное деление без остатка

                // Каждый делитель, находимый данным циклом, является наименьшим возможным делителем
                // рассматриваемого числа. А наименьший возможный делитель числа
                // всегда будет простым числом, поэтому можно записывать его сразу в результат,
                // не проверяя, является ли он простым числом.
                result = i;
                // Если делитель найден, можно смело делить на него N
                // и дальнейший поиск проводить уже для числа (N/i),
                // т.к., если N делится на i, то для (N/i) не может существовать делителей,
                // меньших, чем i. Так же, если есть простой делитель числа N, больший, чем i,
                // то он будет и делителем числа (N/i). Обновляем предел счётчика цикла:
                n = N/i;
                counterLimit = Math.sqrt(n);
            }
        }

        System.out.println("result: " + result);
    }
}
