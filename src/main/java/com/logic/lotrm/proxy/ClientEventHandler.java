package com.logic.lotrm.proxy;

import com.logic.lotrm.common.utils.ModUtils;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class ClientEventHandler implements IResourceManagerReloadListener
{
    @Override
    public void onResourceManagerReload(IResourceManager p_110549_1_)
    {
        ModUtils.setClientMap();
    }
}
