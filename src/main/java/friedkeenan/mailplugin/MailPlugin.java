package friedkeenan.mailplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MailPlugin extends JavaPlugin implements Listener{
	@Override
	public void onDisable() {}
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void openEnvelope(PlayerInteractEvent e) {
		Player p=e.getPlayer();
		if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			p.sendMessage("Right click");
		}
	}
}
