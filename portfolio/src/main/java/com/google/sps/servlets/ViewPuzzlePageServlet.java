package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /viewpuzzle URL.*/
@WebServlet("/viewpuzzle")
public class ViewPuzzlePageServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");

    
    String id = request.getParameter("id");
    if(id == null) {
      //if an id was not given
      response.sendRedirect("viewpuzzle.html");
    }

    try {
      Long puzzleId = Long.parseLong(id);
      //if an id was given
      response.sendRedirect("viewpuzzle.html?id=" + puzzleId.toString());
    } catch(NumberFormatException e) {
      //the id couldn't be parsed
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().println("Invalid puzzleId");
    }
  }
}
