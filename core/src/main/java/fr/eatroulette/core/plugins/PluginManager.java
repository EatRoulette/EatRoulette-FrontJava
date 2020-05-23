package fr.eatroulette.core.plugins;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private File pluginFolder;
    private File[] listOfjars;


    public PluginManager(){
        this.pluginFolder = new File("plugin/");
        try {
            loadAllJar();
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading all jars in plugin directory
     */
    public void loadAllJar() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.listOfjars = this.pluginFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        for (File f: this.listOfjars){
            System.out.println(f.getPath());
//            System.out.println(f.getAbsolutePath());
//            JarFile jar = new JarFile(this.listOfjars[].getAbsolutePath());

            URL url = f.toURI().toURL(); //Because File.toURL is deprecated and the doc tell to do like this
            //Init classLoader
            ClassLoader classLoader = new URLClassLoader(new URL[] {url}, getClass().getClassLoader());

            JarFile jar = new JarFile(f.getAbsolutePath());
            Enumeration<JarEntry> enumeration = jar.entries();
            String className;

            while (enumeration.hasMoreElements()){
                JarEntry entry = enumeration.nextElement();
                if(entry.getName().endsWith(".class")){
                    className = entry.getName().substring(0, entry.getName().length() - 6);//.replace("/", ".")
                    System.out.println(className);
                    if(className.equals("Test")){
                        System.out.println("Toto");
                        Class<?> loadedClass = Class.forName(className, true, classLoader);
                        System.out.println("1-"+loadedClass.getCanonicalName());
                        Constructor<?> loadedClassContructor = loadedClass.getConstructor();
                        System.out.println("2-"+loadedClassContructor.getName());
                        Object instanceOfLodadClass = loadedClassContructor.newInstance();
                        Method method = loadedClass.getMethod("getName");
                        System.out.println(instanceOfLodadClass);
                        String name = (String) method.invoke(instanceOfLodadClass);

                        System.out.println("REFLECT NAME : " + name);
                    }
                    System.out.println("Found class => "+entry.getName());
                }
            }
        }
    }


    public void runPlugin() throws Exception {
//        if(pathPlugin.isEmpty() || pathPlugin.isBlank()){
//            throw new Exception("File path does not exist");
//        }
//
//        //Loading jar in memory
//        JarFile jar = new JarFile(pathPlugin);
//        //Loading jar content
//        Enumeration enumeration = jar.entries();
//
//
////      enumeration.hasMoreElements()


    }
}
