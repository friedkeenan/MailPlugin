package friedkeenan.mailplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Envelope extends ItemStack{
	Envelope(Player p){
		super(Material.PAPER);
		ItemMeta meta=getItemMeta();
		meta.setDisplayName("Unsealed Envelope");
		List<String> lore=new ArrayList<String>();
		lore.add("Envelope");
		lore.add("Unsealed");
		meta.setLore(lore);
		setItemMeta(meta);
		p.getInventory().addItem(this);
	}
	static boolean isEnvelope(ItemStack stack) {
		return stack.getType()==Material.PAPER && stack.getItemMeta().getLore().get(0)=="Envelope";
	}
}
