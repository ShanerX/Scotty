package org.shanerx.discord.scotty;

import org.json.simple.JSONArray;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
	
	boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event);
	String getUsage();
	String getDescription();
	
	default boolean userIsAuthorized(User sender) {
		JSONArray ops = (JSONArray) ScottyBot.getInstance().getBotConfiguration().get("ops");
		return ops.contains(sender.getId()) || sender.getId().equals(ScottyBot.getInstance().getJDA().getSelfUser().getId()) || ScottyBot.getInstance().getAccountType() == AccountType.BOT;
	}

}