package org.shanerx.discord.scotty.commands.info;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.List;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Channel implements Command {
	
	@Override
	public boolean onCommand(net.dv8tion.jda.core.entities.User sender, String[] cmd, MessageReceivedEvent event) {
		if (!event.isFromType(ChannelType.TEXT)) {
			return true;
		}
		else if (ScottyBot.getInstance().getAccountType() == AccountType.CLIENT) {
			if (!userIsAuthorized(sender)) {
				return true;
			}
		}
		
		if (event.getMessage().getMentionedChannels().isEmpty()) {
			Guild g = event.getGuild();
			TextChannel text = event.getTextChannel();
			String name = text.getName();
			String id = text.getId();
			String desc = text.getTopic();
			OffsetDateTime creation = text.getCreationTime();
			int position = text.getPosition() + 1;
			int pins = text.getPinnedMessages().complete().size();
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setColor(Color.CYAN).
			setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/ScottyBot", event.getJDA().getSelfUser().getAvatarUrl()).
			setTitle("Channel Information", "https://www.github.com/ShanerX/Scotty").
			setThumbnail("http://i.imgur.com/SwQM8Ei.png").
			addField("Name", name, true).
			addField("Channel ID", id, true).
			addField("Guild Name", g.getName(), true).
			addField("Topic", desc == null ? "*No topic*" : desc, true).
			addField("Created", creation.getYear() + "-" + (creation.getMonthValue() < 10 ?
					creation.getMonthValue() + "0" : creation.getMonthValue()) + "-" +
					(creation.getDayOfMonth() < 10 ? "0" + creation.getDayOfMonth() :
						creation.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Position", Integer.toString(position), true).
			addField("Pinned Messages", Integer.toString(pins), true).
			setFooter("Requested by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), null);
			event.getChannel().sendMessage(builder.build()).queue();
			return true;
		}
		
		for (TextChannel text : event.getMessage().getMentionedChannels()) {
			Guild g = event.getGuild();
			String name = text.getName();
			String id = text.getId();
			String desc = text.getTopic();
			OffsetDateTime creation = text.getCreationTime();
			int position = text.getPosition() + 1;
			int pins = text.getPinnedMessages().complete().size();
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setColor(Color.CYAN).
			setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/Scotty", event.getJDA().getSelfUser().getAvatarUrl()).
			setTitle("Channel Information", "https://www.github.com/ShanerX/Scotty").
			setThumbnail("http://i.imgur.com/SwQM8Ei.png").
			addField("Name", name, true).
			addField("Channel ID", id, true).
			addField("Guild Name", g.getName(), true).
			addField("Topic", desc == null ? "*No topic*" : desc, true).
			addField("Created", creation.getYear() + "-" + (creation.getMonthValue() < 10 ?
					creation.getMonthValue() + "0" : creation.getMonthValue()) + "-" +
					(creation.getDayOfMonth() < 10 ? "0" + creation.getDayOfMonth() :
						creation.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Position", Integer.toString(position), true).
			addField("Pinned Messages", Integer.toString(pins), true).
			setFooter("Requested by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), null);
			event.getChannel().sendMessage(builder.build()).queue();
		}
		return true;
	}
	
	@Override
	public String getUsage() {
		return "channel [<#text1> [#text2] [#text3] ...]";
	}

	@Override
	public String getDescription() {
		return "Request information about a channel.";
	}

}