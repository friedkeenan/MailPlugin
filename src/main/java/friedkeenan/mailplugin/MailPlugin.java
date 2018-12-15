package friedkeenan.mailplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MailPlugin extends JavaPlugin implements Listener{
	@Override
	public void onDisable() {}
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void clickEnvelope(PlayerInteractEvent e) {
		Player p=e.getPlayer();
		ItemStack stack=e.getItem();
		if(e.getAction()==Action.RIGHT_CLICK_AIR) {
			if(stack.getAmount()==1 && Envelope.isEnvelope(stack)) {
				Envelope env=Envelope.getEnvelope(stack);
				if(!env.isSealed())
					env.open(p);
				else {
					//Set name of envelope
				}
			}
		}
	}
	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if(!(e.getAction().toString().contains("PICKUP") || e.getAction().toString().contains("MOVE") || e.getAction().toString().contains("HOTBAR")))
			return;
		final Player p=(Player)e.getWhoClicked();
		ItemStack stack=e.getCurrentItem();
		if(e.getClickedInventory().getName()=="Envelope" && stack.getType()!=Material.AIR && stack.getItemMeta().getLocalizedName()=="Seal Envelope") {
			e.setCancelled(true);
			Envelope env=Envelope.getEnvelope(p.getInventory().getItemInMainHand());
			p.getInventory().setItemInMainHand(env.setSealed(true));
			getServer().getScheduler().runTask(this,new Runnable() {
				public void run() {
					p.closeInventory();
				}});
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
