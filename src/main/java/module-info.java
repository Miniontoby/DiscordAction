module com.stream_pi.discordaction
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;

    requires java.desktop;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.discordaction.DiscordAction;
}
