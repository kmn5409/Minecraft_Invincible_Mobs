package me.keanunichols.invinciblemob;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class invincibleMob extends JavaPlugin {
	
	/*
	 * Mobs to spawn every 2 minutes?: Creeper, Evoker, Husk
	 * 
	 */
	
	private Random random;
	private invincibleMob instance;
	private BukkitTask task;
	
	private Random getRandom() {
        return this.random;
    }
	
	private boolean getRandomBoolean() {
	    Random random = new Random();
	    return random.nextBoolean();
	}
	
	private void invulnerableAspects(Entity mob) {
		//make a chance that it's invulnerable
		//maybe change mobs to zomibe, husk, zombie vuillager
		//remove some after like 9 spawn, also make it random that they are invulnerable
		//also need to make them spawn a bit further from me
		if(getRandomBoolean()){
			mob.setInvulnerable(true);
			mob.setGlowing(true);
		}
	}
	
	@Override
	/*
	 Do the plugin for like one minute and a half to 2 minutes
	 */
	public void onEnable() {
		this.random = new Random();
		this.instance = this;
		this.task = new BukkitRunnable() {
            public void run()
            {		
            	if(Bukkit.getOnlinePlayers().size() == 0)
            		return;
            	//for(Player plr: Bukkit.getOnlinePlayers())
            	//	plr.sendMessage(Bukkit.getOnlinePlayers().size() + "");
            	int randomSize = getRandom().nextInt(Bukkit.getOnlinePlayers().size());
                final Player plr = new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(randomSize);
                Location pLocation = plr.getLocation();
                World pWld = plr.getWorld();
                Chunk chunk = pLocation.getChunk();
                int playerY = pLocation.getBlockY();
                Location randLoc = chunk.getBlock(random.nextInt(16), playerY,random.nextInt(16)).getLocation();
                //Entity zombie = pWld.spawnEntity(pLocation, EntityType.ZOMBIE);
                Entity zombie = pWld.spawnEntity(randLoc, EntityType.ZOMBIE);
                randLoc = chunk.getBlock(random.nextInt(16), playerY,random.nextInt(16)).getLocation();
                //Entity zombieVillager = pWld.spawnEntity(pLocation, EntityType.ZOMBIE_VILLAGER);
                Entity zombieVillager = pWld.spawnEntity(randLoc, EntityType.ZOMBIE_VILLAGER);
                //Entity husk = pWld.spawnEntity(pLocation, EntityType.HUSK);
                randLoc = chunk.getBlock(random.nextInt(16), playerY,random.nextInt(16)).getLocation();
                Entity husk = pWld.spawnEntity(randLoc, EntityType.HUSK);
                final Set<Entity> entitySet = new HashSet<Entity>();
                entitySet.add(zombie);
                entitySet.add(zombieVillager);
                entitySet.add(husk);
                invulnerableAspects(zombie);
                invulnerableAspects(zombieVillager);
                invulnerableAspects(husk);
                new BukkitRunnable() {
                    public void run() {
                    	//if(Bukkit.getOnlinePlayers().size() == 0)
                    	//	return;
                    	//final Player plr = new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(randomSize);
                    	//plr.sendMessage("Ran");
                        for (final Entity entity : entitySet) {
                            entity.remove();
                            //plr.sendMessage("removed");
                        }
                    }
                //}.runTaskLater((Plugin)invincibleMob.this.instance, 2400L);
                }.runTaskLater((Plugin)invincibleMob.this.instance, 2400L);
                
                //creeper.setGlowing(true);
                //creeper.setInvulnerable(true);
                //PotionRain pRain = new PotionRain();
        		//pRain.makeRain(plr);
            }
        //}, 1200L, randomNumberTime());
		}.runTaskTimer((Plugin)this, 1200L, 1200L);
	}
	

}
