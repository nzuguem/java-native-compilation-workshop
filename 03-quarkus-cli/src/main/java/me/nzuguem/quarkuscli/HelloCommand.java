package me.nzuguem.quarkuscli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "hello", mixinStandardHelpOptions = true)
public class HelloCommand implements Runnable {

    @Parameters(paramLabel = "<name>", defaultValue = "picocli",
        description = "Your name.")
    String name;

    @Override
    public void run() {
        System.out.printf("Hello %s, go go commando!\n", name);
    }

}
