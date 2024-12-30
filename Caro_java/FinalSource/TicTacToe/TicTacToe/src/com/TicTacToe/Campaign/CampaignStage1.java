package com.TicTacToe.Campaign;

import com.TicTacToe.Caro.PlayWithAiCaro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.TicTacToe.JFrameMain.jFrame;

public class CampaignStage1 extends PlayWithAiCaro {
    public static String winner;
    /**
     * Constructor to setup the game and the GUI components
     *
     * @param name
     */
    protected CampaignStage1(String name) {
        super(name);
    }
    @Override
    protected void PlayGame(String name1, String name2) {
        SetUpBoard(newRow);
        Player1Name = name1;
        Player2Name ="Máy";
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        JOptionPane.showMessageDialog(null,"Chào mừng bạn đến với màn "+Campaign.stage);
        // The canvas (JPanel) fires a MouseEvent upon mouse-click
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // click vào vị trí muốn đi
                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;
                if (currentState == GameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                            && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                        //Lưu dòng cột đã chọn của người chơi
                        rowPreSelected = rowSelected;
                        colPreSelected = colSelected;
                        board[rowSelected][colSelected] = currentPlayer; // Make a move
                        updateGame(currentPlayer, rowSelected, colSelected); // update state
                        // Switch player
                        if(STEPS>2) {
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                        STEPS++;
                        if (currentPlayer == Seed.NOUGHT && currentState == GameState.PLAYING) {
                            if(STEPS == 3) {
                                runFirst(board,1,Seed.NOUGHT);
                                updateGame(currentPlayer, rowSelected, colSelected); // update state
                                // Switch player
                                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                            }
                            else {
                                HeuristicBot botRun = new HeuristicBot(ROWS, COLS, Seed.NOUGHT, Seed.CROSS);
                                String run = botRun.getPoint(board);
                                String[] splStr = run.split(" ");
                                rowSelected = Integer.parseInt(splStr[0]);
                                colSelected = Integer.parseInt(splStr[1]);
                                if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                                        && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                                    //Lưu dòng cột đã chọn của máy
                                    rowBotPreSelected = rowSelected;
                                    colBotPreSelected = colSelected;
                                    board[rowSelected][colSelected] = currentPlayer; // Make a move
                                    updateGame(currentPlayer, rowSelected, colSelected); // update state
                                    // Switch player
                                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                                }
                            }
                        }

                    }
                } else if(currentState == GameState.DRAW) {       // game over
                    initGame(); // restart the game
                }
                else if(currentState == GameState.NOUGHT_WON){
                    dispose();
                    jFrame.setVisible(true);
                }
                else if(currentState == GameState.CROSS_WON){
                    dispose();
                    new CampaignStage2(winner);
                }
                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.setVisible(true);
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        btnDiLai = new Button("Đi lại");
        btnDiLai.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        btnDiLai.setVisible(false);

        btnBoDiLai = new Button("Bỏ đi lại");
        btnBoDiLai.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        btnBoDiLai.setVisible(false);
        //Tạo label
        JLabel jLabel = new JLabel("Campaign màn "+Campaign.stage);
        pnButton = new JPanel();
        pnButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnButton.add(jLabel);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);
        cp.add(pnButton, BorderLayout.PAGE_START);
        pack();  // đóng gói các components
        setTitle("Campaign màn "+Campaign.stage);
        setLocationRelativeTo(null);
        setVisible(true);  // show this JFrame
        board = new Seed[ROWS][COLS];
        initGame();
    }
    @Override
    public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
        if (hasWon(theSeed, rowSelected, colSelected)) {  // ktra đk thắng
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
            if(theSeed == Seed.CROSS){
                winner = Player1Name;
                int nexStage =  Campaign.stage +1;
                JOptionPane.showMessageDialog(null,Player1Name+" thắng màn " + Campaign.stage+ " rồi! Click chuột để chơi tiếp màn "+nexStage);
                Campaign.stage++;
            }
            else {
                winner = "nought";
                JOptionPane.showMessageDialog(null, Player1Name + " đã thua campiagn rồi");
            }
        } else if (isDraw()) {  //ktra đk hòa
            currentState = GameState.DRAW;
            JOptionPane.showMessageDialog(null,"Hòa rồi! Click chuột để chơi lại");
        }
    }
}
