package me.WesBag.Toontown.GagShop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;

public class GagShopController {
	public static Map<UUID, Boolean> inGagShop = new HashMap<>();
	public static Map<UUID, GagShopData> gagShopDatas = new HashMap<>();
	public static String GagShopTitle = ChatColor.DARK_AQUA + "[Gag Shop]";
	
	public static boolean getInGagShop(UUID pUUID) {
		if (inGagShop.containsKey(pUUID))
			return inGagShop.get(pUUID);
		return false;
	}
	
	public static void setInGagShop(UUID pUUID) {
		inGagShop.put(pUUID, true);
	}
	
	public static void removeInGagShop(UUID pUUID) {
		if (inGagShop.containsKey(pUUID))
			inGagShop.remove(pUUID);
	}
	
	public static void setGagData(UUID pUUID, GagShopData pData) {
		gagShopDatas.put(pUUID, pData);
	}
	
	public static GagShopData getGagShopData(UUID pUUID) {
		if (gagShopDatas.containsKey(pUUID))
			return gagShopDatas.get(pUUID);
		return null;
	}
	
	public static void removeGagData(UUID pUUID) {
		if (gagShopDatas.containsKey(pUUID))
			gagShopDatas.remove(pUUID);
	}
	
	
	
}
