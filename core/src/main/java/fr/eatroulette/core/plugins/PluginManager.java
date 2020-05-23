package fr.eatroulette.core.plugins;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarFile;

public class PluginManager {
    private File pluginFolder;
    private File[] listOfjars;


    public PluginManager(){
        this.pluginFolder = new File("plugin/");
        loadAllJar();
    }

    public void loadAllJar(){
        this.listOfjars = this.pluginFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        for (File f: this.listOfjars){
            System.out.println(f.getPath());
//            if (f.isFile()){
//                String fullyQualifiedName = f.getPath()
//                        .replace("lib\\", "")
//                        .replace("\\", ".")
//                        .replace(".class", "");
//
//                listClasses.add(fullyQualifiedName);
//            } else if(f.isDirectory()){
////                listClasses = findClasses(f);
//            }
        }

    }


    public void runPlugin(String pathPlugin) throws Exception {
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
