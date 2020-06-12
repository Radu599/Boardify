package boardify.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import boardify.gateway.security.config.JwtConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PreFilter extends ZuulFilter {

    private static final String APPLICATION_URL_ENCODED_TYPE = "application/x-www-form-urlencoded";
    private final Logger logger = LogManager.getLogger(PreFilter.class);
    @Autowired
    private JwtConfig config;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        logger.info("NEW REQUEST IN PREFILTER");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("ROUTE: {}", request.getRequestURI());

        if (!request.getRequestURI().equals(config.getLoginUrl())) {
            String contentType = request.getHeader("Content-type");
            if (contentType != null && contentType.equals(APPLICATION_URL_ENCODED_TYPE)) {
                Map<String, String> map = ctx.getZuulRequestHeaders();
                map.put("content-type", "application/json");
            }
        }
        logger.info("FINAL PREFILTER");
        return null;
    }
}
