package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import com.project.Dao.DaoConnection;


public class ChequeBook extends HttpServlet {
	
    public ChequeBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		String sql="select chequeDate from account_project where aid=? ";
        Connection con;
        DaoConnection daoc =new DaoConnection();
        HttpSession session = request.getSession(false);
        int aid =(int) session.getAttribute("aid");
        try
        {
            con=daoc.OpenConection();
            PreparedStatement pst=con.prepareCall(sql);
            pst.setInt(1,aid);
            ResultSet rs=pst.executeQuery();
           
           
            long millis=System.currentTimeMillis();
            java.sql.Date pDate=new java.sql.Date(millis);
            java.sql.Date nDate=new java.sql.Date(millis+(86400000*14));
            java.sql.Date oDate =null;           
            
            
          
           
            while(rs.next())
                oDate=rs.getDate(1);
           
            
            
            if(oDate==null)
            {
                String date="update account_project set chequeDate=? where aid=? ";
                pst=con.prepareStatement(date);
                pst.setDate(1,nDate );
                pst.setInt(2,aid);
                int res=pst.executeUpdate();
                pw.print("<body onload='myFunction()'><script>");
                pw.print("function myFunction(){");
                pw.print("alert('Expected delivery date is "+nDate+"');" + "window.location = './afterLogin';");
       
                pw.print("} </script></body></html>");
               // pw.println("Expected delivery date is "+nDate);
               
               
               
               
            }
            else
            {
                if(pDate.before(oDate))
                {	
                	pw.print("<body onload='myFunction()'><script>");
                    pw.print("function myFunction(){");
                	 pw.print("alert('Cheque Book Request under process,expected before "+oDate+"');" + "window.location = './afterLogin';");
                     pw.print("} </script></body></html>");
                   /* pw.println("Cheque Book Request under process,expected before "+oDate);*/
                }
                else
                {		
                	pw.print("<body onload='myFunction()'><script>");
                    pw.print("function myFunction(){");
                	pw.print("alert('Cheque book was delivered on "+oDate+"');" + "window.location = './afterLogin';");
                    pw.print("} </script></body></html>");
                    pw.println("Cheque book was delivered on "+oDate);
                }
                   
            }
           
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
