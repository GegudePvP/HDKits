package net.fightpvp.kits;

import net.minecraft.server.v1_7_R1.EntityFishingHook;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.EntitySnowball;
import net.minecraft.server.v1_7_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R1.PlayerConnection;
import net.minecraft.server.v1_7_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class CopyOfFishingHook extends EntityFishingHook
{
  private Snowball sb;
  private EntitySnowball controller;
  public int a;
  public EntityHuman owner;
  public Entity hooked;
  public boolean lastControllerDead;
  public boolean isHooked;

  public CopyOfFishingHook(org.bukkit.World world, EntityHuman entityhuman)
  {
    super(((CraftWorld)world).getHandle(), entityhuman);
    this.owner = entityhuman;
  }

  protected void c() {
  }

  public void h() {
    if (!this.lastControllerDead);
    this.lastControllerDead = this.controller.dead;
    for (Entity entity : this.controller.world.getWorld().getEntities()) {
      if (((entity instanceof Firework)) || (entity.getEntityId() == getBukkitEntity().getEntityId()) || (entity.getEntityId() == this.owner.getBukkitEntity().getEntityId()) || (entity.getEntityId() == this.controller.getBukkitEntity().getEntityId()) || ((entity.getLocation().distance(this.controller.getBukkitEntity().getLocation()) >= 2.0D) && ((!(entity instanceof Player)) || (((Player)entity).getEyeLocation().distance(this.controller.getBukkitEntity().getLocation()) >= 2.0D))))
        continue;
      this.controller.die();
      this.hooked = entity;
      this.isHooked = true;
      this.locX = entity.getLocation().getX();
      this.locY = entity.getLocation().getY();
      this.locZ = entity.getLocation().getZ();
      this.motX = 0.0D;
      this.motY = 0.04D;
      this.motZ = 0.0D;
    }try {
      this.locX = this.hooked.getLocation().getX();
      this.locY = this.hooked.getLocation().getY();
      this.locZ = this.hooked.getLocation().getZ();
      this.motX = 0.0D;
      this.motY = 0.04D;
      this.motZ = 0.0D;
      this.isHooked = true;
    } catch (Exception e) {
      if (this.controller.dead) {
        this.isHooked = true;
      }
      this.locX = this.controller.locX;
      this.locY = this.controller.locY;
      this.locZ = this.controller.locZ;
    }
  }

  public void die() {
  }

  public void remove() {
    super.die();
  }

  public void spawn(Location location) {
    this.sb = ((Snowball)this.owner.getBukkitEntity().launchProjectile(Snowball.class));
    this.controller = ((CraftSnowball)this.sb).getHandle();

    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.controller.getId() });
    for (Player p : Bukkit.getOnlinePlayers()) {
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
    ((CraftWorld)location.getWorld()).getHandle().addEntity(this);
  }

  public boolean isHooked() {
    return this.isHooked;
  }

  public void setHookedEntity(Entity damaged) {
    this.hooked = damaged;
  }
}