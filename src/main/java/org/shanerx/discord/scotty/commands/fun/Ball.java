package org.shanerx.discord.scotty.commands.fun;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.shanerx.discord.scotty.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ball implements Command {

	public static final String POS_1 = "Undoubtedly.";
	public static final String POS_2 = "Signs point to yes.";
	public static final String POS_3 = "Outlook good.";
	public static final String POS_4 = "It is certain.";
	public static final String NEU_1 = "Ask again later.";
	public static final String NEU_2 = "Cannot predict now.";
	public static final String NEU_3 = "Ask yourself.";
	public static final String NEG_1 = "My sources say no.";
	public static final String NEG_2 = "Don't count on it.";
	public static final String NEG_3 = "Very doubtful.";
	public static final String NEG_4 = "Outlook not so good.";
	
	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		else if (cmd.length < 2) {
			return false;
		}
		Random rand = new Random();
		List<String> response = (List<String>) Arrays.asList(POS_1,POS_2,POS_3,POS_4,NEU_1,NEU_2,NEU_3,NEG_2,NEG_3,NEG_4);
		event.getChannel().sendMessage(response.get(rand.nextInt(11))).queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "8ball <Question>";
	}

	@Override
	public String getDescription() {
		return "Ask the magic 8-ball whether you'll succeed.";
	}

}