package org.shanerx.discord.scotty.commands.info;

import java.util.concurrent.TimeUnit;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Uptime implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		long time = ScottyBot.getUptimeMillis() / 1000;
		TimeUnit unit = TimeUnit.SECONDS;
		if (time >= 86400) {
			time = (time - (time % 86400)) / 86400;
			unit = TimeUnit.DAYS;
		}
		else if (time >= 3600) {
			time = (time - (time % 3600)) / 3600;
			unit = TimeUnit.HOURS;
		}
		else if (time >= 60) {
			time = (time - (time % 60)) / 60;
			unit = TimeUnit.MINUTES;
		}
		event.getChannel().sendMessage("Current uptime: **" + time+ "** ***" + unit.toString().toLowerCase() +  "***").queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "uptime";
	}

	@Override
	public String getDescription() {
		return "See the bot's uptime.";
	}

}