package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.PuzzleDao;
import com.google.sps.PuzzleDao.DifficultyFilter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/listpuzzles")
public class ListPuzzlesServlet extends HttpServlet {

  private PuzzleDao puzzleDao;
  private Gson gson;

  public ListPuzzlesServlet() {
    gson = new Gson();
    puzzleDao = new PuzzleDao();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String cursorUrl = request.getParameter("cursor");
    DifficultyFilter difficulty = PuzzleDao.DifficultyFilter.valueOf(request.getParameter("difficulty"));
    String json = gson.toJson(puzzleDao.listPuzzles(cursorUrl, difficulty, 6));

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
