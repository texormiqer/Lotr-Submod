package com.logic.lotrm.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;

public class ClientProxy extends CommonProxy
{
    private ClientEventHandler clientEventHandler;

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        clientEventHandler = new ClientEventHandler();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(clientEventHandler);
    }

}
