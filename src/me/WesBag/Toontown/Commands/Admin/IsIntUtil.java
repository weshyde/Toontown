package me.WesBag.TTCore.Commands.AdminCommands;

public class IsIntUtil {
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static int getInt(String s) {
		return Integer.parseInt(s);
	}
}
