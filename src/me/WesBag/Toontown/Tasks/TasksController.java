package me.WesBag.Toontown.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class TasksController {
	
	public static Map<Integer, List<Quest>> allQuests = new HashMap<>(); //Integer key is playground number
	public static List<Quest> ttcQuests = new ArrayList<>();
	public static Quests qp;
	public TasksController() {
		TasksController.qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	}
	
	public static void loadAllTasks() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ttcQuests.add(qp.getQuestById("custom1"));
				ttcQuests.add(qp.getQuestById("custom2"));
				ttcQuests.add(qp.getQuestById("custom3"));
				ttcQuests.add(qp.getQuestById("custom5")); //To get loony louis task to show up 4-23-22
				//ttcQuests.add(qp.getQuest("Tutorial"));
				//ttcQuests.add(qp.getQuest("BUTTHOLE"));
				System.out.println("-------------------Size of ttcQuests: " + ttcQuests.size());
				//System.out.println("0 = " + ttcQuests.get(0).getName());
				//System.out.println("1 = " + ttcQuests.get(1).getName());
				allQuests.put(0, ttcQuests);
			}
		}.runTaskLater(Main.getInstance(), 20);
	}
}
