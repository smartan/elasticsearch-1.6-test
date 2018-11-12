package org.elasticsearch.test.api.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;

/**
 * @author liang.yin
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

//        Node node = nodeBuilder().local(true).node(); // node.local

        // on shutdown
        node.close();
    }
}
