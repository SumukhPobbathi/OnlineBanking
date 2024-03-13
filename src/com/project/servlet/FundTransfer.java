package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoOperation;

public class FundTransfer extends HttpServlet {

    public FundTransfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try{
		HttpSession session = request.getSession(false);
		int aid = (int)session.getAttribute("aid");
		PrintWriter pw = response.getWriter();
		String head="<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>";
		String style="<style type='text/css'>"
				+ "body{background-image: url('login_back.jpg');background-size: cover;}"
				+".bg1{background-color: white; height:400px;border-radius: 25px; margin-top:40px; margin-left:200px;margin-right:200px;}"
				+"p{font-size: 350%;}"
				+ "</style>";
		String nav = "<nav class='navbar navbar-default'><div class='container-fluid'>  <div class='navbar-header'>  </div>  <ul class='nav navbar-nav'> <li class='active'><a href='./afterLogin'>Home</a></li><li><a href ='EmiCalculator.html'>Calculate your EMI</a></li><li><a href ='FdCalculator.html'>Returns on Fixed Deposit</a></li><li><a href ='RdCalculator.html'>Returns on Recurring Deposit</a></li><li><a href='contact.html'> Contact Us</a><li><li><a href='logout.jsp'>Log Out</a><li> </ul></div></nav>";
		
		pw.print("<html><head><title>Transaction Page</title>"+head+style+"</head><body>"+nav
				+"<P align='center'>Welcome to Transaction Page</P><div class='bg1'>");
		pw.print("<div align='center'><br>");
		pw.print("<h1 class='a'>Welcome "+session.getAttribute("name")+"</h1><br>");
		
		pw.print("<form action = ./DoTransfer  method='POST'>");
		pw.print("<label class='abc'>Enter Creditors ID <br> <input class='a' type='number' name='creditor_aid'></label><br><br>");
		pw.print("<label class='abc'>Enter Transaction Password <br><input class='b' type='password' name='trans_pw'></label><br><br>");
		pw.print("<label class='abc'>Enter Transfer Amount <br><input class='c' type='number' name='amount'></label><br><br>");
		pw.print("<input type='submit' value = 'transfer'>");
		pw.print("</form>");
		pw.print("</div>");
		pw.print("</div></body></html>");
		
		
		}catch (Exception e) {
			response.sendRedirect("login.html");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
