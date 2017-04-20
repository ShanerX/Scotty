package org.shanerx.discord.scotty.listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageReceived extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getChannel().getName().equalsIgnoreCase("cross-server")) {
			if (!event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
				for (Guild g : event.getJDA().getGuilds()) {
					if (g.getId().equals(event.getGuild().getId())) continue;
					g.getTextChannelsByName("cross-server", false).get(0).sendMessage(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + event.getMessage().getContent()).queue();
				}
			}
		}
	}

}