package dk.mosberg.village;

import com.mojang.serialization.Codec;
import dk.mosberg.defense.FortificationManager;
import dk.mosberg.governance.GovernanceManager;
import dk.mosberg.quests.QuestManager;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

/**
 * Persistent state for village-related systems: quests, governance, fortifications.
 */
public class VillageDataPersistentState extends PersistentState {
    private static final String FILE_NAME = "rotv_village_data";
    private static final Codec<VillageDataPersistentState> CODEC = NbtCompound.CODEC
            .xmap(VillageDataPersistentState::fromNbt, VillageDataPersistentState::toNbt);
    private static final PersistentStateType<VillageDataPersistentState> TYPE =
            new PersistentStateType<>(FILE_NAME, VillageDataPersistentState::new, CODEC,
                    DataFixTypes.LEVEL);

    public static VillageDataPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }

    public static VillageDataPersistentState fromNbt(NbtCompound nbt) {
        VillageDataPersistentState state = new VillageDataPersistentState();

        // Load quests
        nbt.getCompound("Quests").ifPresent(QuestManager::loadFromNbt);

        // Load governance
        nbt.getCompound("Governance").ifPresent(GovernanceManager::loadFromNbt);

        // Load fortifications
        nbt.getCompound("Fortifications").ifPresent(FortificationManager::loadFromNbt);

        return state;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();

        // Save quests
        nbt.put("Quests", QuestManager.saveToNbt());

        // Save governance
        nbt.put("Governance", GovernanceManager.saveToNbt());

        // Save fortifications
        nbt.put("Fortifications", FortificationManager.saveToNbt());

        return nbt;
    }

    /**
     * Ensure this state is marked dirty so it gets saved.
     */
    public void markDirtyStatic() {
        markDirty();
    }
}
