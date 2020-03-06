package com.nl2go.plivosmsslackhook;

import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public @ResponseBody void createMessage(
        @RequestParam("From") String from,
        @RequestParam("To") String to,
        @RequestParam("Text") String text
        ){
        messageService.createMessage(new Message(from, to, text));
    }
}
