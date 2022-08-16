package com.ryhnik.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.DbxUserSharingRequests;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.users.FullAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class DropBoxService {

    private final static Logger LOG = LoggerFactory.getLogger(DropBoxService.class);
    private final static String ACCESS_TOKEN = "sl.BNeG2IcDU-1p8G1TBHaLTQLrxy2f73NQjEZM79DdfUZnQ0pnn6QTG_dfAu3QRlhTSwyrJ8j-nNyr6t5qIJY-_IwWPJczkxHV6pBzH_Lrb4Bg1NOpq2SOu6DEFlzXKxRETi23Os3IJRxr";

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    public String saveImage(MultipartFile multipartFile) throws DbxException, IOException {
        File file = convertMultiPartFileToFile(multipartFile);
        final String fileName = LocalDateTime.now() + "_" + file.getName();
        LOG.info("Uploading file with name {}", fileName);

        DbxRequestConfig config = new DbxRequestConfig("master-club", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        String url = "";
        try (InputStream in = new FileInputStream(file)) {
            FileMetadata metadata = client.files().uploadBuilder("/master-club/" + fileName)
                    .uploadAndFinish(in);
            DbxUserSharingRequests sharing = client.sharing();
            SharedLinkMetadata shared = sharing.createSharedLinkWithSettings(metadata.getPathDisplay());
            url = shared.getUrl();
        }
        return url;
    }


    public void saveImage() throws DbxException, IOException {
        DbxRequestConfig config = new DbxRequestConfig("master-club", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

//         Get files and folder metadata from Dropbox root directory
//        ListFolderResult result = client.files().listFolder("");
//        while (true) {
//            for (Metadata metadata : result.getEntries()) {
//                System.out.println(metadata.getPathLower());
//            }
//
//            if (!result.getHasMore()) {
//                break;
//            }
//
//            result = client.files().listFolderContinue(result.getCursor());
//        }

        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("pom.xml")) {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                    .uploadAndFinish(in);
        }
    }

}
