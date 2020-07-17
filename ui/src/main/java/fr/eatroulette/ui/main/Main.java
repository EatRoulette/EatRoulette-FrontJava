package fr.eatroulette.ui.main;

import fr.eatroulette.cli.main.CliMode;
import fr.eatroulette.ui.main.plugin.PluginController;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("-cli")) {
            System.out.println(args[0]);
            CliMode.main();
        } else {
            PluginController.main(args);
        }
    }

}