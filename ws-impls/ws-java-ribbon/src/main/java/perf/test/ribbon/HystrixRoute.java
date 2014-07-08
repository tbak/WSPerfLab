package perf.test.ribbon;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsPoller;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsPoller.MetricsAsJsonPollerListener;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Tomasz Bak
 */
public class HystrixRoute {

    private static final int DEFAULT_INTERVAL = 2000;

    private static final byte[] HEADER = "data: ".getBytes(Charset.defaultCharset());
    private static final byte[] FOOTER = {10, 10};
    private static final int EXTRA_SPACE = HEADER.length + FOOTER.length;

    public HystrixRoute() {
    }

    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        response.getHeaders().add("Content-Type", "text/event-stream;charset=UTF-8");
        response.getHeaders().add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        response.getHeaders().add("Pragma", "no-cache");

        MetricsAsJsonPollerListener listener = new MetricsAsJsonPollerListener() {
            @Override
            public void handleJsonMetric(String json) {
                byte[] bytes = json.getBytes(Charset.defaultCharset());
                ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(bytes.length + EXTRA_SPACE);
                byteBuf.writeBytes(HEADER);
                byteBuf.writeBytes(bytes);
                byteBuf.writeBytes(FOOTER);
                response.writeAndFlush(byteBuf);
            }
        };

        HystrixMetricsPoller poller = new HystrixMetricsPoller(listener, getInterval(request));
        poller.start();

        return Observable.never();
    }

    private int getInterval(HttpServerRequest<ByteBuf> request) {
        List<String> param = request.getQueryParameters().get("interval");
        if (param != null && param.size() > 0) {
            return Integer.valueOf(param.get(0));
        }
        return DEFAULT_INTERVAL;
    }
}
