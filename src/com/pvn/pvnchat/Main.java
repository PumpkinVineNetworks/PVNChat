package com.pvn.pvnchat;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	ArrayList<String> staffChannel = new ArrayList<String>();
	ArrayList<String> donatorChannel = new ArrayList<String>();
	
	public void onEnable(){
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public void onDisable(){
		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		
		Player player = event.getPlayer();
		
	    if (staffChannel.contains(player.getName())){
	    	
	    	for (Player staff : Bukkit.getOnlinePlayers()){
		    	
	    		event.setCancelled(true);
	    		staff.sendMessage(ChatColor.RED + "[STAFF CHAT]" + " " + player.getName() + ": " + event.getMessage());
		    		
				
			}
	    	
	    	
	    }
	    
	    if (donatorChannel.contains(player.getName())){
	    	
	    	for (Player donator : Bukkit.getOnlinePlayers()){
	    		
	    		event.setCancelled(true);
	    		donator.sendMessage(ChatColor.DARK_AQUA + " " + player.getName() + ": " + event.getMessage());
	    	}
	    } 
	    
	    if (!staffChannel.contains(player.getName()) && !donatorChannel.contains(player.getName())){
	    	
	    	event.setCancelled(true);
	    	event.setMessage(ChatColor.GRAY + player.getName() + ": " + event.getMessage());
	    }
		
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if (staffChannel.contains(player.getName())) {
			
			staffChannel.remove(player.getName());
			
		} else if (donatorChannel.contains(player.getName())){
			
			donatorChannel.remove(player.getName());
			
		}
		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args){
		
		Player player = (Player) sender;
		
		if (args.length == 0){
			player.sendMessage(ChatColor.GRAY + "================ " + " Help " + "================");
			player.sendMessage("");
			player.sendMessage(ChatColor.YELLOW + "/chat staff" + ChatColor.GRAY + " ->" + "Switches your chat channel to the Staff Chat");
			player.sendMessage(ChatColor.YELLOW + "/chat donator" + ChatColor.GRAY + " ->" + "Switches your chat channel to the Donator Chat");
		}
		
		if (cmd.getName().equalsIgnoreCase("chat")){
			
			if (args.length == 0){
				player.sendMessage(ChatColor.GRAY + "================ " + " Help " + "================");
				player.sendMessage("");
				player.sendMessage(ChatColor.YELLOW + "/chat staff" + ChatColor.GRAY + " ->" + "Switches your chat channel to the Staff Chat");
				player.sendMessage(ChatColor.YELLOW + "/chat donator" + ChatColor.GRAY + " ->" + "Switches your chat channel to the Donator Chat");
				return true;
			}
			
			if (args[0].equals("staff")){
				
				if (staffChannel.contains(player.getName())){
					
					staffChannel.remove(player.getName());
					
				} else {
					
					staffChannel.add(player.getName());
					
					PacketsUtil.sendActionBar(player, ChatColor.RED + "You are now in the Staff Chat");
					
				}
				
				
				
			} else if (args[0].equals("donator")){
				
				if (donatorChannel.contains(player.getName())){
					
					donatorChannel.add(player.getName());
					
					PacketsUtil.sendActionBar(player, ChatColor.DARK_AQUA + "You are now in the Donator Chat");					
				}
				
				
				
			}
			
			
		}
		
		
		return false;
		
	}
	
	

}
