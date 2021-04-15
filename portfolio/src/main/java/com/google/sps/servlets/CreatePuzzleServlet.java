// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

//Need to add blobstore and datastore dependencies to pom.xml
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
// import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.sps.Puzzle;
import com.google.sps.PuzzleDao;

/**
 * Takes an image submitted by the user and uploads it to Cloud Storage, and then displays it as
 * HTML in the response.
 */
@WebServlet("/upload")
@MultipartConfig
public class CreatePuzzleServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Get the difficulty indicated by user.
    Puzzle.Difficulty difficulty;
    try {
        difficulty = Puzzle.Difficulty.valueOf(request.getParameter("difficulty"));
    } catch (IllegalArgumentException e) {
        response.getWriter().println("Invalid value for difficulty");
        return;
    }

    // Get the file chosen by the user.
    Part filePart = request.getPart("image");
    String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName() ;
    InputStream fileInputStream = filePart.getInputStream();

    // Upload the file to blobstore and get its URL.
    String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);

    Puzzle puzzle = new Puzzle();

    puzzle.setImageUrl(uploadedFileUrl);
    puzzle.setDifficulty(difficulty);

    PuzzleDao dao = new PuzzleDao();

    puzzle = dao.create(puzzle);
    
    // Send the user to view puzzle page.
    response.setContentType("application/json;");
    response.getWriter().println(new Gson().toJson(puzzle));
  }

  /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(String fileName, InputStream fileInputStream) {
    String projectId = "simplyfaisal-sps-spring21";
    String bucketName = "simplyfaisal-sps-spring21.appspot.com";
    Storage storage =
        StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    BlobId blobId = BlobId.of(bucketName, fileName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    // Upload the file to Cloud Storage.
    Blob blob = storage.create(blobInfo, fileInputStream);

    // Return the uploaded file's URL.
    return blob.getMediaLink();
  }

}
