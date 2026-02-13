package dk.mosberg.village;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class VillageProfile {
    private final String id;
    private BlockPos center;
    private VillageTier tier = VillageTier.HAMLET;
    private VillageSpecialization specialization = VillageSpecialization.NONE;
    private int population;
    private int beds;
    private int workstations;
    private float happiness;
    private float security;
    private int food;
    private int wealth;
    private long lastUpdated;

    public VillageProfile(String id, BlockPos center) {
        this.id = id;
        this.center = center;
    }

    public static VillageProfile fromNbt(NbtCompound nbt) {
        String id = nbt.getString("Id").orElse("");
        BlockPos center = new BlockPos(nbt.getInt("CenterX").orElse(0),
                nbt.getInt("CenterY").orElse(0), nbt.getInt("CenterZ").orElse(0));
        VillageProfile profile = new VillageProfile(id, center);
        String tierValue = nbt.getString("Tier").orElse(VillageTier.HAMLET.name());
        try {
            profile.tier = VillageTier.valueOf(tierValue);
        } catch (IllegalArgumentException ignored) {
            profile.tier = VillageTier.HAMLET;
        }
        String specializationValue =
                nbt.getString("Specialization").orElse(VillageSpecialization.NONE.name());
        try {
            profile.specialization = VillageSpecialization.valueOf(specializationValue);
        } catch (IllegalArgumentException ignored) {
            profile.specialization = VillageSpecialization.NONE;
        }
        profile.population = nbt.getInt("Population").orElse(0);
        profile.beds = nbt.getInt("Beds").orElse(0);
        profile.workstations = nbt.getInt("Workstations").orElse(0);
        profile.happiness = nbt.getFloat("Happiness").orElse(0.0f);
        profile.security = nbt.getFloat("Security").orElse(0.0f);
        profile.food = nbt.getInt("Food").orElse(0);
        profile.wealth = nbt.getInt("Wealth").orElse(0);
        profile.lastUpdated = nbt.getLong("LastUpdated").orElse(0L);
        return profile;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Id", id);
        nbt.putInt("CenterX", center.getX());
        nbt.putInt("CenterY", center.getY());
        nbt.putInt("CenterZ", center.getZ());
        nbt.putString("Tier", tier.name());
        nbt.putString("Specialization", specialization.name());
        nbt.putInt("Population", population);
        nbt.putInt("Beds", beds);
        nbt.putInt("Workstations", workstations);
        nbt.putFloat("Happiness", happiness);
        nbt.putFloat("Security", security);
        nbt.putInt("Food", food);
        nbt.putInt("Wealth", wealth);
        nbt.putLong("LastUpdated", lastUpdated);
        return nbt;
    }

    public String getId() {
        return id;
    }

    public BlockPos getCenter() {
        return center;
    }

    public void setCenter(BlockPos center) {
        this.center = center;
    }

    public VillageTier getTier() {
        return tier;
    }

    public void setTier(VillageTier tier) {
        this.tier = tier;
    }

    public VillageSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(VillageSpecialization specialization) {
        this.specialization = specialization;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getWorkstations() {
        return workstations;
    }

    public void setWorkstations(int workstations) {
        this.workstations = workstations;
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public float getSecurity() {
        return security;
    }

    public void setSecurity(float security) {
        this.security = security;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
