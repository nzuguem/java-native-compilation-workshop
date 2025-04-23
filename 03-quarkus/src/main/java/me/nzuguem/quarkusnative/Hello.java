package me.nzuguem.quarkusnative;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Hello {
    
    // This piece of code must be evaluated during the initialisation phase at build-time
    // The consequence is that System.currentTimeMillis() will be frozen in the resulting native image: This shows the use cases to be avoided with native images 
    public final static String MESSAGE = "Hello from Reflection - " + System.currentTimeMillis();

}
