package com.logic.lotrm;


import com.logic.lotrm.common.config.WorldConfigJSON;
import com.logic.lotrm.common.utils.ModUtils;
import com.logic.lotrm.common.world.ModWorld;
import com.logic.lotrm.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lotr.common.LOTRDimension;

@Mod(modid = LOTRManHunt.MODID, name = LOTRManHunt.MODNAME, version = LOTRManHunt.VERSION, dependencies = "required-after:lotr")
public class LOTRManHunt
{
    @Mod.Instance(LOTRManHunt.MODID)
    public static LOTRManHunt instance;
    public static final String MODID = "lotrm";
    public static final String VERSION = "0.1";
    public static final String MODNAME = "LOTRManhunt";
    @SidedProxy(clientSide = "com.logic.lotrm.proxy.ClientProxy", serverSide = "com.logic.lotrm.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        WorldConfigJSON.init();
        ModUtils.mapPath = "textures/map/map.png";
        ModWorld.WorldUtils.preInit();

        proxy.preInit(event);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModWorld.WorldUtils.init();
        proxy.init(event);
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModUtils.setGenMap(LOTRManHunt.MODID, LOTRDimension.MIDDLE_EARTH);
        proxy.postInit(event);
    }

}
