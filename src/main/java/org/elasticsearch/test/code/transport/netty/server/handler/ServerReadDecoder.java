package org.elasticsearch.test.code.transport.netty.server.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.util.CharsetUtil;
 
// 解决接收流数据时，数据出现碎片化的问题
public class ServerReadDecoder extends StringDecoder{
 
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		System.out.println("######ServerReadDecoder");
		
		// 从msg中取出的数据类型是ChannelBuffer的
		byte[] buffer = ((ChannelBuffer)msg).array();
		byte last = buffer[buffer.length - 1];
		// 46 is '.'
		if(last == 46)
			// 并将ChannelBuffer转为String
			return new String(buffer, CharsetUtil.UTF_8);
		return null;
	}
}