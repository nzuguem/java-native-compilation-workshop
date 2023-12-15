class CountUppercase {

    static final int ITERATIONS = Integer.getInteger("iterations", 1);
    static final int SENTENCE_ITERATIONS = Integer.getInteger("sentenceIterations", 10_000_000);

    public static void main(String[] args) {

        String sentence = String.join(" ", args);

        for (int iter = 0; iter < ITERATIONS; iter++) {

            if (ITERATIONS != 1) System.out.println("-- iteration " + (iter + 1) + " --");

            long total = 0, start = System.currentTimeMillis(), last = start;

            for (int i = 1; i < SENTENCE_ITERATIONS; i++) {

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