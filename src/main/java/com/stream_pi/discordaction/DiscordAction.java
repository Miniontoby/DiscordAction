package com.stream_pi.discordaction;

import com.stream_pi.discordaction.DiscordWebhook;
import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

public class DiscordAction extends NormalAction {
    Button newWebhookButton;
    Label currentWebhook;

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
        webhookURLProperty.setControlType(ControlType.STRING);
        webhookURLProperty.setDisplayName("Webhook URL");

        addClientProperties(webhookURLProperty);
    }

    @Override
    public void onActionClicked() throws MinorException {
        sendMessage();
    }

    public void sendMessage() throws MinorException {
        Property webhookURL = getClientProperties().getSingleProperty("file_location");

        if (webhookURL.getStringValue().isBlank()) {
            throw new MinorException("No Discord Webhook URL specified!");
        }

    }
}
