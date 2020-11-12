package org.project.common.utils;

import org.project.common.attachment.FileHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class to backup the <code>MySQL</code> database server.
 *
 * @author Zon-g
 */
@Component
public class Backups {

    private static final SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Value(value = "${backup.host}")
    private String host;

    @Value(value = "${backup.port}")
    private String port;

    @Value(value = "${spring.datasource.druid.username}")
    private String username;

    @Value(value = "${spring.datasource.druid.password}")
    private String password;

    @Value(value = "${backup.database}")
    private String database;

    private void check() {
        String backupRoot = FileHelper.getBackupRoot();
        File backup = new File(backupRoot);
        if (!backup.exists() || !backup.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            backup.mkdirs();
        }
    }

    private String defaultFilename() {
        return FileHelper.getBackupRoot() + defaultFormat.format(new Date()) + ".sql";
    }

    /**
     * Deletes a backup file with specified filename and return true when the backup
     * file is deleted successful.
     *
     * @param filename specified file
     * @return true if and only if the specified backup file is deleted successful;
     * otherwise, return false.
     */
    public boolean deleteBackupFile(String filename) {
        return FileHelper.deleteFile(FileHelper.getBackupRoot() + filename);
    }

    /**
     * Gets all the backup files of <code>MySQL</code> database server in the default
     * path and returns a list of backup file name.
     *
     * @return list of backup file name
     */
    public List<String> getBackupFiles() {
        return FileHelper.getFiles(FileHelper.getBackupRoot()).stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    /**
     * Backups native <code>MySQL</code> database server using default parameters
     * and returns an int value representing whether the backup process terminated
     * correctly.
     *
     * @param table tables to be backup, representing as an object of String; of null
     *              if all tables in the database are backup.
     * @return 0 if backup process exits correctly; otherwise, a non-zero value.
     */
    public int backup(String... table) {
        return backup(host, port, username, password, database, defaultFilename(), table);
    }

    /**
     * Backups <code>MySQL</code> database server with specified parameters and returns
     * an int value representing if the backup process terminated correctly.
     *
     * @param host     host of <code>MySQL</code> server
     * @param port     port of <code>MySQL</code> server
     * @param username username to access the <code>MySQL</code> server
     * @param password password to access the <code>MySQL</code> server
     * @param database database to be backup
     * @param filename backup file name
     * @param tables   tables in the specified database to be backup
     * @return 0 if backup process exits correctly; otherwise, a non-zero value.
     */
    public int backup(String host, String port,
                      String username, String password,
                      String database, String filename,
                      String... tables) {
        check();
        StringBuilder s = new StringBuilder("mysqldump");
        s.append(" -h ").append(host);
        s.append(" -P ").append(port);
        s.append(" -u ").append(username);
        s.append(" -p").append(password);
        s.append(" ").append(database).append(" ");
        for (String table : tables) s.append(table).append(" ");
        s.append("> ").append(filename);
        String[] commands = {"cmd", "/c", s.toString()};
        Process backupPro;
        int val = 0;
        try {
            backupPro = Runtime.getRuntime().exec(commands);
            val = backupPro.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * Rollbacks the <code>MySQL</code> database server with specified backup file and
     * default host, port, username password and database and returns an int value
     * representing if the rollback process terminated correctly.
     *
     * @param filename specified backup file for rollback
     * @return 0 if and only if the rollback process terminated correctly; otherwise, returns
     * a non-zero value
     */
    public int rollback(String filename) {
        String file = FileHelper.getBackupRoot() + filename;
        return rollback(host, port, username, password, database, file);
    }

    /**
     * Rollbacks the <code>MySQL</code> database server with specified parameters and
     * returns an int value representing if the roll back process has terminated correctly.
     *
     * @param host     host of <code>MySQL</code> server
     * @param port     port of <code>MySQL</code> server
     * @param username username to access the <code>MySQL</code> server
     * @param password password to access the <code>MySQL</code> server
     * @param database database to be rollback
     * @param file     backup file for rollback
     * @return 0 if and only if the rollback process terminated correctly; otherwise,
     * returns a non-zero value
     */
    public int rollback(String host, String port,
                        String username, String password,
                        String database, String file) {
        StringBuilder s = new StringBuilder("mysql");
        s.append(" -h ").append(host)
                .append(" -P ").append(port)
                .append(" -u ").append(username)
                .append(" -p").append(password)
                .append(" ")
                .append(database)
                .append(" < ")
                .append(file);
        String[] commands = {"cmd", "/c", s.toString()};
        Process process;
        int val = 0;
        try {
            process = Runtime.getRuntime().exec(commands);
            val = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return val;
    }

}
