package me.WesBag.Toontown.BattleCore.Cogs.Sellbot.TheMingler;

public class TheMingler {

	public static final int[][] Buzzword = {
			{20, 20, 20, 20, 20},
			{10, 11, 13, 15, 16},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] ParadigmShift = {
			{25, 25, 25, 25, 25},
			{12, 15, 18, 21, 24},
			{60, 70, 75, 80, 90}
	};
	public static final int[][] PowerTrip = {
			{15, 15, 15, 15, 15},
			{10, 13, 14, 15, 18},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] Schmooze = {
			{30, 30, 30, 30, 30},
			{7, 8, 12, 15, 16},
			{55, 65, 75, 85, 95}
	};
	public static final int[][] TeeOff = {
			{10, 10, 10, 10, 10},
			{8, 9, 10, 11, 12},
			{70, 75, 80, 85, 95}
	};
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {Buzzword, ParadigmShift, PowerTrip, Schmooze, TeeOff};
	public static final String[] attackNames = {"Buzzword", "Paradigm Shift", "Power Trip", "Schmooze", "Tee Off"};
	public static final boolean[] hitAll = {false, true, true, false, false};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1ODg5NTk3NCwKICAicHJvZmlsZUlkIiA6ICJiMGQ3MzJmZTAwZjc0MDdlOWU3Zjc0NjMwMWNkOThjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJPUHBscyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMjU3ZDgyM2IzMWQwMGRhNWRhODI3YjNiMjcxOTg3ZTFhMjNjOWU5ZWVkMjMyMTE5MmUyZDdhZjQ0YzU0MDFkIgogICAgfQogIH0KfQ";
	public static final String skinSignature = "egDFv+bIDlQSoHx0ksW6d/13yPbuhLAs1prMeF7+YfbEQhxhWDpWfnWG72jm8q6W3BoQSeYJ4xpBYmv8sl9cKQybiCwmi4EOHpxJIM1oK/MKldsnv5j08Hj9H6ZyWRUKlFevNYja7bYfP2CxORYeJQABFKw+3IBXXo3Xy+ftdaelthsxXgWqfM/n6kxIskIt4ZXI3na6bxBvOHHD0nYslDeYOkspOJzt0iY90iOIa8+7oBx6FBApbDdg55vbYxm9N19Y08zrFtZQnkTzopeNCO045LCpIKkaOOa22wrl0Vx0uMxAY3SGc034JH8yLXmwKACKb9BrFSlB0znE+pgiUZrTOWM8BTmUs+GH/APvgtb8rYHtz9HtNij5ikG8BSpX9D/DZxc9nRsh/YXhyjwSc05kuY/CPBhypww6j9oN3stdu0ejMDfjfE2e4RcJCVYdoLaq6pMFoM8ffwAXbcLFMzOzzgaCqa9LXkcZSa7ZUN+ICeqjDTpwwjoMMVY0hq4kpma4Wa6l3ltO4L7RHiaQk8F3z20Y/9l13B9CiPVYJzZskACmqA7OBclAlzv/hGsPXVX8hGINqXLXscnh73T90YA8Lk6a8TOfWsB+I9YH2J5+exUph9QfgqLj6Tc0Mfb8oXIGZSEFwQpc7wmWGvLJV06Oo+hi+K+/zqNtmvMCdGk=";
}
