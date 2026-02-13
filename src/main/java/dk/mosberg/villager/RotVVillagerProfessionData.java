package dk.mosberg.villager;

import net.minecraft.nbt.NbtCompound;

public class RotVVillagerProfessionData {
    private RotVProfession profession = RotVProfession.NONE;
    private int level;
    private int xp;

    public void readFromNbt(NbtCompound nbt) {
        String value = nbt.getString("Id").orElse(null);
        if (value != null) {
            try {
                profession = RotVProfession.valueOf(value);
            } catch (IllegalArgumentException ignored) {
                profession = RotVProfession.NONE;
            }
        }
        level = nbt.getInt("Level").orElse(level);
        xp = nbt.getInt("Xp").orElse(xp);
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Id", profession.name());
        nbt.putInt("Level", level);
        nbt.putInt("Xp", xp);
    }

    public RotVProfession getProfession() {
        return profession;
    }

    public void setProfession(RotVProfession profession) {
        setProfession(profession, false);
    }

    public void setProfession(RotVProfession profession, boolean resetProgress) {
        RotVProfession next = profession == null ? RotVProfession.NONE : profession;
        if (this.profession != next && resetProgress) {
            level = 0;
            xp = 0;
        }
        this.profession = next;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, level);
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = Math.max(0, xp);
    }

    public void addXp(int amount) {
        setXp(xp + amount);
    }
}
