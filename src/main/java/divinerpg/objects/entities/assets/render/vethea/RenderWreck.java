package divinerpg.objects.entities.assets.render.vethea;

import divinerpg.api.Reference;
import divinerpg.objects.entities.assets.model.vethea.ModelWreckForm1;
import divinerpg.objects.entities.entity.vethea.EntityWreck;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderWreck extends RenderLiving<EntityWreck> {


    ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/wreck_form_1.png");

    public RenderWreck(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelWreckForm1(), 1F);

    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWreck entity) {
        return texture;
    }

}