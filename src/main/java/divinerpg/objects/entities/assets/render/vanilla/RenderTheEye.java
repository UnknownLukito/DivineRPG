package divinerpg.objects.entities.assets.render.vanilla;

import divinerpg.api.Reference;
import divinerpg.objects.entities.assets.model.vanilla.ModelTheEye;
import divinerpg.objects.entities.entity.vanilla.EntityTheEye;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTheEye extends RenderLiving<EntityTheEye> {

    ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/the_eye.png");

    public RenderTheEye(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelTheEye(), 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTheEye entity) {
        return texture;
    }
}