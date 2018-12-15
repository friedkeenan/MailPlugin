package friedkeenan.mailplugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Envelope{
	private static int lastID=-1;
	private static Map<Integer,Envelope> envelopes=new HashMap<Integer,Envelope>();
	private ItemStack item;
	private Inventory contents;
	Envelope(ItemStack env) throws Exception{
		if(!isEnvelope(env)) {
			throw new Exception("ItemStack is not an envelope");
		}
		item=env;
		contents=Bukkit.createInventory(null,9,"Envelope");
		ItemStack accept=new ItemStack(Material.STAINED_GLASS_PANE,1,(short)13);
		ItemMeta meta=accept.getItemMeta();
		meta.setLocalizedName("Seal Envelope");
		accept.setItemMeta(meta);
		contents.addItem(accept);
	}
	static void giveEnvelope(Player p){
		ItemStack stack=new ItemStack(Material.PAPER);
		ItemMeta meta=stack.getItemMeta();
		meta.setLocalizedName("Unsealed Envelope");
		List<String> lore=new ArrayList<String>();
		lore.add("Envelope");
		lore.add("Unsealed");
		lore.add(Integer.toString(++lastID));
		meta.setLore(lore);
		stack.setItemMeta(meta);
		p.getInventory().addItem(stack);
	}
	static boolean isEnvelope(ItemStack stack) {
		return stack.getType()==Material.PAPER && stack.getItemMeta().getLore().get(0)=="Envelope";
	}
	static int getID(ItemStack stack) {
		return Integer.parseInt(stack.getItemMeta().getLore().get(2));
	}
	static Envelope getEnvelope(ItemStack stack) {
		int id=getID(stack);
		if(!envelopes.containsKey(id)) {
			try {
				Envelope env=new Envelope(stack);
				envelopes.put(id, env);
				return env;
			}catch(Exception exc) {
				return null;
			}
		}
		return envelopes.get(id);
	}
	boolean isSealed() {
		return item.getItemMeta().getLore().get(1)=="Sealed";
	}
	ItemStack setSealed(boolean sealed) {
		ItemMeta meta=item.getItemMeta();
		List<String> lore=meta.getLore();
		lore.set(1,sealed?"Sealed":"Unsealed");
		meta.setLore(lore);
		meta.setLocalizedName(sealed?"Sealed Envelope":"Unsealed Envelope");
		item.setItemMeta(meta);
		return item;
	}
	void open(Player p) {
		p.openInventory(contents);
	}
}