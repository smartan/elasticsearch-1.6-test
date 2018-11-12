package org.elasticsearch.test.code.transport.netty.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;
 
public class ClientWriteEncoder extends StringEncoder{
 
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		System.out.println("######ClientWriteEncoder");
		
		String str = (String)msg;
		ChannelBuffer channelBuffer = 
				ChannelBuffers.copiedBuffer(str, CharsetUtil.UTF_8);
		return channelBuffer;
	}
}