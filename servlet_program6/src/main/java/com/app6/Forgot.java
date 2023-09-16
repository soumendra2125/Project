package com.app6;

import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

/**
 * Servlet implementation class Forgot
 */
public class Forgot extends GenericServlet {
	private static final long serialVersionUID = 1L;
	Connection cn=null;
	PreparedStatement ps=null;
	ServletContext context=null;
       
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public Forgot() {
        System.out.println("Forgot servlet object is constructed");
    }

    @Override
    public void init()
    {
    	System.out.println("Forgot servlet object is initialized");
    	context=getServletContext();
    	String d=context.getInitParameter("driver");
    	String ur=context.getInitParameter("url");
    	String us=context.getInitParameter("username");
    	String pa=context.getInitParameter("password");
    	try 
    	{
    		Class.forName(d);
    		cn=DriverManager.getConnection(ur,us,pa);
    		ps=cn.prepareStatement("select * from login_data where name=(?) and phone=(?)");
    		
    	}
    	catch(ClassNotFoundException ce)
    	{
    		System.out.println("Class not found");
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    	}
    }

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String s1=request.getParameter("user");
		String s2=request.getParameter("phone");
		try
		{
			ps.setString(1,s1);
			ps.setString(2, s2);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				out.println("<html><body style='background-color:cyan; color: red; font-size: 30px; text-align: center;'>");
				out.println("<br><br><b>Your password is </b>"+rs.getString(4));
				out.println("<br><a href='http://localhost:8082/servlet_program6/login.html'>Login</a>");
				out.println("</body></html>");
			}
			else
			{
				out.println("<html><body style='background-color: crimson; font-size: 30px; text-align: center;'>");
				out.println("<br><br><b><i>No account linked to the username. Check Username.</i></b>");
				out.println("<br><a href='http://localhost:8082/servlet_program6/signup.html'>Signup</a>");
				out.println("<br><a href='http://localhost:8082/servlet_program6/forgot.html'>Go back</a>");
				out.println("</body></html>");
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	@Override
	public void destroy()
	{
		try
		{
			ps.close();
			cn.close();
		}
		catch(NullPointerException ne)
		{
			System.out.println("Create the object first");
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
	}

}
