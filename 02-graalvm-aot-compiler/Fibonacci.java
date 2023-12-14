import java.util.stream.IntStream;

class Fibonacci {

    public static void main(String[] args) {

        var loopCount =  Integer.parseInt(args[1]);
        var value = Integer.parseInt(args[0]);

        for (int i = 0; i < loopCount; i++) {
            var result = fibonacci(value);

            if (i == loopCount - 1) {
                System.out.println(result);
            }
        }

    }

    private static int fibonacci(int value) {
        return value <= 1 ? value : fibonacci(value-1) + fibonacci(value-2);
    }
}