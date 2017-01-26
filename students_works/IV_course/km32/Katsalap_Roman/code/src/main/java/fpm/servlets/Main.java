package fpm.servlets;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.IOException;

@WebServlet(name="Main", urlPatterns={"/index.html","/start"})
public class Main extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        boolean logged = true;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {
            logged = false;
        }

        req.setAttribute("logged",logged);
        req.setAttribute("showErrorMsg",false);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma","no-cache");
        req.getRequestDispatcher("start.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
