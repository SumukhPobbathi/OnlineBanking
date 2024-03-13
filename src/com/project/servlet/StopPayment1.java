package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;


/**
 * Servlet implementation class StopPayment
 */
public class StopPayment1 extends HttpServlet {
	
    public StopPayment1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		// TODO Auto-generated method stub
		try{
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession(false);
		String cusID=(String) session.getAttribute("cus_id");
		int accId=(int) session.getAttribute("aid");
		String val=request.getParameter("val");
		Connection con;
		String sql="update account_project set chpay=? where aid=?";
		
		pw.println("<html><body onload='myFunction()'>");
		
		try
		{	
			DaoConnection daoc = new DaoConnection();
			con=daoc.OpenConection();
			PreparedStatement pst=con.prepareStatement(sql);
			if(val.equals("yes"))
			{
				pst.setString(1,"blocked");
				pst.setInt(2,accId);
				int res=pst.executeUpdate();
				if(res==1){
					
					pw.print("<script>function myFunction(){alert('Payment of cheques is stopped');window.location='./afterLogin';}</script>");
				
				}
				else{
					pw.print("<script>function myFunction(){alert('Payment of cheques is not stopped, Try again!');window.location='./afterLogin';}</script>");
					
				}
				
			}
			else if(val.equals("no"))
			{
				pw.print("<script>function myFunction(){alert('Request Cancelled!!');window.location='./afterLogin';}</script>");
				
			}
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}catch (Exception e) {
			response.sendRedirect("login.html");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
