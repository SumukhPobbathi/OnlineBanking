package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoOperation;

public class DoTransfer extends HttpServlet {

    public DoTransfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try{
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		int aid = (int)session.getAttribute("aid");
		String cus_id = (String) session.getAttribute("cus_id");
		int creditor_aid = Integer.parseInt(request.getParameter("creditor_aid"));
		String trans_pw = request.getParameter("trans_pw");
		int amount=0;
		 amount = Integer.parseInt(request.getParameter("amount"));
		DaoOperation dao = new DaoOperation();
		
		pw.print("<html><body onload='myFunction()'><script>");
		
		try {
			
			String transfer = dao.fundTransfer(cus_id,aid, creditor_aid, trans_pw, amount);
			if(transfer.equalsIgnoreCase("SUCCESS")){
				/*
				 * dao.debitmsg(cus_id, amount, aid); dao.creditmsg(cus_id, amount,
				 * creditor_aid);
				 */
				response.sendRedirect("TransactionSuccesfull.html");
				
			}
			else{
				/*
				 * dao.debitmsg(cus_id, amount, aid); dao.creditmsg(cus_id, amount,
				 * creditor_aid);
				 */
				response.sendRedirect("TransactionFailed.jsp?status="+transfer);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			pw.print("function myFunction(){ alert('Transaction Failed : All fields must be filled');"
					+ "window.location='login.html'"
					+ "}");
			
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
