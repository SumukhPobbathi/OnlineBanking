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

public class afterLogin extends HttpServlet {

    public afterLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		try{
		HttpSession session = request.getSession(false);
		System.out.println("inside afterlogin");
		String sql = "update customer_project set pwc = 3 where cid=?";
		DaoConnection daoc = new DaoConnection();
		Connection con;
		String cus_id = (String)session.getAttribute("cus_id");
		
		try {
			
			con = daoc.OpenConection();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1,cus_id );
			pstm.execute();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			System.out.println(e2);
			response.sendRedirect("login.html");
		}
		
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String name = (String)session.getAttribute("name");
		String head="<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>";
		String style="<style type='text/css'>"
				+ "body{background-image: url('login_back.jpg');background-size: cover;}"
				+".bg1{background-color: white;margin-right:420px;border-radius: 25px;height:400px; margin-top:70px; margin-left:420px;}"
				+ "</style>";
		String nav = "<nav class='navbar navbar-default'><div class='container-fluid'>  <div class='navbar-header'>  </div>  <ul class='nav navbar-nav'>    <li class='active'><a href='./afterLogin'>Home</a></li><li><a href ='EmiCalculator.html'>Calculate your EMI</a></li><li><a href ='FdCalculator.html'>Returns on Fixed Deposit</a></li><li><a href ='RdCalculator.html'>Returns on Recurring Deposit</a></li><li><a href='contact.html'> Contact Us</a><li> <li><a href='logout.jsp'>Log Out</a><li> </ul></div></nav>";
		pw.print("<html><head>"+head+style+"</head>");
				pw.print("<body>"+nav+"<div align='center' class='bg1'>");
		pw.print("<br><br><h1>Welcome "+name+"</h1><br>");
		DaoOperation dao = new DaoOperation();
		
		try {
			ResultSet rs = dao.getAccounts(cus_id);
			System.out.println("acccc");
			while(rs.next()){
				int acc_id = rs.getInt("aid");
				System.out.println(acc_id);
				pw.print("<h2> Account ID : "+acc_id+"</h2>");
				pw.print("<a href = 'afterAccount?id="+acc_id+"'>Click here for details.</a>");
			}
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("login.html");
		}
		pw.print("</div></body></html>");
		
		
		}catch(Exception e ){
			System.out.println(e);
			response.sendRedirect("login.html");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
