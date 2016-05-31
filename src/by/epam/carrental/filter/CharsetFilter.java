package by.epam.carrental.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CharsetFilter.class.getName());
    private static String LOG_MESSAGE = "CharsetFilter : {} encoding has been successfully setted";
    private static String CHAR_ENCODING = "character-encoding";
    private String encoding;

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHAR_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        LOG.debug(LOG_MESSAGE, encoding);
        filterChain.doFilter(request, response);
    }
}
