package filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;

/**
 * Servlet Filter implementation class UserObjectFilter
 */
public class UserObjectFilter implements Filter {

    public final String PACKAGE="bean";

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("filter");
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		Enumeration<String> paramName=req.getParameterNames();
		String className=null;
		while(paramName.hasMoreElements()){
			String param=paramName.nextElement();
			className=objectToClass(param.substring(0, param.indexOf(".")));
		}
		
		
		Object o=createObject(PACKAGE+"."+className);
		System.out.println(o.getClass().toString());
		Enumeration<String> paramNames=req.getParameterNames();
		for(Method m:o.getClass().getDeclaredMethods()){
			while(paramNames.hasMoreElements()){
				String param=paramName.nextElement();
			
				if(m.getName().contains("set")&&m.getName().contains(param)){
					try {
						m.invoke(o, request.getParameter(param));
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
			/*User user=new User();
			user.setUserName(req.getParameter("userName"));
			user.setPassword(req.getParameter("password"));
			request.setAttribute("user", user);*/
		
			
			chain.doFilter(request, response);
		
	}

	private Object createObject(String string) {
		Class classObj = null;
		try {
			classObj=Class.forName(string);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return classObj.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classObj;
		
	}

	private String objectToClass(String objectName) {
		char first=objectName.charAt(0);
		String last=objectName.substring(1, objectName.length());
		String firstS=Character.toString(first).toUpperCase();
		
		return firstS+last;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
