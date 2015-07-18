package com.minehut.core.util.common.items;

import com.minehut.core.util.common.chat.F;
import com.minehut.core.util.common.items.EnchantGlow;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luke on 10/18/14.
 */
public class ItemStackFactory {
	
    public static ItemStack createItem(String name, String lore, Material material, int amount) {
        return createItem(material, name, Arrays.asList(lore), amount);
    }

    public static ItemStack createItem(String name, String lore, Material material) {
        return createItem(name, lore, material, 1);
    }

    public static ItemStack createDye(String name, int data, List<String> lore) {
        ItemStack itemStack = createItem(Material.INK_SACK, name, lore, 1);

        itemStack.setDurability((short) data);

        return itemStack;
    }
    
    public static ItemStack createItem(Material material, String name, List<String> lore) {
        return createItem(material, name, lore, 1);
    }
    
    public static ItemStack createItem(Material material, String name, List<String> lore, int amount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
//        return makeUnbreakable(item);
        return item;
    }
    

//    public static ItemStack makeUnbreakable(ItemStack item) {
//    	//if (!item.hasItemMeta()) {
//    		//item.setItemMeta(item.getItemMeta());
//    	//}
//    	net.minecraft.server.v1_8_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item); //This can be used for any NBT tag edit for any item block or entity
//		addUnbreakingTag(nmsItem);
//		ItemStack out = CraftItemStack.asBukkitCopy(nmsItem);
//        return out;
//    }
    
    
    public static ItemStack createItem(Material material, GameItemStackEnchantment... args) {
    	return createItem(material, 1, args);
    }
    
    public static ItemStack createItem(Material material, int amount, GameItemStackEnchantment... args) {
    	ItemStack item = new ItemStack(material, amount);
    	ItemMeta meta = item.getItemMeta();
    	for (GameItemStackEnchantment theEnchantment : args) {
    		meta.addEnchant(theEnchantment.enchantment, theEnchantment.level, true);
    	}
    	item.setItemMeta(meta);
//    	return makeUnbreakable(item);
        return item;
    }
    
    public static ItemStack createItem(Material material, String name, GameItemStackEnchantment... args) {
    	return createItem(material, name, 1, args);
    }
    
    public static ItemStack createItem(Material material, String name, int amount, GameItemStackEnchantment... args) {
    	ItemStack item = new ItemStack(material, amount);
    	ItemMeta meta = item.getItemMeta();
    	meta.setDisplayName(name);
    	for (GameItemStackEnchantment theEnchantment : args) {
    		meta.addEnchant(theEnchantment.enchantment, theEnchantment.level, true);
    	}
    	item.setItemMeta(meta);
//    	return makeUnbreakable(item);
        return item;
    }
    
    public static ItemStack createItem(Material material, String name, String lore) {
        return createItem(name, lore, material);
    }

//    public static ItemStack createItem(Material material, String name, int amount) {
//        return createItem(material, name, amount);
//    }

    public static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        //    	return makeUnbreakable(item);
        return item;
    }

    public static ItemStack createItemWithGlow(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        //    	return makeUnbreakable(item);
        com.minehut.core.util.common.items.EnchantGlow.addGlow(item);
        return item;
    }

    public static ItemStack createItem(Material material) {
        ItemStack item = new ItemStack(material);
        //    	return makeUnbreakable(item);
        return item;
    }

    public static ItemStack createItem(Material material, int amount) {
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        //    	return makeUnbreakable(item);
        return item;
    }

    public static ItemStack createItem(Material material, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material);
        item.addEnchantment(enchantment, level);
        //    	return makeUnbreakable(item);
        return item;
    }

	public static ItemStack createItem(Material material, String name, Enchantment enchantment, int level) {
		ItemStack item = new ItemStack(material);
		item.addEnchantment(enchantment, level);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);

        //    	return makeUnbreakable(item);
        return item;
	}

    public static ItemStack createColoredArmor(Material material, Color color) {
        ItemStack item = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        //    	return makeUnbreakable(item);
        return item;
    }

    public static ItemStack createColoredArmor(Material material, String name, Color color) {
        ItemStack item = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createColoredArmorWithGlow(Material material, String name, Color color) {
        ItemStack item = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        EnchantGlow.addGlow(item);

        return item;
    }

    public static ItemStack createColoredWool(Color color, String name) {
        @SuppressWarnings("deprecation")
        ItemStack wool = new ItemStack(Material.WOOL, 1, (short) 0, DyeColor.getByColor(color).getData());
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(name);
        wool.setItemMeta(meta);
        return wool;
    }

    public static ItemStack skullFromString(String s, String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(s);
        if (name != null) {
            meta.setDisplayName(name);
        }
        skull.setItemMeta(meta);
        return skull;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack fromString(String itemString) {
        String[] parts = itemString.split(":");

        String mat_string = parts[0];
        String data_string = (parts.length > 1 ? parts[1] : null);
        String amount_string = (parts.length > 2 ? parts[2] : null);
        String ench_string = (parts.length > 3 ? parts[3] : null);

        class Ench {
            private int level;
            private Enchantment ench;

            public Ench(Enchantment ench, int level) {
                this.level = level;
                this.ench = ench;
            }

            public int getLevel() {
                return level;
            }

            public Enchantment getEnchantment() {
                return ench;
            }
        }

        List<Ench> enchantments = new ArrayList<Ench>();
        if (ench_string != null) {
            String[] enchs_string = ench_string.split(";");

            for (String str : enchs_string) {
                String enchant = str.replaceAll("[^A-Za-z_]", "");
                String level = str.replaceAll("[^\\d]", "");

                Enchantment enchantment = null;
                for (Enchantment ench : Enchantment.values()) {
                    if (enchant.equalsIgnoreCase(ench.getName())) {
                        enchantment = ench;
                        break;
                    }
                }

                int lvl = -1;
                try {
                    lvl = Integer.valueOf(level);
                } catch (NumberFormatException e) {
                }

                if (lvl >= 0 && enchantment != null) {
                    Ench ench = new Ench(enchantment, lvl);
                    enchantments.add(ench);
                }
            }
        }

        try {
            Material mat = Material.getMaterial(mat_string);

            if (mat == null) {
				F.log("---------> unknown material: " + mat_string);
                return null;
            }

            Byte data = (data_string != null ? Byte.valueOf(data_string) : null);
            Integer amount = (amount_string != null ? Integer.valueOf(amount_string) : null);

            ItemStack item = new ItemStack(mat);
            if (data != null)
                item = new ItemStack(mat, 1, (short) 1, data);

            if (amount != null)
                item.setAmount(amount);

            for (Ench ench : enchantments) {
                item.addUnsafeEnchantment(ench.getEnchantment(), ench.getLevel());
            }

            return item;
        } catch (NumberFormatException e) {
            return null;
        }

    }

	public static boolean isStair(Material material) {
		if(
				material == Material.WOOD_STAIRS ||
				material == Material.ACACIA_STAIRS ||
				material == Material.BIRCH_WOOD_STAIRS ||
				material == Material.BRICK_STAIRS ||
				material == Material.COBBLESTONE_STAIRS ||
				material == Material.DARK_OAK_STAIRS ||
				material == Material.JUNGLE_WOOD_STAIRS ||
				material == Material.NETHER_BRICK_STAIRS ||
				material == Material.QUARTZ_STAIRS ||
				material == Material.SANDSTONE_STAIRS ||
				material == Material.SMOOTH_STAIRS ||
				material == Material.SPRUCE_WOOD_STAIRS) {
			return true;
		}
		return false;
	}

	public static boolean isSlab(Material material) {
		if (
				material == Material.STEP ||
						material == Material.DOUBLE_STEP ||
						material == Material.WOOD_DOUBLE_STEP ||
						material == Material.WOOD_STEP ||
						material == Material.DOUBLE_STEP
				) {
			return true;
		}
		return false;
	}

	public static boolean isPickaxe(Material material) {
		if (material == Material.WOOD_PICKAXE
				|| material == Material.STONE_PICKAXE
				|| material == Material.IRON_PICKAXE
				|| material == Material.GOLD_PICKAXE
				|| material == Material.DIAMOND_PICKAXE) {
			return true;
		}
		return false;
	}
}
