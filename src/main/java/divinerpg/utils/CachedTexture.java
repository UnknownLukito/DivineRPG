package divinerpg.utils;

import divinerpg.DivineRPG;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CachedTexture {
    // single instance to store all loaded values
    private static final Map<String, ResourceLocation> values = new HashMap<>();
    public static final CachedTexture PROJECTILES = new CachedTexture("textures/projectiles/%s.png");

    private final String pattern;

    public CachedTexture(String pattern) {
        this.pattern = pattern;
    }

    public ResourceLocation getTexture(String name) {
        if (!values.containsKey(name)) {
            values.put(name, new ResourceLocation(DivineRPG.MODID, String.format(pattern, name)));
        }

        return values.get(name);
    }
}