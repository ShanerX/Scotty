package org.shanerx.discord.scotty.commands.info;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ping implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		event.getChannel().sendMessage("Current ping: **" + event.getJDA().getPing() + "** ***ms***").queue();		
		return true;
	}

	@Override
	public String getUsage() {
		return "ping";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Get the bot's internet latency.";
	}

}