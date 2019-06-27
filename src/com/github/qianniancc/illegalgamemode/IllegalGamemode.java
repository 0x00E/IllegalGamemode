package com.github.qianniancc.illegalgamemode;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class IllegalGamemode extends JavaPlugin implements Listener {

	@EventHandler
	public void onEnable() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), "config.yml");
		if (!(file.exists())) {
			saveDefaultConfig();
		}

		getLogger().info("启用完毕！");
		reloadConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("======================================");
		getLogger().info("[IllegalGamemode]插件已经加载");
		getLogger().info("======================================");
	}

	@EventHandler

	public void onDisable() {
		getLogger().info("======================================");
		getLogger().info("[IllegalGamemode]插件已经关闭");
		getLogger().info("======================================");
	}

	@EventHandler
	public void changeGamemode(PlayerGameModeChangeEvent evt) {
		Player p = evt.getPlayer();
		String worldname = p.getWorld().getName();
		if (this.getConfig().getStringList("world").contains(worldname)) {
			if ((!p.hasPermission("ig.gamemode") && !p.hasPermission("ig.*") && !p.isOp())) {

				new BukkitRunnable() {
					int x = 0;

					@Override
					public void run() {
						if (x >= 1) {

							this.cancel();

						} else {

							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
									"gamemode " + getConfig().getString("Gamemode") + " " + p.getName());
							x++;
						}
					}
				}.runTaskLaterAsynchronously(this, 20 * 1);

			}
		}
	}

	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equals("igreload")) {
			if (sender.isOp() || sender.hasPermission("ig.*") || sender.hasPermission("ig.reload")) {

				reloadConfig();
				sender.sendMessage("§e§l[IllegalGamemode]§a§l插件重载成功");
			} else {
				sender.sendMessage("§e§l[IllegalGamemode]§c§l你没有权限执行这个指令");
			}
		}
		return false;
	}

}
