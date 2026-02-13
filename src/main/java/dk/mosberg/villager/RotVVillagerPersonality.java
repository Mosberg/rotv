package dk.mosberg.villager;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class RotVVillagerPersonality {
    private int workEfficiency;
    private int courage;
    private int tradingStyle;
    private int sociability;

    public RotVVillagerPersonality() {
        this(50, 50, 50, 50);
    }

    public RotVVillagerPersonality(int workEfficiency, int courage, int tradingStyle,
            int sociability) {
        this.workEfficiency = MathHelper.clamp(workEfficiency, 0, 100);
        this.courage = MathHelper.clamp(courage, 0, 100);
        this.tradingStyle = MathHelper.clamp(tradingStyle, 0, 100);
        this.sociability = MathHelper.clamp(sociability, 0, 100);
    }

    public static RotVVillagerPersonality random(Random random) {
        return new RotVVillagerPersonality(30 + random.nextInt(71), 20 + random.nextInt(81),
                20 + random.nextInt(81), 30 + random.nextInt(71));
    }

    public void readFromNbt(NbtCompound nbt) {
        workEfficiency = nbt.getInt("WorkEfficiency").orElse(workEfficiency);
        courage = nbt.getInt("Courage").orElse(courage);
        tradingStyle = nbt.getInt("TradingStyle").orElse(tradingStyle);
        sociability = nbt.getInt("Sociability").orElse(sociability);
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("WorkEfficiency", workEfficiency);
        nbt.putInt("Courage", courage);
        nbt.putInt("TradingStyle", tradingStyle);
        nbt.putInt("Sociability", sociability);
    }

    public int getWorkEfficiency() {
        return workEfficiency;
    }

    public int getCourage() {
        return courage;
    }

    public int getTradingStyle() {
        return tradingStyle;
    }

    public int getSociability() {
        return sociability;
    }
}
