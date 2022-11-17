import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class GraphFrame extends JFrame {
    String title = "각 대륙별 공항 개수";
    Continent[] continents = new Continent[7];
    Integer[] numOfAirport = new Integer[7];
    int barStartX = 150; //첫번째 버튼이 시작하는 X좌표
    int barStartY; //버튼이 그려지는 Y좌표
    int barWidth = 50;  //막대 너비
    int distance = 150; //막대 간격
    public GraphFrame(){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //화면 크기 저장
        setDefaultCloseOperation(EXIT_ON_CLOSE); //끄기버튼 활성화
        setSize(1300, d.height*8/10);
        this.barStartY = d.height*7/10;
        this.setLocation((d.width/2)-this.getWidth()/2, (d.height/2)-this.getHeight()/2);

        for(int i = 0; i<7; i++){ //대륙별 공항 개수 정보 가져와서 저장
            continents[i] = ProjectMain.allContinent.get(MainFrame.cons[i]);
            numOfAirport[i] = continents[i].getNumAirport();
            //System.out.println(((Continent) ProjectMain.allContinent.get(ConList.cons[i])).myCountry.get(0).getEngName());
        }
        GraphPanel myGraphPanel = new GraphPanel(this);
        RoundedButton[] conBtn = new RoundedButton[7];
        Font btnFont = new Font("맑은 고딕", Font.BOLD, 20 );
        for(int i = 0; i<7; i++){ //버튼 생성 및 Panel에 추가
            conBtn[i] = new RoundedButton(MainFrame.cons[i]);
            conBtn[i].setLocation(barStartX+distance*i, barStartY+50);
            conBtn[i].setSize(100,50);
            conBtn[i].setBackground(Color.darkGray);
            conBtn[i].setForeground(Color.white);
            conBtn[i].setFont(btnFont);
            conBtn[i].setActionCommand(MainFrame.cons[i]);
            int finalI = i;
            conBtn[i].addActionListener(e -> {
                GraphByCountry gc = new GraphByCountry(this, e.getActionCommand(),continents[finalI], GraphByCountry.colorList[finalI] );
                gc.setVisible(true);
            });
            myGraphPanel.add(conBtn[i]);
        }
        RoundedButton btnBack = new RoundedButton();
        btnBack.setText("뒤로가기");
        Font backBtnFont = new Font("맑은 고딕", Font.BOLD, 30);
        btnBack.setFont(backBtnFont);
        btnBack.setForeground(Color.white);
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setLocation(30, 10);
        btnBack.setSize(150, 40);
        btnBack.addActionListener(e -> {
            new ShowContinent();
            setVisible(false);
        });
        myGraphPanel.add(btnBack);
        myGraphPanel.setLayout(null);
        myGraphPanel.setBackground(Color.white);
        setContentPane(myGraphPanel);

        RoundedButton saveBtn = new RoundedButton("저장");
        saveBtn.setSize(100,50);
        saveBtn.setLocation(1150, 0);
        saveBtn.setBackground(Color.PINK);
        saveBtn.addActionListener(e -> {
            JFileChooser c = new JFileChooser();
            File nf;
            String dir;
            //필터링될 확장자
            FileNameExtensionFilter filter = new FileNameExtensionFilter("csv 파일", "csv");

            //필터링될 확장자 추가
            c.addChoosableFileFilter(filter);
            int rVal = c.showSaveDialog(this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                try {
                    dir = c.getSelectedFile() + "";
                    nf = new File(dir);

                    BufferedWriter bw = new BufferedWriter(new FileWriter(nf));
                    bw.write("대륙, 공항 개수");
                    for(Continent ct: this.continents){
                        bw.write("\n");
                        bw.write(ct.getName()+","+ct.getNumAirport());
                    }


                    bw.close();
                    File selectedFile = c.getSelectedFile();
                    System.out.println(selectedFile.getPath().toString());
                    File moveTo = new File(selectedFile.getPath().toString()+".csv");
                    selectedFile.renameTo(moveTo);

                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        add(saveBtn);

        setVisible(true);


    }
}
class GraphPanel extends JPanel{
    GraphFrame gf;
    public GraphPanel(GraphFrame gf){
        this.gf = gf;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i<7; i++){
            Font titleFont = new Font("맑은 고딕", Font.BOLD, 50 );
            g.setFont(titleFont);
            g.drawString(gf.title, 600, 110);
            g.setColor(Color.cyan);
            g.fillRect(+gf.barStartX + gf.distance*i, gf.barStartY -gf.numOfAirport[i]*4/5,gf.barWidth, gf.numOfAirport[i]*4/5);
            g.setColor(Color.black);
            Font countFont = new Font("맑은 고딕", Font.ITALIC, 25 );
            g.setFont(countFont);
            g.drawString(gf.numOfAirport[i].toString(),gf.barStartX+10 + gf.distance*i, gf.barStartY -gf.numOfAirport[i]*4/5 );
        }
    }
}
class RoundedButton extends JButton { //예쁜 버튼

    public RoundedButton() {
        super();
        decorate();
    }

    public RoundedButton(String text) {
        super(text);
        decorate();
    }

    public RoundedButton(Action action) {
        super(action);
        decorate();
    }

    public RoundedButton(Icon icon) {
        super(icon);
        decorate();
    }

    public RoundedButton(String text, Icon icon) {
        super(text, icon);
        decorate();
    }

    protected void decorate() {
        setBorderPainted(false);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            graphics.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            graphics.setColor(getBackground().brighter());
        } else {
            graphics.setColor(getBackground());
        }

        graphics.fillRoundRect(0, 0, width, height, 10, 10);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();

        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

        graphics.setColor(getForeground());
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);
        graphics.dispose();

        super.paintComponent(g);
    }
}

