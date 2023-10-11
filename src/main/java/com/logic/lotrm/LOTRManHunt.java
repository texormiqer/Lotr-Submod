package com.logic.lotrm;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LOTRManHunt.MODID, name = LOTRManHunt.MODNAME, version = LOTRManHunt.VERSION, dependencies = "required-after:lotr")
public class LOTRManHunt
{
    @Mod.Instance(LOTRManHunt.MODID)
    public static LOTRManHunt instance;
    public static final String MODID = "lotrm";
    public static final String VERSION = "0.1";

    public static final String MODNAME = "LOTRManhunt";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
