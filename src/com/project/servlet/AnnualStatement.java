package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;
import com.project.Dao.DaoOperation;

public class AnnualStatement extends HttpServlet {

    public AnnualStatement() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		// TODO Auto-generated method stub
		try{
		HttpSession session = request.getSession(false);
		int aid = (int)session.getAttribute("aid");
		
		DaoOperation dao = new DaoOperation();
		
		PrintWriter pw = response.getWriter();
		
		ResultSet rs = dao.annualStatement(aid);
		String date = request.getParameter("date");
		java.sql.Date sql_date = java.sql.Date.valueOf(date);
		String head="<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>";
					
			pw.print("<html><head>"+head+"</head><body>");
			pw.print("<h1> Todays Date : "+date+"</h1>");
		
		
			pw.print("<div align='center'><table style='width:100%>' class='table table-striped'");
			pw.print("<tr>");
				pw.print("<th> Transaction ID</th>");
				pw.print("<th> Transaction Type</th>");
				pw.print("<th> Account ID</th>");
				pw.print("<th> Amount</th>");
				pw.print("<th> Date</th>");
			pw.print("</tr>");
		try {
			rs.next();
			java.sql.Date date2 = rs.getDate("tdate");
			while(rs.next()){
				
				if(date2.before(sql_date)){
				pw.print("<tr>");
					pw.print("<td> "+rs.getInt(1)+"</th>");
					pw.print("<td>"+rs.getString(2)+"</th>");
					pw.print("<td>"+rs.getInt(3)+"</th>");
					pw.print("<td>"+rs.getInt(4)+"</th>");
					pw.print("<td> "+rs.getString(5)+"</th>");
				pw.print("</tr>");
				}
			}
			pw.print("</div>");
			pw.print("<br>");
			pw.print("<div align='left'><br><br><h4>C->Credit</h4><br><h4>D->Debit</h4><div>");
			
			
			
		} catch (SQLException e) {
			
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
