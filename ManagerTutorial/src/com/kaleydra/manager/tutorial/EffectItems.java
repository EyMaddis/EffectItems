package com.kaleydra.manager.tutorial;

import java.io.File;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.kaleydra.manager.tutorial.managerlib.SerializableManager;

public class EffectItems extends JavaPlugin {
	private static EffectItems instance;
	private SerializableManager<EffectItem> itemManager;
	
	public void onEnable(){
		instance = this;
		
		// enable serialization with our pretty name
		ConfigurationSerialization.registerClass(EffectItem.class, "EffectItem");
		
		
		getServer().getPluginManager().registerEvents(new ItemListener(this), this);
		
		// this manager will store our effect items and has save (serialize) and load (deserialize) methods
		itemManager = new SerializableManager<EffectItem>();
		
		// load items from items.yml in our plugin folder
		itemManager.load(new File(getDataFolder()+"/items.yml"));
		
		// only to create the first file
		itemManager.add(new EffectItem());
	}
	
	@Override
	public void onDisable(){
		// save all EffectItems to items.yml
		itemManager.save(new File(getDataFolder()+"/items.yml"));
	}
	
	
	public static EffectItems getInstance(){
		return instance;
	}
	
	public SerializableManager<EffectItem> getItemManager(){
		return itemManager;
	}
}
