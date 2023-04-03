package com.example.qr_go_gotta_scan_em_all;

import static java.lang.Math.pow;

import android.graphics.Bitmap;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**

 A class representing a Pokemon object.
 Contains information about the Pokemon's image, location, and ID.
 */
public class Pokemon implements Serializable {
    // Implement later


    private String ID;

//    private Bitmap img;



    /** Constructor for making a new Pokemon when a QR code is scanned
     *
     * @param rawName (the Raw value of the QR code)
     */
    public Pokemon(String rawName) {
        this.ID = calculateHash(rawName);
//        this.img = null;
    }
    /**
     * Constructor for creating an empty Pokemon object.
     */
    public Pokemon() {
        this.ID = null;
//        this.img = null;
    }

    /**
     * Constructor for creating a Pokemon object with ID.
     */

    /**
     * Initializes the Pokemon's ID using a given raw name.
     * @param rawName The raw name to be hashed to generate the ID.
     */
    public void initHash(String rawName){
        this.ID = calculateHash(rawName);
    }
    /**
     * Returns the generated name of the Pokemon based on its ID.
     * @return The name of the Pokemon.
     */
    public String getName() {

        return generateName();
    }

    /**
     * Returns the ID of the Pokemon.
     * @return The ID of the Pokemon.
     */
    public String getID() {
        return ID;
    }
    /**
     * Sets the ID of the Pokemon to a given value.
     * @param ID The new ID of the Pokemon.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the visual representation of the Pokemon.
     * @return The visual representation of the Pokemon.
     */
    public String visualReper(){
        String binary = hexToBinary(this.ID);
        List<List<String>> nameList = new ArrayList<List<String>>();
        nameList.add(Arrays.asList("/\\____/\\","/    \\"));
        nameList.add(Arrays.asList("| _  _ |","| 0  0 |"));
        nameList.add(Arrays.asList("|  ||  |","@  ||  @"));
        nameList.add(Arrays.asList("| ,`` ,|","|      |"));
        nameList.add(Arrays.asList("| `--` |","|/----\\|"));
        nameList.add(Arrays.asList("|______|","\\______/"));

        String name = "";
        for (int i = 0; i < nameList.size(); i++){
            String temp = "";
            char c = binary.charAt(i);
            temp+=c ;
            int index = Integer.parseInt(temp);
            name += nameList.get(i).get(index) +"\n";

        }
        return name;
    }

    public String visualReperN(){
        List<List<String>> nameList = new ArrayList<List<String>>();

        // There are 20 possible different pokemon bodies
        nameList.add(Arrays.asList(
                "      .\"`\".\n",
                "  .-./ _=_ \\.-.\n",
                " {  },(oYo),{ }}\n",
                " {{ |   \"   |} }\n",
                " { { \\(---)/  }}\n",
                " {{  }'-=-'{ } }\n",
                " { { }._:_.{  }}\n",
                " {{  } -:- { } }\n",
                " {_{ }`===`{  _}\n",
                "((((\\)     (/))))\n"
        ));

        nameList.add(Arrays.asList(
                " /\\                 /\\\n",
                "/ \\'._   (\\_/)   _.'/ \\\n",
                "|.''._'--(o.o)--'_.''.|\n",
                " \\_ / `;=/ \" \\=;` \\ _/\n",
                "   `\\__| \\___/ |__/`\n",
                "        \\(_|_)/\n",
                "         \" ` \"\n"
        ));

        nameList.add(Arrays.asList(
                " /=\\\"\"/=\\\n",
                "|=(0_0)=|__\n",
                " \\_\\ _/_/   )\n",
                "  /_/   _  /\\\n",
                "  | /|\\ || |\n",
                "    ~ ~  ~\n"
        ));
        nameList.add(Arrays.asList(
                "  /.--.\\  .-.  /.||.\\\n",
                " /.,   \\\\(0.0)// || \\\\\n",
                "/;`\";/\\ \\\\|m|//  ||  ;\\\n",
                "|:   \\ \\__`:`____||__:|\n",
                "|:    \\__ \\T/ (@~)(~@)|\n",
                "|:    _/|     |\\_\\/  :|\n",
                "|:   /  |     |  \\   :|\n",
                "|'  /   |     |   \\  '|\n",
                " \\_/    |_____|    \\_/\n"
        ));

        nameList.add(Arrays.asList(
                "(\\_/) \n",
                "(◕‿◕)  . / \\\n",
                "/ \" \\. /'.|\n",
                "\\___/\\ _/\n",
                "(_|_)/\n",
                "\" ` \"  \n"
                ));

        nameList.add(Arrays.asList(
                "        ___\n",
                "       (. .)\n",
                "   ,-.(.___.),-.\n",
                "  ( \\ \\ '--' / / )\n",
                "   \\ \\ / ,. \\ / /\n",
                "    ) '| || |' ( mrf\n",
                "OoO'- OoO''OoO -'OoO\n"
        ));
        nameList.add(Arrays.asList(
                "     _ _\n",
                "    (oTo)\n",
                " _.-( _ )-._\n",
                "`/`( '-' )`\\`\n",
                "   /'---'\\\n",
                " __\\     /__\n",
                " \\_/     \\_/\n"
        ));
        nameList.add(Arrays.asList(
                "   /\\`.   ,'/\\\n",
                "  //\\\\(0\"0)//\\\\\n",
                "//     ,^.    \\\\\n",
                "\\\\            //\n"
        ));

        nameList.add(Arrays.asList(
                "  /^ ^\\\n",
                " /(0 0)\\\n",
                " V\\ Y /V\n",
                "  / - \\\n",
                " /    |\n",
                "V__) ||\n"
        ));
        nameList.add(Arrays.asList(
                "  |_|\n",
                " (o o)\n",
                " /===\\\n",
                "(/===\\)\n",
                "  \\_/\n"
        ));

        nameList.add(Arrays.asList(
                "     (()__(()\n",
                "     /       \\ \n",
                "    ( /    \\  \\\n",
                "     \\(o o)   /\n",
                "     (_()_)__/ \\\n",
                "    / _,==.____ \\\n",
                "   (   |--|      )\n",
                "   /\\_.|__|'-.__/\\_\n",
                "  / (        /     \\\n",
                "  \\  \\      (      /\n",
                "   )  '._____)    /    \n",
                "(((____.--(((____/m\n"
        ));

        nameList.add(Arrays.asList(
                " /\\/\\\n",
                "(o.o)\n",
                " >^<\n",
                "/ \" \\. /'.|\n",
                "\\___/\\ _/\n",
                "(_|_)/\n",
                "\" ` \" \n"
        ));

        nameList.add(Arrays.asList(
                "  ^~^  ,\n",
                " (◕Y◕) )\n",
                " /   \\/ \n",
                "(\\|||/) \n"
        ));

        nameList.add(Arrays.asList(
                "((.))\n",
                "(o o)\n",
                " \\ /\n",
                "  ^ \n"
        ));

        nameList.add(Arrays.asList(
                "   | \\--/ |\n",
                "   \\ (0_0)|\n",
                "    \\==Y==/\n",
                "    /'-\"-'>\n",
                "  _/ < ; (;\n",
                " / ,_ |_|_\\\n",
                "( _,,)\\,,),)\n",
                "\\ '.___\n",
                " '-----'\n"
        ));

        nameList.add(Arrays.asList(
                "//\\\\\n",
                "|^ ^|\n",
                "(O|O)\n",
                "|  ~ \n",
                "\\ O /\n",
                " | |\n"
        ));

        nameList.add(Arrays.asList(
                "   .,--.\n",
                " .' __  \\\n",
                " | .._  |\n",
                " |(} {)|\n",
                " / /|  |.\n",
                "(_/ /____)\n",
                "  |_||\n",
                "    /'\n",
                "    //\n",
                "  .'''\\\n",
                " /\\:::/\\\n",
                "( /|::|\\\\\n",
                "_\\:|;;|{/_\n",
                "'.;|**|\\;,/\n",
                "   \\_ /\n",
                "   | ||\n",
                "   | ||\n",
                "   | ||\n",
                "   | ||\n",
                " ._| ||_.\n",
                ";,_.-._,;\n"
        ));

        nameList.add(Arrays.asList(
                " /----(  ^_^  )----\\\n",
                "|  {   \\('v')/   }  |\n",
                "|   {   /   \\   }   |\n",
                "|_)(   /\\   /\\   )(_|\n",
                "|)  (_ | \\|/  |_)  (|\n",
                "'     \"--^^^^--\"    '\n"
        ));

        nameList.add(Arrays.asList(
                " /\\_/\\\n",
                "/(o o)\\\n",
                "\\     /\n",
                " )   (\n",
                "/ / \\ \\\n",
                "(_) (_)\n"
        ));

        nameList.add(Arrays.asList(
                "{} {}\n",
                "(.Y.)\n",
                " \\_/\n",
                "/|||\\\n",
                "\\|||/\n",
                "i| |i\n",
                "`` ``\n"
        ));

        String hat =" [```]\n=======\n";
        String glasses = "(◪-◪)";

        // Determine the Pokemon
        char first = ID.charAt(0);
        char second = ID.charAt(1);
        char third = ID.charAt(2);
        char fourth = ID.charAt(3);
        char fifth = ID.charAt(4);
        char sixth = ID.charAt(5);
        boolean isHat = false;
        boolean isGlasses = false;
        List<String> pokemon;
        String name = "";

        // Mega or not
        if (third == '0' || third == '1' || third == '2' || third == '3' ||
                third == '4' || third == '5' || third == '6' || third == '7') {
            name += "Mega ";
        } else{

        }

        // Get the class of Pokemon firstly by checking the first digit of the ID
        // 0, 1, 2, 3 = Normal
        // 4, 5, 6, 7 = Electric
        // 8, 9, a, b = Ghost
        // c, d, e, f = Water

        List<String> normalPokemon = Arrays.asList("Meowth", "Evee", "Snorlax", "Ditto", "Kangaskhan", "Tauros", "Bidoof", "Null");
        List<String> electricPokemon = Arrays.asList("Pikachu", "Raichu", "Jolteon", "Raikou", "Bellibolt", "Electrode", "Electivire", "Zeraora");
        List<String> ghostPokemon = Arrays.asList("Gengar", "Dusknoir", "Cofagrigus", "Sinistea", "Spectrier", "Giratina", "Palossand", "Mimikyu");
        List<String> waterPokemon = Arrays.asList("Blastoise", "Psyduck", "Golduck", "Vaporeon", "Wailord", "Quaxly", "Wiglett", "Gyarados");

        if (first == '0' || first == '1' || first == '2' || first == '3'){
            // Handle Normal Names
            if (second == '0' || second == '1') {
                pokemon = nameList.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = nameList.get(0);
            } else if (second == '4' || second == '5') {
                pokemon = nameList.get(1);
            } else if (second == '6' || second == '7') {
                pokemon = nameList.get(1);
            } else if (second == '8' || second == '9') {
                pokemon = nameList.get(2);
            } else if (second == 'a' || second == 'b') {
                pokemon = nameList.get(2);
            } else if (second == 'c' || second == 'd') {
                pokemon = nameList.get(3);
            } else if (second == 'e' || second == 'f') {
                pokemon = nameList.get(3);
            }

        } else if (first == '4' || first == '5' || first == '6' || first == '7'){
            // Handle Electric names
            if (second == '0' || second == '1') {
                pokemon = electricPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = electricPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = electricPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = electricPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = electricPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = electricPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = electricPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = electricPokemon.get(7);
            }
        } else if (first == '8' || first == '9' || first == 'a' || first == 'b'){
            // Handle Ghost names
            if (second == '0' || second == '1') {
                pokemon = ghostPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = ghostPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = ghostPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = ghostPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = ghostPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = ghostPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = ghostPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = ghostPokemon.get(7);
            }
        } else{
            // Handle Water names
            if (second == '0' || second == '1') {
                pokemon = waterPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = waterPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = waterPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = waterPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = waterPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = waterPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = waterPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = waterPokemon.get(7);
            }
        }

        name += pokemon;

        // Cap or no cap
        if (fifth == '0' || fifth == '1' || fifth == '2' || fifth == '3' ||
                fifth == '4' || fifth == '5' || fifth == '6' || fifth == '7') {
            name += " with Cap";
            isHat = true;
        }
        // Sunglasses or no sunglasses
        if (sixth == '0' || sixth == '1' || sixth == '2' || sixth == '3' ||
                sixth == '4' || sixth == '5' || sixth == '6' || sixth == '7') {
            if(isHat){
                name += " and Glasses";
            } else{
                name += " with Glasses";
            }
            isGlasses = true;
        }

        if (isGlasses && isHat){
            // Sigma or not
            if (fourth == '0' || fourth == '1' || fourth == '2' || fourth == '3' ||
                    fourth == '4' || fourth == '5' || fourth == '6' || fourth == '7') {
                name += " **Sigma**";
            }
        }
        return name;

    }




    /** Simple Scoring function that calculates the score based on the number of characters.
     *  The score changes based on the hexadecimal representation of the SHA-256 encrypted string
     *  The score is calculated by finding consecutive repeating characters, converting the repeated
     *  char into a string and put it to the power of the number of the repeats of that char - 1
     * @return the long of the score

     **/
    public double getScore(){
        return calculateScore();
    }



    /**
     * Calculates the SHA-256 hash of a given name.
     * @param name The name to be hashed.
     * @return The SHA-256 hash of the name.
     */
    private String calculateHash(String name){
        // https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(name.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    /**
     * Converts a byte array to a hexadecimal string.
     * @param bytes The byte array to be converted.
     * @return The hexadecimal string representation of the byte array.
     */
    private static String bytesToHex(byte[] bytes) {
        // Source https://stackoverflow.com/questions/2817752/java-code-to-convert-byte-to-hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**

     Calculates a score for a given ID based on the consecutive characters in the ID.

     @return A double value representing the calculated score.
     */
    double calculateScore(){

        System.out.println(ID);
        char previousChar = 'G';
        char c;
        String consecutiveChar = "";
        double score = 0;
        for (int i = 0; i < ID.length(); i++){
            c = ID.charAt(i);
            if (i > 0){
                previousChar = ID.charAt(i - 1);
            }
            // Check if c is equal to the previous char if it is, then add it to the consecutiveChar
            if (c == previousChar){
                consecutiveChar += c;
            }

            else{
                if (!consecutiveChar.isEmpty()){
                    // calculate the score of the consecutive char
                    char x = consecutiveChar.charAt(0);
                    int base = 0;

                    // if the character is 0 then convert it to 20
                    if (x == '0'){
                        base = 20;
                    } else{
                        base = Integer.parseInt(String.valueOf(x),16);
                    }
                    score += pow(base,consecutiveChar.length());

                }
                consecutiveChar = "";
            }


        }
        return score;
    }

    /**

     Converts a hexadecimal string to a binary string.
     @param hex The hexadecimal string to be converted.
     @return A binary string representing the input hexadecimal string.
     */
    private String hexToBinary(String hex) {
        BigInteger i = new BigInteger(hex, 16);
        String bin = i.toString(2);
        return bin;
    }

    /**

     Generates a name based on the binary representation of the ID.

     @return A string representing the generated name.
     */
    protected String generateName(){
        char first = ID.charAt(0);
        char second = ID.charAt(1);
        char third = ID.charAt(2);
        char fourth = ID.charAt(3);
        char fifth = ID.charAt(4);
        char sixth = ID.charAt(5);
        boolean isHat = false;
        boolean isGlasses = false;
        String pokemon = "";
        String name = "";

        // Mega or not
        if (third == '0' || third == '1' || third == '2' || third == '3' ||
                third == '4' || third == '5' || third == '6' || third == '7') {
            name += "Mega ";
        } else{

        }

        // Get the class of Pokemon firstly by checking the first digit of the ID
        // 0, 1, 2, 3 = Normal
        // 4, 5, 6, 7 = Electric
        // 8, 9, a, b = Ghost
        // c, d, e, f = Water

        List<String> normalPokemon = Arrays.asList("Meowth", "Evee", "Snorlax", "Ditto", "Kangaskhan", "Tauros", "Bidoof", "Null");
        List<String> electricPokemon = Arrays.asList("Pikachu", "Raichu", "Jolteon", "Raikou", "Bellibolt", "Electrode", "Electivire", "Zeraora");
        List<String> ghostPokemon = Arrays.asList("Gengar", "Dusknoir", "Cofagrigus", "Sinistea", "Spectrier", "Giratina", "Palossand", "Mimikyu");
        List<String> waterPokemon = Arrays.asList("Blastoise", "Psyduck", "Golduck", "Vaporeon", "Wailord", "Quaxly", "Wiglett", "Gyarados");

        if (first == '0' || first == '1' || first == '2' || first == '3'){
            // Handle Normal Names
            if (second == '0' || second == '1') {
                pokemon = normalPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = normalPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = normalPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = normalPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = normalPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = normalPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = normalPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = normalPokemon.get(7);
            }

        } else if (first == '4' || first == '5' || first == '6' || first == '7'){
            // Handle Electric names
            if (second == '0' || second == '1') {
                pokemon = electricPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = electricPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = electricPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = electricPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = electricPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = electricPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = electricPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = electricPokemon.get(7);
            }
        } else if (first == '8' || first == '9' || first == 'a' || first == 'b'){
            // Handle Ghost names
            if (second == '0' || second == '1') {
                pokemon = ghostPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = ghostPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = ghostPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = ghostPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = ghostPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = ghostPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = ghostPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = ghostPokemon.get(7);
            }
        } else{
            // Handle Water names
            if (second == '0' || second == '1') {
                pokemon = waterPokemon.get(0);
            } else if (second == '2' || second == '3') {
                pokemon = waterPokemon.get(1);
            } else if (second == '4' || second == '5') {
                pokemon = waterPokemon.get(2);
            } else if (second == '6' || second == '7') {
                pokemon = waterPokemon.get(3);
            } else if (second == '8' || second == '9') {
                pokemon = waterPokemon.get(4);
            } else if (second == 'a' || second == 'b') {
                pokemon = waterPokemon.get(5);
            } else if (second == 'c' || second == 'd') {
                pokemon = waterPokemon.get(6);
            } else if (second == 'e' || second == 'f') {
                pokemon = waterPokemon.get(7);
            }
        }

        name += pokemon;

        // Cap or no cap
        if (fifth == '0' || fifth == '1' || fifth == '2' || fifth == '3' ||
                fifth == '4' || fifth == '5' || fifth == '6' || fifth == '7') {
            name += " with Cap";
            isHat = true;
        }
        // Sunglasses or no sunglasses
        if (sixth == '0' || sixth == '1' || sixth == '2' || sixth == '3' ||
                sixth == '4' || sixth == '5' || sixth == '6' || sixth == '7') {
            if(isHat){
                name += " and Glasses";
            } else{
                name += " with Glasses";
            }
            isGlasses = true;
        }

        if (isGlasses && isHat){
            // Sigma or not
            if (fourth == '0' || fourth == '1' || fourth == '2' || fourth == '3' ||
                    fourth == '4' || fourth == '5' || fourth == '6' || fourth == '7') {
                name += " **Sigma**";
            }
        }
        return name;
    }
    /**
     * Compresses the given Bitmap image to a byte array using the JPEG format with a specified compression quality.
     *
     * @param img The Bitmap image to be compressed.
     * @return A byte array representing the compressed image data.
     */
    private byte[] compressImage(Bitmap img){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

//    public void setImg(Bitmap img){
//        this.img = img;
//    }
//
//    public Bitmap getImg(){
//        return img;
//    }



}
