import java.lang.reflect.Method;

class CountUppercase {
    static void process(String sentence) {
        for (int iter = 0; iter < 1; iter++) {

            long total = 0, start = System.currentTimeMillis(), last = start;

            for (int i = 1; i < 10_000_000; i++) {

                total += sentence.chars().filter(Character::isUpperCase).count();

                if (i % 1_000_000 == 0) {
                    long now = System.currentTimeMillis();
                    System.out.printf("%d (%d ms)%n", i / 1_000_000, now - last);
                    last = now;
                }

            }

            System.out.printf("total: %d (%d ms)%n", total, System.currentTimeMillis() - start);
        }
    }
}

public class Task {

    public static void main(String[] args) throws ReflectiveOperationException {

        var program = args[0];
        var input = args[1];

        Class<?> clazz = Class.forName(program);
        Method method = clazz.getDeclaredMethod("process", String.class);
        method.invoke(null, input);
    }
}