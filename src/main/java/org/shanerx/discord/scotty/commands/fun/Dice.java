package org.shanerx.discord.scotty.commands.fun;

import java.util.Random;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Dice implements Command {
	
	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		Random rand = new Random();
		int side = rand.nextInt(6) + 1;
		event.getChannel().sendMessage("Outcome: **" + side + "**!").queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "dice";
	}

	@Override
	public String getDescription() {
		return "Play with dice.";
	}

}