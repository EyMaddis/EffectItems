package com.kaleydra.manager.tutorial;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.kaleydra.manager.tutorial.managerlib.Identifiable;

/**
 * Item that applies a potion effect to the player
 * @author maddis
 *
 */
@SerializableAs("EffectItem") // avoid ugly names in .yml files (no com.kaleydra.manager.tutorial.EffectItem
public class EffectItem implements ConfigurationSerializable, Identifiable { // ignore Identifiable for the tutorial

	/* we will default this, so it wont spam the console if something breaks
	 * Just for demo purposes.
	 * You should do better than this. 
	 */
	String displayName = ChatColor.GOLD+"Effect Item";
	PotionEffect effect = new PotionEffect(PotionEffectType.HEAL, 1, 2); 
	Material material = Material.GOLD_HOE; // what should the item be made of
	
	public EffectItem(){} // only for my demo item.
	
	public EffectItem(Map<String,Object> input){
		// I could also use a cast here: (String) input.get("displayName");
		displayName = input.get("displayName").toString();
		

		/*
		 * Bukkit made PotionEffects serializable by default, great!
		 * this means that the map will store a PotionEffect object
		 */ 
		effect = (PotionEffect) input.get("effect");
		
		/* 
		 * now for the Material of the item
		 * we will do this manually with the name of the Material, e.g. "GOLD_HOE"
		 *  and use that to get a object of Material
		 */
		String materialName = input.get("material").toString();
		try {
			/*
			 * Material is an enum which have a valueOf() function, but 
			 * looking at the javadocs tells us that it can throw an Exception
			 * if it was not found
			*/
			material = Material.valueOf(materialName.toUpperCase());
		} catch (Exception e) {
			EffectItems.getInstance().getLogger().severe("invalid material!");
		}
	}
	
	public void apply(Player player){
		player.addPotionEffect(effect);
	}
	
	/**
	 * this will allow bukkit to convert this to YAML
	 * Keep in mind that every object you put in the map
	 * has to be serializable too (primitive data is always)
	 */
	@Override
	public Map<String, Object> serialize() { 
		/* 
		 * The map that this method will return.
		 * LinkedHashMap will keep the order of items, so that
		 * the order in the .yml file is the same as here.
		 */
		Map<String, Object> output = new LinkedHashMap<String, Object>(); 
		output.put("displayName", displayName);
		
		// Bukkit made PotionEffects serializable by default, great!
		output.put("effect", effect);
		
		// use the internal name for serialisation, e.g. GOLD_HOE
		output.put("material", material.name()); 
		return output;
	}

	@Override // from Identifiable
	public String getIdentifier() {
		return displayName;
	}

}
