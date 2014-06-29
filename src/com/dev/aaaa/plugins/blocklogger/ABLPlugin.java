package com.dev.aaaa.plugins.blocklogger;

import java.io.File;

import com.dev.aaaa.plugins.blocklogger.model.commands.CheckBlock;
import com.dev.aaaa.plugins.blocklogger.model.handlers.BlockEvents;
import com.dev.aaaa.plugins.blocklogger.model.storage.Database;
import com.mbserver.api.Library;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.Manifest;

@Manifest(name = "ADV_BlockLogger",authors = "AAAA",libraries = Library.SQLITE)
public class ABLPlugin extends MBServerPlugin{
	private Database db;
	
	@Override
	public void onEnable() {
		
		new File("plugins/ADV_BlockLogger").mkdirs();
		db = new Database();
		if(!db.init())
		{
			this.getLogger().severe("[ABL] Could not init Database!");
			return;
		}
		
		this.getPluginManager().registerCommand("checkblock",new CheckBlock());
		this.getPluginManager().registerEventHandler(new BlockEvents(db));
	}
	
	@Override
	public void onDisable() {
		db.commit();
		db.close();
	}
	
}
