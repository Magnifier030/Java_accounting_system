package Person;

import Account.Account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Person{
    public Account[] accounts;
    public String maximum_date="0-0-0";
    public String minimum_date="9999-9999-9999";
    public Person(){};

    private int line_count(){
        try{
            File f = new File("./src/Account/account.txt");
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            int count=0;
            while((fr.readLine())!=null){
                count++;
            }
            return count;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    private Boolean date_correct(String ndate,String ldate,String sdate){
        String[] x = ndate.split("-");
        int ny = Integer.parseInt(x[0]),nm = Integer.parseInt(x[1]),nd = Integer.parseInt(x[2]);
        String[] y = ldate.split("-");
        int hy = Integer.parseInt(y[0]),hm = Integer.parseInt(y[1]),hd = Integer.parseInt(y[2]);
        String[] z = sdate.split("-");
        int ly = Integer.parseInt(z[0]),lm = Integer.parseInt(z[1]),ld=Integer.parseInt(z[2]);
        if(ny<=hy && ny>=ly && nm<=hm && nm >=lm && nd<=hd && nd>=ld){
            return true;
        }
        return false;
    }
    public Account minimun_value(String ldate,String sdate){
        int min=0;
        Account min_a = new Account("","","","0","");
        for (int i =0;i<accounts.length;i++){
            int temp = Integer.parseInt(accounts[i].dollor);
            if(temp>0){
                continue;
            }
            if(!date_correct(accounts[i].date,ldate,sdate)){
                continue;
            }
            if(temp<min){
                min = temp;
                min_a = accounts[i];
            }
        }
        return min_a;
    }
    public void compare_date(String date){
        String[] x = date.split("-");
        int ny = Integer.parseInt(x[0]),nm = Integer.parseInt(x[1]),nd = Integer.parseInt(x[2]);
        String[] y = maximum_date.split("-");
        int hy = Integer.parseInt(y[0]),hm = Integer.parseInt(y[1]),hd = Integer.parseInt(y[2]);
        String[] z = minimum_date.split("-");
        int ly = Integer.parseInt(z[0]),lm = Integer.parseInt(z[1]),ld=Integer.parseInt(z[2]);
        if(!(ny<=hy && nm<=hm && nd <=hd)){
            maximum_date=date;
        }
        else if(!(ny>=ly && nm>=lm && nd>=ld)){
            minimum_date = date;
        }
    }
    public int calculate_total(int mode , String ldate,String sdate){
        int revenue =0,loss=0,total=0;
        for(int i =0;i<accounts.length;i++){
            if(!date_correct(accounts[i].date,ldate,sdate)){
                continue;
            }
            int temp = Integer.parseInt(accounts[i].dollor);
            if(temp>0){
                revenue+=temp;
            }
            else{
                loss+=temp;
            }
            total+=temp;
        }
        switch (mode){
            case 0:
                return revenue;
            case 1:
                return loss;
            default:
                return total;
        }
    }
    public int calculate_total(int mode){
        int revenue =0,loss=0,total=0;
        for(int i =0;i<accounts.length;i++){
            int temp = Integer.parseInt(accounts[i].dollor);
            if(temp>0){
                revenue+=temp;
            }
            else{
                loss+=temp;
            }
            total+=temp;
        }
        switch (mode){
            case 0:
                return revenue;
            case 1:
                return loss;
            default:
                return total;
        }
    }
    public String show_accounts(){
        String text ="";
        for(int i =0;i<accounts.length;i++){
            text=String.format("編號:%d\n%s %s\n標籤:%s\n花費:%s\n備註:%s\n\n",i+1,accounts[i].date,accounts[i].time,accounts[i].type,accounts[i].dollor,accounts[i].text)+text;
        }
        return text;
    }
    public void add_account(Account account){
        Account[] new_accounts = new Account[accounts.length+1];
        for(int i =0;i<accounts.length;i++){
            new_accounts[i]=accounts[i];
        }
        new_accounts[new_accounts.length-1] = account;
        this.accounts = new_accounts;
        account.record();
    }

    public void get_account(){
        try{
            File f = new File("./src/Account/account.txt");
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String str;
            Account[] a = new Account[line_count()];
            int index = 0;
            while((str = fr.readLine())!=null){
                String[] text = new String[5];
                int _index=0;
                for(String x :str.split("-xx-")){
                    text[_index]=x;
                    _index++;
                }
                Account x = new Account(text[0],text[1],text[2],text[3],text[4]);
                compare_date(x.date);
                a[index] = x;
                index++;
            }
            fr.close();
            this.accounts=a;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}