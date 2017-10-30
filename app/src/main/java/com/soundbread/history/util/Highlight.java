package com.soundbread.history.util;



import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Highlight {

    public static Pattern getQueryPattern(String query){
        if( query == null ){
            return null;
        }

        String        valtmp = removeAllSpecial(query).trim();
        String[]      saSearchMask = valtmp.split("[\\s]+");
        int           count = 0;
        StringBuffer  buffer = new StringBuffer(256);
        Pattern       pp = null;

        switch( saSearchMask.length ){
            case 0 :
                return null;
            case 1 :
                pp = Pattern.compile(saSearchMask[0], Pattern.CASE_INSENSITIVE);
                break;
            default :
                Arrays.sort(saSearchMask, String.CASE_INSENSITIVE_ORDER);
                buffer.append("(");
                for( int i = saSearchMask.length - 1 ; i >= 0 ; i-- ){
                    if( saSearchMask[i].equals("") ){
                        continue;
                    }
                    buffer.append(saSearchMask[i]);
                    if( i > 0 ){
                        buffer.append("|");
                    }
                    count++;
                }
                buffer.append(")");
                pp = Pattern.compile(buffer.toString(), Pattern.CASE_INSENSITIVE);
        }

        return pp;
    }

    public static String multi(String source, Pattern query){
        return multi(source, query, "<b>", "</b>");
    }

    public static String multi(String source, Pattern query, String begin, String end){
        if( source == null || query == null ){
            return "";
        }

        StringBuffer  buffer = new StringBuffer(256);
        Matcher m = query.matcher(source);

        while( m.find() ){
            m.appendReplacement(buffer, begin+m.group()+end);
        }
        m.appendTail(buffer);

        return buffer.toString();
    }

    public static String multi(String source, String query){
        return multi(source, query, "<b>", "</b>");
    }

    public static String multi(String source, String query, String begin, String end){
        String valtmp = removeAllSpecial(query).trim();
        String[] saSearchMask = valtmp.split("[\\s]+");
        int           count = 0;
        StringBuffer  buffer = new StringBuffer(256);
        Pattern       pp;

        switch( saSearchMask.length ){
            case 0 :
                return source;
            case 1 :
                pp = Pattern.compile(saSearchMask[0], Pattern.CASE_INSENSITIVE);
                break;
            default :
                Arrays.sort(saSearchMask, String.CASE_INSENSITIVE_ORDER);
                buffer.append("(");
                for( int i = saSearchMask.length - 1 ; i >= 0 ; i-- ){
                    if( saSearchMask[i].equals("") ){
                        continue;
                    }
                    buffer.append(saSearchMask[i]);
                    if( i > 0 ){
                        buffer.append("|");
                    }
                    count++;
                }
                buffer.append(")");
                pp = Pattern.compile(buffer.toString(), Pattern.CASE_INSENSITIVE);
        }

        if( count == 0 ){
            return source;
        }

        Matcher m = pp.matcher(source);
        buffer.setLength(0);

        while( m.find() ){
            m.appendReplacement(buffer, begin+m.group()+end);
        }
        m.appendTail(buffer);

        return buffer.toString();
    }

    public static String simple(String source, String query){
        return simple(source, query, "<b>", "</b>");
    }

    public static String simple(String source, String query, String begin, String end){
        return source.replaceAll(query, begin+query+end);
    }

    private static String removeAllSpecial(String value){
        if( value == null || value.length() == 0 ){
            return "";
        }
        char[] val = value.toCharArray();
        for( int i = 0 ; i < val.length ; i++ ){
            if( Character.isLetterOrDigit(val[i]) == false ){
                val[i] = ' ';
            }
        }
        return new String(val);
    }

    public static void main(String[] args){
        String value = "홍길동전 수호지전 대한민국";
        String query = "홍길     홍 (대한)";
        switch( args.length ){
            case 2 :
                query = args[1];
                value = args[0];
                break;
            case 1 :
                query = args[0];
                break;
            case 0 :
                break;
            default:
                System.out.println("USAGE: value query");
                return;
        }

        System.out.println("#VALUE: " + value);
        System.out.println("#QUERY: " + query);
        System.out.println("#MULTI: " + multi(value, query));
        System.out.println("#SIMPLE: " + simple(value, query));

        Pattern qp = getQueryPattern(query);
        System.out.println("#QPATTERN: " + multi(value, qp));
    }
}