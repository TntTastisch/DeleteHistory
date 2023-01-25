package de.tnttastisch.utils.log;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Logger {

    private File directory;
    private File logs;
    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;
    private FileOutputStream fileOut;
    private PrintStream printOut;

    public void createSystemLog() {
        try {
            directory = new File("logs");
            if (!(directory.exists())) {
                directory.mkdir();
            }
            logs = new File("logs/latest.log");
            if (!(logs.exists())) {
                logs.createNewFile();
            } else {
                fileOut = new FileOutputStream(logs);
                printOut = new PrintStream(fileOut);
                System.setOut(printOut);
                System.setErr(printOut);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.fillInStackTrace());
        } catch (IOException e) {
            System.err.println(e.fillInStackTrace());
        }
    }

    public void outPutLogFile() {
        try {
            System.setOut(originalOut);
            System.setErr(originalErr);
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(logs)));
            int i = 1;
            for (String line : lines) {
                if (i != lines.size()) {
                    System.out.println(timeValue() + line);
                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void archive() {
        try {
            int i = 1;
            String zipName = "logs/archive_" + dateValue() + "-" + i + ".zip";
            FileOutputStream fos = new FileOutputStream(zipName);
            CompressorOutputStream cos = new CompressorStreamFactory().createCompressorOutputStream("zip", fos);
            ZipOutputStream zos = new ZipOutputStream(cos);

            if (new File(zipName).exists()) {
                i = i + 1;
            }

            FileInputStream fis = new FileInputStream(logs);
            ZipEntry entry = new ZipEntry(logs.getName());
            zos.putNextEntry(entry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            fis.close();

            if (!(logs.exists())) {
                System.out.println("File not found: " + logs);
            }

            zos.closeEntry();
            fis.close();
            zos.close();
            fos.close();

            if (logs.exists()) {
                logs.delete();
            }
        } catch (IOException e) {
            System.err.println(e.fillInStackTrace());
        } catch (CompressorException e) {
            System.err.println(e.fillInStackTrace());
        }
    }

    public String dateValue() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public String timeValue() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }
}
