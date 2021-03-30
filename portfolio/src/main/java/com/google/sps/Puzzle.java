package com.google.sps;

/** Puzzle domain object */
public class Puzzle {

  /*
  * Datastore generated id
   */
  private String puzzleId;
  
  /*
  * Blobstore url
   */
  private String imageUrl; // Blobstore url

  public String getPuzzleId() {
    return this.puzzleId;
  }

  public Puzzle setPuzzleId(String puzzleId) {
    this.puzzleId = puzzleId;
    return this;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public Puzzle setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }
}