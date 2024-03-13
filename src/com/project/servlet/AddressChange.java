package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.project.Dao.DaoConnection;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class AddressChange
 */
public class AddressChange extends jakarta.servlet.http.HttpServlet {
	
    public AddressChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try{
		HttpSession session = request.getSession(false);
		String cus_id = (String)session.getAttribute("cus_id");
		PrintWriter pw = response.getWriter();
		pw.print("<html><body>");
		DaoConnection daoc = new DaoConnection();
		try {
			Connection con = daoc.OpenConection();
			String query = "select address from customer_project where cid = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cus_id);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			String address = rs.getString(1);
			
			String query1 = "update customer_project set address = ? where cid= ?";
			PreparedStatement pstm1 = con.prepareStatement(query1);
			pstm1.setString(1,request.getParameter("address"));
			pstm1.setString(2, cus_id);
			
			int result = pstm1.executeUpdate();
			
			if(result==1){
				pw.print("<html><body onload='myFunction()'>");
				pw.print("<script>function myFunction(){alert('Address Changed Succesfully !!'); window.location='./afterLogin'}</script></body></html>");
				pw.print("<h2>Address changed succesfully</h2>");
				pw.print("<a href = './afterLogin'> Click here to go back</a>");
			}
			else{
				pw.print("<html><body onload='myFunction()'>");
				pw.print("<script>function myFunction(){alert('Address didn't Changed , Please retry after few minutes!!'); window.location='./afterLogin'}</script></body></html>");
			}
			
			pw.print("</body></html>");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}catch (Exception e) {
			response.sendRedirect("login.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
