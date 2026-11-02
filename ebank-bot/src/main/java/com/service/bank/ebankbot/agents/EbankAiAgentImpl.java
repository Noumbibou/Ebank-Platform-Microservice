package com.service.bank.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("removal")
public class EbankAiAgentImpl implements EbankAiAgent {
    private final ChatClient chatClient;

    public EbankAiAgentImpl(ChatClient.Builder chatClient, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClient
                .defaultSystem("""
                        Vous etes un assistant qui se charge de répondre aux questions de l'utilisateur 
                        à propos des clients et des comptes bancaires, en
                        fonction du contexe fourni.
                        Si aucun contexte n'est fourni, réponds JE NE SAIS PAS
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).defaultTools(tools)
                .build();
    }

    @Override
    public String chat(Prompt prompt, String conversationId){
        return chatClient.prompt()
                .user((Resource) prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
