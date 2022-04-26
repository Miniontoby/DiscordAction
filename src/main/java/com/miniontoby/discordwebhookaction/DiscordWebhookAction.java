package com.miniontoby.discordwebhookaction;

import com.miniontoby.discordwebhookaction.DiscordWebhook;
import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.io.IOException;

public class DiscordWebhookAction extends NormalAction {

    public DiscordWebhookAction() {
        setName("Discord Webhook");
        setCategory("Discord");
        setAuthor("Miniontoby");
        setServerButtonGraphic("fab-discord");
        setHelpLink("https://edugit.org/Miniontoby/DiscordAction");
        setVersion(new Version(1,1,0));
    }


    @Override
    public void initProperties() throws MinorException {
        Property webhookURLProperty = new StringProperty("webhook_url");
        webhookURLProperty.setDisplayName("Webhook URL");

        Property usernameProperty = new StringProperty("username");
        usernameProperty.setDisplayName("Webhook Username");

        Property contentProperty = new StringProperty("content");
        contentProperty.setDisplayName("Message Content");

        Property titleProperty = new StringProperty("title");
        titleProperty.setDisplayName("Embed Title");

        Property descriptionProperty = new StringProperty("description");
        descriptionProperty.setDisplayName("Embed Description");

        addClientProperties(webhookURLProperty, usernameProperty, contentProperty, titleProperty, descriptionProperty);
    }

    @Override
    public void onActionClicked() throws MinorException {
        sendMessage();
    }

    public void sendMessage() throws MinorException {
        String webhookURL = getClientProperties().getSingleProperty("webhook_url").getStringValue();
        String webhookUser = getClientProperties().getSingleProperty("username").getStringValue();
        String webhookContent = getClientProperties().getSingleProperty("content").getStringValue();
        String webhookEmbedTitle = getClientProperties().getSingleProperty("title").getStringValue();
        String webhookEmbedDescription = getClientProperties().getSingleProperty("description").getStringValue();

        if (webhookURL.isBlank()) {
            throwMinorException("No Discord Webhook URL specified!");
        }

        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
	webhook.setUsername(webhookUser);
	webhook.setContent(webhookContent);

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
        embed.setTitle(webhookEmbedTitle);
        embed.setDescription(webhookEmbedDescription);

        try {
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (IOException e) {
            throwMinorException(e.getMessage());
        }
    }
}
