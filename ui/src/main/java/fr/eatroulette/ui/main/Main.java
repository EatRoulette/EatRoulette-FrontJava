package fr.eatroulette.ui.main;

import fr.eatroulette.cli.CliMode;
import fr.eatroulette.ui.main.plugin.PluginController;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-cli")) {
                CliMode.main();
            }
        } else {
            PluginController.main(args);
        }
    }

}