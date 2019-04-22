package org.elasticsearch.test.ad;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class ElasticsearchAdTest {
    public static void main(String[] args) {
        // settings
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "recom_ad_es_cluster")
                .put("client.transport.ping_timeout", "1200s")
                .put("client.transport.sniff", true)
                .build();

        // on startup
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("10.2.28.11", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.2.29.10", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.2.29.11", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.2.34.128", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.2.34.132", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.2.34.133", 9300));

        SearchResponse scrollResp = client.prepareSearch("ad_item_v2")
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.termQuery("normlize_bidword", "cpccat###"))
                .setSize(1000).get(); //max of 100 hits will be returned for each scroll
        //Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
                int recommendBid = Integer.parseInt(String.valueOf(hit.getSource().get("recommend_bid")));
                int bid = Integer.parseInt(String.valueOf(hit.getSource().get("bid")));
                if(recommendBid != bid){
                    System.out.println(hit.getSource().get("ad_id"));
                }
            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.

    }
}
