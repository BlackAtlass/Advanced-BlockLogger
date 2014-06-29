package com.dev.aaaa.plugins.blocklogger.model.commands;

import com.dev.aaaa.plugins.blocklogger.model.Keys;
import com.mbserver.api.CommandExecutor;
import com.mbserver.api.CommandSender;
import com.mbserver.api.game.Player;

public class CheckBlock implements CommandExecutor{

	
	@Override
	public void execute(String command, CommandSender sender, String[] args,
			String label) {
		
		if(!(sender instanceof Player))
		{
			sender.sendMessage("[ABL] This command can only be executed as a player!");
			return;
		}
		
		Player player = (Player)sender;
		
		if(!player.hasPermission("abl.checkblock"))
		{
			sender.sendMessage("You don't have permission to use this command!");
			return;
		}
		
		if(player.getMetaData(Keys.CHECK_KEY,null) !=  null)
		{
			sender.sendMessage("[ABL] Check mode has been Deactivated!");
			player.removeMetaData(Keys.CHECK_KEY);
			return;
		}
		
		player.setMetaData(Keys.CHECK_KEY,"urmom");
		player.sendMessage("[ABL] Check mode has been Activated!");
		player.sendMessage("[ABL] Place a block to see who destroyed that block!");
		player.sendMessage("[ABL] Break a block to see who placed that block!");
		
		
	}

}
