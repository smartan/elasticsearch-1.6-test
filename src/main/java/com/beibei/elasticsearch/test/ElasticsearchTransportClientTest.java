package com.beibei.elasticsearch.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Administrator
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

        SearchResponse response = client.prepareSearch("mart_item")
                .setQuery(QueryBuilders.termQuery("iid", "28589790"))
                .setFrom(0)
                .setSize(10)
                .get();

        System.out.println(response);

        // on shutdown
        client.close();
    }
}
