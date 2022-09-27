package me.WesBag.Toontown;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Countdown;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuildingController;
import me.WesBag.Toontown.BattleCore.Cogs.CogTraits.CogBuildingTrait;
import me.WesBag.Toontown.BattleCore.Toons.ToonListener;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Admin.ChangeSkin;
import me.WesBag.Toontown.Commands.Admin.CogBuildingsCommand;
import me.WesBag.Toontown.Commands.Admin.FishingBucketMenuCommand;
import me.WesBag.Toontown.Commands.Admin.GiveJellybeans;
import me.WesBag.Toontown.Commands.Admin.HealLaff;
import me.WesBag.Toontown.Commands.Admin.MaxToon;
import me.WesBag.Toontown.Commands.Admin.MaxTrack;
import me.WesBag.Toontown.Commands.Admin.ResetGags;
import me.WesBag.Toontown.Commands.Admin.SaveStreetBattleLocation;
import me.WesBag.Toontown.Commands.Admin.SetGag;
import me.WesBag.Toontown.Commands.Admin.SetGagAmount;
import me.WesBag.Toontown.Commands.Admin.SetGagExp;
import me.WesBag.Toontown.Commands.Admin.SetGagMax;
import me.WesBag.Toontown.Commands.Admin.SetJellybeans;
import me.WesBag.Toontown.Commands.Admin.SetLaff;
import me.WesBag.Toontown.Commands.Admin.StartInvasion;
import me.WesBag.Toontown.Commands.Admin.StopAllBattles;
import me.WesBag.Toontown.Commands.Admin.StreetsControllerCommand;
import me.WesBag.Toontown.Commands.Testing.TeleportAnimationTest;
import me.WesBag.Toontown.Commands.Testing.TestCommand;
import me.WesBag.Toontown.Commands.Testing.ThrowItemAnimation;
import me.WesBag.Toontown.Commands.Player.BookCommand;
import me.WesBag.Toontown.Files.BattleData;
import me.WesBag.Toontown.Files.FinalDataFile;
import me.WesBag.Toontown.Files.VolatileDataFile;
import me.WesBag.Toontown.Files.PlayerData;
import me.WesBag.Toontown.Files.PlayerDataController;
import me.WesBag.Toontown.Fishing.FishController;
import me.WesBag.Toontown.Fishing.FishingController;
import me.WesBag.Toontown.GagShop.GagShopListener;
import me.WesBag.Toontown.Listeners.PlayerDamageCanceller;
import me.WesBag.Toontown.Listeners.PlaygroundChanges;
import me.WesBag.Toontown.BattleCore.Toons.ShtickerBook.ShtickerBook;
import me.WesBag.Toontown.Streets.StreetsController;
import me.WesBag.Toontown.Tasks.Tasks;
import me.WesBag.Toontown.Tasks.TasksController;
import me.WesBag.Toontown.Tasks.GUI.TasksGUI;
import me.WesBag.Toontown.Trolley.TrolleyListener;
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.CGListener;
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.CannonGame;
import me.WesBag.Toontown.Trolley.Minigames.IceSlide.ISListener;
import me.WesBag.Toontown.Trolley.Minigames.IceSlide.IceSlide;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Tutorial.Tutorial;
import me.blackvein.quests.Quests;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.TraitInfo;

public class Main extends JavaPlugin implements Listener {
	
	//public DataManager data; //Changed for testing playerdata
	public Quests qp;
	public PlayerData data2;
	//This is a test 
	public BattleCore battleCore;
	public Logger log;
	//public BattleData battleData;
	public static VolatileDataFile vDataFile;
	public static FinalDataFile fDataFile;
	public NPCRegistry registry;
	//public CogsController cogs;
	//public BattleCore battle;
	public Countdown timer1;
	//public static DataFile dataFile;
	private static Main instance;
	
	//public BattleCore battle = new BattleCore(data, main);
	
	
	
	@Override
	public void onEnable() {
		//this.data = new DataManager(this); //Changed for testing playerdata
		//this.data2 = new PlayerData(this);
		Main.vDataFile = new VolatileDataFile(this);
		Main.fDataFile = new FinalDataFile(this);
		instance = this;
		registry = CitizensAPI.getNPCRegistry();
		log = getLogger();
		getLogger().info("Toontown Core Enabled!");
		this.getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new PlayerDamageCanceller(), this);
		getServer().getPluginManager().registerEvents(new PlaygroundChanges(), this);
		getServer().getPluginManager().registerEvents(new BattleCore(this), this);
		
		getServer().getPluginManager().registerEvents(new Tutorial(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDataController(this), this);
		getServer().getPluginManager().registerEvents(new Tasks(), this);
		getServer().getPluginManager().registerEvents(new ToonListener(log), this);
		getServer().getPluginManager().registerEvents(new GagShopListener(this, log), this);
		getServer().getPluginManager().registerEvents(new TrolleyListener(), this);
		getServer().getPluginManager().registerEvents(new CogBuildingController(this), this);
		getServer().getPluginManager().registerEvents(new StreetsController(), this);
		getServer().getPluginManager().registerEvents(new FishingController(), this);
		getServer().getPluginManager().registerEvents(new TasksGUI(), this);
		getServer().getPluginManager().registerEvents(new ShtickerBook(), this);
		
		TasksController tc = new TasksController();
		
		//-----Player Commands-----//
		
		getCommand("book").setExecutor(new BookCommand());
		
		//-----Admin / Testing Commands-----/
		getCommand("setgag").setExecutor(new SetGag());		
		getCommand("stopallbattles").setExecutor(new StopAllBattles());
		getCommand("resetgags").setExecutor(new ResetGags());
		getCommand("setgagexp").setExecutor(new SetGagExp());
		getCommand("heallaff").setExecutor(new HealLaff());
		getCommand("maxtoon").setExecutor(new MaxToon());
		getCommand("maxtrack").setExecutor(new MaxTrack());
		getCommand("setgagmax").setExecutor(new SetGagMax());
		getCommand("setlaff").setExecutor(new SetLaff());
		getCommand("setgagamount").setExecutor(new SetGagAmount());
		getCommand("teleporttest").setExecutor(new TeleportAnimationTest());
		getCommand("throwitem").setExecutor(new ThrowItemAnimation());
		getCommand("spawnnpcs").setExecutor(new TestCommand());
		getCommand("spawnbuilding").setExecutor(new TestCommand());
		getCommand("testpathing").setExecutor(new TestCommand());
		getCommand("testbuildinganimation").setExecutor(new TestCommand());
		getCommand("savestreetbattlelocation").setExecutor(new SaveStreetBattleLocation());
		getCommand("testspawncogs").setExecutor(new TestCommand());
		getCommand("startinvasion").setExecutor(new StartInvasion());
		getCommand("givejellybeans").setExecutor(new GiveJellybeans());
		getCommand("setjellybeans").setExecutor(new SetJellybeans());
		getCommand("cogbuildings").setExecutor(new CogBuildingsCommand());
		getCommand("streets").setExecutor(new StreetsControllerCommand());
		getCommand("fishing").setExecutor(new FishingBucketMenuCommand());
		getCommand("changeskin").setExecutor(new ChangeSkin());
		//////Minigame Init start!!! (Dont forget to add the minigames in the game manager lists too!!!!)
		
		//Cannon Game
		GameManager.allGames.add(CannonGame.class);
		Location CG1 = new Location(getServer().getWorld("world"), 10, 60, 10);
		Location CG2 = new Location(getServer().getWorld("world"), 20, 60, 20);
		CannonGame.arenasFree.add(CG1);
		CannonGame.arenasFree.add(CG2);
		getServer().getPluginManager().registerEvents(new CGListener(), this);
		
		//Ice Slide
		GameManager.allGames.add(IceSlide.class);
		Location IS1 = new Location(getServer().getWorld("world"), 50, 60, 50);
		IceSlide.arenasFree.add(IS1);
		getServer().getPluginManager().registerEvents(new ISListener(), this);
		
		
		
		//////Minigame Init end
		
		//FISHING
		FishController.initPonds(); //Uncomment when fishing regions are done!!!! (only TTC done 4/3/22 10:25pm)
		
		//TASKS STUFF START (Loads all quests and assigns to lists within TasksController
		//this.qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
		TasksController.loadAllTasks();
		//TasksController.ttcQuests.add(qp.getQuest("Quest1"));
		//UUID test = null;
		//qp.getQuester(test).getQ;
		//TasksController.allQuests.put(1, TasksController.ttcQuests); //1 = TTC, 2 = DD, etc
		//TASKS END

		//Cog Building Locations loading:
		//Main.dataFile = new DataFile(this);
		
		if (Main.vDataFile != null) {
			if (Main.vDataFile.getData().contains("buildings")) {
				Main.vDataFile.getData().getConfigurationSection("buildings").getKeys(false).forEach(key ->{
					UUID uuid = UUID.fromString(Main.vDataFile.getData().get("buildings." + key).toString());
					//System.out.println("-------LOADED UUID: " + uuid.toString());
					Location unserialL = VolatileDataFile.unserializeLocation(key);
					//String str = ((String) Main.dataFile.getData().get("buildings." + key));
					//String newStr = str.substring(str.indexOf(0))
					//UUID buildUUID = UUID.fromString(str);
					CogBuildingController.readyCogBuildings.put(unserialL, uuid);
					//CogBuildingController.readyCogBuildings2.put(unserialL, uuid);
				});
			}
		}
		
		
		//TESTING SPAWNING STREET COGS HERE:
		
		//Adding Custom Traits to Citizens2
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(CogBuildingTrait.class).withName("CogBuilding"));	
		
		
		
		//battle = new me.WesBag.Toontown.BattleCore.BattleCore(this, data);
		//cogs = new me.WesBag.Toontown.BattleCore.Cogs.CogsController(this);
		
		if (!vDataFile.getData().contains("locations.")) {
			String[] tempLocationNames = {"ttc", "dd", "dg", "mml", "b", "ddl", "sb", "cb", "lb", "bb"};
			Location ttcL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location ddL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location dgL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location mmlL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location bL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location ddlL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location sbL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location cbL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location lbL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location bbL = new Location(getServer().getWorld("world"), 1, 1, 1);
			Location[] tempLocations = {ttcL, ddL, dgL, mmlL, bL, ddlL, sbL, cbL, lbL, bbL};
			for (int i = 0; i < 1; i++) { //Only sets location ttcL currently, change to i < amount to correct eventually
				if (!vDataFile.getData().contains("locations." + tempLocationNames[i])) {
					vDataFile.getData().set("locations." + tempLocationNames[i], tempLocations[i]);
				}
			}
			vDataFile.saveDataFile();
		}
	}
	
	public void onDisable() {
		getLogger().info("Toontown Core Disabled");
		
		ToonsController.unloadAll();
		
		//Saves all Cog Building Locations to data.yml
		for (Map.Entry<Location, UUID> entry : CogBuildingController.readyCogBuildings.entrySet()) {
			String serialL = VolatileDataFile.serializeLocation(entry.getKey());
			if (!Main.vDataFile.getData().contains("buildings." + serialL)) {
				//System.out.println("UUID: " + entry.getValue().get)
				Main.vDataFile.getData().set("buildings." + serialL, entry.getValue().toString());
			}
		}
		/* Maybe replace with this? VVV
		for (Map.Entry<Location, UUID> entry : CogBuildingController.readyCogBuildings2.entrySet()) {
			String serialL = DataFile.serializeLocation(entry.getKey());
			if (!Main.dataFile.getData().contains("buildings." + serialL)) {
				//System.out.println("UUID: " + entry.getValue().get)
				Main.dataFile.getData().set("buildings." + serialL, entry.getValue().toString());
			}
		}
		*/
		Main.vDataFile.saveDataFile();
	}
	
	
    public static Main getInstance() {
        return instance;
    }
    
    public static VolatileDataFile getVolatileDataFile() {
    	return vDataFile;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("testcore")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("ttc.admin")) {
					//if (battle.isInBattle(player.getUniqueId())) {
						
					//}
					//if (BattleData.isInBattle(player.getUniqueId())) {
						
					//}
					player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Toontown Core Working!");
					return true;
				}
				player.sendMessage(ChatColor.DARK_RED + "Invalid Permissions");
				return true;
			}
			else {
				sender.sendMessage("[Console] Core Working!");
				return true;
			}
		}
		
		else if (label.equalsIgnoreCase("xmebattle")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("ttc.admin")) {
					//battle.removePlayerFromBattle(player.getUniqueId());
					//BattleData.removePlayerBattle(player.getUniqueId());
					BattleData battleData = BattleCore.getBattleDataPlayer(player);
					battleData.removePlayer(player.getUniqueId());
					return true;
				}
				player.sendMessage(ChatColor.DARK_RED + "Invalid Permissions");
				return true;
			}
			
			else {
				sender.sendMessage("[Console] This command is only for players!");
				return true;
			}
		}
		return false;
	}
}
