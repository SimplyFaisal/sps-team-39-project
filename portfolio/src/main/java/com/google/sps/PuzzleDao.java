package com.google.sps;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

/** Puzzle DAO implementation*/
public class PuzzleDao implements Dao<Puzzle> {

    private Datastore datastore;
    private KeyFactory keyFactory;

    public PuzzleDao() {
      datastore = DatastoreOptions.getDefaultInstance().getService();
      keyFactory = datastore.newKeyFactory().setKind("Puzzle");
    }

    @Override
    public void create(Puzzle puzzle) {
      //saves puzzle object on datastore
      FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
          .set("image", puzzle.getImage())
          .build();
      datastore.put(taskEntity);
    }

    @Override
    public void update(Puzzle puzzle) {
      //converts the id of the object into a Long.
      Long puzzleId = Long.parseLong(puzzle.getPuzzleId());

      //updates the entity with the data contained in "puzzle", 
      // except the key which is used to search for the entity.
      Entity task = 
        Entity.newBuilder(datastore.get(keyFactory.newKey(puzzleId)))
          .set("image", puzzle.getImage())
          .build();
      datastore.update(task);
    }

    @Override
    public Puzzle read(String puzzleId) {
      //reads the entity whose id match "puzzleID"
      Entity entity = datastore.get(keyFactory.newKey(Long.parseLong(puzzleId)));
      
      //saves the retrieved data into a "Puzzle" object
      Puzzle puzzle = new Puzzle()
        .setPuzzleId(entity.getKey().getId().toString())
        .setImage(entity.getString("image"));

      //returns the object
      return puzzle;
    }

    @Override
    public void delete(String puzzleId) {
      //deletes the entity whose id match "puzzleID"
      datastore.delete(keyFactory.newKey(Long.parseLong(puzzleId)));
    }
}