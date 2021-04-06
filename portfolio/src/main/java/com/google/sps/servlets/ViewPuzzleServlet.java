package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.Puzzle;
import com.google.sps.PuzzleDao;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Handles requests to /puzzle url. */
@WebServlet("/puzzle")
public class ViewPuzzleServlet extends HttpServlet {

  private PuzzleDao puzzleDao;
  private Gson gson;

  public ViewPuzzleServlet() {
    puzzleDao = new PuzzleDao();
    gson = new Gson();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = "{}";

    try {
      //search for a puzzle with the given id
      Long puzzleId = Long.parseLong(request.getParameter("id"));
      Puzzle puzzle = puzzleDao.read(puzzleId);

      //converts puzzle into json only if a puzzle was found
      if(puzzle != null) {
        json = gson.toJson(puzzle);
      }
    } catch(NumberFormatException e) {
      //the given id could not be parsed into a Long
    }

    //returns json
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
