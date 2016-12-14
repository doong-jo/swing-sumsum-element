
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingScene extends JPanel implements Runnable {

	//�г� ũ��, ���� ũ��, ������ �� ����
	private final int WIDTH = 800, HEIGHT = 600;
	private final int EM_W = 100, EM_H = 100;
	private final int EM_NUMBER = 32;
	private final int nEmMargin = 100;
	
	//�߰��� ������ �ε����� ������ �迭
	private int[] EM_LIST;
	//��� ������ �̸� �迭
	private String[] EM_NAME;
	//�߰��� ���� ��ü�� ������ ����Ʈ
	private ArrayList<Element> element_ArrList;
	
	//����ڰ� ���� ������ ����, ȭ�鿡 �ѷ��� ���� ����, ����Ʈ ������, z�� ���� ���� ���������ִ� ����
	private int nEmCnt, nEmNum, nEmLyX, nEmLyY;
	private int nComZorder;
	
	
	private JPanel topPanel, bottomPanel;					//���Ʒ� �ΰ��� �гη�
	private JButton btnOK, btnStart, btnMenu, btnName;		//ok��ư, start��ư, menu��ư, �̸� ���� ��
	private JLabel lblResult, bkgndImgResult;				//����� �˷��ִ� ��, �޹�� �����ϴ� ��
	private GameListener gameL;								//�׼� ������
	private GameMListener gameML;							//���콺 ������
	private Thread myThread;								//���Ұ� ���Ӿ��� �����̰� �ϴ� ������

	public EndingScene() {
		//�г� ũ�� �� ���̾ƿ� �޴��� ����
		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(Color.white);
		setLayout(null);

		//���� �ʱ�ȭ
		nEmCnt = 0;
		nEmNum = 0;
		nEmLyX = 10;
		nEmLyY = 10;

		EM_NAME = new String[EM_NUMBER];					//���� �̸� ����

		element_ArrList = new ArrayList<Element>();			//Element��� ����Ʈ ����

		gameL = new GameListener();							//�׼Ǹ�����
		gameML = new GameMListener();						//���콺������
		myThread = null;									//Tread�� �ʱⰪ�� null�� �Ϲ���

		//������ ó�� �������� ������ ���� �������� ������ �������� ȭ��  
		ImageIcon ImgSumbkgnd = new ImageIcon("imgs/bkgndendingtext.png");
		bkgndImgResult = new JLabel("", ImgSumbkgnd, SwingConstants.CENTER);
		bkgndImgResult.setBounds(0, 0, WIDTH, HEIGHT);
		bkgndImgResult.setHorizontalAlignment(SwingConstants.CENTER);
		bkgndImgResult.setVerticalAlignment(SwingConstants.CENTER);
		add(bkgndImgResult);
		
		//������ ó�� ������ ���̴� ��� �� - try again Ȥ�� congratulation���
		lblResult = new JLabel("try again!");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(10, 280, 780, 70);
		lblResult.setFont(new Font("Agency FB", Font.BOLD, 50));
		lblResult.setForeground(Color.white);
		add(lblResult);

		//������ ó�� ������ ���̴� ��ư - ������ ImgSumbkgnd�� setVisible�� (�׼� ������) 
		btnOK = new JButton(new ImageIcon("imgs/ok.png"));
		btnOK.setBounds(290, 350, 208, 78);
		btnOK.setFont(new Font("Agency FB", Font.BOLD, 20));
		btnOK.setBorderPainted(false);				//��輱 �����ϰ�
		btnOK.setContentAreaFilled(false);			//��Ŀ�� �����ϰ�
		btnOK.setFocusPainted(false);				//��ư ��� ����! �� �� ������ ���� �˱��� ��ư�̹����� ������ �� �� ����ϴ�!
		btnOK.addActionListener(gameL);				//��ư Ŭ���� ������ �������� ȭ�� setVisible	
		btnOK.addMouseListener(gameML);				//��ư ������ ���� ������
		add(btnOK);

		//����ؼ� �ٲ�� ���ҵ��� �̸� ���
		btnName = new JButton("", new ImageIcon("imgs/name.png"));
		btnName.setHorizontalAlignment(SwingConstants.CENTER);
		btnName.setBounds(WIDTH / 2 - 163 / 2, 360, 163, 78);
		btnName.setFont(new Font("Agency FB", Font.BOLD, 30));
		btnName.setForeground(new Color(91, 91, 80));			//���� �� ����
		btnName.setBorderPainted(false);						
		btnName.setContentAreaFilled(false);
		btnName.setFocusPainted(false);
		btnName.setHorizontalTextPosition(JButton.CENTER);		//�������� �߾�
		btnName.setVerticalTextPosition(JButton.CENTER);		//�������� �߾�
		add(btnName);

		//���� �ִ� �г�
		topPanel = new JPanel();
		topPanel.setBounds(0, 60, 800, 340);
		topPanel.setBackground(new Color(221, 221, 207));
		topPanel.setLayout(null);
		add(topPanel);
		nComZorder++;											//������Ʈ x���� ������ ���̴� ���� ���� �ϳ� ���涧���� �Խ� ������ ���� 
		
		//�Ʒ��� �г�
		bottomPanel = new JPanel();
		bottomPanel.setBounds(10, 480, 780, 100);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(null);
		add(bottomPanel);
		nComZorder++;

		//Menu������ �̵��ϴ� ��ư
		btnMenu = new JButton(new ImageIcon("imgs/home.png"));
		btnMenu.setHorizontalAlignment(SwingConstants.CENTER);
		btnMenu.setBounds(51, 1, 208, 78);
		btnMenu.setBorderPainted(false);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setFocusPainted(false);
		btnMenu.addActionListener(gameL);					//��ư Ŭ���� �޴������� �̵�
		btnMenu.addMouseListener(gameML);					//��ư �� ����
		bottomPanel.add(btnMenu);

		//Game������ ���� ��ư
		btnStart = new JButton(new ImageIcon("imgs/start.png"));
		btnStart.setHorizontalAlignment(SwingConstants.CENTER);
		btnStart.setBounds(540, 1, 208, 78);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(gameL);					//��ư Ŭ���� ���Ӿ����� �̵�
		btnStart.addMouseListener(gameML);					//��ư �� ����
		bottomPanel.add(btnStart);
	}

	//set method (Ending���� �������̶� �����͸� ��� �� �ʿ䰡 ���� - get method�� ����)
	public void setElementCnt(int count) { nEmCnt = count;}			//����ڰ� ���� ���� ���� ����
	public void setElementName(String[] name) {	EM_NAME = name; }	//���� �̸� �迭 ����
	public void setElementList(int[] list) {						//
		EM_LIST = list;										

		Init();														//�ʱ� ����
		for (int i = 0; i < nEmCnt; i++) {							//���� ������ ���� ȭ�鿡 ���� �߰��ϴ� �żҵ�
			Addelement(EM_LIST[i], i);
		}
		start();													//������ ����!
	}

	//
	public void Init() {
		nEmNum = 0;													//���� �ʱ�ȭ
		nEmLyX = 10;
		nEmLyY = 10;
		
		//����ڰ� ���� ������ ������ �� ������ �������� ���ų� ũ�� Congratulation!�� ȭ�鿡 ��� �ƴϸ� Try again!���
		if (nEmCnt >= EM_NUMBER) 
			lblResult.setText("Congratulation!");
		else
			lblResult.setText("Try again!");
		
		//ó�� ������ ���������� ������Ʈ�� setVisible
		bkgndImgResult.setVisible(true);
		btnOK.setVisible(true);
		lblResult.setVisible(true);
		btnStart.setEnabled(false);
		btnMenu.setEnabled(false);
		

		//Element ���� ����
		for (Element spot : element_ArrList) {
			remove(spot);
		}
		
		element_ArrList.clear();				//Array list�� ���� ��� ����
	}
	
	//������~!!~!~!~~! - ������ ���� ������ stop�� ���� ����
	//�����ִ� ������ ����!
	public void start() {
		if (myThread == null) {	myThread = new Thread(this);}		//�����尡 null�϶��� ����
		 myThread.start();//run() �θ�
	}
	
	public void run() { // thread inhavior
		while (true) {
			try {
				Element_Move();
				Element_Scaling();
				Thread.sleep(3);
			} catch (Exception e) {
			}
		}
	}

	public void Element_Scaling() {
		int i;
		for (Element spot : element_ArrList) {												//ArrList�� ���������� �˻��ϸ鼭 for�� ����
			if (spot.getListPosX() > -(EM_W)) {										
				int pivot = Math.abs(WIDTH / 2 - (spot.getListPosX() + EM_W / 2));			//�߽ɿ��������� �Ÿ��� ����
				float mul = (float) (WIDTH / 2 - pivot) / 200;								//�󸶳� �������ִ����� ���� ���� ����
				float S = EM_W * mul;														//������ ���� ũ�� ����

				if (S < EM_W)	{ S = EM_W; }												//�ּ� ũ�⸦ 100*100���� ����
				if (pivot < 70)	{ btnName.setText(EM_NAME[spot.getFlag()]);	}				//pivot�� 70���ϰ� �Ǿ ����� ���� �����̸� ȭ�鿡 ���

				ImageIcon imageIcon = new ImageIcon(new ImageIcon("imgs/" + spot.getFlag() + ".png").getImage()
						.getScaledInstance((int)(S), (int) (S), Image.SCALE_DEFAULT));		//
				spot.setScale((int) (S));													//�ش� �ε����� ũ�� ����
				spot.setIcon(imageIcon);													//�ش� �ε����� ������ ����
				
				int YPos = 165 - (int) ((float) (S - EM_W) / 2);							//���Ұ� Ŀ���� ���� ���̸� �缳��
				spot.setBounds(spot.getListPosX(), YPos, spot.getScale(), spot.getScale());	//������ ���� ������ ũ��� ����
				spot.setListPosY(YPos);														
			}
		}
	}

	public void Element_Move() {

		for (Element spot : element_ArrList) {												
			int LastX = 0;								
			//���� ������ �� �Ⱥ�������
			if (spot.getListPosX() <= -(EM_W)) {			
				//������ ���� ã�´�.
				for (Element spot_ : element_ArrList) {										
					if (LastX < spot_.getListPosX()) {										
						LastX = spot_.getListPosX() + EM_W;									
					}
				}
				//���ҵ��� ������ ���� ��ġ��Ų��.
				spot.setBounds(LastX + nEmMargin, spot.getListPosY(), spot.getScale(), spot.getScale());
				spot.setListPosX(LastX + nEmMargin);
			} 
			//�ƴ϶�� �������� -1�� �����δ�.
			else {
				spot.setBounds(spot.getListPosX() - 1, spot.getListPosY(), spot.getScale(), spot.getScale());
				spot.setListPosX(spot.getListPosX() - 1);
			}
		}
	}

	private class GameListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();

			//ok��ư�� ������ ó�� �������� ���� �� ���̴� �г��� �Ⱥ��̰� ��
			if (btnOK == obj) {
				bkgndImgResult.setVisible(false);
				lblResult.setVisible(false);
				btnOK.setVisible(false);
				btnStart.setEnabled(true);
				btnMenu.setEnabled(true);
			} 
			//Menu��ư ������ Menu������ �̵�
			else if (btnMenu == obj) {
				
				Scene.getInstance().selectScene("Menu");
			} 
			
			//Srtart��ư ������ Game������ �̵�
			else if (btnStart == obj) {
				Scene.getInstance().selectScene("Game");
			}
		}
	}

	private class GameMListener implements MouseListener {
		public void mouseClicked(MouseEvent event) {}

		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource();

			//��ư ������ �� ��Ӱ���
			if (btnOK == obj) {	btnOK.setIcon(new ImageIcon("imgs/ok_c.png")); } 
			else if (btnMenu == obj) { btnMenu.setIcon(new ImageIcon("imgs/home_c.png")); }
			else if (btnStart == obj) { btnStart.setIcon(new ImageIcon("imgs/start_c.png")); }
		}

		public void mouseReleased(MouseEvent event) {

			//��ư�� �����ٰ� ��� ��� ��ư���� �� ���ƿ�
			btnOK.setIcon(new ImageIcon("imgs/ok.png"));
			btnMenu.setIcon(new ImageIcon("imgs/home.png"));
			btnStart.setIcon(new ImageIcon("imgs/start.png"));
		}

		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}

	public void Addelement(int flag, int i) {
		nEmLyX = (EM_W) * nEmNum + nEmMargin * (nEmNum + 1);		
		nEmLyY = 165;

		Element element = new Element(flag);						//���� Ŭ���� �����ͼ� ����
		element.setBounds(nEmLyX, nEmLyY, EM_W, EM_H);				//���� ��ġ ����
		element.setListPosX(nEmLyX);								
		element.setListPosY(nEmLyY);								
		add(element);												//�гο� �߰�
		element_ArrList.add(element);								//Array list�� ���� �߰�

		//������ ������Ʈ ���� ������
		nEmNum++;
		setComponentZOrder(element, nEmNum);

		setComponentZOrder(topPanel, nComZorder + nEmNum + 1);
		setComponentZOrder(bottomPanel, nComZorder + nEmNum + 1);
		setComponentZOrder(btnName, nComZorder + nEmNum + 1);

		setComponentZOrder(bkgndImgResult, 0);
		setComponentZOrder(lblResult, 0);
		setComponentZOrder(btnOK, 0);

	}
}