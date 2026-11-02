package com.service.bank.ebankbot.discord;

import com.service.bank.ebankbot.agents.EbankAiAgent;
import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.ai.chat.prompt.Prompt;

@DiscordController
public class DiscordBot {
    private final EbankAiAgent ebankAiAgent;

    public DiscordBot(EbankAiAgent ebankAiAgent) {
        this.ebankAiAgent = ebankAiAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event){
        System.out.println(event);

        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();
        String conversationId = event.getAuthor().getId();
        String response = ebankAiAgent.chat(new Prompt(query), conversationId);
        event.getChannel().sendMessage(response).queue();
    }
}
