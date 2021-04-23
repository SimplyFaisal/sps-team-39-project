package com.google.sps;

import java.util.List;

public class ListPuzzles {

  private List<Puzzle> puzzles;
  private String cursorUrl;

  public ListPuzzles(List<Puzzle> puzzles, String cursorUrl) {
    this.puzzles = puzzles;
    this.cursorUrl = cursorUrl;
  }
}
