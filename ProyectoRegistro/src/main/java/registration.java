

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.logging.Logger;
import sun.util.logging.PlatformLogger.Level;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class registration extends HttpServlet {
	
	Connection con;
	PreparedStatement pst;
	PreparedStatement pst1;
	ResultSet rs;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/hmtl");
			PrintWriter out = response.getWriter();
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost/ebank","root","");
			String fname= request.getParameter("fname");
			String lname= request.getParameter("lname");
			String mail= request.getParameter("mail");
			String phone= request.getParameter("phone");
			String age= request.getParameter("age");
			String pass= request.getParameter("pass");
			
			String data = pass;
			MessageDigest messageDigest;
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(data.getBytes());
			byte[] messageDigestMD5 = messageDigest.digest();
			StringBuffer stringBuffer = new StringBuffer();
			for (byte bytes : messageDigestMD5) {
				stringBuffer.append(String.format("%02x", bytes & 0xff));
			}
			
			out.println("Registro completado");
			out.println(fname);
			out.println(lname);
			out.println(mail);
			out.println(phone);
			out.println(age);
			out.println("Contraseña Encriptada: "+ stringBuffer.toString());
			
			
			
			
			pst = con.prepareStatement("insert into registation(fname,lname,mail,phone,age)values(?,?,?,?,?)");
			pst.setString(1, fname);
			pst.setString(1, lname);
			pst.setString(1, mail);
			pst.setString(1, phone);
			pst.setString(1, age);
			pst.executeUpdate();
			
			pst1 = con.prepareStatement("select max(id) from registation");
			rs = pst1.executeQuery();
			rs.next();
			
			int regno;
			
			regno = rs.getInt(1);
			
			out.println("Registro Exitoso");
			out.println("Numero de Registro: "+ regno);
			
			

			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			   e.printStackTrace();
		  }
	}

}
