package com.ncjk.utcs.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="loginFilter"
			,urlPatterns={"/modules/*"}
			,initParams={
				@WebInitParam(name = "loginPage",value="sessionError.jsp")
			})
public class LoginFilter implements Filter{

	private FilterConfig config;
	@Override
	public void destroy() {
		
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest requ = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = requ.getSession();
		String requestPath = requ.getServletPath();
		String path = requ.getContextPath();
		String loginPage = path+"/"+config.getInitParameter("loginPage");
		if(session.getAttribute("user")==null	
			&&!requestPath.endsWith(config.getInitParameter("loginPage"))){
			request.setAttribute("tips", "session失效");
			resp.sendRedirect(loginPage);
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
		this.config = config;
	}
	

}
