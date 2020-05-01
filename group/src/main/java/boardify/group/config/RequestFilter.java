package boardify.group.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(RequestContext.REQUEST_HEADER_NAME);

        if (token == null || "".equals(token)) {
            throw new IllegalArgumentException("Can't retrieve JWT Token");
        }

        RequestContext.getContext().setToken(token);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() { }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}


}
