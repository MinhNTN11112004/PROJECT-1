package com.TicTacToe.TicTacToe;

import com.TicTacToe.GetAndSetHighScore;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static com.TicTacToe.JFrameMain.jFrame;


@SuppressWarnings("serial")
public class Play2Players extends JFrame {
    public static int newRow =3;
    protected Seed PlayerReRun;
    public static int rowPreSelected = -1;
    public static int colPreSelected = -1;
    public static int rowPreDiLai;
    public static int colPreDiLai;
    private int winsPlayer1 = 0;
    private int winsPlayer2 = 0;
    private int losesPlayer1=0;
    private int losesPlayer2=0;
    public static  int ROWS = 3;
    public static  int COLS = 3;
    public static String Player1Name;
    public static String Player2Name;
    public static boolean Player1TwoMove;
    public static int STEPS=0;
    public static  int CELL_SIZE = 100;
    public static  int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static  int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static  int GRID_WIDTH = 2;
    public static  int GRID_WIDHT_HALF = GRID_WIDTH / 2;
    public static  int CELL_PADDING = CELL_SIZE / 6;
    public static  int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static  int SYMBOL_STROKE_WIDTH = 8;

    private Image player1Avatar;
    private Image player2Avatar;
    // Các trạng thái trong game
    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }
    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }
    protected GameState currentState;  //trạng thái hiện tại trong game
    // các kí hiệu trong game(X,O,none)
    public enum Seed {
        EMPTY, CROSS, NOUGHT
    }
    protected Seed currentPlayer;  //người chơi hiện tại
    public Seed[][] board   ; // Bản đồ
    protected DrawCanvas canvas;
    protected JLabel statusBar;
    protected JPanel pnButton;
    protected Button btnDiLai;
    protected Button btnBoDiLai;
    /** Constructor to setup the game and the GUI components */
    public Play2Players(String name1, String name2) {
        STEPS=0;
        PlayGame(name1,name2);
    }
    // Set up lại kích cỡ bàn cờ.
    public void SetUpBoard(int row){
        ROWS = row;
        COLS = row;
        CELL_SIZE = (25/row) *30;
        CANVAS_WIDTH = CELL_SIZE * COLS;
        CANVAS_HEIGHT = CELL_SIZE * ROWS;
        CELL_PADDING = CELL_SIZE / 6;
        SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    }


    protected void PlayGame(String name1, String name2){
        try {
            player1Avatar = loadImage("D:\\gomoku\\Caro_java\\FinalSource\\TicTacToe\\TicTacToe\\.idea\\Screenshot\\1736341500149.jpg"); // Thay đổi đường dẫn
            player2Avatar = loadImage("D:\\gomoku\\Caro_java\\FinalSource\\TicTacToe\\TicTacToe\\.idea\\Screenshot\\mèo thần tài.jpg"); // Thay đổi đường dẫn
            SetUpBoard(newRow);
            Player1Name = name1;
            Player2Name = name2;
            canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
            // The canvas (JPanel) fires a MouseEvent upon mouse-click
            canvas.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    // vị trí ô được chọn (X hoặc 0 tương ứng)
                    int rowSelected = mouseY / CELL_SIZE;
                    int colSelected = mouseX / CELL_SIZE;
                    if (currentState == GameState.PLAYING) {
                        if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                                && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                            rowPreSelected = rowSelected;
                            colPreSelected = colSelected;
                            board[rowSelected][colSelected] = currentPlayer; // tạo nước đi
                            updateGame(currentPlayer, rowSelected, colSelected); // cập nhật trạng thái
                            // đổi người chơi
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                            STEPS++;
                        }
                        btnDiLai.setEnabled(true);
                        btnBoDiLai.setEnabled(false);
                    } else {       // game over
                        initGame(); // restart the game
                    }
                    repaint();  // Call-back paintComponent().
                }
            });

            // tạo thanh trạng thái thông báo
            statusBar = new JLabel("  ");
            statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
            statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

            //Thêm Button
            btnDiLai = new Button("Đi lại");
            btnDiLai.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
            btnDiLai.setEnabled(false);
            btnDiLai.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentState != GameState.PLAYING) return;
                    if (STEPS == 0) return;
                    rowPreDiLai = rowPreSelected;
                    colPreDiLai = colPreSelected;
                    PlayerReRun = board[rowPreSelected][colPreSelected];
                    currentPlayer = PlayerReRun;
                    board[rowPreSelected][colPreSelected] = Seed.EMPTY;
                    btnBoDiLai.setEnabled(true);
                    btnDiLai.setEnabled(false);
                    repaint();
                }
            });
            btnBoDiLai = new Button("Bỏ đi lại");
            btnBoDiLai.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
            btnBoDiLai.setEnabled(false);
            btnBoDiLai.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentState != GameState.PLAYING) return;
                    if (STEPS == 0 || PlayerReRun == null) return;
                    board[rowPreDiLai][colPreDiLai] = PlayerReRun;
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    btnBoDiLai.setEnabled(false);
                    repaint();
                }
            });
            pnButton = new JPanel();
            pnButton.setLayout(new FlowLayout(FlowLayout.CENTER));
            pnButton.add(btnDiLai);
            pnButton.add(btnBoDiLai);
            winsPlayer1 = GetAndSetHighScore.getWin(Player1Name);
            losesPlayer1 = GetAndSetHighScore.getLose(Player1Name);
            winsPlayer2 = GetAndSetHighScore.getWin(Player2Name);
            losesPlayer2 = GetAndSetHighScore.getLose(Player2Name);
            JPanel playerInfoPanel = new JPanel();
            playerInfoPanel.setLayout(new GridLayout(2, 2));
            playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));

            JLabel player1Label = new JLabel("Người chơi 1: " + Player1Name);
            JLabel player1wins = new JLabel("Thắng: "+ winsPlayer1);
            JLabel player1loses = new JLabel("Thua: "+ losesPlayer1);
            JLabel player2Label = new JLabel("Người chơi 2: " + Player2Name);
            JLabel player2wins = new JLabel("Thắng: "+ winsPlayer2);
            JLabel player2loses = new JLabel("Thua: "+ losesPlayer2);
            // Tạo JLabel với ảnh đã chỉnh kích cỡ
            ImageIcon player1Icon = new ImageIcon(player1Avatar);
            Image player1Image = player1Icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Kích thước 100x100
            JLabel player1AvatarLabel = new JLabel(new ImageIcon(player1Image));

            ImageIcon player2Icon = new ImageIcon(player2Avatar);
            Image player2Image = player2Icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Kích thước 100x100
            JLabel player2AvatarLabel = new JLabel(new ImageIcon(player2Image));

            playerInfoPanel.add(player1AvatarLabel);
            playerInfoPanel.add(player1Label);
            playerInfoPanel.add(player1wins);
            playerInfoPanel.add(player1loses);
            playerInfoPanel.add(player2AvatarLabel);
            playerInfoPanel.add(player2Label);
            playerInfoPanel.add(player2wins);
            playerInfoPanel.add(player2loses);

            Container cp = getContentPane();
            cp.setLayout(new BorderLayout());
            cp.add(canvas, BorderLayout.CENTER);
            cp.add(playerInfoPanel, BorderLayout.EAST);
            cp.add(statusBar, BorderLayout.PAGE_END);
            cp.add(pnButton, BorderLayout.PAGE_START);
            pack();
            setTitle("Tic Tac Toe 2 người");
            setLocationRelativeTo(null);
            setVisible(true);  //JFrame
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jFrame.setVisible(true);
                }
            });
            board = new Seed[ROWS][COLS];
            initGame();
        }catch(Exception e){
            e.printStackTrace(); // khởi tạo các components
        }
    }
    /** Initialize the game-board contents and the status */
    public void initGame() {
        btnDiLai.setEnabled(false);
        btnBoDiLai.setEnabled(false);
        STEPS = 0;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.EMPTY; // các ô rỗng
            }
        }
        currentState = GameState.PLAYING;
        currentPlayer = Seed.CROSS;
    }
    /** Update the currentState after the player with "theSeed" has placed on
     (rowSelected, colSelected). */
    public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
        if (hasWon(theSeed, rowSelected, colSelected)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
            if(theSeed == Seed.CROSS){
                try {
                    winsPlayer1++;
                    GetAndSetHighScore.ghiFile(GetAndSetHighScore.FILE_NAME,Player1Name,Player2Name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,Player1Name+" thắng rồi! Click chuột để chơi lại");
            }
            else {
                    try {
                        winsPlayer2++;
                        GetAndSetHighScore.ghiFile(GetAndSetHighScore.FILE_NAME,Player2Name,Player1Name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                JOptionPane.showMessageDialog(null, Player2Name + " thắng rồi! Click chuột để chơi lại");
                }
        } else if (isDraw()) {  // check for draw
            currentState = GameState.DRAW;
            JOptionPane.showMessageDialog(null,"Hòa rồi! Click chuột để chơi lại");
        }
    }
    /** Return true if it is a draw (i.e., no more empty cell) */
    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.EMPTY) {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no more empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing at
     (rowSelected, colSelected) */
    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
        return (board[rowSelected][0] == theSeed  // 3-in-the-row
                && board[rowSelected][1] == theSeed
                && board[rowSelected][2] == theSeed
                || board[0][colSelected] == theSeed      // 3-in-the-column
                && board[1][colSelected] == theSeed
                && board[2][colSelected] == theSeed
                || rowSelected == colSelected            // 3-in-the-diagonal
                && board[0][0] == theSeed
                && board[1][1] == theSeed
                && board[2][2] == theSeed
                || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == theSeed
                && board[1][1] == theSeed
                && board[2][0] == theSeed);
    }

    /**
     *  Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
     */
    public class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE); // nền trắng
            // đặc trưng các dòng kẻ
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
            }

            // đặc tính X và 0
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));  // Graphics2D only
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col] == Seed.CROSS) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col] == Seed.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }

            // Print status-bar message
            if (currentState == Play2Players.GameState.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                if (currentPlayer == Play2Players.Seed.CROSS) {
                    statusBar.setText("Lượt của "+Player1Name);
                } else {
                    statusBar.setText("Lượt của "+Player2Name);
                }
            } else if (currentState == Play2Players.GameState.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("Hòa rồi! Click chuột để chơi lại");
            } else if (currentState == Play2Players.GameState.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText(Player1Name+" thắng rồi! Click chuột để chơi lại");
            } else if (currentState == Play2Players.GameState.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText(Player2Name+" thắng rồi! Click chuột để chơi lại");
            }
        }
    }

    public boolean CheckAdjacent(int x, int y){
        if(x== rowPreSelected +1 && y== colPreSelected) return true;
        if(x== rowPreSelected -1 && y== colPreSelected) return true;
        if(x== rowPreSelected && y== colPreSelected +1) return true;
        if(x== rowPreSelected && y== colPreSelected -1) return true;
        if(x== rowPreSelected +1 && y== colPreSelected +1) return true;
        if(x== rowPreSelected -1 && y== colPreSelected -1) return true;
        if(x== rowPreSelected +1 && y== colPreSelected -1) return true;
        if(x== rowPreSelected -1 && y== colPreSelected +1) return true;
        //if(x>=3 || x<0 || y>=3 || y<0) return false;
        return false;
    }

    protected boolean CheckEmptyBoard(){
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] != Seed.EMPTY) {
                    return false; //nếu không tìm thấy ô nào trống trả về false
                }
            }
        }
        return true;
    }



}
