package me.Sebbben.HypixelMinions.Minions;

import me.Sebbben.HypixelMinions.Events.MinionManager;
import me.Sebbben.HypixelMinions.Main;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class Minion implements InventoryHolder {
    private ItemStack head;
    private ItemStack chestPlate;
    private ItemStack pants;
    private ItemStack boots;
    private ItemStack tool;
    private ArmorStand armorStand;
    private final Location location;
    private final Material minionBlockType;
    private final Material minionMaterialType;
    private final Color clothingColor;
    private final Material toolType;
    private final String minionName;
    private final long miningPeriod;
    private final ItemStack idealLayoutItem;
    protected List<ItemStack> info;
    protected List<ItemStack> upgrades;
    protected List<ItemStack> inventory;
    private final Main plugin;
    public static ItemStack pickMeUp;
    public static ItemStack upgrade;
    public static ItemStack collectAll;
    public static ItemStack idealLayout;

    public static void init() {
        pickMeUp = new ItemStack(Material.BEDROCK);
        ItemMeta pickMeUpMeta = pickMeUp.getItemMeta();
        pickMeUpMeta.setDisplayName(ChatColor.WHITE+"Pick Me Up");
        pickMeUp.setItemMeta(pickMeUpMeta);

        upgrade = new ItemStack(Material.DIAMOND);
        ItemMeta upgradeMeta = upgrade.getItemMeta();
        upgradeMeta.setDisplayName(ChatColor.WHITE+"Fast Upgrade");
        upgrade.setItemMeta(upgradeMeta);

        collectAll = new ItemStack(Material.CHEST);
        ItemMeta collectAllMeta = collectAll.getItemMeta();
        collectAllMeta.setDisplayName(ChatColor.WHITE+"Collect All");
        collectAll.setItemMeta(collectAllMeta);

        idealLayout = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta idealLayoutMeta = idealLayout.getItemMeta();
        idealLayoutMeta.setDisplayName("Ideal Layout");
        idealLayout.setItemMeta(idealLayoutMeta);
    }


    public Minion(Main plugin1, Location l) {
        plugin = plugin1;
        minionBlockType = getBlockType();
        minionMaterialType = getMaterialType();
        idealLayoutItem = getIdealLayoutItem();
        clothingColor = getClothingColor();
        toolType = getToolType();
        minionName = getMinionName();
        miningPeriod = getMiningPeriod();
        location = l;
        makeInventories();
        createOutFit();
        placeMinion();
        doMining();
    }

    public abstract String getMinionName();
    public abstract Material getToolType();
    public abstract Color getClothingColor();
    public abstract Material getBlockType();
    public abstract Material getMaterialType();
    public abstract ItemStack getIdealLayoutItem();
    public abstract Long getMiningPeriod();
    public abstract ItemStack getMinionHead();

    public UUID getUUID() {
        return armorStand.getUniqueId();
    }

    private void doMining() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.getServer().getEntity(armorStand.getUniqueId()) == null) {
                    this.cancel();
                    return;
                }
                boolean placedBlock = placeBlock();
                if (!placedBlock) {
                    mineBlock();
                }
            }
        }.runTaskTimer(plugin, 0L, 20*miningPeriod);

    }
    private boolean placeBlock() {
        Location l;
        for (int i=-2; i<=2; i++) {
            for (int j=-2;j<=2;j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                l = location.clone().add(i,-1,j);
                l.getBlock();
                if (l.getBlock().getType().equals(Material.AIR)) {
                    l.getBlock().setType(minionBlockType);
                    armorStand.setRotation(135,0);
                    return true;
                }
            }
        }
        return false;
    }
    private void mineBlock() {
        Location l;
        Random random = new Random();
        int i,j;
        int attempts = 0;

        do {
            i = random.nextInt(5)-2;
            j = random.nextInt(5)-2;
            l = location.clone().add(i,-1,j);
            attempts++;
        } while (l.getBlock().getType() != minionBlockType && attempts < 25);

        boolean addedBlockToInv = false;

        for (ItemStack item : inventory) {
            if (item.getType().equals(minionMaterialType)) {
                if (item.getAmount() < 64) {
                    item.setAmount(item.getAmount()+1);
                    addedBlockToInv = true;
                    break;
                }
            }
        }

        if (!addedBlockToInv && inventory.size() < 15) {
            inventory.add(new ItemStack(minionMaterialType, 1));
        }

        if (inventory.size() == 15 && inventory.get(14).getAmount() == 64) {
            return;
        }
        l.getBlock().setType(Material.AIR);
    }

    private void removeMinion() {
        armorStand.getWorld().dropItem(location,getMinionHead());
        armorStand.remove();
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


    public void placeMinion() {
        armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
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
        inventory.add(new ItemStack(minionMaterialType, 60));
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
        info.add(idealLayout);
        info.add(getMinionHead());
        info.add(new ItemStack(Material.GOLD_INGOT));
    }

    private Inventory fillInv(Inventory inv) {
        ItemStack bFiller = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta bFillerMeta = bFiller.getItemMeta();
        bFillerMeta.setDisplayName(" ");
        bFiller.setItemMeta(bFillerMeta);

        for (int i=0;i<inv.getSize(); i++) {
            if (inv.getContents()[i] == null || inv.getContents()[i].getType() == Material.AIR) {
                inv.setItem(i,bFiller);
            }
        }
        return inv;
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, 54,getMinionName());

        inv = fillInv(inv);

        inv.setItem(3, info.get(0));
        inv.setItem(4, info.get(1));
        inv.setItem(5, info.get(2));

        inv.setItem(10, upgrades.get(0));
        inv.setItem(19, upgrades.get(1));
        inv.setItem(28, upgrades.get(2));
        inv.setItem(37, upgrades.get(3));
        inv.setItem(46, upgrades.get(4));

        inv.setItem(48, collectAll);
        inv.setItem(50, upgrade);
        inv.setItem(53, pickMeUp);


        List<ItemStack> minionInv = createInventory();

        for (int i = 21; i<=43; i++) {

            inv.setItem(i,minionInv.remove(0));

            if ((i+2)%9==0) {
                i += 4;
            }
        }


        return inv;
    }

    private List<ItemStack> createInventory() {
        List<ItemStack> inv = new ArrayList<>();
        inv.addAll(inventory);

        ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        while (inv.size() <= 15) {
            inv.add(filler);
        }
        return inv;
    }

    private void transferItems(Player player) {
        while (inventory.size() != 0) {
            player.getInventory().addItem(inventory.remove(0));
            player.closeInventory();
            player.openInventory(getInventory());
        }
    }

    private Inventory getIdealLayoutInv() {
        Inventory inv = Bukkit.createInventory(this, 45, "Ideal Layout (Top-down view)");
        inv = fillInv(inv);
        for (int i=0; i<=4; i++) {
            for (int j=2; j<=6; j++) {
                inv.setItem(i*9+j, idealLayoutItem);
            }
        }
        inv.setItem(22, getMinionHead());
        return inv;
    }

    public void handleInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (Objects.equals(e.getCurrentItem(), Minion.pickMeUp)) {
            player.closeInventory();
            removeMinion();
        } else if (Objects.equals(e.getCurrentItem(), Minion.collectAll)) {
            transferItems(player);
        } else if (Objects.equals(e.getCurrentItem(), Minion.idealLayout)) {
            player.openInventory(getIdealLayoutInv());
        }
    }

}
