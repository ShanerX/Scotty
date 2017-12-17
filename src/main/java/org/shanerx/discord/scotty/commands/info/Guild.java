package org.shanerx.discord.scotty.commands.info;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.List;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Guild implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!event.isFromType(ChannelType.TEXT)) {
			return true;
		}
		else if (ScottyBot.getInstance().getAccountType() == AccountType.CLIENT) {
			if (!userIsAuthorized(sender)) {
				return true;
			}
		}
		net.dv8tion.jda.core.entities.Guild g = event.getGuild();
		String guildName = g.getName();
		String id = g.getId();
		String icon = g.getIconUrl();
		OffsetDateTime date = g.getCreationTime();
		int members = g.getMembers().size();
		String owner = g.getOwner().getUser().getName() + "#" + g.getOwner().getUser().getDiscriminator();
		String ownerID = g.getOwner().getUser().getId();
		String region = g.getRegion().getName();
		int text = g.getTextChannels().size();
		int voice = g.getVoiceChannels().size();
		String vLevel = g.getVerificationLevel().toString();
		int emoteCount = g.getEmotes().size();
		List<Role> roles = g.getRoles();
		int roleCount = roles.size();
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.
		setColor(Color.CYAN).
		setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/Scotty", event.getJDA().getSelfUser().getAvatarUrl()).
		setTitle("Guild Information", "https://www.github.com/ShanerX/Scotty").
		setThumbnail("http://i.imgur.com/SwQM8Ei.png").
		addField("Name", guildName, true).
		addField("ID", id, true).
		addField("Icon", icon, true).
		addField("Creation", date.getYear() + "-" + (date.getMonthValue() < 10 ? date.getMonthValue() + "0" : date.getMonthValue()) + "-" + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth()) + " (yyyy-mm-dd)", true).
		addField("Owner", owner, true).
		addField("Owner ID", ownerID, true).
		addField("Members", Integer.toString(members), true).
		addField("Region", region, true).
		addField("Channels", "Text: " + text + " | Voice: " + voice, true).
		addField("Security", vLevel, true).
		addField("Emotes", Integer.toString(emoteCount), true).
		addField("Roles", "(" + roleCount + ")" + " " + rolesToString(roles), true).
		setFooter("Requested by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), null);
		event.getChannel().sendMessage(builder.build()).queue();
		return true;
	}
	
	private String rolesToString(List<Role> roles) {
		StringBuilder sb = new StringBuilder();
		for (Role r : roles) {
			sb.append(r.getName()).append(", ");
		}
		return sb.toString().substring(0, sb.toString().length() - 2);
	}

	@Override
	public String getUsage() {
		return "guild";
	}

	@Override
	public String getDescription() {
		return "Request information about the guild you're in.";
	}

}