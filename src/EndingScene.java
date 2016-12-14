
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

	//패널 크기, 원소 크기, 원소의 총 개수
	private final int WIDTH = 800, HEIGHT = 600;
	private final int EM_W = 100, EM_H = 100;
	private final int EM_NUMBER = 32;
	private final int nEmMargin = 100;
	
	//추가된 원소의 인덱스를 저장한 배열
	private int[] EM_LIST;
	//모든 원소의 이름 배열
	private String[] EM_NAME;
	//추가된 원소 객체를 저장한 리스트
	private ArrayList<Element> element_ArrList;
	
	//사용자가 맞춘 원소의 개수, 화면에 뿌려준 원소 개수, 리스트 시작점, z축 방향 깊이 설정도와주는 변수
	private int nEmCnt, nEmNum, nEmLyX, nEmLyY;
	private int nComZorder;
	
	
	private JPanel topPanel, bottomPanel;					//위아래 두개의 패널로
	private JButton btnOK, btnStart, btnMenu, btnName;		//ok버튼, start버튼, menu버튼, 이름 띄우는 라벨
	private JLabel lblResult, bkgndImgResult;				//결과를 알려주는 라벨, 뒷배경 결정하는 라벨
	private GameListener gameL;								//액션 리스너
	private GameMListener gameML;							//마우스 리스너
	private Thread myThread;								//원소가 끊임없이 움직이게 하는 쓰레드

	public EndingScene() {
		//패널 크기 및 레이아웃 메니저 설정
		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(Color.white);
		setLayout(null);

		//변수 초기화
		nEmCnt = 0;
		nEmNum = 0;
		nEmLyX = 10;
		nEmLyY = 10;

		EM_NAME = new String[EM_NUMBER];					//원소 이름 저장

		element_ArrList = new ArrayList<Element>();			//Element어레이 리스트 선언

		gameL = new GameListener();							//액션리스너
		gameML = new GameMListener();						//마우스리스너
		myThread = null;									//Tread의 초기값은 null이 일반적

		//엔딩씬 처음 들어왔을때 엔딩씬 위에 보여지는 검정색 불투명한 화면  
		ImageIcon ImgSumbkgnd = new ImageIcon("imgs/bkgndendingtext.png");
		bkgndImgResult = new JLabel("", ImgSumbkgnd, SwingConstants.CENTER);
		bkgndImgResult.setBounds(0, 0, WIDTH, HEIGHT);
		bkgndImgResult.setHorizontalAlignment(SwingConstants.CENTER);
		bkgndImgResult.setVerticalAlignment(SwingConstants.CENTER);
		add(bkgndImgResult);
		
		//엔딩씬 처음 들어오면 보이는 결과 라벨 - try again 혹은 congratulation출력
		lblResult = new JLabel("try again!");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(10, 280, 780, 70);
		lblResult.setFont(new Font("Agency FB", Font.BOLD, 50));
		lblResult.setForeground(Color.white);
		add(lblResult);

		//엔딩씬 처음 들어오면 보이는 버튼 - 누르면 ImgSumbkgnd이 setVisible됨 (액션 리스너) 
		btnOK = new JButton(new ImageIcon("imgs/ok.png"));
		btnOK.setBounds(290, 350, 208, 78);
		btnOK.setFont(new Font("Agency FB", Font.BOLD, 20));
		btnOK.setBorderPainted(false);				//경계선 제거하고
		btnOK.setContentAreaFilled(false);			//포커스 제거하고
		btnOK.setFocusPainted(false);				//버튼 배경 제거! 이 세 과정을 통해 똥그한 버튼이미지를 입혔을 떄 안 어색하당!
		btnOK.addActionListener(gameL);				//버튼 클릭시 검은색 불투명한 화면 setVisible	
		btnOK.addMouseListener(gameML);				//버튼 눌릴때 뗄때 색변경
		add(btnOK);

		//계속해서 바뀌는 원소들의 이름 출력
		btnName = new JButton("", new ImageIcon("imgs/name.png"));
		btnName.setHorizontalAlignment(SwingConstants.CENTER);
		btnName.setBounds(WIDTH / 2 - 163 / 2, 360, 163, 78);
		btnName.setFont(new Font("Agency FB", Font.BOLD, 30));
		btnName.setForeground(new Color(91, 91, 80));			//글자 색 설정
		btnName.setBorderPainted(false);						
		btnName.setContentAreaFilled(false);
		btnName.setFocusPainted(false);
		btnName.setHorizontalTextPosition(JButton.CENTER);		//수평으로 중앙
		btnName.setVerticalTextPosition(JButton.CENTER);		//수직으로 중앙
		add(btnName);

		//위에 있는 패널
		topPanel = new JPanel();
		topPanel.setBounds(0, 60, 800, 340);
		topPanel.setBackground(new Color(221, 221, 207));
		topPanel.setLayout(null);
		add(topPanel);
		nComZorder++;											//컴포넌트 x오더 설정시 쓰이는 변수 뭐가 하나 생길때마다 게쏙 증가할 예정 
		
		//아래쪽 패널
		bottomPanel = new JPanel();
		bottomPanel.setBounds(10, 480, 780, 100);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(null);
		add(bottomPanel);
		nComZorder++;

		//Menu씬으로 이동하는 버튼
		btnMenu = new JButton(new ImageIcon("imgs/home.png"));
		btnMenu.setHorizontalAlignment(SwingConstants.CENTER);
		btnMenu.setBounds(51, 1, 208, 78);
		btnMenu.setBorderPainted(false);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setFocusPainted(false);
		btnMenu.addActionListener(gameL);					//버튼 클릭시 메뉴씬으로 이동
		btnMenu.addMouseListener(gameML);					//버튼 색 변경
		bottomPanel.add(btnMenu);

		//Game씬으로 가능 버튼
		btnStart = new JButton(new ImageIcon("imgs/start.png"));
		btnStart.setHorizontalAlignment(SwingConstants.CENTER);
		btnStart.setBounds(540, 1, 208, 78);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(gameL);					//버튼 클릭시 게임씬으로 이동
		btnStart.addMouseListener(gameML);					//버튼 색 변경
		bottomPanel.add(btnStart);
	}

	//set method (Ending씬이 마지막이라서 데이터를 어디에 줄 필요가 없음 - get method도 없음)
	public void setElementCnt(int count) { nEmCnt = count;}			//사용자가 맞춘 원소 개수 설정
	public void setElementName(String[] name) {	EM_NAME = name; }	//원소 이름 배열 설정
	public void setElementList(int[] list) {						//
		EM_LIST = list;										

		Init();														//초기 설정
		for (int i = 0; i < nEmCnt; i++) {							//원소 개수에 맞춰 화면에 원소 추가하는 매소드
			Addelement(EM_LIST[i], i);
		}
		start();													//쓰레드 시작!
	}

	//
	public void Init() {
		nEmNum = 0;													//변수 초기화
		nEmLyX = 10;
		nEmLyY = 10;
		
		//사용자가 맞춘 원소의 개수가 총 원소의 개수보다 같거나 크면 Congratulation!이 화면에 출력 아니면 Try again!출력
		if (nEmCnt >= EM_NUMBER) 
			lblResult.setText("Congratulation!");
		else
			lblResult.setText("Try again!");
		
		//처음 엔딩씬 들어왔을때의 컴포넌트들 setVisible
		bkgndImgResult.setVisible(true);
		btnOK.setVisible(true);
		lblResult.setVisible(true);
		btnStart.setEnabled(false);
		btnMenu.setEnabled(false);
		

		//Element 원소 제거
		for (Element spot : element_ArrList) {
			remove(spot);
		}
		
		element_ArrList.clear();				//Array list의 내용 모두 제거
	}
	
	//쓰레드~!!~!~!~~! - 영원히 돌기 떄문에 stop은 구현 안함
	//멈춰있던 쓰레드 시작!
	public void start() {
		if (myThread == null) {	myThread = new Thread(this);}		//쓰레드가 null일때만 시작
		 myThread.start();//run() 부름
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
		for (Element spot : element_ArrList) {												//ArrList를 순차적으로 검사하면서 for문 진행
			if (spot.getListPosX() > -(EM_W)) {										
				int pivot = Math.abs(WIDTH / 2 - (spot.getListPosX() + EM_W / 2));			//중심에서부터의 거리를 구함
				float mul = (float) (WIDTH / 2 - pivot) / 200;								//얼마나 떨어져있는지에 대한 비율 구함
				float S = EM_W * mul;														//비율에 따른 크기 설정

				if (S < EM_W)	{ S = EM_W; }												//최소 크기를 100*100으로 고정
				if (pivot < 70)	{ btnName.setText(EM_NAME[spot.getFlag()]);	}				//pivot이 70이하가 되어서 가까워 지면 원소이름 화면에 출력

				ImageIcon imageIcon = new ImageIcon(new ImageIcon("imgs/" + spot.getFlag() + ".png").getImage()
						.getScaledInstance((int)(S), (int) (S), Image.SCALE_DEFAULT));		//
				spot.setScale((int) (S));													//해당 인덱스의 크기 설정
				spot.setIcon(imageIcon);													//해당 인덱스의 아이콘 설정
				
				int YPos = 165 - (int) ((float) (S - EM_W) / 2);							//원소가 커짐에 따라 높이를 재설정
				spot.setBounds(spot.getListPosX(), YPos, spot.getScale(), spot.getScale());	//위에서 정한 원소의 크기로 설정
				spot.setListPosY(YPos);														
			}
		}
	}

	public void Element_Move() {

		for (Element spot : element_ArrList) {												
			int LastX = 0;								
			//왼쪽 끝으로 가 안보여지면
			if (spot.getListPosX() <= -(EM_W)) {			
				//오른쪽 끝을 찾는다.
				for (Element spot_ : element_ArrList) {										
					if (LastX < spot_.getListPosX()) {										
						LastX = spot_.getListPosX() + EM_W;									
					}
				}
				//원소들의 마지막 끝에 위치시킨다.
				spot.setBounds(LastX + nEmMargin, spot.getListPosY(), spot.getScale(), spot.getScale());
				spot.setListPosX(LastX + nEmMargin);
			} 
			//아니라면 왼쪽으로 -1씩 움직인다.
			else {
				spot.setBounds(spot.getListPosX() - 1, spot.getListPosY(), spot.getScale(), spot.getScale());
				spot.setListPosX(spot.getListPosX() - 1);
			}
		}
	}

	private class GameListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();

			//ok버튼을 누르면 처음 엔딩씬에 들어갔을 때 보이는 패널을 안보이게 함
			if (btnOK == obj) {
				bkgndImgResult.setVisible(false);
				lblResult.setVisible(false);
				btnOK.setVisible(false);
				btnStart.setEnabled(true);
				btnMenu.setEnabled(true);
			} 
			//Menu버튼 누르면 Menu씬으로 이동
			else if (btnMenu == obj) {
				
				Scene.getInstance().selectScene("Menu");
			} 
			
			//Srtart버튼 누르면 Game씬으로 이동
			else if (btnStart == obj) {
				Scene.getInstance().selectScene("Game");
			}
		}
	}

	private class GameMListener implements MouseListener {
		public void mouseClicked(MouseEvent event) {}

		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource();

			//버튼 눌리면 색 어둡게함
			if (btnOK == obj) {	btnOK.setIcon(new ImageIcon("imgs/ok_c.png")); } 
			else if (btnMenu == obj) { btnMenu.setIcon(new ImageIcon("imgs/home_c.png")); }
			else if (btnStart == obj) { btnStart.setIcon(new ImageIcon("imgs/start_c.png")); }
		}

		public void mouseReleased(MouseEvent event) {

			//버튼을 눌렀다가 뗴면 모든 버튼색이 다 돌아옴
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

		Element element = new Element(flag);						//원소 클래스 가져와서 선언
		element.setBounds(nEmLyX, nEmLyY, EM_W, EM_H);				//원소 위치 설정
		element.setListPosX(nEmLyX);								
		element.setListPosY(nEmLyY);								
		add(element);												//패널에 추가
		element_ArrList.add(element);								//Array list에 원소 추가

		//각각의 컴포넌트 깊이 정해줌
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