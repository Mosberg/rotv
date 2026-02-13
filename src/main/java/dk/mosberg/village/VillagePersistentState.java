package dk.mosberg.village;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.mojang.serialization.Codec;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

public class VillagePersistentState extends PersistentState {
    private static final String FILE_NAME = "rotv_villages";
    private static final Codec<VillagePersistentState> CODEC =
            NbtCompound.CODEC.xmap(VillagePersistentState::fromNbt, VillagePersistentState::toNbt);
    private static final PersistentStateType<VillagePersistentState> TYPE =
            new PersistentStateType<>(FILE_NAME, VillagePersistentState::new, CODEC,
                    DataFixTypes.LEVEL);

    private final Map<String, VillageProfile> profiles = new HashMap<>();

    public static VillagePersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }

    public static VillagePersistentState fromNbt(NbtCompound nbt) {
        VillagePersistentState state = new VillagePersistentState();
        NbtList list = nbt.getList("Villages").orElse(new NbtList());
        for (int i = 0; i < list.size(); i++) {
            list.getCompound(i).ifPresent(tag -> {
                VillageProfile profile = VillageProfile.fromNbt(tag);
                state.profiles.put(profile.getId(), profile);
            });
        }
        return state;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtList list = new NbtList();
        for (VillageProfile profile : profiles.values()) {
            list.add(profile.toNbt());
        }
        nbt.put("Villages", list);
        return nbt;
    }

    public VillageProfile getOrCreate(String id, BlockPos center) {
        return profiles.computeIfAbsent(id, key -> new VillageProfile(key, center));
    }

    public Collection<VillageProfile> getProfiles() {
        return profiles.values();
    }
}
