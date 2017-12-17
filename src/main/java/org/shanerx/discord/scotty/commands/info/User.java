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
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class User implements Command {

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
		
		if (event.getMessage().getMentionedUsers().isEmpty()) {
			Guild g = event.getGuild();
			net.dv8tion.jda.core.entities.User u = sender;
			Member m = g.getMember(u);
			
			String member = u.getName() + "#" + u.getDiscriminator();
			String nick = m.getNickname();
			String id = u.getId();
			String avatar = u.getAvatarUrl();
			OffsetDateTime registration = u.getCreationTime();
			OffsetDateTime join = m.getJoinDate();
			String online = m.getOnlineStatus().toString();
			List<Permission> perms = m.getPermissions();
			List<Role> roles = m.getRoles();
			int roleCount = roles.size();
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setColor(Color.CYAN).
			setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/ScottyBot", event.getJDA().getSelfUser().getAvatarUrl()).
			setTitle("User Information", "https://www.github.com/ShanerX/ScottyBot").
			setThumbnail("http://i.imgur.com/SwQM8Ei.png").
			addField("Username", member, true).
			addField("User ID", id, true).
			addField("Nickname", nick == null ? member : nick, true).
			addField("Avatar", avatar, true).
			addField("Status", online, true).
			addField("Registered", registration.getYear() + "-" + (registration.getMonthValue() < 10 ?
					registration.getMonthValue() + "0" : registration.getMonthValue()) + "-" +
					(registration.getDayOfMonth() < 10 ? "0" + registration.getDayOfMonth() :
						registration.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Joined Guild", join.getYear() + "-" + (join.getMonthValue() < 10 ?
					join.getMonthValue() + "0" : join.getMonthValue()) + "-" + (join.getDayOfMonth() < 10 ?
							"0" + join.getDayOfMonth() : join.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Roles", "(" + roleCount + ")" + rolesToString(roles), true).
			addField("Permissions", permissionsToString(perms), true).
			setFooter("Requested by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), null);
			event.getChannel().sendMessage(builder.build()).queue();
			return true;
		}
		
		Guild g = event.getGuild();
		
		for (net.dv8tion.jda.core.entities.User u : event.getMessage().getMentionedUsers()) {
			Member m = g.getMember(u);
			
			String member = u.getName() + "#" + u.getDiscriminator();
			String nick = m.getNickname();
			String id = u.getId();
			String avatar = u.getAvatarUrl();
			OffsetDateTime registration = u.getCreationTime();
			OffsetDateTime join = m.getJoinDate();
			String online = m.getOnlineStatus().toString();
			List<Permission> perms = m.getPermissions();
			List<Role> roles = m.getRoles();
			int roleCount = roles.size();
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setColor(Color.CYAN).
			setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/Scotty", event.getJDA().getSelfUser().getAvatarUrl()).
			setTitle("User Information", "https://www.github.com/ShanerX/Scotty").
			setThumbnail("http://i.imgur.com/SwQM8Ei.png").
			addField("Username", member, true).
			addField("User ID", id, true).
			addField("Nickname", nick == null ? member : nick, true).
			addField("Avatar", avatar, true).
			addField("Status", online, true).
			addField("Registered", registration.getYear() + "-" + (registration.getMonthValue() < 10 ?
					registration.getMonthValue() + "0" : registration.getMonthValue()) + "-" +
					(registration.getDayOfMonth() < 10 ? "0" + registration.getDayOfMonth() :
						registration.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Joined Guild", join.getYear() + "-" + (join.getMonthValue() < 10 ?
					join.getMonthValue() + "0" : join.getMonthValue()) + "-" + (join.getDayOfMonth() < 10 ?
							"0" + join.getDayOfMonth() : join.getDayOfMonth()) + " (yyyy-mm-dd)", true).
			addField("Roles", "(" + roleCount + ")" + rolesToString(roles), true).
			addField("Permissions", permissionsToString(perms), true).
			setFooter("Requested by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), null);
			event.getChannel().sendMessage(builder.build()).queue();
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "user [<@User1> [@User2] [@User3] ...]";
	}
	
	
	private String rolesToString(List<Role> roles) {
		if (roles.isEmpty()) {
			return null;
		}
		if (roles.size() == 1) {
			return roles.get(0).getName();
		}
		StringBuilder sb = new StringBuilder();
		for (Role r : roles) {
			sb.append(r.getName()).append(", ");
		}
		return sb.toString().substring(0, sb.toString().length() - 2);
	}
	
	private String permissionsToString(List<Permission> perms) {
		StringBuilder sb = new StringBuilder();
		for (Permission p : perms) {
			sb.append(p.toString()).append(", ");
		}
		return sb.toString();
	}

	@Override
	public String getDescription() {
		return "Request information about the mentioned users.";
	}

}