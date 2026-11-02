package com.service.bank.ebankbot.agents;

import org.springframework.ai.chat.prompt.Prompt;

public interface EbankAiAgent {
    public String chat(Prompt query, String conversationId);
}
