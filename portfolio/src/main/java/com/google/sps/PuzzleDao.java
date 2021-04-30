package com.google.sps;

import java.util.ArrayList;

import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

/** Puzzle DAO implementation*/
public class PuzzleDao implements Dao<Puzzle> {

    private Datastore datastore;
    private KeyFactory keyFactory;

    public enum DifficultyFilter{
      ALL,
      EASY,
      MEDIUM,
      HARD;
    }

    public PuzzleDao() {
      datastore = DatastoreOptions.getDefaultInstance().getService();
      keyFactory = datastore.newKeyFactory().setKind("Puzzle");
    }

    @Override
    public Puzzle create(Puzzle puzzle) {
      //saves puzzle object on datastore
      FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
          .set("imageUrl", puzzle.getImageUrl())
          .set("difficulty", puzzle.getDifficulty().toString())
          .set("name", puzzle.getName())
          .set("username", puzzle.getUsername())
          .build();
      Entity entity = datastore.put(taskEntity);

      //saves the id generated by datastore on the puzzle object
      puzzle.setPuzzleId(entity.getKey().getId());

      return puzzle;
    }

    @Override
    public void update(Puzzle puzzle) {
      //updates the entity with the data contained in "puzzle", 
      // except the id which is used to search for the entity.
      Long puzzleId = puzzle.getPuzzleId();
      Entity task = 
        Entity.newBuilder(datastore.get(keyFactory.newKey(puzzleId)))
          .set("imageUrl", puzzle.getImageUrl())
          .set("difficulty", puzzle.getDifficulty().toString())
          .set("name", puzzle.getName())
          .set("username", puzzle.getUsername())
          .build();
      datastore.update(task);
    }

    @Override
    public Puzzle read(Long puzzleId) {
      //reads the entity whose id match "puzzleID"
      Entity entity = datastore.get(keyFactory.newKey(puzzleId));

      //Return null if there was no matching entity or the puzzle object
      return entity != null ? getPuzzleFrom(entity) : null;
    }

    @Override
    public void delete(Long puzzleId) {
      //deletes the entity whose id match "puzzleID"
      datastore.delete(keyFactory.newKey(puzzleId));
    }
  
    private Puzzle getPuzzleFrom(Entity entity) {
        return new Puzzle()
            .setPuzzleId(entity.getKey().getId())
            .setImageUrl(entity.getString("imageUrl"))
            .setDifficulty(Puzzle.Difficulty.valueOf(entity.getString("difficulty")))
            .setName(entity.contains("name") ? entity.getString("name") : "")
            .setUsername(entity.contains("username") ? entity.getString("username") : "");
    }

    public ListPuzzles listPuzzles(String cursorUrl, DifficultyFilter filter, int pageSize) {
      //queries the puzzles
      EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder()
        .setKind("Puzzle")
        .setLimit(pageSize);
      
      //filters by difficulty
      if(filter != DifficultyFilter.ALL) {
        queryBuilder.setFilter(PropertyFilter.eq("difficulty", filter.toString()));
      }
      
      if(cursorUrl != null){
        Cursor pageCursor = Cursor.fromUrlSafe(cursorUrl);
        queryBuilder.setStartCursor(pageCursor);
      }        
      
      //creates a list of puzzles using the querie results
      ArrayList<Puzzle> puzzles = new ArrayList<>();
      QueryResults<Entity> tasks = datastore.run(queryBuilder.build());
      while (tasks.hasNext()) {
        Entity task = tasks.next();
        Puzzle puzzle = getPuzzleFrom(task);
        puzzles.add(puzzle);
      }

      //returns a ListPuzzles object containing a list and a cursor url
      Cursor nextPageCursor = tasks.getCursorAfter();
      return new ListPuzzles(puzzles, nextPageCursor.toUrlSafe());
    }
}
