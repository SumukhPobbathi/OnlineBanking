package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.project.Dao.DaoOperation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class afterAccount extends HttpServlet {

    public afterAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		try{
		PrintWriter pw = response.getWriter();
		
		String head="<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>";
		
		String style="<style type='text/css'>"
				+ "body{background-image: url('login_back.jpg');background-size: cover;}"
				+".bg1{background-color: white; margin-top:70px;border-radius: 25px; margin-left:200px;margin-right:200px;}"
				+".x{margin-left:180px;}"
				+".xx{margin-left:30px;}"
				+ "</style>";
		String nav = "<nav class='navbar navbar-default'><div class='container-fluid'>  <div class='navbar-header'>  </div>  <ul class='nav navbar-nav'>    <li class='active'><a href='./afterLogin'>Home</a></li><li><a href ='EmiCalculator.html'>Calculate your EMI</a></li><li><a href ='FdCalculator.html'>Returns on Fixed Deposit</a></li><li><a href ='RdCalculator.html'>Returns on Recurring Deposit</a></li><li><a href='contact.html'> Contact Us</a><li> <li><a href='logout.jsp'>Log Out</a><li> </ul></div></nav>";
		
		pw.print("<html><head>"+head+style+"</head><body>"+nav+"<div class = 'bg1' align='center'>");																																												
		
		DaoOperation dao = new DaoOperation();
		
		String acc_id = (String) request.getParameter("id");
		int id = Integer.parseInt(acc_id);
	
			
			try {
				ResultSet rs = dao.getBalance(id);
				rs.next();
				int aid= rs.getInt("aid");
				int balance = rs.getInt("balance");
				pw.print("<br><h2>Account ID is : "+aid+"</h2><br>");
				
				pw.print("<h2 class='a'>Balance : "+balance+"</h2><br>");
				
				pw.print("<div class='row x' >");
				
				pw.print("<div class='col-sm-3'><form action = './FundTransfer'>");
				pw.print("<input type='submit' value = 'Money Transfer'/>");
				pw.print("</form></div>");
				
				pw.print("<div class='col-sm-3'><form action = 'AnnualStatement.html'>");
				pw.print("<input type='submit' value = 'Annual Statement'/>");
				pw.print("</form></div>");
				
				pw.print("<div class='col-sm-3'><form action = 'AddressChange.html'  >");
				pw.print("<input type='submit' value = 'Address Change'/>");
				pw.print("</form></div></div>");
				
				pw.print("<div align='center' class='row x' >");
				
				pw.print("<div class='col-sm-3'><form action = './ChequeBook'  >");
				pw.print("<input type='submit'  value = 'Request Cheque Book'/>");
				pw.print("</form></div>");
				
				pw.print("<div class='col-sm-3'><form action = 'MiniStatement.html'  >");
				pw.print("<input type='submit' value = 'Mini Statement'/>");
				pw.print("</form></div>");
				
				pw.print("<div class='col-sm-3'><form action = 'stoppayment.html'  >");
				pw.print("<input type='submit' value = 'Stop Payment of Cheque'/></div>");
				
				pw.print("</form></div><br><br><br></div>");
				
				
				
				
				HttpSession session = request.getSession(false);
				session.setAttribute("aid", aid);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			pw.print("</body></html>");
			
		}catch (Exception e) {
			response.sendRedirect("login.html");
		}
			
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
