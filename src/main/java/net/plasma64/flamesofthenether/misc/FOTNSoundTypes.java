package net.plasma64.flamesofthenether.misc;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

public class FOTNSoundTypes {

    public static final ForgeSoundType MOLTEN_DEBRIS = new ForgeSoundType(1.0F, 1.0F, FOTNSoundEvents.MOLTEN_DEBRIS_BREAK, FOTNSoundEvents.MOLTEN_DEBRIS_STEP, FOTNSoundEvents.MOLTEN_DEBRIS_PLACE, FOTNSoundEvents.MOLTEN_DEBRIS_HIT, FOTNSoundEvents.MOLTEN_DEBRIS_FALL);
    public static final ForgeSoundType SOUL_PEPPER_BUSH = new ForgeSoundType(1.0F, 1.0F, FOTNSoundEvents.SOUl_PEPPER_BUSH_BREAK, () -> SoundEvents.GRASS_STEP, FOTNSoundEvents.SOUl_PEPPER_BUSH_PLACE, () -> SoundEvents.GRASS_HIT, () -> SoundEvents.GRASS_FALL);
    public static final ForgeSoundType ASH = new ForgeSoundType(1.0F, 1.0F, FOTNSoundEvents.ASH_BREAK, FOTNSoundEvents.ASH_STEP, FOTNSoundEvents.ASH_PLACE, FOTNSoundEvents.ASH_HIT, FOTNSoundEvents.ASH_FALL);

}
