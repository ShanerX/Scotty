package org.shanerx.discord.scotty;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.shanerx.discord.scotty.commands.fun.Ball;
import org.shanerx.discord.scotty.commands.fun.Coin;
import org.shanerx.discord.scotty.commands.fun.Dice;
import org.shanerx.discord.scotty.commands.games.Poker;
import org.shanerx.discord.scotty.commands.games.Rps;
import org.shanerx.discord.scotty.commands.info.Bot;
import org.shanerx.discord.scotty.commands.info.Channel;
import org.shanerx.discord.scotty.commands.info.Guild;
import org.shanerx.discord.scotty.commands.info.Ping;
import org.shanerx.discord.scotty.commands.info.Uptime;
import org.shanerx.discord.scotty.commands.info.Usage;
import org.shanerx.discord.scotty.commands.info.User;
import org.shanerx.discord.scotty.commands.settings.Game;
import org.shanerx.discord.scotty.commands.settings.Status;
import org.shanerx.discord.scotty.commands.util.Spam;
import org.shanerx.discord.scotty.listeners.GuildMessageReceived;
import org.shanerx.discord.scotty.listeners.MessageReceived;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class ScottyBot {
	
	private HashMap<String,Command> commandMap;
	
	private String clientID;
	private String secret;
	private AccountType accountType;
	protected JDA jda;
	protected Logger log = Logger.getLogger("ScottyBot");
	
	private static ScottyBot instance;
	protected static File conf;
	private static JSONObject config;
	
	public static final String VERSION = "Scotty-Bot_v#1.0.0-DEV";
	
	private static long uptime = System.currentTimeMillis();
	
	public static ScottyBot getInstance() {
		if (instance == null) {
			instance = new ScottyBot();
		}
		return instance;
	}
	
	public static long getUptimeMillis() {
		return System.currentTimeMillis() - uptime;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public Logger getLogger() {
		return getInstance().log;
	}
	
	public File getConfig() {
		if (conf == null) {
			getLogger().log(Level.SEVERE, "Failed to retrieve values from file: Configuration file hasn't been initialized!");
		}
		return conf;
	}
	
	public JSONObject getBotConfiguration() {
		if (config == null) {
			getLogger().log(Level.SEVERE, "Failed to retrieve values from file: Configuration file hasn't been initialized!");
		}
		return config;
	}
	
	public HashMap<String,Command> getCommandMap() {
		return commandMap;
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public String getClientID() {
		return clientID;
	}
	
	protected String getSecretToken() {
		return secret;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		ScottyBot bot = getInstance();
		conf = new File("bot_conf.json");
		if (!conf.exists()) {
			conf.createNewFile();
			PrintWriter pw = new PrintWriter(conf);
			pw.write("{\n  \"command_prfx\": \"?\",\n  \"status\": \"online\",\n  \"scope\": \"client\",\n  \"game\": \"hello world\",\n  \"client_token\": \"%insert_secret%\",\n  \"client_id\": \"%insert_id%\",\n  \"ops\": [\n    \"108989469687840768\"\n  ]}");
			pw.close();
			bot.getLogger().log(Level.INFO, "Created new bot configuration file. Please edit the values and restart the bot.\nFor more information, see: https://www.github.com/ShanerX/Scotty/");
			System.exit(-1);
		}
		JSONParser jParser = new JSONParser();
		config = (JSONObject) jParser.parse(new FileReader(conf));
		
		String id = (String) config.get("client_id");
		String token = (String) config.get("client_token");
		String scope = (String) config.get("scope");
		String game = (String) config.get("game");
		bot.accountType = AccountType.valueOf(scope.toUpperCase());
		bot.setupBot(id, token, bot.accountType, game);
		String status = (String) config.get("status");
		bot.getJDA().getPresence().setStatus(OnlineStatus.valueOf(status.toUpperCase()));
		bot.setupEventListeners();
		bot.setupCommandListeners();
	}
	
	
	protected ScottyBot setupBot(final String clientID, final String token, final AccountType accountType, String game) {
		this.clientID = clientID;
		secret = token;
		try {
			jda = new JDABuilder(accountType).setToken(secret).setGame(net.dv8tion.jda.core.entities.Game.of(game)).setEnableShutdownHook(true).buildAsync();
		} catch (LoginException | IllegalArgumentException | RateLimitedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	protected void setupEventListeners() {
		getJDA().addEventListener(new MessageReceived());
		getJDA().addEventListener(new GuildMessageReceived());
	}
	
	protected void setupCommandListeners() {
		commandMap = new HashMap<>();
		commandMap.put("ping", new Ping());
		commandMap.put("game", new Game());
		commandMap.put("status", new Status());
		commandMap.put("spam", new Spam());
		commandMap.put("guild", new Guild());
		commandMap.put("user", new User());
		commandMap.put("channel", new Channel());
		commandMap.put("coin", new Coin());
		commandMap.put("dice", new Dice());
		commandMap.put("rps", new Rps());
		commandMap.put("8ball", new Ball());
		commandMap.put("uptime", new Uptime());
		commandMap.put("usage", new Usage());
		commandMap.put("bot", new Bot());
		commandMap.put("poker", new Poker());
	}

}