

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext cs=config.getServletContext();  
		
		String s1=cs.getInitParameter("driver");
		String s2=cs.getInitParameter("url");
		String s3=cs.getInitParameter("username");
		String s4=cs.getInitParameter("password");
		try {
			Class.forName(s1);
			con=DriverManager.getConnection(s2, s3, s4);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1=request.getParameter("uname");
		String s2=request.getParameter("pword");
		PrintWriter pw=response.getWriter();
		try {
			PreparedStatement psmt=con.prepareStatement("select * from registration where username=? and password=? ");
			psmt.setString(1, s1);
			psmt.setString(2, s2);
			ResultSet rs=psmt.executeQuery();
			
			if(rs.next())
			{
				response.sendRedirect("welcome");
			}else {
				pw.println("invalid username/password ");
				RequestDispatcher re=request.getRequestDispatcher("login.html");
				re.include(request, response);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
