package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;
import com.project.Dao.DaoOperation;

public class LoginServlet extends HttpServlet {
	
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		System.out.println("Inside login servel");
		String loginId = request.getParameter("login_id");
		String pass = request.getParameter("login_pwd");
		
		DaoOperation d = new DaoOperation();
		try {
			
			System.out.println("before check user");
			ResultSet rs = d.checkUser(loginId, pass);
			System.out.println("after check user");
		
			
			if(rs!=null){
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(900);
				String name = rs.getString("cname");
				String cus_id= rs.getString("cid");
				session.setAttribute("name",name);
				session.setAttribute("cus_id", cus_id);
				if(rs.getString("account_status").equalsIgnoreCase("active")){
					System.out.println("if active");
					response.sendRedirect("./afterLogin");
				}
				else{
					System.out.println("if not active");
					response.sendRedirect("blocked.html");
				}
			}
			
			

			
			else{
				
				String query = "select * from customer_project where loginid=?";
				DaoConnection daoc = new DaoConnection();
				Connection c = daoc.OpenConection();
				PreparedStatement p = c.prepareStatement(query);
				p.setString(1, loginId);
				ResultSet rs1 = p.executeQuery();
				rs1.next();
				int pwc = rs1.getInt("pwc");
				String cid = rs1.getString("cid");
				
				if(pwc>1){
				String sql1 = "update customer_project set pwc=pwc-1 where cid=?";

				PreparedStatement ps = c.prepareStatement(sql1);
				ps.setString(1, cid);
				ps.execute();
				pwc--;
				response.sendRedirect("error.jsp?att="+pwc);
				}
				else{
					String query1 = "update customer_project set account_status = 'blocked' where cid=?";
					
					PreparedStatement pstm = c.prepareStatement(query1);
					pstm.setString(1,cid);
					pstm.execute();
					response.sendRedirect("blocked.html");
					
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("login.html");
			
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
