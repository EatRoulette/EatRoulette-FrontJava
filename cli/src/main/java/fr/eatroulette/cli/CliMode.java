package fr.eatroulette.cli;

import fr.eatroulette.cli.interpreter.CliInterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CliMode {

    public static void main() {
        launchCli();
    }

    /**
     * Launch cli mode
     */
    private static void launchCli(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("------- EatRoulette admin - Cli -------");
        String input = "";
        CliInterpreter cliInter = new CliInterpreter();

        while (!input.equals("exit")){
            System.out.print("EatRoulette> ");
            input = scanner.nextLine();
            ArrayList<String> args = new ArrayList<String>(Arrays.asList(input.split(" ")));
            if (!args.get(0).equals("exit") && !args.get(0).equals("")) {
                cliInter.setArgs(args);
                cliInter.interpreter();
            }
        }
    }
}
