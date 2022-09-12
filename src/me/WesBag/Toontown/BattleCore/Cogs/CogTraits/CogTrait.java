package me.WesBag.TTCore.BattleMenu.Cogs.CogTrait;

import org.bukkit.plugin.java.JavaPlugin;

import me.WesBag.TTCore.Main;
import net.citizensnpcs.api.trait.Trait;

public class CogTrait extends Trait {

	public CogTrait(String name) {
		super("Cog");
		main = JavaPlugin.getPlugin(Main.class);
		// TODO Auto-generated constructor stub
	}
	
	Main main = null;
	
	
	@Override
	public void onAttach() {
		main.getServer().getLogger().info(npc.getName() + " has been assigned the Cog trait!");
	}

}
