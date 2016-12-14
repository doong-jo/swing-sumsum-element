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
		add(lblTitle); //ImageIcon���� ���� ����
		
			
		btnStartGame = new JButton(new ImageIcon("imgs/start.png"));
		btnStartGame.setBorderPainted(false); //��ư �׵θ� ����
		btnStartGame.setContentAreaFilled(false); //��ư ��� ���� 
		btnStartGame.setFocusPainted(false); // ��Ŀ�� �κ� ����
		btnStartGame.setBounds(50,470,300,100); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton���� ���� ��ư ����
		
		btnHowTo = new JButton(new ImageIcon("imgs/how.png"));
		btnHowTo.setBorderPainted(false); //��ư ������ ����
		btnHowTo.setContentAreaFilled(false); // ��ư ��� ����
		btnHowTo.setFocusPainted(false); // ��Ŀ�� �κ� ����
		btnHowTo.setBounds(450,470,300,100);	
		//btnHowTo.addActionListener(gameL);
		btnHowTo.addMouseListener(gameML);
		add(btnHowTo); //JButton���� ���� ��ư ����
		
	} //constructor
	
	ImageIcon Imgbkgnd = new ImageIcon("imgs/backgroundmenu.png"); //��� �̹���

	public void paintComponent(Graphics g) {
	 g.drawImage(Imgbkgnd.getImage(), 0, 0, null);
	 setOpaque(false); //�����ϰ� ����.
	 super.paintComponent(g);
	 } //paintComponent�� ����̹��� �ҷ��� �׸�.
		
	
	public void moveHowTo() {
		Scene.getInstance().selectScene("Howto");
			} //HowtoScene���� �̵�.
	
	public void moveStartGame() {
		Scene.getInstance().selectScene("Game");
	}//GameScene���� �̵�. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource(); 

			if( btnStartGame == obj ){	// ���۹�ư ������ �̹��� �������� ���� ��ư ����ȿ�� ����.
				btnStartGame.setIcon(new ImageIcon("imgs/start_c.png"));
			}else if( btnHowTo == obj ){ // �����ư ������ �̹��� �������� ���� ��ư ����ȿ�� ����.
				btnHowTo.setIcon(new ImageIcon("imgs/how_c.png"));
			}
		} //press �� �̺�Ʈ
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();

			if( btnStartGame == obj ){ //���� ��ư���� Release�� moveStartGame�޼ҵ� ȣ
				moveStartGame();
			}
			if( btnHowTo == obj ){	//���� ��ư���� Release�� moveHowTo�޼ҵ� ȣ��.
				moveHowTo();
			}
			btnStartGame.setIcon(new ImageIcon("imgs/start.png")); 
			btnHowTo.setIcon(new ImageIcon("imgs/how.png"));	//���� �̹����� ���ƿ�.
		
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
} 