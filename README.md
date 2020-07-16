# EatRoulette-FrontJava

This is the Java Front End Admin Application of the EatRoulette project.



## Create plugin

To create a plugin you will need :

- An IDE
- A Java project



### Add the interface *EatRoulettePlugin*

You will need to create a Java class named EatRoulettePlugin.java with the following content :

```java
public interface EatRoulettePlugin {

    void run();
    
}
```



### Add class *EatRouletteEntry*

Add class **EatRouletteEntry**, it will be the entry point to be launched by our application.  This class have to implement the previous interface like this:

```java
public class EatRouletteEntry implements EatRoulettePlugin {

    public void run() {
        System.out.println("Do your work here ...");
    }
    
}
```



You can now develop your plugins.