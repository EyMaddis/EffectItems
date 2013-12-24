package com.kaleydra.manager.tutorial;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

	EffectItems plugin;
	
	public ItemListener(EffectItems plugin){
		this.plugin = plugin;
	}
	
	public void onInteract(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
		
		EffectItem effectItem = plugin.getItemManager().get(item.getItemMeta().getDisplayName());
		
		if(effectItem == null) return; // no EffectItem found
		
		effectItem.apply(event.getPlayer()); // apply the effects
	}
}
