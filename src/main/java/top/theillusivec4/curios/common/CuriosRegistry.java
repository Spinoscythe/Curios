package top.theillusivec4.curios.common;

import nerdhub.cardinal.components.api.event.ItemComponentCallbackV2;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosComponent;
import top.theillusivec4.curios.api.type.component.ICurio;
import top.theillusivec4.curios.common.inventory.screen.CuriosScreenHandler;
import top.theillusivec4.curios.common.item.AmuletItem;
import top.theillusivec4.curios.common.item.CrownItem;

public class CuriosRegistry {

  public static final Item AMULET = new AmuletItem();
  public static final Item CROWN = new CrownItem();

  public static final ScreenHandlerType<CuriosScreenHandler> CURIOS_SCREENHANDLER = ScreenHandlerRegistry
      .registerSimple(new Identifier(CuriosApi.MODID, "curios_screen"), CuriosScreenHandler::new);

  public static void registerItems() {
    Registry.register(Registry.ITEM, new Identifier(CuriosApi.MODID, "amulet"), AMULET);
    Registry.register(Registry.ITEM, new Identifier(CuriosApi.MODID, "crown"), CROWN);
  }

  public static void registerComponents() {
    ItemComponentCallbackV2.event(AMULET).register(
        ((item, itemStack, componentContainer) -> componentContainer
            .put(CuriosComponent.ITEM, new ICurio() {

              @Override
              public void curioTick(String identifier, int index, LivingEntity livingEntity) {

                if (!livingEntity.getEntityWorld().isClient() && livingEntity.age % 40 == 0) {
                  livingEntity.addStatusEffect(
                      new StatusEffectInstance(StatusEffects.REGENERATION, 80, 0, true, true));
                }
              }
            })));

    ItemComponentCallbackV2.event(CROWN).register(
        ((item, itemStack, componentContainer) -> componentContainer
            .put(CuriosComponent.ITEM, new ICurio() {

              @Override
              public void curioTick(String identifier, int index, LivingEntity livingEntity) {

                if (!livingEntity.getEntityWorld().isClient() && livingEntity.age % 20 == 0) {
                  livingEntity
                      .addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, -1, true, true));
                  itemStack.damage(1, livingEntity,
                      damager -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, damager));
                }
              }
            })));
  }
}
