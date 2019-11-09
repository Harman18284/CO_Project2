import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    static double binToint(String binary){
        double count =0;
        for(int i=0;i<binary.length();i++){
            if(binary.charAt(binary.length()-1-i)=='1'){
                count=count+ Math.pow(2,i);
            }
        }
        return count;
    }

    static String addBinary(String a, String b)
    {
        String result = "";

        int s = 0;

        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0 || s == 1)
        {

            s += ((i >= 0)? a.charAt(i) - '0': 0);
            s += ((j >= 0)? b.charAt(j) - '0': 0);

            result = (char)(s % 2 + '0') + result;

            s /= 2;

            i--; j--;
        }

        return result;
    }

    public static void appendStrToFile(String fileName,
                                       String str)
    {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public static String twoscompliment(String str) {
        int flag = 0;
        StringBuffer s = new StringBuffer(str);
        for (int i = str.length() - 1; i >= 0; i--) {
            if (flag == 1) {
                if (str.charAt(i) == '0') {
                    s.replace(i, i + 1, "1");
                } else {
                    s.replace(i, i + 1, "0");
                }
            }

            if (s.charAt(i) == '1') {
                flag = 1;
            }

        }
        return s.toString();
    }

    public static String onesCompliment(String str) {
        StringBuffer s = new StringBuffer(str);
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                s.replace(i, i + 1, "1");
            } else {
                s.replace(i, i + 1, "0");
            }
        }

        return s.toString();
    }

    public static String intToBinary(int a,int bits) {
        String temp = Integer.toBinaryString(a);
        while(temp.length() !=bits){
            temp = "0"+temp;
        }
        return temp;
    }

    public static void divide(int dividend, int divisor){
        //no of bits
        int n=0;
        boolean negflag=false;

        if((dividend<0 && divisor>0) || (dividend>0 && divisor<0) ){
            dividend = Math.abs(dividend);
            divisor = Math.abs(divisor);
            negflag=true;
        }

        //This part of code decides the value of n...
        if(divisor>dividend){
            String temp = Integer.toBinaryString(divisor);
            n=temp.length();
        }
        else if(divisor<dividend){
            String temp = Integer.toBinaryString(dividend);
            n=temp.length();
        }
        else if(divisor==dividend){
            String temp = Integer.toBinaryString(divisor);
            n=temp.length();
        }

        int len=n;

        //divisor
        String M=intToBinary(divisor,n+1);
        String minusM=twoscompliment(M);
        //dividend
        String Q=intToBinary(dividend,n);

        //Accumulator
        String A =intToBinary(0,n+1);

        String restoreA = "";

//        System.out.println(M + Q + A);

        appendStrToFile("division.txt","n    M    A    Q");
        appendStrToFile("division.txt","\n");
        appendStrToFile("division.txt",n + " " + M + " " + A + " " + Q);
        appendStrToFile("division.txt","\n");

        while (n>0){

            //shift left AQ
            A = A.substring(1,A.length());
            A = A + Q.charAt(0);
            Q = Q.substring(1,Q.length());
            restoreA=A;
            appendStrToFile("division.txt",n + "      " + A + " " + Q+"?");
            appendStrToFile("division.txt","\n");


            // A = A + minusM
            A = addBinary(A,minusM);
            if(A.length()>len+1){
                A = A.substring(1,A.length());
            }
            appendStrToFile("division.txt","        " + A + " " + Q+"?");
            appendStrToFile("division.txt","\n");

            // most significant bit check
            char xx = A.charAt(0);
            if(xx=='0'){
                Q = Q + "1";
            }
            else if(xx=='1'){
                Q = Q + "0";
                A=restoreA;
            }
            appendStrToFile("division.txt","        " + A + " " + Q);
            appendStrToFile("division.txt","\n");

            //decrement of n
            n--;
        }

        //ending condition
        if(n==0){
            if(negflag){
                System.out.println("Quotient = " + Q + " --> " + "-" + binToint(Q));
            }
            else{
                System.out.println("Quotient = " + Q + " --> " + binToint(Q));
            }
            System.out.println("Remainder = " + A + " --> " + binToint(A));

        }


    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the numbers to be divided or multiplied..");
        int a = scan.nextInt();
        int b = scan.nextInt();

        System.out.println("Select the operation that you want to perform.");
        System.out.println("1 - Multiplication");
        System.out.println("2 - Division");

        int option=scan.nextInt();
        if(option==1){

        }
        else if(option==2){
            divide(a,b);
        }
    }
}
