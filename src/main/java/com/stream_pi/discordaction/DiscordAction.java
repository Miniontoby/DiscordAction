package com.stream_pi.discordaction;

import com.stream_pi.discordaction.DiscordWebhook;
import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.io.IOException;

public class DiscordAction extends NormalAction {

    public DiscordAction() {
        setName("Discord");
        setCategory("Discord");
        setAuthor("Miniontoby");
        setServerButtonGraphic("fab-discord");
        setHelpLink("https://edugit.org/Miniontoby/DiscordAction");
        setVersion(new Version(1,0,0));
    }


    @Override
    public void initProperties() throws MinorException {
        Property webhookURLProperty = new StringProperty("webhook_url");
        webhookURLProperty.setDisplayName("Webhook URL");

        Property usernameProperty = new StringProperty("username");
        usernameProperty.setDisplayName("Webhook Username");

        Property titleProperty = new StringProperty("title");
        titleProperty.setDisplayName("Webhook Title");

        Property contentProperty = new StringProperty("content");
        contentProperty.setDisplayName("Webhook Text Content");

        addClientProperties(webhookURLProperty, usernameProperty, titleProperty, contentProperty);
    }

    @Override
    public void onActionClicked() throws MinorException {
        sendMessage();
    }

    public void sendMessage() throws MinorException {
        String webhookURL = getClientProperties().getSingleProperty("webhook_url").getStringValue();
        String webhookTitle = getClientProperties().getSingleProperty("title").getStringValue();
        String webhookUser = getClientProperties().getSingleProperty("username").getStringValue();
        String webhookContent = getClientProperties().getSingleProperty("content").getStringValue();

        if (webhookURL.isBlank()) {
            throwMinorException("No Discord Webhook URL specified!");
        }

        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
	webhook.setUsername(webhookUser);

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
        embed.setTitle(webhookTitle);
        embed.setDescription(webhookContent);

        try {
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (IOException e) {
            throwMinorException(e.getMessage());
        }
    }
}
