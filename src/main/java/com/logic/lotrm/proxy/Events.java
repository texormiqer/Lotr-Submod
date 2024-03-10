package com.logic.lotrm.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

public class Events
{
    public Events()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    // Coordinates for the initial teleportation location
    private double initialTeleportX;
    private double initialTeleportY;
    private double initialTeleportZ;

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            /*
            * Get the player NBT data tag
            */
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            NBTTagCompound playerData = player.getEntityData();
            String key = "hasJoinedBefore";

            /*
            * if player hasKey = if player joined server before
            * if player joined server before, don't teleport him anymore
            */
            if (!playerData.hasKey(key))
            {
                playerData.setBoolean(key, true);
                teleportPlayer(player, -90704, LOTRWaypoint.WAYMEET.getY(), -81648);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            NBTTagCompound playerData = player.getEntityData();
            String key = "hasJoinedBefore";
            playerData.setBoolean(key, true);
        }
    }


    public void teleportPlayer(EntityPlayerMP player, double x, double y, double z) {
        WorldServer world = MinecraftServer.getServer().worldServerForDimension(player.dimension);
        player.setPositionAndUpdate(x, y, z);
    }
}
