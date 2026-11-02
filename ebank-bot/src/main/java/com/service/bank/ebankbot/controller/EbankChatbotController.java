package com.service.bank.ebankbot.controller;

import com.service.bank.ebankbot.agents.EbankAiAgent;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EbankChatbotController {
    private EbankAiAgent ebankAiAgent;

    public EbankChatbotController(EbankAiAgent ebankAiAgent) {
        this.ebankAiAgent = ebankAiAgent;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "query", defaultValue = "bonjour") String query,
                       @RequestParam(name = "conversationId", defaultValue = "default") String conversationId){
        return ebankAiAgent.chat(new Prompt(query), conversationId);
    }
}
