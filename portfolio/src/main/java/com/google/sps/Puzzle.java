package com.google.sps;

/** Puzzle domain object */
public class Puzzle {

  private String puzzleId; //Datastore generated id
  private String image; // Blobstore url

  public String getPuzzleId() {
    return this.puzzleId;
  }

  public Puzzle setPuzzleId(String puzzleId) {
    this.puzzleId = puzzleId;
    return this;
  }

  public String getImage() {
    return this.image;
  }

  public Puzzle setImage(String image) {
    this.image = image;
    return this;
  }
}