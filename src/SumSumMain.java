import javax.swing.JFrame;
import sun.audio.*;
import java.io.*;

public class SumSumMain
{
	public static void main(String[] args) throws IOException{		
  
		JFrame frame = new JFrame("Sum Sum!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//������ ����� ���Ƿ� �������� ���ϰ� �����Ѵ�.

		frame.getContentPane().add(Scene.getInstance());
		
		Scene.getInstance().getgameScene().FileInputOutput("data/list.txt", "data/description.txt");
		
		frame.pack();
		frame.setVisible(true);
	} // main()
} // HighLowGame class