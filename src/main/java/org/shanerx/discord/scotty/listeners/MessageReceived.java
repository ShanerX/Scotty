package org.shanerx.discord.scotty.listeners;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getMessage().getContent().startsWith((String) ScottyBot.getInstance().getBotConfiguration().get("command_prfx")) && ScottyBot.getInstance().getAccountType() == AccountType.BOT) {
			if (event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) return;
			if (event.getMessage().getContent().contains(event.getJDA().getSelfUser().getName())) {
				String msg = event.getMessage().getContent().replaceAll(event.getJDA().getSelfUser().getName(), "");
				try {
					event.getChannel().sendMessage(getChatBotResponse(msg)).queue();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return;
		}
		User	author = event.getAuthor();
		String[]	message = event.getMessage().getContent().split(" ");
		String prefix = (String) ScottyBot.getInstance().getBotConfiguration().get("command_prfx");
		String cmd = message[0].substring(prefix.length());

		if (ScottyBot.getInstance().getCommandMap().containsKey(cmd)) {
			Command executor = ScottyBot.getInstance().getCommandMap().get(cmd);
			boolean notAuthorized = !executor.userIsAuthorized(author);
			boolean wrongUsage = !executor.onCommand(author, message, event);
			if (notAuthorized) {
				return;
			}
			if (wrongUsage) {
				event.getChannel().sendMessage("Wrong usage! Try: ``" + prefix + executor.getUsage() + "``").queue();
			}
		}
	}
	
	public static String getChatBotResponse(String x) throws Exception {
        ChatterBotFactory factory = new ChatterBotFactory();
        ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        ChatterBotSession bot2session = bot2.createSession();
        return bot2session.think(x);
	}

}