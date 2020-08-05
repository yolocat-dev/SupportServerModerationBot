package org.golde.discordbot.shared.command.guildmod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.golde.discordbot.shared.ESSBot;
import org.golde.discordbot.shared.command.BaseCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public abstract class GuildModCommand extends BaseCommand {

	private static final Category CATEGORY_GUILD_MODERATOR = new Category("\ud83e\uddd9\ud83c\udffc Guild Moderator");
	
	public GuildModCommand(@Nonnull ESSBot bot, @Nonnull String nameIn, @Nullable String argsIn, @Nullable String helpIn, @Nullable String... aliasesIn) {
		super(bot, nameIn, argsIn, helpIn, aliasesIn);
		this.category = CATEGORY_GUILD_MODERATOR;
		this.requiredRole = CATEGORY_GUILD_MODERATOR.getName();
	}
	
	public final static boolean isModerator(Member person) {
		for(Role r : person.getRoles()) {
			if(r.getName().equals(CATEGORY_GUILD_MODERATOR.getName())) {
				return true;
			}
		}
		return false;
	}
	
	
}