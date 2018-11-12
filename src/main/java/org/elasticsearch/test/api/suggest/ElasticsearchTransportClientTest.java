package org.elasticsearch.test.api.suggest;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.io.IOException;

/**
 * @author liang.yin
 * @date 2018/9/25 11:19
 */
public class ElasticsearchTransportClientTest {
    public static void main(String[] args) throws IOException {
        // settings
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.ping_timeout", "1200s")
                .put("client.transport.sniff", true)
                .build();

        // on startup
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));


        SuggestBuilder.SuggestionBuilder suggestBuilder  = new CompletionSuggestionBuilder("log_completion")
                .field("log_all")
                .text("花王")
                .size(2);

        XContentBuilder builder = XContentFactory.contentBuilder(Requests.CONTENT_TYPE);
        System.out.println(suggestBuilder.toXContent(builder, ToXContent.EMPTY_PARAMS).toString());

//        SuggestRequestBuilder suggestRequestBuilder = client.prepareSuggest("beidian_sugg")
//                .addSuggestion(
//
//                );

//        XContentBuilder builder = XContentFactory.contentBuilder(Requests.CONTENT_TYPE);
//        suggestRequestBuilder.toXContent(builder, ToXContent.EMPTY_PARAMS);
//        System.out.println(suggestRequestBuilder.toString());
        System.out.println();

        // on shutdown
        client.close();
    }
}
