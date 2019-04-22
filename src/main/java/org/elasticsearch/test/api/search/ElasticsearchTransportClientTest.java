package org.elasticsearch.test.api.search;

import org.elasticsearch.action.admin.cluster.state.ClusterStateRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Iterator;
import java.util.Map;

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


        Iterator<ObjectObjectCursor<String, IndexMetaData>> iterator = client
                .admin()
                .cluster()
                .state(new ClusterStateRequest())
                .actionGet()
                .getState()
                .getMetaData()
                .indices()
                .iterator();

        while (iterator.hasNext()){
            ObjectObjectCursor<String, IndexMetaData> objectObjectCursor =iterator.next();
            if(IndexMetaData.State.CLOSE == objectObjectCursor.value.state()){
                // 已经关闭的index 名称
                String index = objectObjectCursor.key;
            }
        }

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
