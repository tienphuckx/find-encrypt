import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
    static String TARGET_FILE_PATH = "D:\\phuc\\dev\\java\\java_invisible_encrypt\\target-area\\target.txt";
    static String RESULT_FILE_PATH = "D:\\phuc\\dev\\java\\java_invisible_encrypt\\result-area\\result.txt";
    static String DECRYPT_FILE_PATH = "D:\\phuc\\dev\\java\\java_invisible_encrypt\\decrypt\\message.txt";

    static String readFileContent(String targetFilePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(targetFilePath)));        
    }

    static String convertStringBinaryContent(String targetStringContent) {
        StringBuilder binaryContent = new StringBuilder();
        for(char character : targetStringContent.toCharArray()) {
            String charAsBinaryString = Integer.toBinaryString(character);
            // Pad with leading zeros to ensure it's 8 bits long
            while (charAsBinaryString.length() < 8) {
                charAsBinaryString = "0" + charAsBinaryString;
            }
            binaryContent.append(charAsBinaryString);
        }
        return binaryContent.toString();
    }

    static String convertToInvisibleString(String binaryString) {
        StringBuilder invisibleString = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i++) {
            // Add SPACE for '0' and TAB for '1'
            if (binaryString.charAt(i) == '0') {
                invisibleString.append(" "); // space for 0
            } else if (binaryString.charAt(i) == '1') {
                invisibleString.append("\t"); // tab for 1
            }
        }
        return invisibleString.toString();
    }

    static void printInvisibleString(String invisibleString) {
        for(char invisibleCharacter : invisibleString.toCharArray()){
            if (invisibleCharacter == ' ') {
                System.out.print("SPACE_");
            } else if (invisibleCharacter == '\t') {
                System.out.print("TAB_");
            }
        }
    }

    static void writeInvisibleStringToResultFile(String resultFilePath, String invisibleString) throws IOException {
        Files.write(Paths.get(resultFilePath), invisibleString.getBytes());
    }

    static String loadInvisibleContent(String inviFilePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(inviFilePath)));
    }

    static String convertInvisibleContentToBinaryLine(String inviContent) {
        StringBuilder binaryLine = new StringBuilder();
        for(char character : inviContent.toCharArray()) {
            if(character == ' ') {
                binaryLine.append("0");
            }else{
                binaryLine.append("1");
            }
        }
        return binaryLine.toString();
    }

    static String convertBinaryLineToPlaintext(String binaryLine) {
        StringBuilder textContent = new StringBuilder();
        // Process each 8-bit binary chunk
        for (int i = 0; i < binaryLine.length(); i += 8) {
            String byteStr = binaryLine.substring(i, i + 8);
            char character = (char) Integer.parseInt(byteStr, 2); // Convert binary to char
            textContent.append(character);
        }
        return textContent.toString();
    }

    static void writePlainTextToDecryptMessage(String decryptFilePath, String decryptMessage) throws IOException{
        Files.write(Paths.get(decryptFilePath), decryptMessage.getBytes());
    }

    public static void main(String[] args) {
        try {
            //ENCRYPT
            String targetStringContent = readFileContent(TARGET_FILE_PATH);
            String targetStringBinary = convertStringBinaryContent(targetStringContent);
            String invisibleString = convertToInvisibleString(targetStringBinary);
            writeInvisibleStringToResultFile(RESULT_FILE_PATH, invisibleString);

            //DECRYPT
            String inviContent = loadInvisibleContent(RESULT_FILE_PATH);
            String binaryLine = convertInvisibleContentToBinaryLine(inviContent);
            String decryptContent = convertBinaryLineToPlaintext(binaryLine);
            writePlainTextToDecryptMessage(DECRYPT_FILE_PATH ,decryptContent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}