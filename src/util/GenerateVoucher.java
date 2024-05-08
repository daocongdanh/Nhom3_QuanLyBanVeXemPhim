/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author daoducdanh
 */
public class GenerateVoucher {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public static Set<String> generatePromoCodes(String startStr, String endStr, int length, int numCodes) {
        Set<String> codes = new HashSet<>();
        while (codes.size() < numCodes) {
            StringBuilder codeBuilder = new StringBuilder();
            codeBuilder.append(startStr);
            for (int i = startStr.length(); i < length - endStr.length(); i++) {
                char randomChar = CHARACTERS.charAt(new Random().nextInt(CHARACTERS.length()));
                codeBuilder.append(randomChar);
            }
            codeBuilder.append(endStr);
            codes.add(codeBuilder.toString());
        }
        return codes;
    }
}
