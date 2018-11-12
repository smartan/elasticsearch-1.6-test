package org.elasticsearch.test.api.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author liang.yin
 * @date 2018/9/25 11:19
 */
public class ElasticsearchTransportClientTest {
    public static void main(String[] args) {
        // settings
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.ping_timeout", "1200s")
                .put("client.transport.sniff", true)
                .build();

        // on startup
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

        // on shutdown
        client.close();
    }
}
