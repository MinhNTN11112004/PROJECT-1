package com.TicTacToe;

import com.TicTacToe.Campaign.Campaign;
import com.TicTacToe.Caro.ModChap1Nuoc2NguoiCaro;
import com.TicTacToe.Caro.ModChap1NuocVSMayCaro;
import com.TicTacToe.Caro.Play2PlayersCaro;
import com.TicTacToe.Caro.PlayWithAiCaro;
import com.TicTacToe.Connect4.ModChap1Nuoc2NguoiConnect4;
import com.TicTacToe.Connect4.ModChap1NuocVSMayConnect4;
import com.TicTacToe.Connect4.Play2PlayersConnect4;
import com.TicTacToe.Connect4.PlayWithAiConnect4;
import com.TicTacToe.TicTacToe.ModChap1Nuoc2Nguoi;
import com.TicTacToe.TicTacToe.ModChap1NuocVSMay;
import com.TicTacToe.TicTacToe.Play2Players;
import com.TicTacToe.TicTacToe.PlayWithAI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrameMain {
    public static JFrame jFrame;
    private JPanel JPanelMain;
    private JButton btnPlay2Players;
    private JButton btnPlayVSAI;
    private JTextField txtPlayer1Name;
    private JTextField txtPlayer2Name;
    private JButton btnExit;
    private JButton btnHighScore;
    private JCheckBox cbxModChap1Buoc;
    private JSpinner spinnerRow;
    private JButton btnCampaign;

    public JFrameMain() {
        // Cài đặt model cho spinner nhập dòng, cột.
        SpinnerModel spinnerModel =
                new SpinnerNumberModel(10, //initial value
                        3, //min
                        25, //max
                        1);//step
        spinnerRow.setModel(spinnerModel);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnPlayVSAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Player1Name = txtPlayer1Name.getText();
                if(Player1Name.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Bạn chưa nhập tên người chơi!!!");
                    return;
                }
                int row = (int)spinnerRow.getValue();
                if( row<3 || row >25){
                    JOptionPane.showMessageDialog(null,"Bạn phải nhập số dòng, cột nằm trong khoảng" +
                            " giá trị từ 3 đến 25!!!");
                    return;
                }
                // Chọn độ khó máy
                Object[] options1 = {"Dễ",
                        "Bình thường"};
                int resultLevel = JOptionPane.showOptionDialog(null,"Chọn độ khó","Chọn độ khó" +
                        "",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options1,options1[1]);


                if(cbxModChap1Buoc.isSelected()){
                    Object[] options = {"Người chơi 1",
                            "Máy"};
                    int result = JOptionPane.showOptionDialog(null,"Bạn muốn ai được chấp 1 bước","Chọn người chấp" +
                            "",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);

                    if(row>=5){
                        ModChap1NuocVSMayCaro.newRow =row;
                        ModChap1NuocVSMayCaro modChap1NuocVSMay = new ModChap1NuocVSMayCaro(Player1Name);
                        if (result == JOptionPane.YES_OPTION)
                            modChap1NuocVSMay.Player1TwoMove = true;
                        else modChap1NuocVSMay.Player1TwoMove = false;
                        if (resultLevel == JOptionPane.YES_OPTION) {
                            modChap1NuocVSMay.GameBot = PlayWithAI.Bot.EASY_BOT;
                        } else modChap1NuocVSMay.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                    }
                    else if(row==3) {
                        ModChap1NuocVSMay.newRow = row;
                        ModChap1NuocVSMay modChap1NuocVSMay = new ModChap1NuocVSMay(Player1Name);
                        if (result == JOptionPane.YES_OPTION)
                            modChap1NuocVSMay.Player1TwoMove = true;
                        else modChap1NuocVSMay.Player1TwoMove = false;
                        if (resultLevel == JOptionPane.YES_OPTION) {
                            modChap1NuocVSMay.GameBot = PlayWithAI.Bot.EASY_BOT;
                        } else modChap1NuocVSMay.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                    }
                    else if (row == 4){
                        ModChap1NuocVSMayConnect4.newRow =row;
                        ModChap1NuocVSMayConnect4 modChap1NuocVSMay = new ModChap1NuocVSMayConnect4(Player1Name);
                        if (result == JOptionPane.YES_OPTION)
                            ModChap1NuocVSMayConnect4.Player1TwoMove = true;
                        else ModChap1NuocVSMayConnect4.Player1TwoMove = false;
                        if (resultLevel == JOptionPane.YES_OPTION) {
                            ModChap1NuocVSMayConnect4.GameBot = PlayWithAI.Bot.EASY_BOT;
                        } else ModChap1NuocVSMayConnect4.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                    }

                }
                else {
                    if(row>=5){
                        if(resultLevel == JOptionPane.YES_OPTION){
                            PlayWithAiCaro.GameBot = PlayWithAI.Bot.EASY_BOT;
                        }
                        else PlayWithAiCaro.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                        PlayWithAiCaro.newRow =row;
                        PlayWithAiCaro heuristicBotCaro = new PlayWithAiCaro(Player1Name);
                    }
                    else if (row ==3){
                        if(resultLevel == JOptionPane.YES_OPTION){
                            PlayWithAI.GameBot = PlayWithAI.Bot.EASY_BOT;
                        }
                        else PlayWithAI.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                        PlayWithAI.newRow = row;
                        new PlayWithAI(Player1Name);
                    }
                    else if(row ==4){
                        if(resultLevel == JOptionPane.YES_OPTION){
                            PlayWithAiConnect4.GameBot = PlayWithAI.Bot.EASY_BOT;
                        }
                        else PlayWithAiConnect4.GameBot = PlayWithAI.Bot.HEURISTIC_BOT;
                        PlayWithAiConnect4.newRow =row;
                        PlayWithAiConnect4 heuristicBotCaro = new PlayWithAiConnect4(Player1Name);
                    }
                }
                jFrame.setVisible(false);
            }
        });
        btnPlay2Players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Player1Name = txtPlayer1Name.getText();
                String Player2Name = txtPlayer2Name.getText();
                if(Player1Name.isEmpty() || Player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Bạn chưa nhập đủ tên 2 người chơi!!!");
                    return;
                }
                int row = (int)spinnerRow.getValue();
                if( row<3 || row >25){
                    JOptionPane.showMessageDialog(null,"Bạn phải nhập số dòng, cột nằm trong khoảng" +
                            " giá trị từ 3 đến 25!!!");
                    return;
                }
                if(cbxModChap1Buoc.isSelected()){
                    Object[] options = {"Người chơi 1",
                            "Người chơi 2"};
                    int result = JOptionPane.showOptionDialog(null,"Bạn muốn ai được 1 bước","Chọn người chấp" +
                            "",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);

                    if(row>=5){
                        ModChap1Nuoc2NguoiCaro.newRow = row;
                        ModChap1Nuoc2NguoiCaro modChap1Nuoc2Nguoi = new ModChap1Nuoc2NguoiCaro(Player1Name, Player2Name);
                        if (result == JOptionPane.YES_OPTION)
                            modChap1Nuoc2Nguoi.Player1TwoMove = true;
                        else modChap1Nuoc2Nguoi.Player1TwoMove = false;
                    }
                    else if (row == 3){
                        ModChap1Nuoc2Nguoi.newRow = row;
                        ModChap1Nuoc2Nguoi modChap1Nuoc2Nguoi = new ModChap1Nuoc2Nguoi(Player1Name, Player2Name);
                        if (result == JOptionPane.YES_OPTION)
                            modChap1Nuoc2Nguoi.Player1TwoMove = true;
                        else modChap1Nuoc2Nguoi.Player1TwoMove = false;
                    }
                    else if(row ==4){
                        ModChap1Nuoc2NguoiConnect4.newRow = row;
                        ModChap1Nuoc2NguoiConnect4 modChap1Nuoc2Nguoi = new ModChap1Nuoc2NguoiConnect4(Player1Name, Player2Name);
                        if (result == JOptionPane.YES_OPTION)
                            modChap1Nuoc2Nguoi.Player1TwoMove = true;
                        else modChap1Nuoc2Nguoi.Player1TwoMove = false;
                    }

                }
                else{
                    if(row>=5){
                        Play2PlayersCaro.newRow = row;
                        new Play2PlayersCaro(Player1Name,Player2Name);
                    }
                    else if(row==3) {
                        Play2Players.newRow = row;
                        new Play2Players(Player1Name,Player2Name);
                    }
                    else if (row==4){
                        Play2PlayersConnect4.newRow =row;
                        new Play2PlayersConnect4(Player1Name,Player2Name);
                    }
                }
                jFrame.setVisible(false);
            }
        });
        btnHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighScoreForm highScore = new HighScoreForm();
                highScore.CreateAndShow();
                jFrame.setVisible(false);
            }
        });
        btnCampaign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                String name = txtPlayer1Name.getText();
                Campaign campaign = new Campaign(name);
                campaign.PlayCamp1();

            }
        });
    }

    public void CreateAndShow(){
        SpinnerModel spinnerModel =
                new SpinnerNumberModel(3, //initial value
                        3, //min
                        25, //max
                        1);//step
        spinnerRow.setModel(spinnerModel);
        jFrame =new JFrame("GOMOKU GAME");
        jFrame.setContentPane(new JFrameMain().JPanelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
