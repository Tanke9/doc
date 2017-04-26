import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/platzi-java")
public class PlatziJava extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String message = req.getParameter("message");
		resp.setContentType("application/json");
		try (PrintWriter out = resp.getWriter()){
			out.print("{\"message\": \"" + message + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer json = new StringBuffer();
		String line="";
		System.out.print("por aqui pasa");
		try (BufferedReader reader = req.getReader()){
			System.out.print("aqui entra");
			while ((line = reader.readLine())!=null){
				System.out.println("line: " + line);
				json.append(line);
			}
		}
	//	System.out.println(json.toString());
		ObjectMapper mapper = new ObjectMapper();
		Message2 message2 = mapper.readValue(json.toString(),Message2.class);
		System.out.println("mesasge2 : " + message2.getMessage());
		System.out.print("message2 size "+ message2.getSize());
		
	}
	
}