package me.WesBag.Toontown.SchematicUtilities;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;

import me.WesBag.Toontown.Main;

public class SchematicSaver {
	
	/*
	public static void saveSchematic(String fileName, String worldStr, int x1, int y1, int z1, int x2, int y2, int z2) {
		World world = FaweAPI.getWorld(worldStr);
		World weWorld = new BukkitWorld(Bukkit.getWorld(worldStr));
		BlockVector3 pos1 = BlockVector3.at(x1, y1, z1);
		BlockVector3 pos2 = BlockVector3.at(x2, y2, z2);
		CuboidRegion cReg = new CuboidRegion(weWorld, pos1, pos2);
		File dataDirectory = new File(Main.getInstance().getDataFolder() + "/schematics", "temp");
		File file = new File(dataDirectory, fileName + ".schem");
		try {
			BlockArrayClipboard clipboard = new BlockArrayClipboard(cReg);
			//Extent source = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
			Extent source = WorldEdit.getInstance().newEditSession(world);
			Extent destination = clipboard;
			ForwardExtentCopy copy = new ForwardExtentCopy(source, cReg, clipboard.getOrigin(), destination, pos1);
			copy.setSourceMask(new ExistingBlockMask(source));
			Operations.completeLegacy(copy);
			ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file));
			writer.write(clipboard);
			//ClipboardFormat.class.SC
			//ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(file)).write(clipboard,)
		} catch (IOException | MaxChangedBlocksException e1) {
			e1.printStackTrace();
		}
		//CuboidRegion test = new CuboidRegion(weWorld, null, null);
		//WorldData worldData = weWorld.getD
		//WorldData worldData = weWorld.getWo
	}
	*/
	public static void saveSchematic2(String fileName, String worldStr, int x1, int y1, int z1, int x2, int y2, int z2) {
		//World world = FaweAPI.getWorld(worldStr);
		org.bukkit.World w = Bukkit.getWorld(worldStr);
		World world = new BukkitWorld(w);
		//WorldEdit.getInstance().getW
		BlockVector3 pos1 = BlockVector3.at(x1, y1, z1);
		BlockVector3 pos2 = BlockVector3.at(x2, y2, z2);
		CuboidRegion region = new CuboidRegion(pos1, pos2);
		//CuboidRegion region = new CuboidRegion(world, pos1, pos2);
		BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
		//File dataDirectory = new File(Main.getInstance().getDataFolder() + "/schematics/temp");
		//File file = new File(Main.getInstance().getDataFolder() + "/schematics/temp", fileName + ".schematic");
		
		try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
			ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
			//forwardExtentCopy.
			Operations.complete(forwardExtentCopy);
			//ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file));
			//writer.write(clipboard);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		File file = new File(Main.getInstance().getDataFolder() + "/schematics/temp", fileName + ".schem");
		try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))){
			writer.write(clipboard);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSchematic4(String fileName, String worldStr, int x1, int y1, int z1, int x2, int y2, int z2) {
		System.out.println("Started Saving Schematic!!!");
		//World world = FaweAPI.getWorld(worldStr);
		World world = new BukkitWorld(Bukkit.getWorld(worldStr));
		BlockVector3 pos1 = BlockVector3.at(x1, y1, z1);
		BlockVector3 pos2 = BlockVector3.at(x2, y2, z2);
		CuboidRegion region = new CuboidRegion(pos1, pos2);
		
		BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
		
		try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
			ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
			forwardExtentCopy.setCopyingEntities(false);
			Operations.complete(forwardExtentCopy);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		
		File file = new File(Main.getInstance().getDataFolder() + "/schematics/temp", fileName + ".schem");
		try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
			writer.write(clipboard);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finished Saving Schematic!!!");
	}
}
