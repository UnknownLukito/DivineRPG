package divinerpg.events;

import divinerpg.DivineRPG;
import divinerpg.api.Reference;
import divinerpg.config.Config;
import divinerpg.utils.LocalizeUtils;
import divinerpg.utils.Utils;
import divinerpg.utils.log.Logging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class EventClientLogin {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent evt) {
        EntityPlayer player = evt.player;

        if (!player.world.isRemote) {
            // Greetings
            if (Utils.isDeveloperName(player.getUniqueID())) {
                Logging.message(player, TextFormatting.DARK_RED + LocalizeUtils.normal("Welcome DivineRPG developer! " + player.getName() + " is on the DivineRPG dev team"));
            } else if (Utils.isTesterName(player.getUniqueID())) {
                Logging.message(player, TextFormatting.BLUE + LocalizeUtils.normal("Welcome DivineRPG tester! " + player.getName() + " kindly offered their time to help test DivineRPG. Thank you for your service!"));
            } else if (Utils.isPatreon(player.getUniqueID())) {
                Logging.message(player, TextFormatting.YELLOW + LocalizeUtils.normal("Welcome DivineRPG patreon! " + player.getName() + " kindly supported the DivineRPG dev team, thank you for your help!"));
            } else {
                Logging.message(player, "Welcome " + player.getDisplayName().getFormattedText());
            }

            printGeneralInformation(player);
        }
    }

    /**
     * If UpdateChecking enabled, download version asynchronously and writes to chat up-to-date info
     * All methods are using overlay config
     *
     * @param player
     */
    private void printGeneralInformation(EntityPlayer player) {
        if (Config.UpdateChecker) {
            // check online status
            CompletableFuture<Boolean> onlineStatus = CompletableFuture.supplyAsync(() -> checkPlayerOnlineStatus(player));
            // get version
            CompletableFuture<String> version = onlineStatus.thenApply(isOnline -> isOnline ? UpdateChecker.getCurrentVersion() : null);

            // after all tasks print version detect
            version.thenCombineAsync(onlineStatus, (currentVersion, isOnline) -> {
                printVersionStatus(player, currentVersion, isOnline);
                // need to return percantage
                return true;
            });
        }
    }

    /**
     * Checking online status and writing log if detecting offline
     *
     * @param player
     * @return
     */
    private boolean checkPlayerOnlineStatus(EntityPlayer player) {
        // Writing mod version
        DivineRPG.logger.info("Current Version : " + Reference.VERSION);

        // trying ping Google
        boolean isOnline = UpdateChecker.pingGoogle();
        // Log message with is offline
        if (!isOnline && Config.canShowOverlay) {
            Logging.message(player, TextFormatting.LIGHT_PURPLE + LocalizeUtils.normal("message.version.internet"));
        }

        // logging online status
        DivineRPG.logger.info("Is Online: " + isOnline);
        return isOnline;
    }

    /**
     * Retreiving actual DivineRPG mod version and Log
     *
     * @param player
     * @param newVersion
     * @param isOnline
     * @return
     */
    private void printVersionStatus(EntityPlayer player, String newVersion, boolean isOnline) {

        boolean githubAccessError = isOnline && newVersion == null;
        boolean haveUpdates = !githubAccessError && newVersion.contains(Reference.MODID);

        if (Config.canShowOverlay && !githubAccessError) {
            if (haveUpdates) {
                Logging.message(player, TextFormatting.RED + LocalizeUtils.normal("message.version.update"));
                Logging.message(player, TextFormatting.WHITE + LocalizeUtils.version(newVersion));

            } else {
                Logging.message(player, TextFormatting.AQUA + player.getDisplayName().getFormattedText() + " has the most recent version of DivineRPG installed!");
            }
        }

        if (isOnline) {
            if (githubAccessError) {
                DivineRPG.logger.info("Can't retrieve current version from GitHub");
            } else {
                DivineRPG.logger.info("Update Available : " + haveUpdates);
            }
        }
    }
}