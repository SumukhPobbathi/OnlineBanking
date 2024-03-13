package com.project.Dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.project.servlet.LoginServlet;



public class DaoOperation {
	DaoConnection dc = new DaoConnection();
	int otp;
	
	
	public ResultSet checkUser(String loginId, String password) throws ClassNotFoundException, SQLException, IOException{
		
		
		Connection con = dc.OpenConection();
		String query = "select * from customer_project where loginid=?";
		PreparedStatement pstm = con.prepareStatement(query);
		pstm.setString(1, loginId);
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()){
			
			if(rs.getString("loginpw").equals(password)){
				return rs;
			}
			
		} 
		
		return null;
	}
	
	public ResultSet getAccounts(String cus_id) throws ClassNotFoundException, SQLException{
	
		Connection con = dc.OpenConection();
		String query = "select * from account_project where cid = ?";
		PreparedStatement pstm = con.prepareStatement(query);
		pstm.setString(1, cus_id);
		ResultSet rs  = pstm.executeQuery();
	
		return rs;
	}
	
	public ResultSet getBalance(int acc_id) throws ClassNotFoundException, SQLException{
		Connection con = dc.OpenConection();
		String query = "select * from account_project where aid = ?";
		PreparedStatement pstm = con.prepareStatement(query);
		pstm.setInt(1, acc_id);
		ResultSet rs  = pstm.executeQuery();
		return rs;
	}
	
	public String fundTransfer(String cus_id,int aid , int creditor_aid, String trans_pw, int amount) throws ClassNotFoundException, SQLException{
		int update1=0,update2=0,result1=0,result2=0;
		Connection con = dc.OpenConection();
		con.setAutoCommit(false);
		String query = "select balance from account_project where aid=?";
		PreparedStatement pstm = con.prepareStatement(query);
		pstm.setInt(1, aid);
		ResultSet rs  = pstm.executeQuery();
		rs.next();
		int balance = rs.getInt("balance");
		
		
		String query1 = "select transactionpw from customer_project where cid=?";
		PreparedStatement pst = con.prepareStatement(query1);
		pst.setString(1, cus_id);
		ResultSet rs1 = pst.executeQuery();
		rs1.next();
		String trans_pw_fromDb = (String) rs1.getString(1);
		
		String query2 = "select aid from account_project";
		PreparedStatement ps = con.prepareStatement(query2);
		ResultSet r  = ps.executeQuery();
		int iterator = 0;
		while(r.next() ){
			if(creditor_aid!=r.getInt(1)){
				iterator++;
			}
			else{
				break;
			}
		}
		String query3 = "select count(aid) from account_project";
		PreparedStatement p = con.prepareStatement(query3);
		ResultSet rs3  = p.executeQuery();
		rs3.next();
		int count = rs3.getInt(1);
		if(iterator>=count){
			return "aidnotfound";
		}
		
		
		
		if(trans_pw.equals(trans_pw_fromDb)){
		
		if(amount<=balance){
			
			String debit_query = "update account_project set balance = balance - "+amount+"where aid = ?";
			PreparedStatement pstm1 = con.prepareStatement(debit_query);
			pstm1.setInt(1, aid);
			
			String credit_query = "update account_project set balance = balance + "+amount+"where aid = ?";
			PreparedStatement pstm2 = con.prepareStatement(credit_query);
			pstm2.setInt(1, creditor_aid);
			
			 update1 = pstm1.executeUpdate();
			 if(update1!=1){
				 	con.rollback();
					return "UNSUCCESFULL";

			 }
			 update2 = pstm2.executeUpdate();
			 if(update2!=1){
				 con.rollback();
					return "UNSUCCESFULL";

			 }
			 
			 Calendar calendar  = Calendar.getInstance(TimeZone.getDefault());
			 Date date = calendar.getTime();
			   
			 java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			    
			 System.out.println(sqlDate);
			String debit_transaction = "insert into transaction_project values (transaction_sequence.nextval,?,?,?,TO_DATE(?,'DD/MM/YYYY'))";
			PreparedStatement pstm3 = con.prepareStatement(debit_transaction);
			pstm3.setString(1, "D");
			pstm3.setInt(2, aid);
			pstm3.setInt(3, amount);
			pstm3.setDate(4,sqlDate);
			result1 = pstm3.executeUpdate();
			if(result1!=1){
				con.rollback();
				return "UNSUCCESFULL";
			}
		
			
			
			Date date1 = new Date();
			java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
			
			String credit_transaction ="insert into transaction_project values (transaction_sequence.nextval,?,?,?,TO_DATE(?,'DD/MM/YYYY'))";
			PreparedStatement pstm4 = con.prepareStatement(credit_transaction);
			pstm4.setString(1, "C");
			pstm4.setInt(2, creditor_aid);
			pstm4.setInt(3, amount);
			pstm4.setDate(4, sqlDate1);
			
			result2 = pstm4.executeUpdate();
			if(result2!=1){
				con.rollback();
				return "UNSUCCESFULL";

			 }
			
			int finalResult = update1+update2+result1+result2;
			if(finalResult==4){
				update1=0;
				update2=0;
				result1=0;
				result2=0;
				con.commit();
				return "SUCCESFULL";
			}
			else 
				con.rollback();
				return "UNSUCCESFULL";
			
		}
		else{
			return "insufficientbalance";
		}
		}
		else{
			return "wrongpassword";
		}
		
		
	}
	
	public ResultSet annualStatement(int aid){
		
		String query = "select * from transaction_project where accid = ?";
		DaoConnection daoc = new DaoConnection();
		Connection con;
		try {
			con = daoc.OpenConection();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, aid);
			
			ResultSet rs = pstm.executeQuery();
			return rs;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int sendOtp(String receiver_email) throws ClassNotFoundException, SQLException{
		Connection con = dc.OpenConection();
		System.out.println("INSIDE SEND OTP");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		String email = "javamailtest1122@gmail.com";
		String password = "javatest@123";
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(email,password);
			}
		});
		
		try {  
			Random rand = new Random();
			while(otp<1000){
				otp = rand.nextInt(10000);
			}
			 MimeMessage message = new MimeMessage(session);  
			 message.setFrom(new InternetAddress(email));
			 message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver_email));
			 message.setSubject("Otp for Password reset");
			 message.setText("Your OTP for bank is "+otp);
			 
			   
			 //3rd step)send message  
			 Transport.send(message);  
			  
			 return otp;
			  
			 }catch (Exception e) {
				 
				 return 0; 
			 }  

	}
	
	public void debitmsg(String cus_id , int amount,int aid) throws ClassNotFoundException, SQLException{
		Connection con = dc.OpenConection();
		String sql = "select email from customer_project where cid=?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, cus_id);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		String rec_email = rs.getString("email");
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		String email = "javamailtest1122@gmail.com";
		String password = "javatest@123";
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(email,password);
			}
		});
		
		try {  
			
			 MimeMessage message = new MimeMessage(session);  
			 message.setFrom(new InternetAddress(email));
			 message.addRecipient(Message.RecipientType.TO,new InternetAddress(rec_email));
			 message.setSubject("Amount Debited");
			 message.setText("Amount of Rs. "+amount+" has been debited from your account ID : "+aid);
			 
			   
			 //3rd step)send message  
			 Transport.send(message);  
		
		}catch (Exception e) {
	}
	}
	
	public void creditmsg(String cus_id , int amount , int credid) throws ClassNotFoundException, SQLException{
		Connection con = dc.OpenConection();
		String sql = "select email from customer_project where cid=?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, cus_id);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		String rec_email = rs.getString("email");
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		String email = "javamailtest1122@gmail.com";
		String password = "javatest@123";
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(email,password);
			}
		});
		
		try {  
			
			 MimeMessage message = new MimeMessage(session);  
			 message.setFrom(new InternetAddress(email));
			 message.addRecipient(Message.RecipientType.TO,new InternetAddress(rec_email));
			 message.setSubject("Amount Credited");
			 message.setText("Amount of Rs. "+amount+" has been credited to your account ID : "+credid);
			 
			   
			 //3rd step)send message  
			 Transport.send(message);  
		
		}catch (Exception e) {
	}
	}
	}

