package org.shanerx.discord.scotty.commands.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Status implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		if (cmd.length < 2) {
			return false;
		}
		String status = cmd[1].toLowerCase();
		switch (status) {
		case "online":
		case "idle":
		case "do_not_disturb":
		case "invisible":
		case "offline":
			break;
		default:
			return false;
		}
		event.getJDA().getPresence().setStatus(OnlineStatus.valueOf(status.toUpperCase()));
		event.getChannel().sendMessage("Changed online status to: **" + status.toUpperCase() + "**").queue();
		try {
			PrintWriter pw = new PrintWriter(ScottyBot.getInstance().getConfig());
			JSONObject botConfig = ScottyBot.getInstance().getBotConfiguration();
			botConfig.put("status", status.toLowerCase());
			pw.write(botConfig.toJSONString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "status <Online-Status>";
	}

	@Override
	public String getDescription() {
		return "Change the bot's online-status.";
	}

}