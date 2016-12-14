import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameScene extends JPanel
{
	//��ũ�� ũ�⸦ ����
   private final int WIDTH = 800, HEIGHT = 600;
   //���� �̹��� ũ�⸦ ���� 
   private final int EM_W = 100, EM_H = 100;
   //���� �ִ� ������ ���� 
   private final int EM_NUMBER = 32;
   //������ ���� ������ ������ ���� 
   private final int EM_MIX_NUMBER = 28;
   //������(���а���Ƚ��)�� ���� 
   private final int LIFE_MAX = 10;
   
   /*
   *nScore 		: ���ھ ����
   *nLifeCnt 	: �������� ����
   *nEmNum		: ���� ���� ������ ����
   *nLSlotFlag 	: ���� ���Կ� ����� ���� flag�� ����
   *nRSlotFlag 	: ������ ���Կ� ����� ���� flag�� ����
   *nComZorder	: Component���� z-order�� �����ϴµ� ���
   *nListHeight	: ����Ʈ�� ��ġ�� ����
   *IsLSlot		: ���� ������ ��ġ ���θ� ����
   *IsRSlot		: ������ ������ ��ġ���θ� ���� 
   */
   
   private int nScore, nLifeCnt, nEmNum;
   private int nLSlotFlag, nRSlotFlag, nComZorder, nListHeight;

   private boolean IsLSlot, IsRSlot;
   
   //��� ������ ���� ����Ʈ�� �����Ѵ�. ���� �� ���Ҹ� �ǹ��ϸ� ���� ���ո���Ʈ�� �ǹ��Ѵ�.
   private int [][] EM_LIST;
   //ȹ���� ���Ҹ� �����Ѵ�.
   private int [] EM_GET;
   //��� ���� �̸����� �����Ѵ�.
   private String [] EM_NAME;
   //�߰��� ���ҵ��� ��ü�� �����Ѵ�.
   private ArrayList<Element> element_ArrList;
   //���յ� ���Ҹ� �����ϰ� ������ �����ִ� ���� ��ü.
   private Element SumElement;
   
   /*
    * bkgndimg	: ����̹����� ������ ��
    * userScore : ����ڰ� ȹ���� ���ھ ������ ��
    * totalScore: �� ���ھ ������ ��
    * lifeText	: Life : <- �ؽ�Ʈ�� ������ ��
    * lifeCount : ���� ������ ������ ������ ��
    * LSlot		: ���� ������ ������ ��
    * RSlot		: ������ ������ ������ ��
    * resultText: ���� ����� ������ ��
    * SumElementName : ���� ������ �����̸��� ������ ��
    * SumBackImg: ���� ������ ����̹����� ������ ��
    * btnMenu	: �޴� ��ư�� ������ ��
    * btnSum	: ���� ��ư�� ������ ��
    * btnUp		: ����Ʈ �ø��� ��ư�� ������ ��
    * btnDown	: ����Ʈ ������ ��ư�� ������ ��
    * btnSumOk	: ���� ������ ������ ��
    */
   
   private JLabel bkgndimg, userScore, totalScore, lifeText, lifeCount;
   private JLabel LSlot, RSlot, resultText, SumElementName, SumBackImg;
   private JButton btnMenu, btnSum, btnUp, btnDown, btnSumOk;
   
   /*
    * fileIO 		: ������ �����͸� ���ۿ� �о���µ� ���Ǵ� ��ü
    */
   BufferedReader   fileIO;
   
   //�׼� ������
   private GameListener gameL;
   //���콺 ������
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
   
   //���� �����Ҷ� �����Ǵ� �������� �ʱ�ȭ�ϴ� �޼ҵ�
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
      
      //�ʱ��� ���Ҵ� 4���� �߰��Ѵ�.
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
   
   //��������½� ���Ǵ� �޼ҵ�
   public void FileInputOutput(String list, String description) throws IOException {
      fileIO = new BufferedReader(new FileReader(list));
      int y = 0;
      
      
      /*
       * ���� �д� ����
       * 1.�Ӽ�(PIVOT, ADD, RESULT)�� �д´�.
       * 2.�Ӽ��� ���� ����Ǵ� �迭 ��ġ�� �����ϰ� �о���� ���� �����Ѵ�.
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
      
      //�����̸����� ���������� �ִ´�. ���Ͽ��� ���������� �����̸��� ����Ǿ��ִ�.
      while(true){
         String line = fileIO.readLine();
            if (line==null) break;
            EM_NAME[y] = line;
            y++;
      }
      
      fileIO.close();
   }
   
   
   //���Ҹ� �߰��ϴ� �޼ҵ�
   public void Addelement(int flag){
	   // ���� ����Ʈ�� �� ������ X, Y���� �����Ѵ�.
      int nEmLyX = (EM_W) * ((nEmNum-(nListHeight*4)) % 3) + 25 * ((nEmNum-(nListHeight*4)) % 3 + 1);
      int nEmLyY = 50 + 10 * (nEmNum / 3 + 1) + ((nEmNum / 3) * EM_H);
      
      //���Ҹ� �߰��Ѵ�.
      Element element = new Element(flag);
      element.setBounds(nEmLyX,nEmLyY,EM_W,EM_H);
      element.setListPos(nEmLyX, nEmLyY);
      element.setgameScene(this);
      add(element);
      element_ArrList.add(element);
        
      //ȹ���� ���� �迭�� ���� �ε����� �����ϰ�, ȹ�� ���� ������ ������Ų��.
      EM_GET[nEmNum] = flag;
      nEmNum++;
       
      //Slot�� ����̹����� ���Һ��� �׻� �Ʒ��� �־���ϹǷ� �ٽ� z-order�� �������ش�.
      setComponentZOrder(LSlot, nComZorder+nEmNum);
      setComponentZOrder(RSlot, nComZorder+nEmNum);
      setComponentZOrder(bkgndimg, nComZorder+nEmNum);
      //setComponentZOrder(resultText, nComZorder+nEmNum);
   }
   
   //flag�� �ش��ϴ� ������ Slot�� �����Ѵ�.
   public void setSlot(int flag){
      for(Element spot : element_ArrList){
    	 //flag�� �ش��ϴ� element�� ã��
         if( spot.getFlag() == flag ){
        	 //�Ѵ� �������� �޼ҵ带 �����Ѵ�.
            if( IsLSlot && IsRSlot )
               return;
               
            //����ִ� ���� ã�� �ִ´�.
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
   
   
   //flag�� �ش��ϴ� ������ �ٽ� ����Ʈ�� ���ͽ�Ų��.
   public void dropSlot(int flag){
      for(Element spot : element_ArrList){
         if( spot.getFlag() == flag ){
        	 //���Ұ� ��� �������� ã�´�.
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
            
            //������ ����Ʈ ��ǥ�� ����Ʈ �����̸� visible�� false�� ������ �Ⱥ��̰� �Ѵ�.
            if( spot.getListPosY() < 60 )
               spot.setVisible(false);
               
            //���Ҹ� ����Ʈ�� �ٽ� ���ͽ�Ų��.
            spot.setBounds(spot.getListPosX(),spot.getListPosY(),EM_W,EM_H);
            
            return;
         }
      }
   }
   
   //���ھ ������Ų��.
   public void AddScore(int score){
      nScore += score;
      userScore.setText(String.valueOf(nScore));
   }
   
   //�������� mount��ŭ ���δ�.
   public void DecreaseLife(int mount){
      nLifeCnt -= mount;
      lifeCount.setText(String.valueOf(nLifeCnt));
      
      //�������� 0�̵ǰų� ��� ������ �����ϸ� Ending Scene���� �̵��Ѵ�.
      if( nLifeCnt <= 0 || nScore == EM_NUMBER ){
         Scene.getInstance().selectScene("Ending");
         //ȹ���� ���� ����, ȹ���� ���� �ε������ ��� ������ �̸��� Ending Scene�� �����Ѵ�. 
         Scene.getInstance().getEndingScene().setElementCnt(nEmNum);
         Scene.getInstance().getEndingScene().setElementName(EM_NAME);
         Scene.getInstance().getEndingScene().setElementList(EM_GET);
         Init();
      }
   }
   
   //������ ���������� ȣ��Ǵ� �޼ҵ�
   public void Mix_Success(int Index){
	  //Slot�� ����ִ� ���·� �����.
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      
      //���ھ �����ϳ�.
      AddScore(1);
          
      //���� �󺧸� ����Ѵ�
      resultText.setText("SUCCESS !");
      resultText.setVisible(true);
      
      
      //���ռ����� ���Ҹ� �߰��Ѵ�.
      Addelement(EM_LIST[Index][2]);
   
      //�����Ͽ� ������ ���Ҹ� ���, ��ư�� �Բ� �����ش�.
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
   
   //������ �õ������� �̹� ������ �� ȣ��Ǵ� �޼ҵ�
   public void Mix_Exist(){
      resultText.setText("EXIST ELEMENT!");
      resultText.setVisible(true);
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      DecreaseLife(1);
   }
   
   //������ �õ������� �Ұ����� �� ȣ��Ǵ� �޼ҵ�
   public void Mix_Impossible(){
      resultText.setText("IMPOSSIBLE !");
      resultText.setVisible(true);
      dropSlot(nLSlotFlag);
      dropSlot(nRSlotFlag);
      DecreaseLife(1);
   }
   
   //������ �õ��ϴ� �޼ҵ�
   public void Sum(){
	  //�� �߿� �ϳ��� ��������� �����Ѵ�.
      if( nLSlotFlag == -1 || nRSlotFlag == -1 ){
         return;
      }
      
      //���Կ� �ִ� ���ҿ� ���ո���Ʈ�� ���Ͽ� ����� �����Ѵ�.
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
   
   //����Ʈ�� �ִ� ���ҵ��� ���� ���پ� �ø���.
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
   
   //����Ʈ�� �ִ� ���ҵ��� ���پ� ������.
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
         
         //�޴���ư�� ������ �� �޴� ������ �̵�
         if( obj == btnMenu ){
            Scene.getInstance().selectScene("Menu");
         //���չ�ư ������ �� ������ �õ�
         }else if( obj == btnSum ){
            Sum();
         //���� �����ÿ� �������� ��ư�� ������ �� �ٽ� ����ȭ������ ����
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
      
      //���콺�� �������� ��ư�� ���� �̹����� �����Ѵ�.
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
      
      //���콺�� ������ ��ư�� ������ �̹����� ���ͽ�Ų��.
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