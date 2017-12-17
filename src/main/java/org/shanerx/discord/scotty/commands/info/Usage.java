package org.shanerx.discord.scotty.commands.info;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Usage implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		if (cmd.length < 2) {
			return false;
		}
		if (ScottyBot.getInstance().getCommandMap().containsKey(cmd[1])){
			event.getChannel().sendMessage("Usage: ``" +  (String) ScottyBot.getInstance().getBotConfiguration().get("command_prfx") + ScottyBot.getInstance().getCommandMap().get(cmd[1]).getUsage() + "``!").queue();
			return true;
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "usage <command>";
	}

	@Override
	public String getDescription() {
		return "Check the required arguments of a command.";
	}

}