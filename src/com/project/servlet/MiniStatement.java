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

public class MiniStatement extends HttpServlet {

    public MiniStatement() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		// TODO Auto-generated method stub
		try{
		response.setContentType("text/html");
		String n1 = request.getParameter("n");
		int n = Integer.parseInt(n1);
		HttpSession session = request.getSession();
		int aid =(int) session.getAttribute("aid");
		DaoConnection daoc = new DaoConnection();
		PrintWriter pw = response.getWriter();
		try {
			Connection con =daoc.OpenConection();
			String query = "select * from transaction_project where accid=? order by tid desc";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, aid);
			ResultSet rs = pstm.executeQuery();
			String head="<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>";
			
			pw.print("<html><head>"+head+"</head><body>");
			pw.print("<table style='width:100%>' class='table table-striped'");
			pw.print("<tr>");
				pw.print("<th> Transaction ID</th>");
				pw.print("<th> Transaction Type</th>");
				pw.print("<th> Account ID</th>");
				pw.print("<th> Amount</th>");
				pw.print("<th> Date</th>");
			pw.print("</tr>");
			
			while(rs.next() && n>0){
				n--;
				pw.print("<tr>");
				pw.print("<td> "+rs.getInt(1)+"</th>");
				pw.print("<td>"+rs.getString(2)+"</th>");
				pw.print("<td>"+rs.getInt(3)+"</th>");
				pw.print("<td>"+rs.getInt(4)+"</th>");
				pw.print("<td> "+rs.getString(5)+"</th>");
				pw.print("</tr>");
			}
			pw.print("<div align='left'><br><br><h4>C->Credit</h4><br><h4>D->Debit</h4><div>");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		pw.print("<input type='submit' value='PRINT' onClick='window.print()'>");

		}catch (Exception e) {
			response.sendRedirect("login.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
