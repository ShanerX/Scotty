package org.shanerx.discord.scotty.commands;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Embed implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsage() {
		return "embed c:[Colour] a:[Author]";
	}

	@Override
	public String getDescription() {
		return "Generate a custom embed.";
	}

}