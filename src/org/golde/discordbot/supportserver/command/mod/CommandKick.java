package org.golde.discordbot.supportserver.command.mod;

import java.util.List;

import org.golde.discordbot.supportserver.constants.MiscConstants;
import org.golde.discordbot.supportserver.util.ModLog;
import org.golde.discordbot.supportserver.util.StringUtil;
import org.golde.discordbot.supportserver.util.ModLog.ModAction;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class CommandKick extends ModCommand {

	public CommandKick() {
		this.guildOnly = true;
		this.name = "kick";
		this.help = "kick a player";
		this.arguments = "<player> [reason]";
		this.aliases = new String[]{"k"};
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		
		if(event.getArgs().isEmpty())
        {
            event.replyError("Please provide the name of a player to kick!");
            return;
        }
		else {
		
	        Member selfMember = event.getGuild().getSelfMember();
	        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

	        if (args.isEmpty() || mentionedMembers.isEmpty()) {
	            event.replyError("Missing arguments");
	            return;
	        }

	        Member target = mentionedMembers.get(0);
	        String reason = String.join(" ", args.subList(2, args.size()));

	        if (!selfMember.hasPermission(Permission.KICK_MEMBERS) || !selfMember.canInteract(target)) {
	            event.replyError("I can't kick that user or I don't have the kick members permission");
	            return;
	        }
	        
	        if(reason == null || reason.isEmpty()) {
	        	reason = "No reason provided.";
	        }
	        
	        final String reasonFinal = reason;
	        
	        MessageEmbed actionEmbed = ModLog.getActionTakenEmbed(
					ModAction.KICK, 
					event.getAuthor(), 
					new String[][] {
						new String[] {"Offender: ", "<@" + target.getId() + ">"}, 
						new String[] {"Reason:", StringUtil.abbreviate(reason, 250)}
					}
					);
	        ModLog.log(event.getGuild(), actionEmbed);
	        
	        //TODO: Add discord link back to the server
	        target.getUser().openPrivateChannel().queue((dmChannel) ->
	        {
	        	dmChannel.sendMessage(actionEmbed).queue((unused1) ->
		        {
		        	dmChannel.sendMessage("You can join back with this link: " + MiscConstants.DISCORD_INVITE).queue((unused2) ->
			        {
			        	event.getGuild().kick(target, String.format("Kick by: %#s, with reason: %s",
				                event.getAuthor(), reasonFinal)).queue();
			        });
		        });
	        	
	        });

	        

	        event.replySuccess("Success!");
	        
			
		}
	}

}