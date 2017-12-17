package org.shanerx.discord.scotty.commands.games;

import java.util.Set;

import org.shanerx.discord.scotty.Command;
import org.shanerx.discord.scotty.ScottyBot;
import org.shanerx.discord.scotty.util.PokerHand;
import org.shanerx.discord.scotty.util.PokerHand.Card;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Poker implements Command {

	@Override
	public boolean onCommand(User sender, String[] cmd, MessageReceivedEvent event) {
		if (ScottyBot.getInstance().getAccountType() == AccountType.CLIENT) {
			return true;
		}
		if (!userIsAuthorized(sender)) {
			return true;
		}
		PokerHand hand = new PokerHand();
		Set<Card> player = hand.getRandomSet();
		Set<Card> bot = hand.getRandomSet();
		StringBuilder sb1 = new StringBuilder().append("**You got:** ");
		for (Card c : player) {
			sb1.append(c.number != PokerHand.Card.Number.JACK && c.number != PokerHand.Card.Number.QUEEN && c.number != PokerHand.Card.Number.KING
					? c.number.toString().toLowerCase() : "a " + c.number.toString().toLowerCase())
			.append(" (")
			.append(c.suit.toString()
					.toLowerCase())
			.append("), ");
		}
		StringBuilder sb2 = new StringBuilder().append("**I got:** ");
		for (Card c : bot) {
			sb2.append(c.number != PokerHand.Card.Number.JACK && c.number != PokerHand.Card.Number.QUEEN && c.number != PokerHand.Card.Number.KING
					? c.number.toString().toLowerCase() : "a " + c.number.toString().toLowerCase())
			.append(" (")
			.append(c.suit.toString()
					.toLowerCase())
			.append("), ");
		}
		event.getChannel().sendMessage(sb1.toString().substring(0, sb1.toString().length() - 2) + ";\n" + sb2.toString().substring(0,  sb2.toString().length() - 2)).queue();
		return true;
	}

	@Override
	public String getUsage() {
		return "poker";
	}

	@Override
	public String getDescription() {
		return "Play poker.";
	}

}