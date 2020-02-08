package org.jleopard.activiti.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JsonpCallbackFilter implements Filter {

  private static Logger log = LoggerFactory.getLogger(JsonpCallbackFilter.class);

  public void init(FilterConfig fConfig) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    Map<String, String[]> params = httpRequest.getParameterMap();
    if (params.containsKey("callback")) {
      if (log.isDebugEnabled())
          log.debug("Wrapping response with JSONP callback '" + params.get("callback")[0] + "'");

      OutputStream out = httpResponse.getOutputStream();
      GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);
      chain.doFilter(request, wrapper);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write((params.get("callback")[0] + "(").getBytes() );
      outputStream.write(wrapper.getData());
      outputStream.write(");".getBytes());
      byte[] jsonpResponse = outputStream.toByteArray( );

      wrapper.setContentType("text/javascript;charset=UTF-8");
      wrapper.setContentLength(jsonpResponse.length);

      out.write(jsonpResponse);

      out.close();
    
    } else {
      chain.doFilter(request, response);
    }
  }

  public void destroy() {}
}
