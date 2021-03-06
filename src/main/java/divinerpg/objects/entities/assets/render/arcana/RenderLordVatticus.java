package divinerpg.objects.entities.assets.render.arcana;

import divinerpg.api.Reference;
import divinerpg.objects.entities.entity.arcana.EntityLordVatticus;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderLordVatticus extends RenderLiving<EntityLordVatticus> {

    ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/lord_vatticus.png");

    public RenderLordVatticus(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBiped(), 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLordVatticus entity) {
        return texture;
    }

}