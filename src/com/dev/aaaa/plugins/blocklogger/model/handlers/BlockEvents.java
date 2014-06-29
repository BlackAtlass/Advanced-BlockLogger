package com.dev.aaaa.plugins.blocklogger.model.handlers;

import com.dev.aaaa.plugins.blocklogger.model.Keys;
import com.dev.aaaa.plugins.blocklogger.model.storage.Database;
import com.mbserver.api.events.BlockEvent;
import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.Listener;
import com.mbserver.api.events.RunMode;

public class BlockEvents implements Listener{

	
	private Database db;
	private int count;
	
	public BlockEvents(Database data) {
		db = data;
	}
	
	@EventHandler(concurrency = RunMode.THREADED)
	public void blockEvent(BlockEvent e){
		 
		if(e.getPlayer().getMetaData(Keys.CHECK_KEY,null) != null)
			return;
		
		count++;
		db.update(e.getLocation(),e.getPlayer());
		if(count > 500){
			db.commit();
			count = 0;
		}
	}
	
	@EventHandler
	public void blockBreak(BlockEvent e){
		if(e.getPlayer().getMetaData(Keys.CHECK_KEY,null) == null)
			return;
		
		String[] names = db.read(e.getLocation());
		
		if(names == null)
			e.getPlayer().sendMessage("[ABL] This block has never been modified!");
		
		else
		{
			e.getPlayer().sendMessage("[ABL] The person that modified this block was:\nDisplay:"  + names[1]
					+ "  Login:" + names[0]);
		}
		e.setCancelled(true);
	}
	
}
