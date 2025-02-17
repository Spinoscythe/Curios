/*
 * Copyright (c) 2018-2023 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.curios.common.network;

import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import top.theillusivec4.curios.common.network.client.CPacketDestroy;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;
import top.theillusivec4.curios.common.network.client.CPacketOpenVanilla;
import top.theillusivec4.curios.common.network.client.CPacketPage;
import top.theillusivec4.curios.common.network.client.CPacketScroll;
import top.theillusivec4.curios.common.network.client.CPacketToggleCosmetics;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;
import top.theillusivec4.curios.common.network.client.CuriosClientPayloadHandler;
import top.theillusivec4.curios.common.network.server.CuriosServerPayloadHandler;
import top.theillusivec4.curios.common.network.server.SPacketBreak;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;
import top.theillusivec4.curios.common.network.server.SPacketPage;
import top.theillusivec4.curios.common.network.server.SPacketQuickMove;
import top.theillusivec4.curios.common.network.server.SPacketScroll;
import top.theillusivec4.curios.common.network.server.SPacketSetIcons;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncCurios;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncData;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncModifiers;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncRender;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncStack;

public class NetworkHandler {

  public static void register(final IPayloadRegistrar registrar) {
    //Client Packets
    registrar.play(CPacketDestroy.ID, CPacketDestroy::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handleDestroyPacket));
    registrar.play(CPacketOpenCurios.ID, CPacketOpenCurios::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handleOpenCurios));
    registrar.play(CPacketOpenVanilla.ID, CPacketOpenVanilla::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handleOpenVanilla));
    registrar.play(CPacketScroll.ID, CPacketScroll::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handleScroll));
    registrar.play(CPacketPage.ID, CPacketPage::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handlePage));
    registrar.play(CPacketToggleRender.ID, CPacketToggleRender::new,
        handler -> handler.server(CuriosServerPayloadHandler.getInstance()::handlerToggleRender));
    registrar.play(CPacketToggleCosmetics.ID, CPacketToggleCosmetics::new,
        handler -> handler.server(
            CuriosServerPayloadHandler.getInstance()::handlerToggleCosmetics));

    // Server Packets
    registrar.play(SPacketSyncStack.ID, SPacketSyncStack::new,
        handler -> handler.client((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSyncStack(payload, context)));
    registrar.play(SPacketGrabbedItem.ID, SPacketGrabbedItem::new,
        handler -> handler.client((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleGrabbedItem(payload, context)));
    registrar.play(SPacketSyncCurios.ID, SPacketSyncCurios::new,
        handler -> handler.client((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSyncCurios(payload, context)));
    registrar.play(SPacketSyncData.ID, SPacketSyncData::new,
        handler -> handler.client((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSyncData(payload, context)));
    registrar.play(SPacketSyncModifiers.ID, SPacketSyncModifiers::new,
        handler -> handler.client(((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSyncModifiers(payload, context))));
    registrar.play(SPacketSyncRender.ID, SPacketSyncRender::new,
        handler -> handler.client(((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSyncRender(payload, context))));
    registrar.play(SPacketBreak.ID, SPacketBreak::new,
        handler -> handler.client(((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleBreak(payload, context))));
    registrar.play(SPacketScroll.ID, SPacketScroll::new,
        handler -> handler.client(((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleScroll(payload, context))));
    registrar.play(SPacketPage.ID, SPacketPage::new,
        handler -> handler.client(((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handlePage(payload, context))));
    registrar.play(SPacketSetIcons.ID, SPacketSetIcons::new,
        handler -> handler.client((((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleSetIcons(payload, context)))));
    registrar.play(SPacketQuickMove.ID, SPacketQuickMove::new,
        handler -> handler.client((((payload, context) -> CuriosClientPayloadHandler.getInstance()
            .handleQuickMove(payload, context)))));
  }
}
