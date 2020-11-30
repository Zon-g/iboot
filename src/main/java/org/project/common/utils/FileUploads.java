package org.project.common.utils;

import org.project.common.attachment.FileHelper;
import org.project.common.attachment.Folders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class to upload files to the server.
 *
 * @author Zon-g
 */
@Component
public class FileUploads {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    public String upload(Folders folders, MultipartFile file) {
        return upload(folders, file, false);
    }

    /**
     * Accepts a folder name, representing as an enumeration constant, and
     * a <code>MultipartFile</code> object, uploads the specified file to
     * the server using default storage scheme and returns the file path.
     *
     * @param folders     name of folder.
     * @param file        a multipartFile instance.
     * @param reserveName whether reserve original filename for uploaded file.
     * @return
     */
    public String upload(Folders folders, MultipartFile file, boolean reserveName) {
        String folderPath = FileHelper.getRoot() + folders.name() + File.separatorChar;
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        String originName = file.getOriginalFilename();
        String[] strings = originName.split("\\.");
        String filePath = folderPath;
        if (reserveName) {
            filePath += file.getOriginalFilename();
        } else {
            filePath += format.format(new Date()) + "." + strings[1];
        }
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
