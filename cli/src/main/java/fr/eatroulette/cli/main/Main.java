package fr.eatroulette.cli.main;

import fr.eatroulette.core.plugin.PluginManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        PluginManager plManage = new PluginManager();
        System.out.println(plManage.getPluginsName());

        try {
            plManage.runPlugin(plManage.getPluginsName().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

}
