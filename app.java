
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tree.Tree;

import javax.swing.*;


public class app{
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
         System.out.print("Choose an algorithme number : \n");
         Map<Integer, String> Algorithmes = new HashMap<>();
         Algorithmes.put(1, "Huffman statique");
         Algorithmes.put(2, "Huffman dynamique");
        for (Integer i : Algorithmes.keySet()) {
            System.out.println(i + " : " + Algorithmes.get(i));
        }
        String KeyAlgo = scanner.nextLine();
        try
        {
            int _keyalgo = Integer.parseInt(KeyAlgo);
            if(_keyalgo == 2)
            {
                Map<Integer, String> Code = new HashMap<>();
                Code.put(1, "Compression");
                Code.put(2, "Decompression");
                for (Integer i : Code.keySet()) {
                    System.out.println(i + " : " + Code.get(i));
                }
                Scanner _scanner = new Scanner(System.in);
                String _CodeString = _scanner.nextLine();
                try{
                    int _Code = Integer.parseInt(_CodeString);
                    System.out.println( "Enter your message :");
                    Scanner __scanner = new Scanner(System.in);
                    String message = __scanner.nextLine();
                    if(_Code == 1)
                    {
                        System.out.println( Compress(message) );
                    }else
                    {
                        System.out.println( Decompress( message ));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(KeyAlgo + " is not a valid integer");
                    scanner.close();
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println(KeyAlgo + " is not a valid integer");
            scanner.close();
        }
    }

    public static String Compress(String S){
        Tree comp = new Tree ();
        String out = "";
        for(int i = 0 ; i < S.length() ; ++i){
            out = comp.InsertSymbol(S.charAt(i) ,out);
        }
        return out;
    }
    public static String Decompress(String S){
        String out ="";
        Tree decom = new Tree ();
        String x = ""; //don't care

        /*first symbol*/
        char symbol = decom.ShortCodeKey(S.substring(0 ,8) );
        x = decom.InsertSymbol(symbol ,x);
        out +=symbol;

        /*remaining*/
        int i=8; //cursor on the String
        Boolean flag = false;
        while(!flag){
            if (S.length()-i >=decom.NYT.code.length()){
                x = S.substring(i ,i+decom.NYT.code.length());
                i+=decom.NYT.code.length();
            }
            else{
                x=S.substring(i);
                i+=x.length();
            }

            if(decom.NYT.code.matches(x)){
                String Shortcode = S.substring(i,i+8);
                i+=8;
                symbol = decom.ShortCodeKey(Shortcode);
                out +=symbol;
                x = decom.InsertSymbol(symbol ,x);
            }

            else{
                while (true){
                    symbol = decom.getcharfromcode(x);
                    if (symbol !=' ')   {
                        x = decom.InsertSymbol(symbol ,x);
                        out +=symbol;
                        break;
                    }
                    else{
                        i--;
                        x=x.substring(0 ,x.length()-1);
                    }
                }
            }
            if (i == S.length()) flag = true;
        }
        return out;
    }
}