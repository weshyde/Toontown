package me.WesBag.Toontown.SchematicUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

public class SchematicPaster {
	/*
	public void pasteSchematic(org.bukkit.World world, File file, int X, int Y, int Z) {
		com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(world);
		
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

		    Clipboard clipboard = reader.read();

		    try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1)) {

		        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
		                .to(BlockVector3.at(X, Y, Z)).ignoreAirBlocks(true).build();

		        try {
		            Operations.complete(operation);
		            editSession.flushSession();

		        } catch (WorldEditException e) {
		            //player.sendMessage(ChatColor.RED + "OOPS! Something went wrong, please contact an administrator");
		            e.printStackTrace();
		        }
		    }


		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    return;
		} catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
	}
	*/
	public static void pasteSchematic2(String worldStr, File file, int X, int Y, int Z, int yRotation, boolean ignoreAirBlocks) {
		System.out.println("Started Pasting Schematic!!!");
		BlockVector3 to = BlockVector3.at(X, Y, Z);
		World world = new BukkitWorld(Bukkit.getWorld(worldStr));
		//World world = FaweAPI.getWorld(worldStr);
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		ClipboardReader reader = null;
		try {
			reader = format.getReader(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Clipboard clipboard = null;
		try {
			clipboard = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
			ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);
			clipboardHolder.setTransform(new AffineTransform().rotateY(yRotation));
			Operation operation = clipboardHolder.createPaste(editSession).to(to).ignoreAirBlocks(ignoreAirBlocks).build();
			Operations.complete(operation);
			editSession.close();
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		System.out.println("Finished Pasting Schematic!!!");
		/*
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
			Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(to).ignoreAirBlocks(false).build();
			Operations.complete(operation);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		*/
	}
}
