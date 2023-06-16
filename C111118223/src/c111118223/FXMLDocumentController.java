/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c111118223;

import Account.Account;
import Person.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


/**
 *
 * @author 99027
 */
public class FXMLDocumentController {
    
    @FXML
    private void initialize(){
        Show_ChoiceBox.getItems().addAll("請選擇你想查看的模式","歷年最盤","今年最盤","本月最盤","本周最盤");
        Show_ChoiceBox.setValue(0);
        account_type_add();
        all_group=new Group[]{Overview,KeepAccount,Label,GraphAnalysis,AutorsWords};
        Label b = new Label("123456");
        Show_ChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Show_ChoiceBox_Change();
            }
        });
        revenue_or.getItems().addAll("收益","費損");
        myself.get_account();
        overview_update();
    }
    private void account_type_add(){
       try {
           File f = new File("./src/lable/lable.txt");
           BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
           String str;
           while ((str = fr.readLine()) != null) {
               account_type.getItems().add(str);
           }
           fr.close();
       }
       catch(Exception e){
            e.printStackTrace();
       }

    }
    @FXML
    public TextField lable_input;
    @FXML
    public void add_lable(){
        try{
            File f = new File("./src/lable/lable.txt");
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));
            fw.append(String.format("%s\n",lable_input.getText()));
            fw.flush();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        account_type_add();
        lable_input.setText("");
    }
    @FXML
    public Label overview_01_date;
    @FXML
    public Label overview_01_total;
    @FXML
    public Label overview_01_revenue;
    @FXML
    public Label overview_01_loss;
    @FXML
    public Label overview_02_dollor;
    @FXML
    public Label overview_02_detail;
    @FXML
    public Label overview_03_date;
    @FXML
    public Label overview_03_total;
    @FXML
    public Label overview_03_revenue;
    @FXML
    public Label overview_03_loss;
    private int days(int m){
        int[] days = {31,0,31,30,31,30,31,31,30,31,30,31};
        DateFormat D = new SimpleDateFormat("yyyy");
        int Y = Integer.parseInt(D.format(new Date()));
        if(Y%4==0 && Y%100 !=0 || Y%400==0){
            days[1] = 29;
        }
        else {
            days[1] =28;
        }
        return days[m-1];
    }
    @FXML
    private void overview_update(){
        all_text_area.setText(myself.show_accounts());
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String[] date = d.format(new Date()).split("-");
        overview_01_date.setText(String.format("%s-%s(%s/01~%s/%d)",date[0],date[1],date[1],date[1],days(Integer.parseInt(date[1]))));
        overview_01_revenue.setText(String.format("收益:%s",myself.calculate_total(0,String.format("%s-%s-31",date[0],date[1]),String.format("%s-%s-01",date[0],date[1]))));
        overview_01_loss.setText(String.format("負債:%s",myself.calculate_total(1,String.format("%s-%s-31",date[0],date[1]),String.format("%s-%s-01",date[0],date[1]))));
        overview_01_total.setText(String.format("%s",myself.calculate_total(2,String.format("%s-%s-31",date[0],date[1]),String.format("%s-%s-01",date[0],date[1]))));
        if(Integer.parseInt(overview_01_total.getText())>0){
            overview_01_total.setText("收益:+"+overview_01_total.getText());
        }
        overview_03_revenue.setText(String.format("收益:%s",myself.calculate_total(0)));
        overview_03_loss.setText(String.format("負債:%s",myself.calculate_total(1)));
        overview_03_total.setText(String.format("%s",myself.calculate_total(2)));
        if(Integer.parseInt(overview_03_total.getText())>0){
            overview_03_total.setText("收益:+"+overview_03_total.getText());
        }
        overview_03_date.setText(String.format("%s~%s 累計",myself.minimum_date,myself.maximum_date));
    }
    @FXML
    public ChoiceBox revenue_or;
    @FXML
    public TextArea all_text_area;
    private Person myself = new Person();

    @FXML
    public Group add_new_index;
    @FXML
    public Group add_new_child_index;
    @FXML
    public Group edit_index;
    @FXML
    public Group index_home_page;
    @FXML
    public void index_change_page(ActionEvent e){
        Button b = (Button) e.getSource();
        String x = b.getText();
        System.out.println(b.getTextFill());
        add_new_index.setVisible(false);
        add_new_child_index.setVisible(false);
        edit_index.setVisible(false);
        index_home_page.setVisible(false);
        if(x.equals("新增標籤")){
            add_new_index.setVisible(true);
        }
        else if(x.equals("新增小標籤")){
            add_new_child_index.setVisible(true);
        }
        else if(x.equals("返回")){
            index_home_page.setVisible(true);
        }
        else{
            edit_index.setVisible(true);
        }
    }


    /**
     * calculator
     */
    private Account[] accounts = new Account[0];
    private int n1=0,n2=0;
    private String sign="";
    @FXML
    public DatePicker account_date;
    @FXML
    public TextField account_time;
    @FXML
    public TextField account_text;
    @FXML
    public ChoiceBox account_type;
    @FXML
    public TextField screen;
    @FXML
    public Label small_screen;
    @FXML
    protected void add_account(){
        if(!screen.getText().equals("")){
            String type = "無";
            if(account_type.getValue()!=null){
                type=account_type.getValue().toString();
            }
            if(revenue_or.getValue()==null){
                revenue_or.setValue(0);
            }
            if(account_date.getValue()==null){
                account_date.setValue(LocalDate.now());
            }
            if(account_time.getText().equals("")){
                DateFormat d = new SimpleDateFormat("HH:mm:ss");
                account_time.setText(d.format(new Date()));
            }
            if(account_text.getText().equals("")){
                account_text.setText("無");
            }
            String dollor =screen.getText();
            if(!revenue_or.getValue().equals("收益")) {
                dollor="-"+dollor;
            }
            System.out.printf("%s %s %s %s %s\n",type,account_time.getText(),account_date.getValue().toString(),dollor,account_text.getText());
            Account a = new Account(type,account_time.getText(),account_date.getValue().toString(),dollor,account_text.getText());
            myself.add_account(a);
            account_date.setValue(null);
            screen.setText("");
            small_screen.setText("=");
            account_text.setText("");
            account_time.setText("");
            n1=0;
            n2=0;
            sign="";
        }
    }
    @FXML
    protected void add(ActionEvent e){
        Button b = (Button) e.getSource();
        System.out.println(b.getText());
        if(n2!=0){
            processN2(b.getText());
        }
        else {
            processN1(b.getText());
        }
    }
    @FXML
    protected void calculate(){
        System.out.printf("%d %d\n",n1,n2);
        if(n1==0||n2==0){
            return;
        }

        switch (sign){
            case "+":
                screen.setText(String.format("%s",n1+n2));
                break;
            case "-":
                screen.setText(String.format("%s",n1-n2));
                break;
            case "*":
                screen.setText(String.format("%s",n1*n2));
                break;
            case "/":
                screen.setText(String.format("%s",n1/n2));
                break;
        }
    }
    private void processN2(String t){
        System.out.println("n2");
        if(Character.isDigit(t.charAt(0))){
            System.out.println("dight");
            small_screen.setText(small_screen.getText().substring(0,small_screen.getText().length()-1)+t+"=");
            if(t.equals("00")){
                n2*=10;
            }
            n2=n2*10+Integer.parseInt(t);
        }
        else if (t.equals("<")) {
            System.out.println("delete");
            small_screen.setText(small_screen.getText().substring(0,small_screen.getText().length()-2)+"=");
            if(screen.getText().length()<=1) {
                n2 = 0;
                screen.setText("");
                return;
            }
            n2/=10;
        }
        calculate();
    }
    private void processN1(String t){
        if(Character.isDigit(t.charAt(0))){
            System.out.println("dight");
            screen.setText(screen.getText()+t);
            small_screen.setText(small_screen.getText().substring(0,small_screen.getText().length()-1)+t+"=");
            if(sign==""){
                n1= Integer.parseInt(screen.getText());
            }
            else{
                n2=Integer.parseInt(screen.getText());
            }
        }
        else if (t.equals("<")) {
            System.out.println("delete");
            if(small_screen.getText().length()<=1){
                return;
            }
            small_screen.setText(small_screen.getText().substring(0,small_screen.getText().length()-2)+"=");
            if(!sign.equals("")){
                sign ="";
                screen.setText(String.format("%d",n1));
                return;
            }
            screen.setText(screen.getText().substring(0,screen.getText().length()-1));
            if(n1<10){
                n1=0;
            }
            else{
                n1=Integer.parseInt(screen.getText());
            }
        }
        else{
            System.out.println("sign");
            if(n1==0){
                return;
            }
            else if(sign==""){
                small_screen.setText(screen.getText().substring(0,screen.getText().length())+t+"=");
            }
            else{
                small_screen.setText(screen.getText().substring(0,screen.getText().length()-2)+t+"=");
            }
            sign=t;
            screen.setText("");

        }
        calculate();
    }
    /**
     * this is about second
     */
    @FXML
    public ChoiceBox Show_ChoiceBox;
    private String Show_ChoiceBox_old_value="請選擇你想查看的模式";
    private String Show_ChoiceBox_new_value;
    @FXML
    private void Show_ChoiceBox_Change(){
        Show_ChoiceBox_new_value=(String) Show_ChoiceBox.getValue();
        if((String) Show_ChoiceBox.getValue()=="請選擇你想查看的模式" ||Show_ChoiceBox_new_value==Show_ChoiceBox_old_value){
            return;
        }
        Account x = myself.accounts[0];
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String[] date = d.format(new Date()).split("-");
        System.out.printf("%s %s %s\n",date[0],date[1],date[2]);
        switch (Show_ChoiceBox_new_value){
            case "歷年最盤":
                x = myself.minimun_value(myself.maximum_date,myself.minimum_date);
                break;
            case "今年最盤":
                x = myself.minimun_value(String.format("%s-12-31",date[0]),String.format("%s-01-01",date[0]));
                break;
            case "本月最盤":
                x = myself.minimun_value(String.format("%s-%s-31",date[0],date[1]),String.format("%s-%s-01",date[0],date[1]));
                break;
            case "本周最盤":
                x = myself.minimun_value(String.format("%s-%s-%s",date[0],date[1],date[2]),String.format("%s-%s-%s",date[0],date[1],Integer.parseInt(date[2])-7));
                break;
        }
        overview_02_dollor.setText("花費:"+x.dollor);
        overview_02_detail.setText(String.format("%s %s\n標籤:%s\n備註:%s",x.date,x.time,x.type,x.text));
    }

    /**
     * This is about changing page.
     * The pages are  OverView、KeepAccount、Label、GraphAnalysis、AuthorsWords
     */
    @FXML
    public Group Overview;
    @FXML
    public Group KeepAccount;
    @FXML
    public Group Label;
    @FXML
    public Group GraphAnalysis;
    @FXML
    public Group AutorsWords;
    @FXML
    private Group[] all_group;
    /**
     * this is change page function
     */
    @FXML
    protected void Change_Page(MouseEvent e){
        Button b = (Button) e.getSource();
        String text = b.getText();
        for(int i =0;i<all_group.length;i++){
            all_group[i].setVisible(false);
        }
        switch (text){
            case "總攬":
                all_group[0].setVisible(true);
                overview_update();
                break;
            case "記帳":
                all_group[1].setVisible(true);
                break;
            case "標籤":
                all_group[2].setVisible(true);
                break;
            case "圖表分析":
                all_group[3].setVisible(true);
                break;
            case "作者想說的話":
                all_group[4].setVisible(true);
                break;
        }
    }

    @FXML
    public Button CloseButton;
    @FXML
    protected void CloseButtonAction(){
        Stage stage = (Stage)CloseButton.getScene().getWindow();
        stage.close();
    }
    
}
