package org.shanerx.discord.scotty.commands.fun;

import java.util.Random;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Coin implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		Random rand = new Random();
		int side = rand.nextInt(2);
		event.getChannel().sendMessage("Outcome: **" + (side == 1 ? "heads" : "tails") + "**!").queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "coin";
	}

	@Override
	public String getDescription() {
		return "Make the bot flip a coin.";
	}

}
