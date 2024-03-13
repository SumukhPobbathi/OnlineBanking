package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;
import com.project.Dao.DaoOperation;

public class SendOtp extends HttpServlet {
	
    public SendOtp() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginid = request.getParameter("loginid");
		PrintWriter pw = response.getWriter();
		System.out.println(loginid);
		String email = null;
		DaoConnection daoc = new DaoConnection();
		try {
			Connection con = daoc.OpenConection();
			String sql = "select email from customer_project where loginid=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, loginid);
			ResultSet rs = pstm.executeQuery();
			
			rs.next();
			email = rs.getString("email");
			
			DaoOperation dao = new DaoOperation();
			
			int result = dao.sendOtp(email);
			if(result!=0){
				
			
			HttpSession s = request.getSession();
			s.setAttribute("loginid", loginid);
			s.setAttribute("otp", result);
			s.setAttribute("email", email);
			
			response.sendRedirect("Reset.html");
			
			}
			else{
				response.sendRedirect("error.html");
			}
			
		} catch (ClassNotFoundException | SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
