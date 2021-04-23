package com.google.sps;

import java.util.ArrayList;

public class ListPuzzles {

  private List<Puzzle> puzzles;
  private String cursorUrl;

  public ListPuzzles(List<Puzzle> puzzles, String cursorUrl) {
    this.puzzles = puzzles;
    this.cursorUrl = cursorUrl;
  }
}
