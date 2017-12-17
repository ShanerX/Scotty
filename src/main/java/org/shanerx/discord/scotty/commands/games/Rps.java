package org.shanerx.discord.scotty.commands.games;

import java.util.Random;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Rps implements Command {
	
	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		if (cmd.length != 2) {
			return false;
		}
		else if (!(cmd[1].equalsIgnoreCase("rock") || cmd[1].equalsIgnoreCase("paper") || cmd[1].equalsIgnoreCase("scissors"))) {
			return false;
		}
		Random rand = new Random();
		int turn = rand.nextInt(3);
		int player = cmd[1].equalsIgnoreCase("rock") ? 0 : (cmd[1].equalsIgnoreCase("paper") ? 1 : 2);
		String move = (turn == 0 ? "rock" : (turn == 1 ? "paper" : "scissors"));
		String result = null;
		if (turn == 0) {
			if (player == 0) result = "it's a draw";
			else if (player == 1) result = "you win";
			else result = "you lose";
		}
		else if (turn == 1) {
			if (player == 0) result = "you lose";
			else if (player == 1) result = "it's a draw";
			else result = "you win";
		}
		else {
			if (player == 0) result = "you win";
			else if (player == 1) result = "you lose";
			else result = "it's a draw";
		}
		event.getChannel().sendMessage("My move: **" + move + "**. Outcome: " + result + "!").queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "rps <rock|paper|scissors>";
	}

	@Override
	public String getDescription() {
		return "Play R-P-S with the bot.";
	}

}