package Account;

import java.io.*;

public class Account {
    public String type;
    public String time;
    public String date;
    public String dollor;
    public String text;
    public Account(String type,String time,String date,String dollor,String text){
        this.type =type;
        this.time = time;
        this.date = date;
        this.dollor=dollor;
        this.text = text;
    }
    public Account(){

    }

    public void type(){
        System.out.printf("%s %s %s %s %s %d\n",type,time,date,dollor,text);
    }
    public void record(){
        try{
            File f = new File("./src/Account/account.txt");
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));
            fw.append(String.format("%s-xx-%s-xx-%s-xx-%s-xx-%s\n",type,time,date,dollor,text));
            fw.flush();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}