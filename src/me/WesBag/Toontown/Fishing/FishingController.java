package me.WesBag.Toontown.Fishing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.CustomQuests.Objectives.ItemsCaughtFishingObjective;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Admin.IsIntUtil;
import me.WesBag.Toontown.Fishing.Fish.TTFish;
import me.WesBag.Toontown.BattleCore.Toons.ShtickerBook.ShtickerBook;
import me.WesBag.Toontown.Tasks.CustomEvents.PlayerCatchFishEvent;
import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import me.blackvein.quests.Stage;
import me.blackvein.quests.module.ICustomObjective;
import me.blackvein.quests.quests.IStage;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class FishingController implements Listener {
	public static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	public static Map<UUID, Fishing> playersFishing = new HashMap<>();
	public static Inventory fishermanGUI;
	//public static final String[][] allFish = {{"Balloon Fish", "Hot Air Balloon Fish", "Weather Balloon Fish", "Water Balloon Fish", "Red Balloon Fish"},
	//		{"Peanut Butter & Jellfish", "Grape PB&J Fish", "Crunchy PB&J Fish", "Strawberry PB&J Fish", "Concord Grape PB&J Fish"},
	//		{"Cat Fish", "Siamese Cat Fish", "Alley Cat Fish", "Tabby Cat Fish", "Tom Cat Fish"},
	//		{"Clown Fish", "Sad Clown Fish", "Party Clown Fish", "Circus Clown Fish"},
	//		{"Frozen Fish"},
	//		{"Star Fish", "Five Star Fish", "Rock Star Fish", "Shining Star Fish", "All Star Fish"},
	//		{"Holey Mackerel"}
	//};
	//public static final int[][] fishRarity = {{1, 4, 5, 4, 3},
	//		{4, 5, 5, 5, 10}
	//};
	
	@EventHandler
	public void onPlayerWalkOnFishingPier(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		
		Block blockUnder = p.getLocation().subtract(0, 1, 0).getBlock();
		
		if (playersFishing.containsKey(p.getUniqueId())) {
			//if (blockUnder.getType() != Material.ACACIA_PLANKS) { Maybe re-implement?
			//	p.getInventory().clear();
			//	playersFishing.remove(p.getUniqueId());
			//	p.sendMessage("You left the fishing pier");
				return;
			//}
		}
		
		if (blockUnder.getType() == Material.AIR || blockUnder.getType() == null || blockUnder.getType() != Material.ACACIA_PLANKS) return;
		else if (blockUnder.getType() == Material.ACACIA_PLANKS) {
			Fishing fishing = new Fishing(p);
			playersFishing.put(p.getUniqueId(), fishing);
		}
	}
	
	@EventHandler
	public void onPlayerLeaveFishing(PlayerInteractEvent e) {
		if (!playersFishing.containsKey(e.getPlayer().getUniqueId())) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		
		if ((a == Action.PHYSICAL) || (e.getItem() == null) || (e.getItem().getType() != Material.RED_CONCRETE)) {
			return;
		}
		
		p.getInventory().clear();
		ShtickerBook.refreshFishingPages(ToonsController.getToon(p.getUniqueId()));
		playersFishing.remove(p.getUniqueId());
		//p.closeInventory();
	}
	
	@EventHandler 
	public void onPlayerCastLine(PlayerFishEvent e) {
		if (!playersFishing.containsKey(e.getPlayer().getUniqueId())) return;
		if (e.getState() != State.FISHING) return;
		
		Fishing fishing = playersFishing.get(e.getPlayer().getUniqueId());
		Toon toon = ToonsController.getToon(e.getPlayer().getUniqueId());
		if (toon != null) {
			if (toon.canPouchAfford(fishing.getCastCost()) && toon.getFishAmount() < 20) {
				toon.payPouchAmount(fishing.getCastCost());
			}
			else if (toon.getFishAmount() >= 20) {
				toon.getToon().sendMessage("You don't have enough space");
				e.setCancelled(true);
			}
			
			else {
				toon.getToon().sendMessage("You don't have enough jellybeans");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerCatchFish(PlayerFishEvent e) {
		if (!playersFishing.containsKey(e.getPlayer().getUniqueId())) return;
		if (e.getState() != State.CAUGHT_FISH && e.getState() != State.CAUGHT_ENTITY) return;
		
		e.getCaught().remove();
		boolean fishingTask = false;
		System.out.println("Caught fish, got here! 1");
		qp.getCustomObjectives();
		//qp.getCustomObjectives().
		//PlayerCatchItemEvent catchItemEvent = new PlayerCatchItemEvent(e.getPlayer().getUniqueId(), "");
		for (Quest quest : qp.getQuester(e.getPlayer().getUniqueId()).getCurrentQuests().keySet()) {
			//Stage stage = qp.getQuester(e.getPlayer().getUniqueId()).getCurrentStage(quest); //Changed to line below 4-11-22 12:16pm
			Stage stage = (Stage) qp.getQuester(e.getPlayer().getUniqueId()).getCurrentStage(quest);
			//stage.getCustomObjectives();
			//ICustomObjective ic;
			Iterator<?> objIterator = stage.getCustomObjectives().iterator();
			String path = "me.WesBag.CustomQuests.Objectives.ItemsCaughtFishingObjective";
			while (objIterator.hasNext()) { //Trying to check if the player has any objectives that're "ItemsCaughtFishingObjective", if so, fishingTask=true
				//objIterator.next().
				//if (objIterator.next())
				//try {
				//	if (Class.forName(path).isInstance(objIterator.next())) {
				//		fishingTask = true;
				//	}
				//} catch(ClassNotFoundException ex) {
				//	ex.printStackTrace();
				//s}
				if (objIterator.next() instanceof ItemsCaughtFishingObjective) {
					fishingTask = true;
				}
			}
			//if (stage.getCustomObjectives().contains(new ItemsCaughtFishingObjective())) {
			//	fishingTask = true;
			//}
		}
		System.out.println("Caught fish, got here! 2");
		if (!fishingTask) {
			//Not a fishing task, calc and give player fish
			System.out.println("Caught fish, got here! 3");
			Player player = e.getPlayer();
			Fishing fishing = playersFishing.get(player.getUniqueId());
			TTFish caughtFish = fishing.calcAndReturnFish();
			System.out.println("Caught Fish Name: " + caughtFish.getName());
			System.out.println("Caught Fish Species: " + caughtFish.getFishSpecies());
			System.out.println("Caught Fish Num: " + caughtFish.getFishNum());
			int weight = fishing.calcCaughtFishWeight(caughtFish);
			int value = fishing.calcCaughtFishValue(caughtFish, weight);
			System.out.println("Caught fish, got here! 4");
			Toon toon = ToonsController.getToon(player.getUniqueId());
			toon.newFish(caughtFish.getFishSpecies(), caughtFish.getFishNum(), weight, value);
			player.sendMessage("You caught a " + caughtFish.getName() + " weighing " + weight + " pounds, worth " + value + "!");
		}
		
		else {
			//Has fishing task so give chance of task item
			PlayerCatchFishEvent catchFishEvent = new PlayerCatchFishEvent(e.getPlayer().getUniqueId());
			//System.out.println("CustomCatchFishEvent Called!!!");
			Bukkit.getPluginManager().callEvent(catchFishEvent);
		}
	}
	
	@EventHandler
	public void onPlayerRightClickFisherman(NPCRightClickEvent e) {
		if (e.getNPC().getFullName().contains("Fisherman")) {
			NPC npc = e.getNPC();
			Player p = e.getClicker();
			Toon toon = ToonsController.getToon(p.getUniqueId());
			if (toon == null) return;
			if (toon.getFishingBucket3().isEmpty()) {
				toon.getToon().sendMessage("[Fisherman] You have no fish to sell");
				return;
			}
			
			if (fishermanGUI == null) { //Init if null
				fishermanGUI = Bukkit.createInventory(null, 27, "Sell your fish?");
				for (int i = 0; i < 27; i++) {
					fishermanGUI.setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " "));
				}
				fishermanGUI.setItem(11, createGuiItem(Material.GREEN_CONCRETE, "Yes"));
				fishermanGUI.setItem(15, createGuiItem(Material.RED_CONCRETE, "No"));
			}
			
			int totalJbs = 0;
			//for (Map.Entry<String, List<Integer>> entry : toon.getFishingBucket().entrySet()) {
				//System.out.println("Added: " + entry.getValue().get(1));
			//	totalJbs += entry.getValue().get(1);
			//}
			for (String str : toon.getFishingBucket3()) {
				totalJbs += IsIntUtil.getInt(str.substring(6, 8));
			}
			fishermanGUI.setItem(13, createGuiItem(Material.BLUE_CONCRETE, "Jellybeans: " + totalJbs));
			BattleCore.openInventoryLater(p, fishermanGUI);
		}
	}
	
	@EventHandler
	public void onFishingGUIClick(final InventoryClickEvent e) {
		if (!e.getView().getTitle().contains("Sell your fish")) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		
		e.setCancelled(true);
		
		if (e.getRawSlot() == 11) {
			Toon toon = ToonsController.getToon(e.getWhoClicked().getUniqueId());
			int totalJbs = 0;
			//for (Map.Entry<String, List<Integer>> entry : toon.getFishingBucket().entrySet()) {
			//	totalJbs += entry.getValue().get(1);
			//}
			for (String str : toon.getFishingBucket3()) {
				totalJbs += IsIntUtil.getInt(str.substring(6, 8));
			}
			//toon.getFishingBucket().clear();
			toon.getFishingBucket3().clear();
			toon.addPouchJellybeans(totalJbs);
			toon.getToon().sendMessage("Successfully sold your fish for " + totalJbs + " jellybeans");
			e.getWhoClicked().closeInventory();
		}
		else if (e.getRawSlot() == 15) {
			e.getWhoClicked().closeInventory();
		}
		
	}
	
	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	
	/*
	 * 	@EventHandler
	public void onPlayerCatchFish(PlayerCatchFishEvent e) {
		if (e.getPlayerUUID() == null) return;
		
		UUID pUUID = e.getPlayerUUID();
		Player p = Bukkit.getPlayer(pUUID);
		for (Quest quest : qp.getQuester(pUUID).getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(p, this, quest);
			if (map == null) continue;
			
			int recoverChance = Integer.parseInt((String) map.get("Recover-Chance"));
			String neededItem = (String) map.get("Recover-Item");
			
			Random r = new Random();
			int randomInt = r.nextInt(100) + 1;
			if (randomInt <= recoverChance) {
				p.sendMessage(ItemsFromCogsObjective.TasksPrefix + " You recovered a " + neededItem + "!");
				incrementObjective(p, this, 1, quest);
			}
		}
	}
	 */

}
