package mysudoku;
import java.util.ArrayList; // Thư viện ArrayList
import java.util.Collections; // Thư viện thao tác trên đối tượng
import java.util.Random; // Thư viện xử lý số ngẫu nhiên
import javax.swing.JFrame; // Thư viện xử lý JFrame

/**
 *
 * @author Hai Dang
 */
public class MySudoku {

    static JFrame frame; 
    static Panal p;
    private static int[][] grid; 
    private static int[][] temp; 
    private static Random ran = new Random(); // Khai báo biến Random
    private static int level = 2; 
    public static void main(String[] args) {
        grid = new int [9][9]; // Khởi tạo ma trận cho biến grid
        temp = new int[9][9]; // Khởi tạo ma trận cho biến temp
        frame = new JFrame();
        frame.setResizable(false);
        frame.setLocation(320, 40); 
        frame.setSize(650,650);
        frame.setTitle("Suduko designed by Trương Hải Đăng - B1906455");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng toàn bộ frame khi nhấn nút Close
        p = new Panal();
        frame.setContentPane(p); 
        frame.setVisible(true)  ; // Cho phép hiển thị Frame
       
      //  newGame();
      //  getCellList(grid);
    }
    
    // Hàm khởi tạo trò chơi mới phát sinh số ngẫu nhiên(random) với ArrayList
    public static void newGame(){
        int k = 0;
        ArrayList<Integer> randomNum =  getRandomNum();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                grid[i][j]=0; // Khởi tạo các giá trị ban đầu là 0
                if(((j + 2) % 2) == 0 && ((i + 2) % 2) == 0){
                    grid[i][j] = randomNum.get(k);
                    k++;
                    if (k == 9){
                        k = 0;
                    }
                }
               // System.out.print("grid["+i+"]"+"["+j+"]=" +k +"\n");
            }
        }
      /*  System.out.print("|---------Mang phat sinh so ngau nhien ban dau--------------|"+"\n");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(grid[i][j]);
            }
            System.out.print("\n");
        }*/
        if(solveSudoku(grid)){
            System.out.print("OK !!!\n");
        }
        int rann = ran.nextInt(); // Lấy số ngẫu nhiên bất kì
        int c = 0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                temp[i][j] = 0; // Khởi tạo các giá trị của mảng temp bằng 0
                if(c<rann){
                    c++;
                    continue;
                }else{
                    rann = ran.nextInt(level); // Lấy tham số cấp độ
                    c=0;
                    temp[i][j] = grid[i][j];
                }
            }
        }
     /*   System.out.print("|---------Mang sau khi giai--------|\n");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(grid[i][j]);
            }
            System.out.print("\n");
        }*/
        p.setArray(grid,temp);
        p.setText();
       
        
    }
    
    public static boolean solveSudoku(int[][] grid){
        int[][] freeCellList = getCellList(grid);
        int k = 0;
        boolean check = false;
        
        while(!check){
            int i = freeCellList[k][0];
            int j = freeCellList[k][1];
            // Nếu phần tử bằng 0 => gán bằng 1 để kiểm tra đầu tiên
            if(grid[i][j]==0){
                grid[i][j]=1;
            }
            // Kiểm tra số 1 có tồn tại trong hàng, cột, hộp hay không
            if(isAvaliable(i,j,grid)){
                // Nếu tổng số phần tử rỗng bằng k+1 => Số đã được giải  
                if(k+1==freeCellList.length){
                    check = true;
                }
                else{
                    k++;
                }
            }
            // Tăng phần tử lên 1
            else if(grid[i][j]<9){
                grid[i][j]=grid[i][j] + 1;                
            }
            
            else{
                while(grid[i][j]==9){
                    grid[i][j]=0;
                    if(k==0){
                        return false;
                    }
                    k--; // Quay lui đến phần tử sau
                    i = freeCellList[k][0];
                    j = freeCellList[k][1];
                }
                grid[i][j] = grid[i][j] + 1; // Tăng phần tử lên 1
            }
        }
        return true;
    }
    
    
    public static int[][] getCellList(int[][] grid){
        int numOfFreeCell = 0; // Khởi tạo biến đếm số phần tử có giá trị bằng 0
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(grid[i][j]==0){
                    numOfFreeCell++;
                }
            }
        }
    
        
        int[][] freeCellList = new int[numOfFreeCell][2];
        int count=0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(grid[i][j]==0){
                    freeCellList[count][0]=i;
                    freeCellList[count][1]=j;
                    count++;         
                }
            }
        }
       /* for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print("freeCellList["+count+"][0]="+i +"\t");
                System.out.print("freeCellList["+count+"][1]="+j +"\t");
            }
            System.out.print("\n");
        }*/
        return freeCellList;
    }
    
    // Hàm kiểm tra có trùng số hay không
    public static boolean isAvaliable(int i, int j, int[][] grid){
        boolean check = true;
        // Kiểm tra hàng
        for(int column=0;column<9;column++){
            if(column!=j && grid[i][column]==grid[i][j]){
                check = false;
            }
        }
        // Kiểm tra cột
        for(int row=0;row<9;row++){
            if(row!=i && grid[row][j]==grid[i][j]){
                check = false;  
            }
        }
        // Kiểm tra trong hộp
        // 
        for(int row=(i/3)*3;row<(i/3)*3+3;row++){
            for(int column=(j/3)*3;column<(j/3)*3+3;column++){ 
                if(row!=i && column!=j && grid[row][column]==grid[i][j]){
                    check = false;
                }
            }
        }
        
        return check;
    }
    
    // Hàm lấy số ngẫu nhiên 
    public static ArrayList<Integer> getRandomNum(){
        ArrayList<Integer> num = new ArrayList<Integer>();
        for(Integer i = 1;i<10;i++){
            num.add(i);
        }
        Collections.shuffle(num); // Xáo trộn các phần tử trong mảng ArrayList
    //    System.out.print(num+" ");
        return num;
    }
    
    // Hàm đặt lại cấp độ trò chơi
    public static void setLevel(int lev){
        level = lev;             
    }
    
    
    
}
