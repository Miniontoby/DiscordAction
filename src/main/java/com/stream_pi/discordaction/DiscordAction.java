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
        setHelpLink("https://github.com/stream-pi/essential-actions");
        setVersion(new Version(1,0,0));
    }


    @Override
    public void initProperties() throws MinorException {
        Property webhookURLProperty = new StringProperty("webhook_url");
        webhookURLProperty.setDisplayName("Webhook URL");

        Property contentProperty = new StringProperty("content");
        contentProperty.setDisplayName("Webhook Text Content");

        Property usernameProperty = new StringProperty("username");
        usernameProperty.setDisplayName("Webhook Username");

        Property titleProperty = new StringProperty("title");
        titleProperty.setDisplayName("Webhook Title");

        addClientProperties(webhookURLProperty, contentProperty, usernameProperty, titleProperty);
    }

    @Override
    public void onActionClicked() throws MinorException {
        sendMessage();
    }

    public void sendMessage() throws MinorException {
        String webhookURL = getClientProperties().getSingleProperty("webhook_url").getStringValue();
        String webhookContent = getClientProperties().getSingleProperty("content").getStringValue();
        String webhookTitle = getClientProperties().getSingleProperty("title").getStringValue();
        String webhookUser = getClientProperties().getSingleProperty("username").getStringValue();

        if (webhookURL.isBlank()) {
            throwMinorException("No Discord Webhook URL specified!");
        }

        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
	webhook.setAuthor(webhookUser);

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
