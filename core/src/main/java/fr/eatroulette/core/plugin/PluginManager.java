package fr.eatroulette.core.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This plugin manager class can load and run plugins
 */
public class PluginManager {
    private HashMap<String, File> hashMap = new HashMap<String, File>();
    private File pluginFolder;
    private File[] listOfjars;
    private List<String> listPluginName = new ArrayList<String>();


    public PluginManager(){
        this.pluginFolder = new File(PluginManagerConfig.PLUGIN_DIR);
        this.loadAllJar();
    }

    /**
     * Loading all jars in plugin directory
     */
    public void loadAllJar() {
        this.hashMap.clear();
        this.listPluginName.clear();

        this.listOfjars = this.pluginFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        for (File f: this.listOfjars) {
            String pluginName = this.makePluginName(f);
            this.listPluginName.add(pluginName);
            this.hashMap.put(pluginName, f);
        }
    }

    /**
     * Get the lists
     * @return
     */
    public List<String> getPluginsName(){
        return this.listPluginName;
    }

    /**
     * Build the plugin name
     * @param f
     * @return
     */
    private String makePluginName(File f){
        return f.getName().replace(".jar", "");
    }

    /**
     * Run the plugin specified
     * @param pluginName
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public void runPlugin(String pluginName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        File f = this.hashMap.get(pluginName);

        URL url = f.toURI().toURL(); //Because File.toURL is deprecated and the doc tell to do like this
        //Init classLoader
        ClassLoader classLoader = new URLClassLoader(new URL[] {url}, getClass().getClassLoader());

        JarFile jar = new JarFile(f.getAbsolutePath());
        Enumeration<JarEntry> enumeration = jar.entries();
        String className;

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            // Filter on ".class"
            if(entry.getName().endsWith(".class")){
                //Get FQN (example fr.eatroulette.test.myClass)
                className = entry.getName().substring(0, entry.getName().length() - 6).replace("/", ".");

                if(className.endsWith(PluginManagerConfig.PLUGIN_CLASS)){
                    Class<?> loadedClass = Class.forName(className, true, classLoader);
                    Constructor<?> loadedClassContructor = loadedClass.getConstructor();

                    Object instanceOfLodadClass = loadedClassContructor.newInstance();

                    Method method = loadedClass.getMethod(PluginManagerConfig.PLUGIN_METHOD);

                    //Run plugin method
                    method.invoke(instanceOfLodadClass);
                }
            }
        }
    }

}
