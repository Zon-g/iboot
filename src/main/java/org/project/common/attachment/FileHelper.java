package org.project.common.attachment;

import org.project.entity.AttachmentEntity;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileHelper {

    private static final String applicationPath = System.getProperty("user.dir");

    private static final String attachmentFolder = "attachment";

    private static final String root;

    static {
        File app = new File(applicationPath), parent = app.getParentFile();
        root = parent.getPath() + File.separatorChar + attachmentFolder + File.separatorChar;
    }

    /* Gets the root path of attachment */
    public static String getRoot() {
        return root;
    }

    /* Gets the root path of attachment for avatar */
    public static String getAvatarRoot() {
        return root + Folders.Avatar.name() + File.separatorChar;
    }

    /* Gets the root path of attachment for MySQL database backup */
    public static String getBackupRoot() {
        return root + Folders.BackUp.name() + File.separatorChar;
    }

    /* Gets the root path of attachment for Mail */
    public static String getMailRoot() {
        return root + Folders.Mail.name() + File.separatorChar;
    }

    /**
     * Gets the all files in the specified folder and returns a list of filename.
     *
     * @param folder specified folder to be list
     * @return a list of filename
     */
    public static List<String> getFiles(String folder) {
        return Arrays.stream(Objects.requireNonNull(new File(folder).listFiles()))
                .map(File::getName)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a specified file and returns true if this operation is successful.
     *
     * @param filename specified file to be deleted
     * @return true if and only if delete operation succeed; otherwise, returns false.
     */
    public static boolean deleteFile(String filename) {
        File file = new File(filename);
        return file.delete();
    }

    public static List<AttachmentEntity> searchFiles() {
        List<AttachmentEntity> list = new LinkedList<>();
        Queue<String> queue = new LinkedList<String>() {{
            offer(getRoot());
        }};
        while (!queue.isEmpty()) {
            String path = queue.poll();
            File root = new File(path);
            File[] files = root.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                File file = files[i];
                if (file.getName().equals(".git") || file.getName().equals("LICENSE")) continue;
                if (file.isDirectory()) queue.offer(file.getAbsolutePath());
                else list.add(AttachmentEntity.of(file));
            }
        }
        return list;
    }

}
