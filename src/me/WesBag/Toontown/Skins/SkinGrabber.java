package me.WesBag.Toontown.Skins;

import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;

public class SkinGrabber {
	
	public static void changeSkin(Player player, int skinNum) {
		GameProfile profile = ((CraftPlayer)player).getHandle().getProfile();
		PlayerConnection connection = ((CraftPlayer)player).getHandle().b;
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.b, ((CraftPlayer)player).getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", getToonSkin(skinNum));
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, ((CraftPlayer)player).getHandle()));
	}
	
	private static Property getToonSkin(int skinNum) {
		switch (skinNum) {
		case 0: //Cat
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY0OTcxMjExMTM4MywKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80ZWZmNWY2ZGRhMzI4MjU1MWU5Mzg0MGVjYWY2ODIzY2Y3OGUyZTgxNGUxNmY0ZmZiYTM2Y2M5YWMzMmNjNGE4IgogICAgfQogIH0KfQ==", "YF5jng6xEjeHhk6l1XQbnCiDC/3sLVcr8D/XjCwLx3NTc1r/IFCKG9qFMed6Yw7ZaosvQk5istwyJ1zwsAZbfclk928tnJBSy9ZWsKrlZjkBL+aBPCvwwRzFUxoaLXv4LUK4394/13fqQG21M/xT7MDshroHLZlQiqqkof9i+sl7ZR4kfHEIb5FDoUA/k+AGTamjm9z9czTZDf+FB8ry4vDsVab34dWiCW2inwYw5DqXx0AS1alfLpnbCBsL+MXsV2EojbBdXqzw/fxIekd9SFrNxfrgCyAIjSO3l1IYBx444pMxEyfxC/3zOvcH3NtSQMLsf1oHrJSIDRi2JkSr6j+adS0Yuio2bHlhjlpvUkKQa+VoNLEqiVb47/yarLWEmOK7jX7Mq/K2wn7Q2J6UnRFE5W07+ROGspz+3i0DQIAJj8y5LAeWnF5TREPiJv7bXb3o2reRntK39ibPSnCI6dNIqvUnd+/05gNUY7IqVZKojOoGCLdncnW0QMw90P34kUAY/Pms660LslbHOg1RDlStZaOvRcs9/eD4szKcO0EVhQLz9sJRtpf35yi5k7gcWT9ibBp8iFoIbwbjj8xND0wK8jhhtNljx5uJmi2WK37Eylfaq9pw8ExphtouJGG9DeoxH+SLR5ieNHP+S9GlsPeSDJmp47wNNNusY8auXEg=");
		case 1: //Dog
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY0OTcxMjA5MTI3NSwKICAicHJvZmlsZUlkIiA6ICIzYjA1NTdlYmVmYjc0MDdmYmFmMjVhN2IyNzYwZTZlMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNcl9JY2VTcGlrZXMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQxNGJjYzZkN2RlYzc0MTM4ZDZkMjI1Y2VlYTRkMWMxZGMzNTcxY2RmZmZkNDg4MzM0YmY2NjY3ZTc1ZTQxZCIKICAgIH0KICB9Cn0=", "HC367S9FLiZVcyvk6XD6kxPq81PzP4WeRi/hMArU/Oa/o1rMBkxghyMXs3yLM8VgRLBKGCwY1j0HwevDpNn5LuO9M/s2gBWxg0zYFQj+9YQhS2J7joRbx6ZNT5XgJaiTK707HpgTqUNcH/ihA/vKgnYUp35k0rCmOioF0q79OxM06ZhcxCUkhj8KHQJvGtv2dbRr4bgChWClMAKR75t1nmUTtikeu19NFxTeAJOMdzP6N+Iuj22wMQnNcwD1q8AX/KgVitmuWBCxXB4ef6lJKoAkmcbwrwP/3U0SC840Q3lE/WrYxrwevmb6ZdtzzrqmIuUzpqIQ/9VihBR36oPLdAYzR0mhNvNu03d7AkJLbJFZAuAc4tUR9EfxSjv20oAadN8z5g+iwW/8vmUcBKm38DOODWtunx4UFLmpqPHXsmnETUx5/QBPf6msTMOchq99ugIvh+iIcivK1HOeP5LfagtwN1XHhyeqWc8U309r6+XnQNzWakTGFCiDdote5xk0fLo3QHwz3GidT4MvNhPXgLebAu6PA12FNtywHmiaMaTFNP3tCa+CoQ/fnWtCcRDLrHgr6oqQ0LqglVugEO3QoabC0/zHG9B4NE3sLikW+/EIKmH8hYbStGZx0V2ThoOwCLPlf4HHcdETLOvoNhlciyV/Dt+hzf8w2sRYdZuiH0M=");
		case 2: //Duck
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY0OTcxMjEzMDU4MSwKICAicHJvZmlsZUlkIiA6ICIyM2YxYTU5ZjQ2OWI0M2RkYmRiNTM3YmZlYzEwNDcxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICIyODA3IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE0MmUwNWEwNWVmNTA0Y2M0ZTg5MjJjNjY4YWZhZWZkM2NhNTUyOWRjMTU1YmM2NTRmYTc1ZGE4ZDkzMDBjYTEiCiAgICB9CiAgfQp9", "d0YZDaJ0RR2UY1azOu4CxF0w6NKVMO9AzJqTC89vRUY1OkMA20L55FL5kjem9kMYlycOw9Rewcef0ZSYe7tKqSZiA2FRtcc8W+y5L/q+LKbjTWgU7CwYv/TTeoOOmLJSa2dCJK9RTdSqa1OaAAA169ZwnrywZpvOJ12DUGqpVuQ17zrRCkEprgNYJA4EJzI45V1Glu1xQkbQtiNxHPxFqUg3MEDWgdvC8fq+KbmB34stS2ClgkGLQeLXH/hbEWDwMOmPDcK2O1qWF/JxCkDY7ehHCyhxJk6ViJ0kmb4ipgGKaANMU6M1AL3ejHMtGAP2vjLtH3AP4gmlONhqJhNfD99/LJV4XU26+R8j1mTJ8YrBBHfHON8KjBeubaQOkNTfkGbcxVpKOSuluOkBlWQ4ePeZaxSshWkzmps6/M6KQ3bea8EdDIZBMDk+H2YtxSW2OkPP4AtqLFXklmQCAwDoUVsMnzEIRbFo6ZovYRYKPVOjBQZAMj2RoBzwI5rZKQdfCXsn3REKbg5Z9UfeTQY8igSPr3TCVmWmOKGV3lPk/Q3lrMiJIlfB+ZrkbHUMH3gfqli9WXnoxFx9V6zgQXns8s1+iBffhn4eHnsB9Ou8Rg7iqhxrLaTos5/bvSiNw6rg/4nUrwjjlp6ytsfYf8ciiOKPElH7rQqq1swYrQFKXvg=");
		case 3: //Mouse
			return new Property("", "", "");
		case 4: //Pig
			return new Property("", "", "");
		case 5: //Rabbit
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY0OTcxMjE2NjA1OCwKICAicHJvZmlsZUlkIiA6ICIzYTdhMDVjMDc0MTI0N2Q2YWVmMDMzMDNkOWNlMjMzNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcXJ0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk5ZWYyZTg5Yzk5NjAxZTkwMTFhYWU1MTc2ZGU4MDFiN2I0ZTFjOWIxM2RiOWEyMjI0MTc3YjcwY2QwNzRiMmMiCiAgICB9CiAgfQp9", "GfncTIT0/v8gsrQ1Q6ffT5STycTnhPpq+HRR3b4sKE9mERP0xcqidG2wZwzWwGe0QbJM5sgD6v1Jqi5VkXkOQ7Xs0Fv4ZQ8c3ltEOdf9UQNnRvgVpDyE7EhSNLxblB9FUEJKzfgM7SSYM2Z8lpB3vq0DI8mYRXLfh5a71pS+X3XR8D1uofV08SInh+c7BE3qwwpGBdgHQ3odxl17KVi9eDSKm4aFYQIqB6o1kglIs0aOKI7N6I/xNvpFPI+JEl06pR5K4oR5qt6qOczneQYQ44uO2IyJKdmaIRsYE+GyOkjh9kQ1oPT1ahiruBMcp2qXpE27HlvTtK4029PnPHZTA3agoWkS/cCkZWMXqbhqs3QOuVGNYPYVxyAzQukD0dqxbIKrSrBKOPeAFHgP43yuW6NJUTp1ho7t8D2zN3cZEMYSBFnNIr4ktek468KuKiT1VQU9O0eTdvX6QoUpEEOtdaVyByafYF71jQiCBPpUjahE5uaY9mtd4NpMhpTJrA1KyiqEtOuaRYaoOzFqGwjvx8YF2tdit89VvDJ1RtJA26QgT0Sq8Ub6yJQKiWqj5mKIn2gKzrgL4/fRqJAl7vCdVXkzND0L6gkfgNtNF5/Dc1iBwLmnE1LS87jEdfIuBaS3/6SNRhyiF+m8suJ3/vzt3qxhNDT59mqH//Ed3xzNSTc=");
		default: 
			return new Property("", "", "");
		}
	}

}
