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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MailPlugin extends JavaPlugin implements Listener{
	private List<Envelope> envelopes=new ArrayList<Envelope>();
	Envelope findEnvelope(ItemStack stack) {
		for(Envelope e:envelopes) {
			if(e.itemEquals(stack)){
				return e;
			}
		}
		try {
			Envelope env=new Envelope(stack);
			envelopes.add(env);
			return env;
		}catch(Exception exc) {
			return null;
		}
	}
	@Override
	public void onDisable() {}
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void openEnvelope(PlayerInteractEvent e) {
		Player p=e.getPlayer();
		ItemStack stack=e.getItem();
		if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			if(stack.getAmount()==1 && Envelope.isEnvelope(stack)) {
				try {
					Envelope env=findEnvelope(stack);
					env.open(p);
				}catch(Exception exc) {
					p.sendMessage(exc.toString());
				}
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
