package me.Sebbben.HypixelMinions.Minions;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class Minion implements InventoryHolder {
    private ItemStack head;
    private ItemStack chestPlate;
    private ItemStack pants;
    private ItemStack boots;
    private ItemStack tool;
    private final Location location;
    private final Material minionBlockType;
    private final Color clothingColor;
    private final Material toolType;
    private final String minionName;
    protected List<ItemStack> info;
    protected List<ItemStack> upgrades;
    protected List<ItemStack> inventory;


    public Minion(Location l) {
        minionBlockType = getBlockType();
        clothingColor = getClothingColor();
        toolType = getToolType();
        minionName = getMinionName();
        location = l;
        makeInventories();
        createOutFit();
        placeMinion();
        placeBlocks();
    }




    public abstract String getMinionName();
    public abstract Material getToolType();
    public abstract Color getClothingColor();
    public abstract Material getBlockType();

    private void placeBlocks() {

    }

    protected void createOutFit() {

        head = getMinionHead();

        chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta chestPlateItemMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
        chestPlateItemMeta.setColor(clothingColor);
        chestPlate.setItemMeta(chestPlateItemMeta);

        pants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta pantsItemMeta = (LeatherArmorMeta) pants.getItemMeta();
        pantsItemMeta.setColor(clothingColor);
        pants.setItemMeta(pantsItemMeta);


        boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bootsItemMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsItemMeta.setColor(clothingColor);
        boots.setItemMeta(bootsItemMeta);

        tool = new ItemStack(toolType, 1);
    }

    public abstract ItemStack getMinionHead();

    public void placeMinion() {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setCanPickupItems(false);
        armorStand.setCustomName(minionName);
        armorStand.setBasePlate(false);
        armorStand.setArms(true);
        armorStand.setInvulnerable(true);

        armorStand.getEquipment().setHelmet(head);
        armorStand.getEquipment().setChestplate(chestPlate);
        armorStand.getEquipment().setLeggings(pants);
        armorStand.getEquipment().setBoots(boots);
        armorStand.getEquipment().setItemInMainHand(tool);
    }

    private void makeInventories() {
        info = new ArrayList<>(3);
        upgrades = new ArrayList<>(5);
        inventory = new ArrayList<>(15);

        makeInfo();
        makeUpgrades();
        makeInv();


    }

    private void makeInv() {
        inventory.add(null);
        ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 1; i<15; i++) {
            inventory.add(filler);
        }
    }
    private void makeUpgrades() {
        ItemStack filler = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        upgrades.add(new ItemStack(filler));
        filler.setType(Material.ORANGE_STAINED_GLASS_PANE);
        upgrades.add(new ItemStack(filler));
        filler.setType(Material.BLUE_STAINED_GLASS_PANE);
        upgrades.add(new ItemStack(filler));
        filler.setType(Material.YELLOW_STAINED_GLASS_PANE);
        upgrades.add(new ItemStack(filler));
        upgrades.add(new ItemStack(filler));
    }
    private void makeInfo() {
        info.add(new ItemStack(Material.REDSTONE_TORCH));
        info.add(getMinionHead());
        info.add(new ItemStack(Material.GOLD_INGOT));
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, 54,getMinionName());

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i=0; i< inv.getSize(); i++) {
                inv.setItem(i, filler);
        }

        inv.setItem(3, info.get(0));
        inv.setItem(4, info.get(1));
        inv.setItem(5, info.get(2));

        inv.setItem(10, upgrades.get(0));
        inv.setItem(19, upgrades.get(1));
        inv.setItem(28, upgrades.get(2));
        inv.setItem(37, upgrades.get(3));
        inv.setItem(46, upgrades.get(4));

        int b = 0;
        for (int i = 21; i<=43; i++) {
            inv.setItem(i, inventory.get(b++));
            if ((i+2)%9==0) {
                i += 4;
            }
        }

        ItemStack collectAll = new ItemStack(Material.CHEST);
        ItemMeta collectAllMeta = collectAll.getItemMeta();
        collectAllMeta.setDisplayName(ChatColor.WHITE+"Collect All");
        collectAll.setItemMeta(collectAllMeta);
        inv.setItem(48, collectAll);

        ItemStack upgrade = new ItemStack(Material.DIAMOND);
        ItemMeta upgradeMeta = upgrade.getItemMeta();
        upgradeMeta.setDisplayName(ChatColor.WHITE+"Fast Upgrade");
        upgrade.setItemMeta(upgradeMeta);
        inv.setItem(50, upgrade);



        ItemStack pickMeUp = new ItemStack(Material.BEDROCK);
        ItemMeta pickMeUpMeta = pickMeUp.getItemMeta();
        pickMeUpMeta.setDisplayName(ChatColor.WHITE+"Pick Me Up");
        pickMeUp.setItemMeta(pickMeUpMeta);
        inv.setItem(53, pickMeUp);

        return inv;
    }

}
