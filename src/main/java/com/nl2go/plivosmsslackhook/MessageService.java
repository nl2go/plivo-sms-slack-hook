package com.nl2go.plivosmsslackhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.text.MessageFormat.format;
import static java.util.Collections.singletonMap;

@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private Props props;

    private RestTemplate restTemplate;

    public MessageService(
        Props props,
        RestTemplate restTemplate
    ){
        this.props = props;
        this.restTemplate = restTemplate;
    }

    public void createMessage(Message message){
        if(!message.getTo().equals(props.getTargetNumber())){
            return;
        }

        String messageStr = asString(message);
        restTemplate.postForEntity(props.getWebhookUrl(), singletonMap("text", messageStr), Void.class);
        LOG.info("Sent message: {}", messageStr);
    }

    private String asString(Message message){
        return format(props.getMessageTemplate(), message.getFrom(), message.getTo(), message.getText());
    }
}
