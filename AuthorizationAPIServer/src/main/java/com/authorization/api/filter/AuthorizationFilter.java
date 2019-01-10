//package com.authorization.api.filter;
//
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.Map;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import com.authorization.api.redis.RedisService;
//
//@Component
//@Order(2)
//public class AuthorizationFilter implements Filter {
//
//	@Autowired
//	private RedisService redisService;
//
//	@CrossOrigin
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpServletRequest req = (HttpServletRequest) request;
//
//		String ip = req.getRemoteAddr();
//		String access_token = req.getHeader("access_token");
//		
//		Enumeration<String> headerNames = req.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            String name = (String)headerNames.nextElement();
//            String value = req.getHeader(name);
//            System.out.println(name + " : " + value );
//        }
//		if (access_token == null) {
//			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		} else {
//			Map<String, String> map = redisService.getUserInfo(access_token);
//
//			if (map == null || !map.get("ip").equals(ip)) {
//				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				return;
//			}
//		}
//		
//		chain.doFilter(req, res);
//
//	}
//
//	@Override
//	public void destroy() {
//		Filter.super.destroy();
//	}
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//		Filter.super.init(filterConfig);
//	}
//
//}
