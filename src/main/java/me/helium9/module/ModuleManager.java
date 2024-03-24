package me.helium9.module;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

@Getter
public final class ModuleManager {

    private final HashMap<Class<? extends Module>, Module> modules;

    public ModuleManager(){
        this.modules=new HashMap<>();
        register();
    }

    public Module getModule(Class<? extends Module> mod){
        return modules.get(mod);
    }

    public Module getModule(String name){
        for(Module mod: modules.values()){
            if(mod.getName().equalsIgnoreCase(name)){
                return mod;
            }
        }
        return null;
    }

    public Module getModuleByCat(Category cat){
        for(Module mod: modules.values()){
            if(mod.getCategory() == cat){
                return mod;
            }
        }
        return null;
    }

    public Module[] getModulesByCat(Category cat){
        return getModules().values().stream().filter(module -> module.getCategory()==cat).toArray(Module[]::new);
    }

    public final void register(){
        final Reflections refl = new Reflections("me.helium9.module.impl");

        final Set<Class<? extends Module>> classes = refl.getSubTypesOf(Module.class);

        for(Class<? extends Module> aClass : classes){
            try {
                final Module feat = aClass.newInstance();
                modules.put(aClass, feat);
            }catch (InstantiationException | IllegalAccessException ignored){

            }
        }
    }

    public void unregister(Module... module){
        for(Module mod : module){
            modules.remove(mod.getClass());
        }
    }

}
