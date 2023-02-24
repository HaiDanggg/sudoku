
package mysudoku;

import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.Font; 
import java.awt.GridLayout; // Thư viện xử lý lớp lưới hình chữ nhật
import java.awt.event.ActionEvent;    
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;          
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; // Thư viện xử lý thay đổi trạng thái của chuột
import javax.swing.BorderFactory;   
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; // Thư viện xử lý thông báo
import javax.swing.JPanel; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 
import javax.swing.Timer; // Thư viện xử lý thời gian

/**
 *
 * @author Trương Hải Đăng - B1906455
 */
public class Panal extends javax.swing.JPanel {
    MySudoku game;
    private Timer timer; 
    private JButton nbtn = new JButton("Trò chơi mới");
    private static JTextField[][] boxes;
    private JPasswordField pass = new JPasswordField("1");
    private JLabel label = new JLabel("      Thời gian : 00 : 00 : 00");
    private JLabel passLabel = new JLabel("             Mật khẩu");
    private JPanel[][] paneles;
    private JPanel center, bPanel, levelPanel; 
    private JButton nBtn, cBtn, eBtn, hardBtn, midBtn, easyBtn, solve, about; 
    private int[][] temp = new int[9][9]; // Khởi tạo ma trận 9*9 cho biến temp
    private int[][] grid = new int[9][9]; // Khởi tạo ma trận 9*9 cho biến grid
    private int counter = 0;
    
    /*-----------------Hàm khởi tạo các JTextField-------------*/
    public JTextField newTextField(){
        JTextField j = new JTextField(""); // Khởi tạo các JTextField rỗng
        j.setBorder(BorderFactory.createLineBorder(Color.decode("#ecbeaf")));
        j.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
        j.setHorizontalAlignment(JTextField.CENTER); // Căn chỉnh JTextField ở giữa
        /*---------------Xử lí sự kiện khi click chuột----------------*/
        j.addMouseListener(new MouseAdapter() { 
            // Xử lý khi chuột nhấn
            @Override
            public void mouseEntered(MouseEvent e){ 
                if(j.isEditable()){
                    ((JTextField) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.decode("#f9d565")));
                    ((JTextField) e.getSource()).setBackground(Color.decode("#f9d565"));
                }
            }
            // Xử lý khi chuột nhả
            @Override
            public void mouseExited(MouseEvent e){
                if(j.isEditable()){
                    ((JTextField) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.decode("#ecbeaf")));
                    ((JTextField) e.getSource()).setBackground(Color.white);
                }
            }          
         });
        /*------------Xử lí sự kiện khi nhấn bàn phím---------*/
        j.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (j.isEditable()) {
                    ((JTextField) e.getSource()).setForeground(Color.decode("#0c4"));
                } else {
                    ((JTextField) e.getSource()).setForeground(Color.black);
                }
            }
        });
        return j;
    }
    
    
    public Panal() {
        initComponents();
        /*-------------------Khung chính----------------*/
        center  = new JPanel(); 
        center.setLayout(new GridLayout(3,3)); 
        center.setBackground(Color.black); 
        setLayout(new BorderLayout());
        add(center); // Thêm vào khung chính
        
        boxes = new JTextField[9][9]; // Khởi tạo mảng 9*9 cho biến boxes
        paneles = new JPanel[3][3]; // Khởi tạo mảng 3*3 cho biến paneles
        passLabel.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        passLabel.setForeground(Color.black);
        label.setForeground(Color.black);
        label.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        label.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        /*-------Tạo khung 3*3----------*/
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                paneles[i][j] = new JPanel();
                paneles[i][j].setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
                paneles[i][j].setBackground(Color.red);
                paneles[i][j].setLayout(new GridLayout(3,3));
                center.add(paneles[i][j]);
                
            }
        }
        /*---------------Tạo JTextField trong mỗi hộp vuông-------------*/
        
        for(int n=0;n<9;n++){
            for(int i=0;i<9;i++){
                boxes[n][i] = newTextField();
                int fm = (n+1)/3;
                if((n+1)%3 > 0){
                    fm++;
                }
                int cm = (i+1)/3;
                if((i+1)%3 > 0){
                    cm++; 
                }
                paneles[fm - 1][cm - 1].add(boxes[n][i]); 
            }
        }
        
        /*--------------Thiết kế panal cho các nút----------*/
        bPanel = new JPanel();
        bPanel.setBackground(Color.decode("#98B2F5"));
        bPanel.setBorder(BorderFactory.createLineBorder(Color.black,6,true));
        bPanel.setLayout(new GridLayout(4, 3, 0, 20)); // Định dạng với 4 hàng, 3 cột, độ dài 20
        
       
        ActionListener action = new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent event){ 
                label.setText(TimeFormat(counter));
                counter++;
            }
        };
        /*----------Thiết kế panal cho nút "Trò chơi mới"---------*/
        nBtn = new JButton("Trò chơi mới");
        nbtn.setSize(20,50);
        timer = new Timer(1000, action);
        nBtn.addActionListener(new ActionListener(){ 
            
            @Override
            public void actionPerformed(ActionEvent e){ // Phương thức được gọi khi mỗi lần sự kiện xảy ra
                counter = 0;
                timer.start();
                restartGame();
                MySudoku.newGame();
            }
        });     
        /*---------Thiết kế panal cho nút "Kiểm tra +30s"---------*/
        cBtn = new JButton("Kiểm tra +30s");
        
        cBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        if(!boxes[i][j].isEditable()){
                            continue;
                        }else if(boxes[i][j].getText().equals(String.valueOf(grid[i][j]))){
                            boxes[i][j].setBackground(Color.decode("#50B589")); // Đúng => Màu xanh
                        }else if(boxes[i][j].getText().isEmpty()){
                            boxes[i][j].setBackground(Color.white);
                            continue;
                        }else{
                            boxes[i][j].setBackground(Color.red); // Sai => Màu đỏ
                        }
                    }
                }
                counter+=30;
            }
        });
        /*-----------Thiết kế panal cho nút "Thoát"-----------*/
        eBtn = new JButton("Thoát");
        
        eBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        /*--------------Thiết kế panal cho nút "Khó"--------*/
        easyBtn = new JButton("Khó");
        
        easyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                restartGame();  
                counter = 0;
                timer.start();
                MySudoku.setLevel(4);
                MySudoku.newGame();
            }
        });
        
        /*----------------Thiết kế Panal cho nút "Trung bình"------------*/
        midBtn = new JButton("Trung bình");
        
        midBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                restartGame();
                counter = 0;
                timer.start();
                MySudoku.setLevel(3);
                MySudoku.newGame();
            }
        });
        
        /*-----------Thiết kế panal cho nút "Dễ"---------------------*/
        hardBtn = new JButton("Dễ");
        hardBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                restartGame();
                counter = 0;
                timer.start();
                MySudoku.setLevel(2);
                MySudoku.newGame();
            }
        });
        
        /*---------Thiết kế panal cho nút "Dễ"----------*/
        solve = new JButton("Giải");
        solve.setBackground(Color.decode("#E62023"));
        solve.setForeground(Color.black);
        
        solve.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(pass.getText().equals("1")){
                    timer.stop();
                    counter = 0;
                    label.setText(TimeFormat(counter));
 
                    for(int i=0;i<9;i++){
                        for(int j=0;j<9;j++){
                            boxes[i][j].setText(String.valueOf(grid[i][j]));
                            boxes[i][j].setEditable(false); // Không cho phép chỉnh sữa
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(center, "Vui lòng nhập lại mật khẩu !!!");
                }
            }
        });
        
        /*----------Thiết kế panal cho nút "About"--------*/
        about = new JButton("About");
        
        about.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(center, "Sudoku có rất nhiều biến thể nhưng chỉ thay đổi về kích thước và số lượng ô trong trò chơi còn lối chơi cơ bản vẫn giữ nguyên.\n"  +
                    "Ở phiên bản chuẩn (bản gốc) chỉ có kích thước là 9x9 (ô nhỏ) và được chia thành 9 vùng, mỗi vùng có kích thước 3x3. \n"
                    + "Các vùng này được nhóm lại và phân tách với nhau bằng một viền đen đậm hơn so với các ô nhỏ.\n" + "\n" +
                    "Luật chơi của Sudoku là điền kín những ô còn lại với điều kiện:\n" + "\n" +
                    "-  Các hàng ngang: Phải có đủ các số từ 1 đến 9, không trùng số và không cần đúng thứ tự.\n" + "\n" +
                    "-  Các hàng dọc: Đảm bảo có đủ các số từ 1-9, không trùng số, không cần theo thứ tự.\n" + "\n" +
                    "-  Mỗi vùng 3 x 3: Phải có đủ các số từ 1-9 và không trùng số nào trong cùng 1 vùng 3 x3.");
            }
        });
        
        pass.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                ((JPasswordField) e.getSource()).setText("");
            }
        });
        
        /*--------Thêm nút cho Frame và Panel----------*/
        bPanel.add(hardBtn);   
        bPanel.add(midBtn);
        bPanel.add(easyBtn);
        bPanel.add(nBtn);   
        bPanel.add(cBtn);
        bPanel.add(eBtn);
        bPanel.add(passLabel);
        bPanel.add(pass);
        bPanel.add(solve);
        bPanel.add(label);
        bPanel.add(about);

        add(bPanel, "South"); // Thêm toàn bộ nút phía dưới
    }
    
    // Hàm đặt lại mảng các số 
public void setArray(int[][] grid, int[][] temp){
    for(int i=0;i<9;i++){
        for(int j=0;j<9;j++){
            this.temp[i][j]=temp[i][j];
            this.grid[i][j]=grid[i][j];
        }
    }
}

// Hàm thiết kế mặc nhiên cho các TextField
public void setText(){
    for(int i=0;i<9;i++){
        for(int j=0;j<9;j++){
            if(this.temp[i][j]!=0){
                boxes[i][j].setText(String.valueOf(this.temp[i][j]));
                boxes[i][j].setEditable(false);
                boxes[i][j].setBackground(Color.decode("#E6F5E6"));
            }else{
                boxes[i][j].setText("");
            }
        }
    }
}
 
// Hàm khởi động lại game
private static void restartGame(){
    for(int i=0;i<9;i++){
        for(int j=0;j<9;j++){
            boxes[i][j].setForeground(Color.black);
            boxes[i][j].setEditable(true); // Cho phép nhập dữ liệu
            boxes[i][j].setBackground(Color.white);
        }
    }
}

// Hàm định dạng chuỗi thời gian
private String TimeFormat(int count){
    int hours = count / 3600;
    int minutes = (count - hours*3600) / 60;
    int seconds = count - minutes * 60;
    return String.format("      Thoi gian: " + "%02d" ,hours)+ " : " + String.format("%02d",minutes) + " : " + String.format("%02d", seconds);
} 
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
 
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
