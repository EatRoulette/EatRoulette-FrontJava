package fr.eatroulette.core.plugin;

/**
 * First version of the interface for the plugins software.
 * This interface need to be implemented to create a plugin.
 */
public interface EatRoulettePlugin {

    /**
     * Get the plugin name
     * @return
     */
    String getPluginName();

    /**
     * Run
     */
    void run();

}
