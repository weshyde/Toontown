package me.WesBag.Toontown.Tasks.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.CustomQuests.Objectives.TalkToHQOfficerObjective;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Tasks.TasksController;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import me.blackvein.quests.Stage;
import me.blackvein.quests.quests.IStage;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class TasksGUI implements Listener {
	
	
	public static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	//public static Set<UUID> playersChoosingTasks = new HashSet<>(); 
	//public static Map<UUID, Inventory> playersTaskInventorys = new HashMap<>();
	public static Inventory tasksGUITemplate;
	public static Map<UUID, TasksGUI> playersTaskGUIs = new HashMap<>();
	
	
	private Inventory tasksGUI;
	private int maxTasks;
	private List<Quest> shownQuests = new ArrayList<>();
	private Toon toon;
	private UUID toonUUID;
	
	public TasksGUI() {
		
	}
	
	public TasksGUI(Toon toon) {
		this.toon = toon;
		this.maxTasks = toon.getMaxTasks();
		this.toonUUID = toon.getUUID();
		//playersChoosingTasks.add(toonUUID);
		//this.tasksGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "" +"Tasks");
	}
	
	public void openTasksGUI() {
		if (qp.getQuester(toonUUID).getCurrentQuests().size() >= maxTasks) {
			toon.getToon().sendMessage("You already are carrying your max tasks, complete those first!");
			toon.getToon().sendMessage("Current Quest Size: " + qp.getQuester(toonUUID).getCurrentQuests().size());
			toon.getToon().sendMessage("Max Tasks Size: " + maxTasks);
		}
		else {
			//playersChoosingTasks.add(toonUUID);
			this.tasksGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "" +"Tasks");
			for (int i = 0; i < 10; i++)
				tasksGUI.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
			
			List<Quest> playgroundQuests = new ArrayList<>(TasksController.allQuests.get(toon.getCurrentTasklinePlayground()));
			//ConcurrentSkipListSet<Quest> doneQuests = qp.getQuester(toonUUID).getCompletedQuests();
			Iterator<Quest> doneQuests2 = qp.getQuester(toonUUID).getCompletedQuests().iterator();
			//List<Quest> availablePlaygroundQuests = new ArrayList<>();
			while (doneQuests2.hasNext()) {
				Quest tempQuest = doneQuests2.next();
				if (playgroundQuests.contains(tempQuest))
					playgroundQuests.remove(tempQuest);
			}
			
			for (int i = 0; i < playgroundQuests.size() && i < 4; i++) {
				Quest quest = playgroundQuests.get(i);
				shownQuests.add(quest);
				tasksGUI.setItem(((i * 2) + 10), createGuiItem(Material.BOOK, ChatColor.DARK_AQUA + quest.getName(), ChatColor.AQUA + quest.getDescription()));
			}
			
			for (int i = 17; i < 26; i++) {
				tasksGUI.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
			}
			tasksGUI.setItem(26, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Exit"));
			
			
			BattleCore.openInventoryLater(toon.getToon(), tasksGUI);
			//Requirements r = qp.getQuest("QuestTest").getRequirements();
			//r.getNeededQuests();
			//qp.getQuester(toonUUID).get
			//Once quests are done, add quests by playground on enable.
			//Maybe in toons save the quests completed?
			//List<Quest> quests = qp.getQuests();
			//playersTaskInventorys.put(toonUUID, tasksGUI);
			playersTaskGUIs.put(toonUUID, this);
		}
	}
	
	@EventHandler
	public void onPlayerRightClickHQOfficer(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		//Player p = e.getClicker();
		String unformattedNPCName = ChatColor.stripColor(npc.getFullName());
		e.getClicker().sendMessage("Got Here! 1");
		if (unformattedNPCName.contains("HQ")) {
			e.getClicker().sendMessage("Got Here! 2");
			for (Quest quest : qp.getQuester(e.getClicker().getUniqueId()).getCurrentQuests().keySet()) {
				IStage currentStage = qp.getQuester(e.getClicker().getUniqueId()).getCurrentStage(quest); //Changed 4-11-22 12:16pm
				if (currentStage.getCustomObjectives().contains(new TalkToHQOfficerObjective())) {
					e.getClicker().sendMessage("Had an HQ Quest, returning...");
					return; //If player has any quest with the current stage of talking to an HQ officer, return / don't open tasks GUI
				}
			}
			e.getClicker().sendMessage("Got Here! 3");
			Toon tempToon = ToonsController.getToon(e.getClicker().getUniqueId());
			TasksGUI taskGUI = new TasksGUI(tempToon);
			taskGUI.openTasksGUI();
		}
	}
	
	@EventHandler
	public void onPlayerClickTask(InventoryClickEvent e) {
		//if (e.getInventory() != playersTaskInventorys.get(e.getWhoClicked().getUniqueId())) return;
		if (!playersTaskGUIs.containsKey(e.getWhoClicked().getUniqueId())) return;
		if (e.getInventory() != playersTaskGUIs.get(e.getWhoClicked().getUniqueId()).getTasksInv()) return;
		e.setCancelled(true);
		
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.WHITE_STAINED_GLASS_PANE)
			return;
		//e.setCancelled(true);
		UUID pUUID = e.getWhoClicked().getUniqueId();
		if (e.getCurrentItem().getType() == Material.BOOK) {
			switch(e.getRawSlot()) {
				case 10:
					System.out.println("Shown Quests Size: " + shownQuests.size());
					//qp.getQuester(e.getWhoClicked().getUniqueId()).offerQuest(shownQuests.get(0), false);
					qp.getQuester(pUUID).offerQuest(playersTaskGUIs.get(pUUID).getShownQuests().get(0), false);
					//playersChoosingTasks.remove(e.getWhoClicked().getUniqueId());
					playersTaskGUIs.remove(pUUID);
					e.getWhoClicked().closeInventory();
					//playersTaskInventorys.remove(e.getWhoClicked().getUniqueId());
					//e.getWhoClicked().closeInventory(); Maybe add?
					break;
				case 12:
					//qp.getQuester(e.getWhoClicked().getUniqueId()).offerQuest(shownQuests.get(1), false);
					qp.getQuester(pUUID).offerQuest(playersTaskGUIs.get(pUUID).getShownQuests().get(1), false);
					//playersChoosingTasks.remove(e.getWhoClicked().getUniqueId());
					//playersTaskInventorys.remove(e.getWhoClicked().getUniqueId());
					playersTaskGUIs.remove(pUUID);
					e.getWhoClicked().closeInventory();
					//e.getWhoClicked().closeInventory();
					break;
				case 14:
					//qp.getQuester(e.getWhoClicked().getUniqueId()).offerQuest(shownQuests.get(2), false);
					qp.getQuester(pUUID).offerQuest(playersTaskGUIs.get(pUUID).getShownQuests().get(3), false);
					//playersChoosingTasks.remove(e.getWhoClicked().getUniqueId());
					//playersTaskInventorys.remove(e.getWhoClicked().getUniqueId());
					playersTaskGUIs.remove(pUUID);
					e.getWhoClicked().closeInventory();
					//e.getWhoClicked().closeInventory();
					break;
				case 16:
					//qp.getQuester(e.getWhoClicked().getUniqueId()).offerQuest(shownQuests.get(3), false);
					qp.getQuester(pUUID).offerQuest(playersTaskGUIs.get(pUUID).getShownQuests().get(3), false);
					//playersChoosingTasks.remove(e.getWhoClicked().getUniqueId());
					//playersTaskInventorys.remove(e.getWhoClicked().getUniqueId());
					playersTaskGUIs.remove(pUUID);
					e.getWhoClicked().closeInventory();
					//e.getWhoClicked().closeInventory();
					break;
				default:
					return;
			}
		}
		
		else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
			//playersChoosingTasks.remove(e.getWhoClicked().getUniqueId());
			//playersTaskInventorys.remove(e.getWhoClicked().getUniqueId());
			playersTaskGUIs.remove(pUUID);
			e.getWhoClicked().closeInventory();
		}
				
	}
	
	@EventHandler
	public void onPlayerCloseTasksGUI(InventoryCloseEvent e) {
		if (!e.getView().getTitle().contains("Tasks"))
			return;
		//if (playersChoosingTasks.contains(e.getPlayer().getUniqueId()))
		if (playersTaskGUIs.containsKey(e.getPlayer().getUniqueId()))
			BattleCore.openInventoryLater(e.getPlayer(), playersTaskGUIs.get(e.getPlayer().getUniqueId()).getTasksInv());
	}
	
	public Inventory getTasksInv() {
		return tasksGUI;
	}
	
	public List<Quest> getShownQuests() {
		return shownQuests;
	}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	
}
