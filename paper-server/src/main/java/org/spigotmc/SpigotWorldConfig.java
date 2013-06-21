package org.spigotmc;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpigotWorldConfig
{

    private final String worldName;
    private final YamlConfiguration config;
    private boolean verbose;

    public SpigotWorldConfig(String worldName)
    {
        this.worldName = worldName;
        this.config = SpigotConfig.config;
        this.init();
    }

    public void init()
    {
        this.verbose = this.getBoolean( "verbose", true );

        this.log( "-------- World Settings For [" + this.worldName + "] --------" );
        SpigotConfig.readConfig( SpigotWorldConfig.class, this );
    }

    private void log(String s)
    {
        if ( this.verbose )
        {
            Bukkit.getLogger().info( s );
        }
    }

    private void set(String path, Object val)
    {
        this.config.set( "world-settings.default." + path, val );
    }

    public boolean getBoolean(String path, boolean def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return this.config.getBoolean( "world-settings." + this.worldName + "." + path, this.config.getBoolean( "world-settings.default." + path ) );
    }

    public double getDouble(String path, double def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return this.config.getDouble( "world-settings." + this.worldName + "." + path, this.config.getDouble( "world-settings.default." + path ) );
    }

    public int getInt(String path)
    {
        return this.config.getInt( "world-settings." + this.worldName + "." + path );
    }

    public int getInt(String path, int def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return this.config.getInt( "world-settings." + this.worldName + "." + path, this.config.getInt( "world-settings.default." + path ) );
    }

    public <T> List getList(String path, T def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return (List<T>) this.config.getList( "world-settings." + this.worldName + "." + path, this.config.getList( "world-settings.default." + path ) );
    }

    public String getString(String path, String def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return this.config.getString( "world-settings." + this.worldName + "." + path, this.config.getString( "world-settings.default." + path ) );
    }

    private Object get(String path, Object def)
    {
        this.config.addDefault( "world-settings.default." + path, def );
        return this.config.get( "world-settings." + this.worldName + "." + path, this.config.get( "world-settings.default." + path ) );
    }

    // Crop growth rates
    public int cactusModifier;
    public int caneModifier;
    public int melonModifier;
    public int mushroomModifier;
    public int pumpkinModifier;
    public int saplingModifier;
    public int beetrootModifier;
    public int carrotModifier;
    public int potatoModifier;
    public int wheatModifier;
    public int wartModifier;
    public int vineModifier;
    public int cocoaModifier;
    public int bambooModifier;
    public int sweetBerryModifier;
    public int kelpModifier;
    public int twistingVinesModifier;
    public int weepingVinesModifier;
    public int caveVinesModifier;
    private int getAndValidateGrowth(String crop)
    {
        int modifier = this.getInt( "growth." + crop.toLowerCase(java.util.Locale.ENGLISH) + "-modifier", 100 );
        if ( modifier == 0 )
        {
            this.log( "Cannot set " + crop + " growth to zero, defaulting to 100" );
            modifier = 100;
        }
        this.log( crop + " Growth Modifier: " + modifier + "%" );

        return modifier;
    }
    private void growthModifiers()
    {
        this.cactusModifier = this.getAndValidateGrowth( "Cactus" );
        this.caneModifier = this.getAndValidateGrowth( "Cane" );
        this.melonModifier = this.getAndValidateGrowth( "Melon" );
        this.mushroomModifier = this.getAndValidateGrowth( "Mushroom" );
        this.pumpkinModifier = this.getAndValidateGrowth( "Pumpkin" );
        this.saplingModifier = this.getAndValidateGrowth( "Sapling" );
        this.beetrootModifier = this.getAndValidateGrowth( "Beetroot" );
        this.carrotModifier = this.getAndValidateGrowth( "Carrot" );
        this.potatoModifier = this.getAndValidateGrowth( "Potato" );
        this.wheatModifier = this.getAndValidateGrowth( "Wheat" );
        this.wartModifier = this.getAndValidateGrowth( "NetherWart" );
        this.vineModifier = this.getAndValidateGrowth( "Vine" );
        this.cocoaModifier = this.getAndValidateGrowth( "Cocoa" );
        this.bambooModifier = this.getAndValidateGrowth( "Bamboo" );
        this.sweetBerryModifier = this.getAndValidateGrowth( "SweetBerry" );
        this.kelpModifier = this.getAndValidateGrowth( "Kelp" );
        this.twistingVinesModifier = this.getAndValidateGrowth( "TwistingVines" );
        this.weepingVinesModifier = this.getAndValidateGrowth( "WeepingVines" );
        this.caveVinesModifier = this.getAndValidateGrowth( "CaveVines" );
    }

    public double itemMerge;
    private void itemMerge()
    {
        this.itemMerge = this.getDouble("merge-radius.item", 2.5 );
        this.log( "Item Merge Radius: " + this.itemMerge );
    }

    public double expMerge;
    private void expMerge()
    {
        this.expMerge = this.getDouble("merge-radius.exp", 3.0 );
        this.log( "Experience Merge Radius: " + this.expMerge );
    }

    public int viewDistance;
    private void viewDistance()
    {
        if ( SpigotConfig.version < 12 )
        {
            this.set( "view-distance", null );
        }

        Object viewDistanceObject = this.get( "view-distance", "default" );
        this.viewDistance = ( viewDistanceObject ) instanceof Number ? ( (Number) viewDistanceObject ).intValue() : -1;
        if ( this.viewDistance <= 0 )
        {
            this.viewDistance = Bukkit.getViewDistance();
        }

        this.viewDistance = Math.max( Math.min( this.viewDistance, 32 ), 3 );
        this.log( "View Distance: " + this.viewDistance );
    }

    public int simulationDistance;
    private void simulationDistance()
    {
        Object simulationDistanceObject = this.get( "simulation-distance", "default" );
        this.simulationDistance = ( simulationDistanceObject ) instanceof Number ? ( (Number) simulationDistanceObject ).intValue() : -1;
        if ( this.simulationDistance <= 0 )
        {
            this.simulationDistance = Bukkit.getSimulationDistance();
        }

        this.log( "Simulation Distance: " + this.simulationDistance );
    }

    public byte mobSpawnRange;
    private void mobSpawnRange()
    {
        this.mobSpawnRange = (byte) this.getInt( "mob-spawn-range", 6 );
        this.log( "Mob Spawn Range: " + this.mobSpawnRange );
    }
}
