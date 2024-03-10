package com.logic.lotrm;


import com.logic.lotrm.common.config.WorldConfigJSON;
import com.logic.lotrm.common.faction.FactionUtils;
import com.logic.lotrm.common.utils.ModUtils;
import com.logic.lotrm.common.world.ModWorld;
import com.logic.lotrm.proxy.CommonProxy;
import com.logic.lotrm.proxy.Events;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import lotr.common.LOTRDimension;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

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
    public static Events events;
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
        events = new Events();
        ModWorld.WorldUtils.init();
        FactionUtils.init();

        proxy.init(event);
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModUtils.setGenMap(LOTRManHunt.MODID, LOTRDimension.MIDDLE_EARTH);
        proxy.postInit(event);
    }
    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event)
    {
        for (Object playerObject : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
        {
            if (playerObject instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = (EntityPlayerMP) playerObject;
                NBTTagCompound playerData = player.getEntityData();
                String key = "hasJoinedBefore";
                playerData.setBoolean(key, true);
            }
        }
    }
}
