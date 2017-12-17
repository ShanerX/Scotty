package org.shanerx.discord.scotty.commands.settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Game implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		if (cmd.length < 2) {
			event.getTextChannel().sendMessage("Current game: **" + event.getJDA().getPresence().getGame().getName() + "**").queue();
			return true;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < cmd.length; i++) {
			sb.append(cmd[i])
			.append(" ");
		}
		event.getJDA().getPresence().setGame(net.dv8tion.jda.core.entities.Game.of(sb.toString().trim()));
		event.getTextChannel().sendMessage("Set game to: **" + event.getJDA().getPresence().getGame().getName() + "**.").queue();
		try {
			PrintWriter pw = new PrintWriter(ScottyBot.getInstance().getConfig());
			JSONObject botConfig = ScottyBot.getInstance().getBotConfiguration();
			botConfig.put("game", sb.toString().trim().toLowerCase());
			pw.write(botConfig.toJSONString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "game [New Game]";
	}

	@Override
	public String getDescription() {
		return "Change the bot's ``Playing...`` status.";
	}

}