/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author daoducdanh
 */
public class FileManager {

    private String generateUniqueFileName(File file) {
        String fileName = file.getName();
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public String createFile(File file) {

        try {
            if (ImageIO.read(file) == null) {
                System.out.println("Không phải file ảnh");
                return null;
            }
            String fileName = generateUniqueFileName(file);
            
            Path pathGoc = file.toPath();
            Path pathDich = Paths.get(getClass().getResource("/img").toURI()).resolve(fileName); 
            Files.copy(pathGoc, pathDich); // Copy vào build -> img
            
            Path pathSrc = Paths.get("src/img").resolve(fileName);
            Files.copy(pathGoc, pathSrc); // Copy vào src -> img
            
            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String updateFile(File fileNew, String fileOld) {
        try {
            if (ImageIO.read(fileNew) == null) {
                System.out.println("Không phải file ảnh");
                return null;
            }
            
            // Xử lý bên build -> img
            Path anhMoi = fileNew.toPath();
            Path anhCu = Paths.get(getClass().getResource("/img").toURI()).resolve(fileOld);
            Files.copy(anhMoi, anhCu, StandardCopyOption.REPLACE_EXISTING);
            
            String fileMoi = generateUniqueFileName(fileNew);
            Path dich = Paths.get(getClass().getResource("/img").toURI()).resolve(fileMoi);
            Files.move(anhCu, dich, StandardCopyOption.REPLACE_EXISTING);
            
            // Xử lý bên src -> img
            Path pathSrc1 = Paths.get("src/img").resolve(fileOld);
            Files.copy(anhMoi, pathSrc1, StandardCopyOption.REPLACE_EXISTING);
            Path pathSrc2 = Paths.get("src/img").resolve(fileMoi);
            Files.move(pathSrc1, pathSrc2, StandardCopyOption.REPLACE_EXISTING);
            return fileMoi;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public boolean deleteFile(String fileName) {
//    	File file = new File(uploadDirectory + fileName);
//        if(file.exists()){
//            return  file.delete();
//        }
//        return false;
//    }
}
