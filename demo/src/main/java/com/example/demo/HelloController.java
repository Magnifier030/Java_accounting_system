package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.Closeable;
public class HelloController {
    @FXML
    private void initialize(){
        Show_ChoiceBox.getItems().addAll("請選擇你想查看的模式","歷年最盤","今年最盤","本月最盤","本周最盤");
        all_group=new Group[]{Overview,KeepAccount,Label,GraphAnalysis,AutorsWords};
        Label b = new Label("123456");
        Show_ChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Show_ChoiceBox_Change();
            }
        });
    }
    private int n1=0,n2=0;
    private String sign="";
    @FXML
    public TextField screen;
    @FXML
    public Label small_screen;
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
        System.out.println(Show_ChoiceBox.getValue());
        System.out.println("good");
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
    public Group[] all_group;
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