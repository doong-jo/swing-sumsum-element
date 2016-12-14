import javax.swing.JFrame;
import sun.audio.*;
import java.io.*;

public class SumSumMain
{
	public static void main(String[] args) throws IOException{		
  
		JFrame frame = new JFrame("Sum Sum!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//윈도우 사이즈를 임의로 변경하지 못하게 설정한다.

		frame.getContentPane().add(Scene.getInstance());
		
		Scene.getInstance().getgameScene().FileInputOutput("data/list.txt", "data/description.txt");
		
		frame.pack();
		frame.setVisible(true);
	} // main()
} // HighLowGame class