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



## CLI Mode

To run the program in cli mode you need to add *-cli* option. Example : 

```sh
java -jar EatRoulette.jar -cli
```



### Available commands 

```
EatRoulette-cli commands ...
*	add [restaurant | type | allergen | characteristic] [data]
*	upd [type | allergen | characteristic] id [data]
*	dell [restaurant | type | allergen | characteristic] id
*	run [plugin-name]
*	show [restaurants | types | allergens | characteristics | plugins]
*	help #To get this panel
*	exit #To quit the application
```

You can type help at every moment to get this reminder.