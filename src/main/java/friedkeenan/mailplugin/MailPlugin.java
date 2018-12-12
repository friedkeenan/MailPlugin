package friedkeenan.mailplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MailPlugin extends JavaPlugin implements Listener{
	List<Envelope> envelopes=new ArrayList<Envelope>();
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
			if(e.getItem().getAmount()==1 && Envelope.isEnvelope(e.getItem())) {
				try {
					Envelope env=new Envelope(e.getItem());
					env.open(p);
				}catch(Exception exc) {}
			}
		}
	}
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("env")) {
			Envelope.giveEnvelope((Player)sender);
			return true;
		}
		return false;
	}
}
