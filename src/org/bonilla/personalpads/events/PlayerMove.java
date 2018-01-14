package org.bonilla.personalpads.events;

import org.bonilla.personalpads.PersonalPads;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMove implements Listener {
    private PersonalPads plugin;
    private final String permission = "personalpads.use";

    public PlayerMove(PersonalPads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player p = event.getPlayer();
        if (p.hasPermission("personalpads.use")) {
            Block block = event.getFrom().getBlock().getRelative(BlockFace.DOWN);
            if ((block.getType() != Material.AIR) && (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ())) {

                Location northLoc = block.getLocation();
                Block northBlock = northLoc.add(0, 0, -1).getBlock(); // NORTH

                Location southLoc = block.getLocation();
                Block southBlock = southLoc.add(0, 0, 1).getBlock(); // SOUTH

                Location eastLoc = block.getLocation();
                Block eastBlock = eastLoc.add(1, 0, 0).getBlock(); // EAST

                Location westLoc = block.getLocation();
                Block westBlock = westLoc.add(-1, 0, 0).getBlock(); // WEST

                if (northBlock.getType() == Material.WALL_SIGN) {
                    throwPlayer(p, northBlock);
                }
                if (southBlock.getType() == Material.WALL_SIGN) {
                    throwPlayer(p, southBlock);
                }
                if (eastBlock.getType() == Material.WALL_SIGN) {
                    throwPlayer(p, eastBlock);
                }
                if (westBlock.getType() == Material.WALL_SIGN) {
                    throwPlayer(p, westBlock);
                }
            }
        }

    }

    public void throwPlayer(Player p, Block b) {
        FileConfiguration config = plugin.getConfig();
        Sign sign = (Sign) b.getState();
        String[] signLines = sign.getLines();
        if (signLines[0].equals(config.getString("sign-data.line-1")) && (Double.valueOf(signLines[1]) >= 0.5 && Double.valueOf(signLines[1]) <= 3)) {
            p.setVelocity(new Vector(0, Double.valueOf(signLines[1]), 0));
            p.setFallDistance(config.getInt("data.fall-distance"));
            if (config.getBoolean("data.use-sound")) {
                p.playSound(p.getLocation(), Sound.valueOf(config.getString("data.sound")), 1, 1);
            }
        }
    }
}
