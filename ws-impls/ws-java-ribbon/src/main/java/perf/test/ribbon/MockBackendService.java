package perf.test.ribbon;

import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.netflix.ribbon.proxy.annotation.TemplateName;
import com.netflix.ribbon.proxy.annotation.Var;
import io.netty.buffer.ByteBuf;

/**
 * Maps mock backend request to Java interface.
 *
 * @author Tomasz Bak
 */
public interface MockBackendService {

    @TemplateName("serviceA.run")
    @Http(method = HttpMethod.GET, uriTemplate = "/mock.json?numItems={numItems}&itemSize={itemSize}&delay={delay}&id={id}")
    @Hystrix(fallbackHandler = DefaultFallbackHandler.class, validator = SampleHystrixContentValidator.class)
    RibbonRequest<ByteBuf> request0(@Var("numItems") Integer numItems, @Var("itemSize") Integer itemSize, @Var("delay") Integer delay, @Var("id") Long id);

    @TemplateName("serviceB.run")
    @Http(method = HttpMethod.GET, uriTemplate = "/mock.json?numItems={numItems}&itemSize={itemSize}&delay={delay}&id={id}")
    @Hystrix(fallbackHandler = DefaultFallbackHandler.class, validator = SampleHystrixContentValidator.class)
    RibbonRequest<ByteBuf> request1(@Var("numItems") Integer numItems, @Var("itemSize") Integer itemSize, @Var("delay") Integer delay, @Var("id") Long id);

    @TemplateName("serviceC.run")
    @Http(method = HttpMethod.GET, uriTemplate = "/mock.json?numItems={numItems}&itemSize={itemSize}&delay={delay}&id={id}")
    @Hystrix(fallbackHandler = DefaultFallbackHandler.class, validator = SampleHystrixContentValidator.class)
    RibbonRequest<ByteBuf> request2(@Var("numItems") Integer numItems, @Var("itemSize") Integer itemSize, @Var("delay") Integer delay, @Var("id") Long id);

    @TemplateName("serviceD.run")
    @Http(method = HttpMethod.GET, uriTemplate = "/mock.json?numItems={numItems}&itemSize={itemSize}&delay={delay}&id={id}")
    @Hystrix(fallbackHandler = DefaultFallbackHandler.class, validator = SampleHystrixContentValidator.class)
    RibbonRequest<ByteBuf> request3(@Var("numItems") Integer numItems, @Var("itemSize") Integer itemSize, @Var("delay") Integer delay, @Var("id") Long id);

    @TemplateName("serviceE.run")
    @Http(method = HttpMethod.GET, uriTemplate = "/mock.json?numItems={numItems}&itemSize={itemSize}&delay={delay}&id={id}")
    @Hystrix(fallbackHandler = DefaultFallbackHandler.class, validator = SampleHystrixContentValidator.class)
    RibbonRequest<ByteBuf> request4(@Var("numItems") Integer numItems, @Var("itemSize") Integer itemSize, @Var("delay") Integer delay, @Var("id") Long id);
}
