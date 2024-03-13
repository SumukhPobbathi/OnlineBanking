package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;

public class CheckOtp extends HttpServlet {

    public CheckOtp() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		int otp = (int)session.getAttribute("otp");
		String loginid = (String)session.getAttribute("loginid");
		String x =request.getParameter("otp");
		int received_otp = Integer.parseInt(x);
		
		if(otp==received_otp){
			String pwd = request.getParameter("pwd");
			String repwd = request.getParameter("repwd");
			if(pwd.equals(repwd)){
				String sql = "update customer_project set loginpw=? where loginid=?";
				DaoConnection daoc = new DaoConnection();
				try {
					Connection con = daoc.OpenConection();
					PreparedStatement pstm = con.prepareStatement(sql);
					pstm.setString(1, pwd);
					pstm.setString(2,loginid );
					pstm.execute();
					pw.print("<html><body onload='myFunction()'>");
					pw.print("<script>function myFunction(){alert('Password Changed Succesfully'); window.location='login.html'}</script></body></html>");
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					pw.print("<html><body onload='myFunction()'>");
					pw.print("<script>function myFunction(){alert('Internal Error : cannot change password nwo, please try again after some time!'); window.location='Forgot.html'}</script></body></html>");
					
				} 
				
			}
		}
		else{
			pw.print("<html><body onload='myFunction()'>");
			pw.print("<script>function myFunction(){alert('OTP not Matched!!'); window.location='Forgot.html'}</script></body></html>");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
