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

    try {
      Long puzzleId = Long.parseLong(request.getParameter("id"));
      response.sendRedirect("viewpuzzle.html?id=" + puzzleId.toString());
    } catch(NumberFormatException e) {
      //the id couldn't be parsed
    }

    response.sendRedirect("viewpuzzle.html");
  }
}
