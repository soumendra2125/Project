package com.app6;

import jakarta.servlet.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class Signup
 */
public class Signup extends GenericServlet {
	private static final long serialVersionUID = 1L;
	
	Connection cn=null;
	PreparedStatement ps=null;
	ServletContext context=null;
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public Signup() {
        System.out.println("Signup servlet object is constructed");
    }
    
    @Override
    public void init()
    {
    	System.out.println("Signup servlet object is initialized");
    	context=getServletContext();
    	String d=context.getInitParameter("driver");
    	String ur=context.getInitParameter("url");
    	String us=context.getInitParameter("username");
    	String pa=context.getInitParameter("password");
    	try 
    	{
    		Class.forName(d);
    		cn=DriverManager.getConnection(ur,us,pa);
    		ps=cn.prepareStatement("insert into login_data values(?,?,?,?)");
    		
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
		String s3=request.getParameter("email");
		String s4=request.getParameter("pass");
		//String s5=request.getParameter("pass1");
		try
		{
			ps.setString(1,s1);
			ps.setString(2, s2);
			ps.setString(3, s3);
			ps.setString(4, s4);
			ps.executeUpdate();
			out.println("<html><body style='background-color:lightcoral; color: blue; font-size: 30px; text-align: center;'>");
			out.println("<br><br><b>Welcome to Java Technocrat</b>");
			out.println("</body></html>");
//			if(s4.equals(s5))
//			{
//				ps.setString(1,s1);
//				ps.setString(2, s2);
//				ps.setString(3, s3);
//				ps.setString(4, s4);
//				ps.executeUpdate();
//				out.println("<html><body style='background-color:lightcoral; color: blue; font-size: 30px; text-align: center;'>");
//				out.println("<br><br><b>Welcome to Java Technocrat</b>");
//				out.println("</body></html>");
//			}
//			else
//			{
//				out.println("<html><body style='background-color:aquamarine; color: red; font-size: 30px; text-align: center;'>");
//				out.println("<br><br><b><i>Password not matched</i></b>");
//				out.println("<br><a href='http://localhost:8082/servlet_program6/signup.html'>Go back</a>");
//				out.println("</body></html>");
//			}
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
