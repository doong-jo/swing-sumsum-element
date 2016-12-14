import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameScene extends JPanel
{
	//스크린 크기를 저장
   private final int WIDTH = 800, HEIGHT = 600;
   //원소 이미지 크기를 저장 
   private final int EM_W = 100, EM_H = 100;
   //원소 최대 개수를 저장 
   private final int EM_NUMBER = 32;
   //원소의 조합 가능한 개수를 저장 
   private final int EM_MIX_NUMBER = 28;
   //라이프(실패가능횟수)를 저장 
   private final int LIFE_MAX = 10;
   
   /*
   *nScore 		: 스코어를 저장
   *nLifeCnt 	: 라이프를 저장
   *nEmNum		: 현재 원소 개수를 저장
   *nLSlotFlag 	: 왼쪽 슬롯에 저장된 원소 flag를 저장
   *nRSlotFlag 	: 오른쪽 슬롯에 저장된 원소 flag를 저장
   *nComZorder	: Component들의 z-order를 지정하는데 사용
   *nListHeight	: 리스트의 위치를 저장
   *IsLSlot		: 왼쪽 슬롯의 배치 여부를 저장
   *IsRSlot		: 오른쪽 슬롯의 배치여부를 저장 
   */
   
   private int nScore, nLifeCnt, nEmNum;
   private int nLSlotFlag, nRSlotFlag, nComZorder, nListHeight;

   private boolean IsLSlot, IsRSlot;
   
   //모든 원소의 조합 리스트를 저장한다. 행은 각 원소를 의미하며 열은 조합리스트를 의미한다.
   private int [][] EM_LIST;
   //획득한 원소를 저장한다.
   private int [] EM_GET;
   //모든 원소 이름들을 저장한다.
   private String [] EM_NAME;
   //추가된 원소들의 객체를 저장한다.
   private ArrayList<Element> element_ArrList;
   //조합된 원소를 저장하고 성공시 보여주는 원소 객체.
   private Element SumElement;
   
   /*
    * bkgndimg	: 배경이미지를 저장한 라벨
    * userScore : 사용자가 획득한 스코어를 보여줄 라벨
    * totalScore: 총 스코어를 보여줄 라벨
    * lifeText	: Life : <- 텍스트를 보여줄 라벨
    * lifeCount : 현재 라이프 개수를 보여줄 라벨
    * LSlot		: 왼쪽 슬롯을 보여줄 라벨
    * RSlot		: 오른쪽 슬롯을 보여줄 라벨
    * resultText: 조합 결과를 보여줄 라벨
    * SumElementName : 조합 성공된 원소이름을 보여줄 라벨
    * SumBackImg: 조합 성공시 배경이미지를 저장한 라벨
    * btnMenu	: 메뉴 버튼을 보여줄 라벨
    * btnSum	: 조합 버튼을 보여줄 라벨
    * btnUp		: 리스트 올리는 버튼을 보여줄 라벨
    * btnDown	: 리스트 내리는 버튼을 보여줄 라벨
    * btnSumOk	: 조합 성공시 보여줄 라벨
    */
   
   private JLabel bkgndimg, userScore, totalScore, lifeText, lifeCount;
   private JLabel LSlot, RSlot, resultText, SumElementName, SumBackImg;
   private JButton btnMenu, btnSum, btnUp, btnDown, btnSumOk;
   
   /*
    * fileIO 		: 파일의 데이터를 버퍼에 읽어오는데 사용되는 객체
    */
   BufferedReader   fileIO;
   
   //액션 리스너
   private GameListener gameL;
   //마우스 리스너
   private GameMListener gameML;
   
   public GameScene() {
      nScore = 0;
      nLifeCnt = 3;
      nEmNum = 0;
      nComZorder = 0;
      nListHeight = 0;
      
      IsLSlot = IsRSlot = false;
      nLSlotFlag = nRSlotFlag = -1;
      
      EM_LIST = new int[EM_MIX_NUMBER][3];
      EM_GET = new int[EM_NUMBER];
      EM_NAME = new String[EM_NUMBER];
      
      gameL = new GameListener();
      gameML = new GameMListener();
      
      element_ArrList = new ArrayList<Element>();
      
      setBounds(0,0,WIDTH,HEIGHT);
      setBackground(Color.white);
      setLayout(null);
   
      Color color = new Color(0,0,0);
      //uiFont
      Font uiFont = new Font("Agency FB",Font.BOLD,30);
      
      ImageIcon Imgbkgnd = new ImageIcon("imgs/background.png");
      bkgndimg = new JLabel("", Imgbkgnd, SwingConstants.CENTER);
      bkgndimg.setBounds(0,50,WIDTH,HEIGHT-50);
      add(bkgndimg);
      
      /////////////////////////TOP UI////////////////////////////
      userScore = new JLabel(String.valueOf(nScore));
      userScore.setBounds(360,00,60,40);
      userScore.setFont(uiFont);
      userScore.setVisible(true);
      add(userScore);
      nComZorder++;
      
      totalScore = new JLabel(" / " + String.valueOf(EM_NUMBER));
      totalScore.setBounds(380,00,60,40);
      totalScore.setFont(uiFont);
      totalScore.setVisible(true);
      add(totalScore);
      nComZorder++;
      
      btnMenu = new JButton(new ImageIcon("imgs/menu.png"));
      btnMenu.setBounds(10,0,50,50);
      btnMenu.setFont(uiFont);
      btnMenu.addActionListener(gameL);
      btnMenu.setEnabled(true);
      btnMenu.setBorderPainted(false);
      btnMenu.setFocusPainted(false);
      btnMenu.setContentAreaFilled(false);
      btnMenu.addMouseListener(gameML);
      add(btnMenu);
      nComZorder++;
      
      lifeText = new JLabel("Life : ");
      lifeText.setBounds(680,00,80,40);
      lifeText.setFont(uiFont);
      lifeText.setVisible(true);
      add(lifeText);
      nComZorder++;
      
      lifeCount = new JLabel(String.valueOf(nLifeCnt));
      lifeCount.setBounds(730,00,60,40);
      lifeCount.setFont(uiFont);
      lifeCount.setVisible(true);
      add(lifeCount);
      nComZorder++;
      ////////////////////////////LEFT UI///////////////////////////
      btnUp = new JButton(new ImageIcon("imgs/btnUp.png"));
      btnUp.setBounds(400,250,50,50);
      btnUp.setFont(uiFont);
      btnUp.addActionListener(gameL);   
      btnUp.setEnabled(true);
      btnUp.setBorderPainted(false);
      btnUp.setFocusPainted(false);
      btnUp.setContentAreaFilled(false);
      btnUp.addMouseListener(gameML);
      add(btnUp);
      nComZorder++;
      
      btnDown = new JButton(new ImageIcon("imgs/btnDown.png"));
      btnDown.setBounds(400,350,50,50);
      btnDown.setFont(uiFont);
      btnDown.addActionListener(gameL);
      btnDown.setEnabled(true);
      btnDown.setBorderPainted(false);
      btnDown.setFocusPainted(false);
      btnDown.setContentAreaFilled(false);
      btnDown.addMouseListener(gameML);
      add(btnDown);
      nComZorder++;
      
      
      ////////////////////////////RIGHT UI//////////////////////////
      ImageIcon ImgSlot = new ImageIcon("imgs/slot.png");
      LSlot = new JLabel("", ImgSlot, SwingConstants.CENTER);
      LSlot.setBounds(450,100,150,150);
      add(LSlot);
      nComZorder++;
        
      ImgSlot = new ImageIcon("imgs/slot.png");
      RSlot = new JLabel("", ImgSlot, SwingConstants.CENTER);
      RSlot.setBounds(600,100,150,150);
      add(RSlot);
      nComZorder++;
        
      btnSum = new JButton(new ImageIcon("imgs/btnSum.png"));
      btnSum.setBounds(550,260,100,100);
      btnSum.setFont(uiFont);
      btnSum.setBorderPainted(false);
	  btnSum.setContentAreaFilled(false);
	  btnSum.setFocusPainted(false);
      btnSum.addActionListener(gameL);
      btnSum.addMouseListener(gameML);
      btnSum.setEnabled(true);
      add(btnSum);
      nComZorder++;
      
      btnSumOk = new JButton("OK");
      btnSumOk.setBounds(WIDTH/2-90, HEIGHT/2+100, 180, 30);
      btnSumOk.setFont(uiFont);
      btnSumOk.addActionListener(gameL);
      btnSumOk.setEnabled(true);
      btnSumOk.setVisible(false);
      add(btnSumOk);
      nComZorder++;
      
      SumElement = new Element(0);
      SumElement.setBounds(WIDTH/2 - EM_W/2,HEIGHT/2 - EM_H/2,EM_W,EM_H);
      SumElement.setVisible(false);
      add(SumElement);
      nComZorder++;
        
      SumElementName = new JLabel("none");
      SumElementName.setBounds(WIDTH/2-75,HEIGHT/2+50,150,30);
      SumElementName.setFont(uiFont);
      SumElementName.setForeground(color.white);
      SumElementName.setHorizontalAlignment(SwingConstants.CENTER);
      SumElementName.setVerticalAlignment(SwingConstants.CENTER);
      SumElementName.setVisible(false);
      add(SumElementName);
      nComZorder++;

      ImageIcon ImgSumbkgnd = new ImageIcon("imgs/backgroundsum2.png");
      SumBackImg = new JLabel("", ImgSumbkgnd, SwingConstants.CENTER);
      SumBackImg.setBounds(0,50,WIDTH,HEIGHT-50);
      SumBackImg.setVisible(false);
      add(SumBackImg);
      nComZorder++;
      
      resultText = new JLabel("IMPOSSIBLE !");
      resultText.setBounds(485,230,230,440);
      resultText.setFont(uiFont);
      resultText.setHorizontalAlignment(SwingConstants.CENTER);
      resultText.setVerticalAlignment(SwingConstants.CENTER);
      resultText.setVisible(false);
      add(resultText);
      nComZorder++;
   }
   
   //씬을 시작할때 변동되는 변수들을 초기화하는 메소드
   public void Init(){
   	  for(Element spot : element_ArrList){
   	  	remove(spot);
   	  }
   	  element_ArrList.clear();
   	  
   	  nScore = 0;
      nLifeCnt = LIFE_MAX;
      nEmNum = 0;
      nListHeight = 0;
      
      IsLSlot = IsRSlot = false;
      nLSlotFlag = nRSlotFlag = -1;
      
      resultText.setVisible(false);
      lifeCount.setText(String.valueOf(nLifeCnt));
      userScore.setText(String.valueOf(nScore));
      
      //초기의 원소는 4개만 추가한다.
   	  Addelement(0);
   	  Addelement(1);
   	  Addelement(2);
   	  Addelement(3);
   	  /*
	  Addelement(4);
	  Addelement(5);
	  Addelement(6);
	  Addelement(7);
	  Addelement(8);
	  Addelement(9);
	  Addelement(10);
	  Addelement(11);
	  Addelement(12);
	  Addelement(13);
	  Addelement(14);
	  Addelement(15);
	  Addelement(16);
	  Addelement(17);
	  */
   	
   }
   
   public int getScore(){
      return nScore;
   }
   
   public int [] getElementList(){
      return EM_GET;
   }
   
   //파일입출력시 사용되는 메소드
   public void FileInputOutput(String list, String description) throws IOException {
      fileIO = new BufferedReader(new FileReader(list));
      int y = 0;
      
      
      /*
       * 파일 읽는 절차
       * 1.속성(PIVOT, ADD, RESULT)을 읽는다.
       * 2.속성에 따라 저장되는 배열 위치를 지정하고 읽어들인 값을 대입한다.
       */
      while(true){
         String line = fileIO.readLine();
            if (line==null) break;
            
            switch(line){
               case "PIVOT":
               line = fileIO.readLine();
               EM_LIST[y][0] = Integer.parseInt(line);
               break;
               
               case "ADD":
               line = fileIO.readLine();
               EM_LIST[y][1] = Integer.parseInt(line);
               break;
               
               case "RESULT":
               line = fileIO.readLine();
               EM_LIST[y][2] = Integer.parseInt(line);
               y++;
               break;
               
               default:
               break;
            }
      }
      fileIO.close();
      
      y=0;
      
      fileIO = new BufferedReader(new FileReader(description));
      
      //원소이름들을 순차적으로 넣는다. 파일에는 순차적으로 원소이름이 저장되어있다.
      while(true){
         String line = fileIO.readLine();
            if (line==null) break;
            EM_NAME[y] = line;
            y++;
      }
      
      fileIO.close();
   }
   
   
   //원소를 추가하는 메소드
   public void Addelement(int flag){
	   // 좌측 리스트에 들어갈 원소의 X, Y값을 저장한다.
      int nEmLyX = (EM_W) * ((nEmNum-(nListHeight*4)) % 3) + 25 * ((nEmNum-(nListHeight*4)) % 3 + 1);
      int nEmLyY = 50 + 10 * (nEmNum / 3 + 1) + ((nEmNum / 3) * EM_H);
      
      //원소를 추가한다.
      Element element = new Element(flag);
      element.setBounds(nEmLyX,nEmLyY,EM_W,EM_H);
      element.setListPos(nEmLyX, nEmLyY);
      element.setgameScene(this);
      add(element);
      element_ArrList.add(element);
        
      //획득한 원소 배열에 원소 인덱스를 저장하고, 획득 원소 개수를 증가시킨다.
      EM_GET[nEmNum] = flag;
      nEmNum++;
       
      //Slot과 배경이미지는 원소보다 항상 아래에 있어야하므로 다시 z-order를 설정해준다.
      setComponentZOrder(LSlot, nComZorder+nEmNum);
      setComponentZOrder(RSlot, nComZorder+nEmNum);
      setComponentZOrder(bkgndimg, nComZorder+nEmNum);
      //setComponentZOrder(resultText, nComZorder+nEmNum);
   }
   
   //flag에 해당하는 슬롯을 Slot에 저장한다.
   public void setSlot(int flag){
      for(Element spot : element_ArrList){
    	 //flag에 해당하는 element를 찾음
         if( spot.getFlag() == flag ){
        	 //둘다 차있으면 메소드를 종료한다.
            if( IsLSlot && IsRSlot )
               return;
               
            //비어있는 곳을 찾아 넣는다.
            if(!IsLSlot){
               IsLSlot = true;
               nLSlotFlag = flag;
               spot.setSlotPos(0);
               spot.setBounds(475, 125, EM_W, EM_H);
            }else if(!IsRSlot){
               IsRSlot = true;
               nRSlotFlag = flag;
               spot.setIsSlot(true);
               spot.setSlotPos(1);
               spot.setBounds(625, 125, EM_W, EM_H);
            }
            spot.setIsSlot(true);
            resultText.setVisible(false);
         }   
      }
   }
   
   
   //flag에 해당하는 슬롯을 다시 리스트로 복귀시킨다.
   public void dropSlot(int flag){
      for(Element spot : element_ArrList){
         if( spot.getFlag() == flag ){
        	 //원소가 어느 슬롯인지 찾는다.
            if( spot.getSlotPos() == 0 ){
               IsLSlot = false;
               nLSlotFlag = -1;
            }
            if( spot.getSlotPos() == 1 ){
               IsRSlot = false;
               nRSlotFlag = -1;
            }
            
            spot.setIsSlot(false);
            spot.setSlotPos(-1);
            
            //원소의 리스트 좌표가 리스트 위쪽이면 visible을 false로 설정해 안보이게 한다.
            if( spot.getListPosY() < 60 )
               spot.setVisible(false);
               
            //원소를 리스트에 다시 복귀시킨다.
            spot.setBounds(spot.getListPosX(),spot.getListPosY(),EM_W,EM_H);
            
            return;
         }
      }
   }
   
   //스코어를 증가시킨다.
   public void AddScore(int score){
      nScore += score;
      userScore.setText(String.valueOf(nScore));
   }
   
   //라이프를 mount만큼 줄인다.
   public void DecreaseLife(int mount){
      nLifeCnt -= mount;
      lifeCount.setText(String.valueOf(nLifeCnt));
      
      //라이프가 0이되거나 모든 조합을 성공하면 Ending Scene으로 이동한다.
      if( nLifeCnt <= 0 || nScore == EM_NUMBER ){
         Scene.getInstance().selectScene("Ending");
         //획득한 원소 개수, 획득한 원소 인덱스들과 모든 원소의 이름을 Ending Scene에 전달한다. 
         Scene.getInstance().getEndingScene().setElementCnt(nEmNum);
         Scene.getInstance().getEndingScene().setElementName(EM_NAME);
         Scene.getInstance().getEndingScene().setElementList(EM_GET);
         Init();
      }
   }
   
   //조합이 성공했을때 호출되는 메소드
   public void Mix_Success(int Index){
	  //Slot을 비어있는 상태로 만든다.
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      
      //스코어를 증가하낟.
      AddScore(1);
          
      //성공 라벨를 출력한다
      resultText.setText("SUCCESS !");
      resultText.setVisible(true);
      
      
      //조합성공한 원소를 추가한다.
      Addelement(EM_LIST[Index][2]);
   
      //조합하여 생성된 원소를 배경, 버튼과 함께 보여준다.
      ImageIcon icon = new ImageIcon("imgs/"+EM_LIST[Index][2]+".png");
      SumElement.setIcon(icon);
      SumElementName.setText(EM_NAME[EM_LIST[Index][2]]);
      
      SumElement.setVisible(true);
      btnSumOk.setVisible(true);
      SumElementName.setVisible(true);
      SumBackImg.setVisible(true);
      btnSum.setVisible(false);
      
      setComponentZOrder(btnUp, nComZorder+nEmNum-1);
      setComponentZOrder(btnDown, nComZorder+nEmNum-1);
   }
   
   //조합이 시도했으나 이미 존재할 때 호출되는 메소드
   public void Mix_Exist(){
      resultText.setText("EXIST ELEMENT!");
      resultText.setVisible(true);
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      DecreaseLife(1);
   }
   
   //조합이 시도했으나 불가능할 때 호출되는 메소드
   public void Mix_Impossible(){
      resultText.setText("IMPOSSIBLE !");
      resultText.setVisible(true);
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      DecreaseLife(1);
   }
   
   //조합을 시도하는 메소드
   public void Sum(){
	  //둘 중에 하나라도 비어있으면 종료한다.
      if( nLSlotFlag == -1 || nRSlotFlag == -1 ){
         return;
      }
      
      //슬롯에 있는 원소와 조합리스트를 비교하여 결과를 도출한다.
      for(int y=0; y<EM_MIX_NUMBER; y++){
         if( (nLSlotFlag == EM_LIST[y][0]
            &&
            nRSlotFlag == EM_LIST[y][1])
            ||
            (nRSlotFlag == EM_LIST[y][0]
            &&
            nLSlotFlag == EM_LIST[y][1])
         ){
            for(Element spot : element_ArrList){
               if( spot.getFlag() == EM_LIST[y][2] ){
                  Mix_Exist();
                  return;
               }
            }
            
            Mix_Success(y);
            return;
         }
      }
      Mix_Impossible();
   }
   
   //리스트에 있는 원소들을 위로 한줄씩 올린다.
   public void ListUp(){
      if( nEmNum <= 15 )
         return;
      if( 3 * nListHeight >= nEmNum-15 )
         return;
      
      if( element_ArrList.get(nListHeight*3).getSlotPos() == -1 )
         element_ArrList.get(nListHeight*3).setVisible(false);
      if( element_ArrList.get(nListHeight*3+1).getSlotPos() == -1 )
         element_ArrList.get(nListHeight*3+1).setVisible(false);
      if( element_ArrList.get(nListHeight*3+2).getSlotPos() == -1 )
         element_ArrList.get(nListHeight*3+2).setVisible(false);
      
      for(Element spot : element_ArrList){
         if( nLSlotFlag == spot.getFlag() || nRSlotFlag == spot.getFlag()){
            spot.setListPosY(spot.getListPosY()-(EM_H+10));
            continue;
         }
         
         spot.setBounds(spot.getListPosX(), spot.getListPosY()-(EM_H+10), EM_W, EM_H);
         spot.setListPosY(spot.getListPosY()-(EM_H+10));
      }
      
      nListHeight++;
   }
   
   //리스트에 있는 원소들을 한줄씩 내린다.
   public void ListDown(){
      if( nListHeight == 0 )
         return;
         
      if( element_ArrList.get((nListHeight-1)*3).getSlotPos() == -1 )
         element_ArrList.get((nListHeight-1)*3).setVisible(true);
      if( element_ArrList.get((nListHeight-1)*3+1).getSlotPos() == -1 )
         element_ArrList.get((nListHeight-1)*3+1).setVisible(true);
      if( element_ArrList.get((nListHeight-1)*3+2).getSlotPos() == -1 )
         element_ArrList.get((nListHeight-1)*3+2).setVisible(true);
      
      for(Element spot : element_ArrList){
         if( nLSlotFlag == spot.getFlag() || nRSlotFlag == spot.getFlag()){
            spot.setListPosY(spot.getListPosY()+(EM_H+10));
            continue;
         }
         spot.setBounds(spot.getListPosX(), spot.getListPosY()+(EM_H+10), EM_W, EM_H);
         spot.setListPosY(spot.getListPosY()+(EM_H+10));
      }
      
      nListHeight--;
   }
   
   private class GameListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();
         
         //메뉴버튼을 눌렀을 때 메뉴 씬으로 이동
         if( obj == btnMenu ){
            Scene.getInstance().selectScene("Menu");
         //융합버튼 눌렀을 때 조합을 시도
         }else if( obj == btnSum ){
            Sum();
         //조합 성공시에 보여지는 버튼을 눌렀을 때 다시 게임화면으로 복귀
         }else if( btnSumOk == obj){
            SumElement.setVisible(false);
            btnSumOk.setVisible(false);
            SumElementName.setVisible(false);
            SumBackImg.setVisible(false);
            btnSum.setVisible(true);
         }
      }
   }
   
   private class GameMListener implements MouseListener
   {
      public void mouseClicked(MouseEvent event) {
         
      }
      
      //마우스를 눌렀을때 버튼을 눌린 이미지로 변경한다.
      public void mousePressed(MouseEvent event) {
         Object obj = event.getSource();

         if( btnMenu == obj ){
            btnMenu.setIcon(new ImageIcon("imgs/menu_down.png"));
         }else if( btnUp == obj ){
            btnUp.setIcon(new ImageIcon("imgs/btnUp_down.png"));
         }else if( btnDown == obj ){
            btnDown.setIcon(new ImageIcon("imgs/btnDown_down.png"));
         }else if ( btnSum == obj ) {
         	btnSum.setIcon(new ImageIcon("imgs/btnSum_down.png"));
         }
      }
      
      //마우스를 뗐을때 버튼을 본래의 이미지로 복귀시킨다.
      public void mouseReleased(MouseEvent event) {
         Object obj = event.getSource();

         if( btnUp == obj ){
            ListDown();
         }
         if( btnDown == obj ){
            ListUp();
         }
         btnMenu.setIcon(new ImageIcon("imgs/menu.png"));
         btnUp.setIcon(new ImageIcon("imgs/btnUp.png"));
         btnDown.setIcon(new ImageIcon("imgs/btnDown.png"));
         btnSum.setIcon(new ImageIcon("imgs/btnSum.png"));
      }
      public void mouseEntered(MouseEvent event) {

      }
      public void mouseExited(MouseEvent event) {}
   }
   
   public void paintComponent(Graphics page){
      super.paintComponent(page);
      
      
   }
}