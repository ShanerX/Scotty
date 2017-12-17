package org.shanerx.discord.scotty.commands.info;

import java.awt.Color;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Bot implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (!userIsAuthorized(sender)) {
			return true;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.GREEN)
		.setAuthor(event.getJDA().getSelfUser().getName(), "https://www.github.com/ShanerX/Scotty", event.getJDA().getSelfUser().getAvatarUrl())
		.setTitle("Bot Information", "https://www.github.com/ShanerX/Scotty")
		.addField("Java Version", "JDK 1.8+", true)
		.addField("Bot Version", ScottyBot.VERSION, true)
		.addField("License", "Apache-2.0", true)
		.addField("JDA", JDAInfo.VERSION, true)
		.addField("json-simple", "1.1.1", true)
		.addField("Source & Download", "http://goo.gl/Ke5TyL", true)
		.addBlankField(false)
		.addField("Links:", "Apache License v2.0: https://github.com/ShanerX/Scotty/blob/master/LICENSE\nJDA (JavaDiscordAPI): https://github.com/DV8FromTheWorld/JDA\njson-simple: https://github.com/fangyidong/json-simple", false);
		event.getChannel().sendMessage(eb.build()).queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "bot";
	}

	@Override
	public String getDescription() {
		return "Get information about the bot, ie. library and version.";
	}

}