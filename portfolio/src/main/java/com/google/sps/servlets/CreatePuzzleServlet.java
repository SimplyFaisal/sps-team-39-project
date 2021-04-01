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

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/** Temporary comment: The errors are because pom.xml is currently 
 * the necessary dependencies. This servlet currently is structured to take
 * in an image and upload it to the blobstore and then upload the the img link 
 * and some metadata into datastore. There is currently no method to actually
 * create a puzzle from the image.
 */


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

    /* Upload the image to blob store */

    // Get the message entered by the user.
    String difficulty = request.getParameter("difficulty");

    // Get the file chosen by the user.
    Part filePart = request.getPart("image");
    String fileName = filePart.getSubmittedFileName();
    InputStream fileInputStream = filePart.getInputStream();

    // Upload the file to blobstore and get its URL
    String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);

    // Upload the puzzle information to datastore
    uploadToDatastore(uploadedFileUrl, difficulty);

    // Sends the user to view puzzle page
    response.sendRedirect("/ViewPuzzle"); 
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

  // This should handle creating the puzzle
  private static String createPuzzle(String mediaLink) {
    return "";
  }

  private static void uploadToDatastore(String uploadedFileUrl, String difficulty) {
    /* Upload the image URL with additional parameters to datastore */
    long timestamp = System.currentTimeMillis();

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Puzzle");
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("url", uploadedFileUrl)
            .set("difficulty", difficulty)
            .set("timestamp", timestamp)
            .build();
    datastore.put(taskEntity);
  }

}
