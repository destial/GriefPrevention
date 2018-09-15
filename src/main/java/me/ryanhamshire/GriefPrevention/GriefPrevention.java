/*
    GriefPrevention Server Plugin for Minecraft
    Copyright (C) 2012 Ryan Hamshire

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ryanhamshire.GriefPrevention;

import me.ryanhamshire.GriefPrevention.claim.ClaimClerk;
import me.ryanhamshire.GriefPrevention.claim.ClaimRegistrar;
import me.ryanhamshire.GriefPrevention.command.AbandonClaimCommand;
import me.ryanhamshire.GriefPrevention.command.CreateClaimCommand;
import me.ryanhamshire.GriefPrevention.command.ExtendClaimCommand;
import me.ryanhamshire.GriefPrevention.command.trust.TrustCommand;
import me.ryanhamshire.GriefPrevention.command.trust.UntrustCommand;
import me.ryanhamshire.GriefPrevention.listener.ClaimListener;
import me.ryanhamshire.GriefPrevention.listener.ClaimTool;
import me.ryanhamshire.GriefPrevention.player.PlayerDataRegistrar;
import me.ryanhamshire.GriefPrevention.storage.FlatFileStorage;
import me.ryanhamshire.GriefPrevention.storage.Storage;
import org.bukkit.plugin.java.JavaPlugin;

public class GriefPrevention extends JavaPlugin
{
    private ClaimRegistrar claimRegistrar;
    private PlayerDataRegistrar playerDataRegistrar;
    private Storage storage; //TODO: add setter, config-controlled

    public void onEnable()
    {
        storage = new FlatFileStorage(this);
        claimRegistrar = new ClaimRegistrar(this, storage);
        playerDataRegistrar = new PlayerDataRegistrar(storage);

        ClaimClerk claimClerk = new ClaimClerk(this, claimRegistrar, playerDataRegistrar, storage);

        new ClaimListener(this, claimRegistrar);
        new ClaimTool(this);

        getCommand("newclaim").setExecutor(new CreateClaimCommand(claimClerk));
        getCommand("extendclaim").setExecutor(new ExtendClaimCommand(claimClerk));
        getCommand("abandonclaim").setExecutor(new AbandonClaimCommand(claimClerk, claimRegistrar));
        getCommand("trustcommand").setExecutor(new TrustCommand(claimClerk));
        getCommand("untrustcommand").setExecutor(new UntrustCommand(claimClerk));
    }
}