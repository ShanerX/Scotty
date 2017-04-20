package org.shanerx.discord.scotty.commands.util;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Spam implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		else if (cmd.length < 3) {
			return false;
		}
		int amount;
		try {
			amount = Integer.parseInt(cmd[1]);
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		StringBuilder sb = new StringBuilder();
		String msg = null;
		for (int i = 2; i < cmd.length; i++) {
			sb.append(cmd[i])
			.append(" ");
		}
		msg = sb.toString().trim();
		for (int i = 0; i < amount; i++) {
			event.getChannel().sendMessage(msg).queue();
		}
		return true;
	}
	
	@Override
	public String getUsage() {
		return "spam <Amount> <Message>";
	}

	@Override
	public String getDescription() {
		return "Make the bot spam a channel.";
	}

}