package com.beibei.elasticsearch.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;

/**
 * @author Administrator
 * @date 2018/9/25 14:45
 */
public class ElasticsearchNodeClientTest {
    public static void main(String[] args) {
        // on startup

        Node node = nodeBuilder()
                        .clusterName("elasticsearch")
                        .settings(ImmutableSettings.settingsBuilder().put("http.enabled", false))
                        .client(true)
                        .node();
        Client client = node.client();

        SearchResponse response = client.prepareSearch("mart_item")
                .setQuery(QueryBuilders.termQuery("iid", "28589790"))
                .setFrom(0)
                .setSize(10)
                .get();

        System.out.println(response);

//        Node node = nodeBuilder().local(true).node(); // node.local

        // on shutdown
        node.close();
    }
}
