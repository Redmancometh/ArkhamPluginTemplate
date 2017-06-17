package org.arkhamnetwork.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.arkhamnetwork.example.config.ExampleConfig;
import org.arkhamnetwork.example.objects.ExampleObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;

import com.redmancometh.redcore.RedPlugin;
import com.redmancometh.redcore.config.ConfigManager;
import com.redmancometh.redcore.mediators.ObjectManager;

public class ArkhamExample extends JavaPlugin implements RedPlugin
{
    // The SessionFactory is the hibernate factory which provides us sessions to query objects
    private SessionFactory factory;
    // The classList is a list of the classes we need SubDatabases for. 
    // Let's try and limit this to once a plugin, and not test if it works properly with mutliples.
    private List<Class> classList = new CopyOnWriteArrayList();
    // ExampleManager is the ObjectManager for this class. 
    // It satisfies the abstract method RedPlugin.getManager()
    private ExampleManager exampleObjectManager;
    // This is the gson-backed config manager. 
    // Very easy to use, and provides our reload, get config, etc methods.
    private ConfigManager<ExampleConfig> configManager;

    public void onEnable()
    {
        // First thing to do is instnatiate the instances of all of our objects
        this.configManager = new ConfigManager("profiles.json", ExampleConfig.class);
        this.exampleObjectManager = new ExampleManager();
        // We want to persist ExampleObject, so we need a subdatabase for it. 
        classList.add(ExampleObject.class);
        // Always initialize the configManager before calling RedPlugin.enable()!
        configManager.init(this);
        // Always call enable after adding all persisteed classes to the classList
        // Init will initialize the SubDatabases, and get hibernate set up for this plugin
        this.enable();
        Bukkit.getOnlinePlayers().forEach((player) -> ArkhamExample.getProfiles().getRecord(player.getUniqueId()).thenAccept((profile) -> Bukkit.getScheduler().scheduleSyncDelayedTask(ArkhamExample.getInstance(), () ->
        {
        })));
    }

    @Override
    public void onDisable()
    {
        // First call bukkit's onEnable method
        super.onDisable();
        // Now call RedCore's disable method to wipe the plugin from it's maps, and power down all the hibernate stuff
        this.disable();
    }

    public void reloadConfig()
    {
        // ConfigManager.init(JavaPlugin plugin) can also be called to reload the plugin.
        // The SlowPollerTask in RedCore will automatically refresh this every 6000 ticks (5 minutes)
        configManager.init(this);
    }

    public SessionFactory getFactory()
    {
        return factory;
    }

    public void setFactory(SessionFactory factory)
    {
        this.factory = factory;
    }

    public List<Class> getClassList()
    {
        return classList;
    }

    public void setClassList(List<Class> classList)
    {
        this.classList = classList;
    }

    public static ExampleManager getProfiles()
    {
        return getInstance().getStatManager();
    }

    public static ExampleConfig getCfg()
    {
        return getInstance().getConfigManager().getCurrentConfig();
    }

    public ExampleManager getStatManager()
    {
        return exampleObjectManager;
    }

    public void setStatManager(ExampleManager statManager)
    {
        this.exampleObjectManager = statManager;
    }

    public static ArkhamExample getInstance()
    {
        return (ArkhamExample) Bukkit.getPluginManager().getPlugin("ArkhamProfiles");
    }

    @Override
    public JavaPlugin getBukkitPlugin()
    {
        return this;
    }

    @Override
    public SessionFactory getInternalFactory()
    {
        return factory;
    }

    @Override
    public List<Class> getMappedClasses()
    {
        return classList;
    }

    @Override
    public void setInternalFactory(SessionFactory newFactory)
    {
        this.factory = newFactory;
    }

    public ConfigManager<ExampleConfig> getConfigManager()
    {
        return configManager;
    }

    public void setConfigManager(ConfigManager<ExampleConfig> configManager)
    {
        this.configManager = configManager;
    }

    // This can be called from any RedPlugin.
    // To get a RedPlugin instance from the Bukkit plugin
    // Use RedCore.getInstance().get
    @Override
    public ObjectManager getManager()
    {
        return exampleObjectManager;
    }

}
