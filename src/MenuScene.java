import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuScene extends JPanel
{
	private JLabel lblTitle; 
	private JButton btnStartGame;
	private JButton btnHowTo;
	// 2. declaration of the listener object
	private GameMListener gameML;
	
	public MenuScene() {
		setBounds(0,0,800,600);
		setBackground(Color.white);
		setLayout(null);		
		// 2. creating of the listener object
		gameML = new GameMListener();
		
		lblTitle = new JLabel(new ImageIcon("imgs/title.jpg"));
		lblTitle.setBounds(50,50,700,150);
		add(lblTitle); //ImageIcon으로 제목 생성
		
			
		btnStartGame = new JButton(new ImageIcon("imgs/start.png"));
		btnStartGame.setBorderPainted(false); //버튼 테두리 제거
		btnStartGame.setContentAreaFilled(false); //버튼 배경 제거 
		btnStartGame.setFocusPainted(false); // 포커스 부분 제거
		btnStartGame.setBounds(50,470,300,100); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton으로 시작 버튼 생성
		
		btnHowTo = new JButton(new ImageIcon("imgs/how.png"));
		btnHowTo.setBorderPainted(false); //버튼 테투리 제거
		btnHowTo.setContentAreaFilled(false); // 버튼 배경 제거
		btnHowTo.setFocusPainted(false); // 포커스 부분 제거
		btnHowTo.setBounds(450,470,300,100);	
		//btnHowTo.addActionListener(gameL);
		btnHowTo.addMouseListener(gameML);
		add(btnHowTo); //JButton으로 설명 버튼 생성
		
	} //constructor
	
	ImageIcon Imgbkgnd = new ImageIcon("imgs/backgroundmenu.png"); //배경 이미지

	public void paintComponent(Graphics g) {
	 g.drawImage(Imgbkgnd.getImage(), 0, 0, null);
	 setOpaque(false); //투명하게 만듦.
	 super.paintComponent(g);
	 } //paintComponent로 배경이미지 불러와 그림.
		
	
	public void moveHowTo() {
		Scene.getInstance().selectScene("Howto");
			} //HowtoScene으로 이동.
	
	public void moveStartGame() {
		Scene.getInstance().selectScene("Game");
	}//GameScene으로 이동. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource(); 

			if( btnStartGame == obj ){	// 시작버튼 누르면 이미지 변경으로 인한 버튼 누름효과 보임.
				btnStartGame.setIcon(new ImageIcon("imgs/start_c.png"));
			}else if( btnHowTo == obj ){ // 설명버튼 누르면 이미지 변경으로 인한 버튼 누름효과 보임.
				btnHowTo.setIcon(new ImageIcon("imgs/how_c.png"));
			}
		} //press 시 이벤트
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();

			if( btnStartGame == obj ){ //시작 버튼에서 Release시 moveStartGame메소드 호
				moveStartGame();
			}
			if( btnHowTo == obj ){	//설명 버튼에서 Release시 moveHowTo메소드 호출.
				moveHowTo();
			}
			btnStartGame.setIcon(new ImageIcon("imgs/start.png")); 
			btnHowTo.setIcon(new ImageIcon("imgs/how.png"));	//원래 이미지로 돌아옴.
		
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
} 