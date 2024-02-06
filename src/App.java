import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import pack.Quiz;

public class App extends JFrame implements ActionListener, Runnable {

    JPanel topPanel, rightPanel, leftPanel, bottomPanel, middlePanel, homePanel, questionPanel, resultPanel, gridPanel, resultTopPanel, resultViewPanel, resultReviewPanel;
    JLabel title, timer, questionHeadingLabel, questionLabel, resultHeadingLabel, resultLabel, resultReviewLabel, instructionLabel;
    JButton startQuiz, logoButton, save; 
    JCheckBox option1, option2, option3, option4;
    String time = "00 ", question[], correctAnswer[], options[][], selectedCheckBox = "", selectedAnswer[], resultReview = "";
    int second = 20, milliSecond = 0, index = 0, quesNo = 1, width, mark = 0, number = 1;
    boolean on = false;
    Image logoImage;
    Icon logo;
    CardLayout card;
    Quiz quiz;
    GridBagConstraints gbc;
    Dimension dimension, invisibleRigidDimension;
    Font letterFont;
    Component invisibleComponent1, invisibleComponent2, invisibleComponent3;
    JScrollPane scrollPane, scrollPane1;

    public App() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) dimension.getWidth();

        quiz = new Quiz();
        question = quiz.getQuestions();
        correctAnswer = quiz.getCorrectAnswer();
        options = quiz.getOptions();
        selectedAnswer = new String[10];

        try{
            logoImage = ImageIO.read(new File("image/GKQuest.png"));
            logoImage = logoImage.getScaledInstance(70, 50, Image.SCALE_DEFAULT); 
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }

        topPanel = new JPanel(new BorderLayout());
        logo = new ImageIcon(logoImage);
        logoButton = new JButton(logo);
        logoButton.addActionListener(this);
        logoButton.setFocusPainted(false);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setActionCommand("Home");
        topPanel.add(logoButton,BorderLayout.WEST);
        title = new JLabel("GK QUEST");
        title.setFont(new Font("serif", Font.BOLD, 100));
        title.setForeground(Color.WHITE);
        topPanel.add(title, BorderLayout.CENTER);
        timer = new JLabel(time);
        timer.setFont(new Font("serif", Font.BOLD, 70));
        timer.setForeground(Color.WHITE);
        topPanel.add(timer, BorderLayout.EAST);
        topPanel.setBackground(Color.BLUE);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLUE);
        
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE);
        
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLUE);

        card = new CardLayout();
        middlePanel = new JPanel(card);
        middlePanel.setBackground(Color.WHITE);

        homePanel = new JPanel(new FlowLayout());
        instructionLabel = new JLabel();
        instructionLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
        instructionLabel.setText("<HTML><p><font size = 10><b>Instruction:</b></font></p><br><p>&emsp;&emsp;1. To take the quiz press start.</p><p>&emsp;&emsp;2. Choose your answer within 20 seconds.</p><p>&emsp;&emsp;3. Click save to save your answer and to move on to next question.</p><p>&emsp;&emsp;4. Click submit to submit your answer.</p><p>&emsp;&emsp;5. Don't worry even if you didn't save your answer within time.</p><p>&emsp;&emsp;6. Your answer will be saved automatically once the time is up.</p><p>&emsp;&emsp;7. Once you have submited your answer you will be taken to result page.</p><p>&emsp;&emsp;&emsp;i] You can see your mark.</p><p>&emsp;&emsp;&emsp;i] You can review your wrong and correct answers.</p></p></HTML>");
        invisibleComponent3 = Box.createHorizontalStrut(width - 20);
        invisibleRigidDimension = new Dimension((width - 10), 50);
        invisibleComponent3 = Box.createRigidArea(invisibleRigidDimension);
        startQuiz = new JButton("Start Quiz");
        startQuiz.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        startQuiz.addActionListener(this);
        homePanel.add(instructionLabel);
        homePanel.add(invisibleComponent3);
        homePanel.add(startQuiz);
        middlePanel.add("Home Panel", homePanel);

        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBackground(Color.WHITE);
        gridPanel =  new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(gridPanel);
        gbc = new GridBagConstraints();
        questionHeadingLabel = new JLabel();
        questionHeadingLabel.setFont(new Font("serif", Font.BOLD, 50));
        questionLabel = new JLabel();
        letterFont = new Font("serif", Font.PLAIN, 30);
        questionLabel.setFont(letterFont);
        questionLabel.setSize(width, 50);
        option1 = new JCheckBox();
        option1.setBackground(Color.WHITE);
        option1.setFont(letterFont);
        option2 = new JCheckBox();
        option2.setFont(letterFont);
        option2.setBackground(Color.WHITE);
        option3 = new JCheckBox();
        option3.setFont(letterFont);
        option3.setBackground(Color.WHITE);
        option4 = new JCheckBox();
        option4.setFont(letterFont);
        option4.setBackground(Color.WHITE);
        option1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Checkbox is selected
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    selectedCheckBox = checkBox.getText();
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);
                } else {
                    // Checkbox is not selected
                    selectedCheckBox = "";
                    option2.setEnabled(true);
                    option3.setEnabled(true);
                    option4.setEnabled(true);
                }
            }
        });
        option2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Checkbox is selected
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    selectedCheckBox = checkBox.getText();
                    option1.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);
                } else {
                    // Checkbox is not selected
                    selectedCheckBox = "";
                    option1.setEnabled(true);
                    option3.setEnabled(true);
                    option4.setEnabled(true);
                }
            }
        });
        option3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Checkbox is selected
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    selectedCheckBox = checkBox.getText();
                    option2.setEnabled(false);
                    option1.setEnabled(false);
                    option4.setEnabled(false);
                } else {
                    // Checkbox is not selected
                    selectedCheckBox = "";
                    option2.setEnabled(true);
                    option1.setEnabled(true);
                    option4.setEnabled(true);
                }
            }
        });
        option4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Checkbox is selected
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    selectedCheckBox = checkBox.getText();
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option1.setEnabled(false);
                } else {
                    // Checkbox is not selected
                    selectedCheckBox = "";
                    option2.setEnabled(true);
                    option3.setEnabled(true);
                    option1.setEnabled(true);
                }
            }
        });
        // selectedAnswer = new String[10];
        save = new JButton("Save");
        save.addActionListener(this);
        invisibleComponent1 = Box.createHorizontalStrut(width - 40);
        invisibleComponent2 = Box.createHorizontalStrut(width - 40);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 50; 
        gbc.anchor = GridBagConstraints.CENTER;
        gridPanel.add(questionHeadingLabel, gbc);
        gbc.gridy = 1;
        gbc.ipady = 30;        
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(questionLabel, gbc);
        gbc.gridy = 3;
        gbc.ipady = 10;
        gbc.gridheight = 1;
        gridPanel.add(option1, gbc);
        gbc.gridy = 4;
        gridPanel.add(option2, gbc);
        gbc.gridy = 5;
        gridPanel.add(option3, gbc);
        gbc.gridy = 6;
        gridPanel.add(option4, gbc);
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gridPanel.add(save, gbc);
        gbc.gridy = 8;
        gbc.ipady = 50;        
        gbc.anchor = GridBagConstraints.SOUTH;
        gridPanel.add(invisibleComponent1, gbc);
        gbc.gridy = 9;
        gridPanel.add(invisibleComponent2, gbc);
        questionPanel.add(scrollPane);
        middlePanel.add("Question Panel", questionPanel);

        resultPanel = new JPanel(new BorderLayout());
        resultTopPanel = new JPanel(new BorderLayout());
        resultHeadingLabel = new JLabel("Result");
        resultHeadingLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        resultTopPanel.add(resultHeadingLabel);
        resultTopPanel.setBackground(Color.LIGHT_GRAY);
        resultLabel = new JLabel();
        resultLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        resultLabel.setForeground(Color.BLUE);
        resultTopPanel.add(resultLabel, BorderLayout.EAST);
        resultReviewPanel = new JPanel(new BorderLayout());
        resultReviewPanel.setBackground(Color.WHITE);
        scrollPane1 = new JScrollPane(resultReviewPanel);
        resultReviewLabel = new JLabel();
        resultReviewLabel.setFont(letterFont);
        resultReviewPanel.add(resultReviewLabel, BorderLayout.WEST);
        resultPanel.add(resultTopPanel, BorderLayout.NORTH);
        resultPanel.add(scrollPane1);
        middlePanel.add("Result Panel", resultPanel);

        add(topPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public void showQuestion(){
            questionHeadingLabel.setText("Question " + quesNo);
            questionLabel.setText(quesNo + ". " + question[index]);
            option1.setText(options[index][0]);
            option2.setText(options[index][1]);
            option3.setText(options[index][2]);
            option4.setText(options[index][3]);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand() == "Start Quiz"){
            showQuestion();
            card.show(middlePanel, "Question Panel");
    		on = true;
            mark = 0;
            new Thread(this, "StopWatch1").start();
        }
        if(ae.getActionCommand() == "Home"){
            on = false;
            timer.setText("00 ");
            timer.setForeground(Color.WHITE);
            second = 20;
            milliSecond = 0;
            index = 0;
            quesNo = 1;
            resultReview = "";
            number = 1;
            resetOf();
            card.show(middlePanel, "Home Panel");
        }
        if(ae.getActionCommand() == "Save") {
            saveAnswer();         
        }
        if(ae.getActionCommand() == "Submit") {
            submit();    
        }
    }

    public void setResult(){
        for(int i = 0; i < 10; i++) {
            if(correctAnswer[i] == selectedAnswer[i]) {
                ++mark;
            }
            if(correctAnswer[i] == selectedAnswer[i]){
                resultReview = resultReview + "<p>" + number++ + ". " + question[i] + "</p>" + "<p><font size = 6 color = \"blue\">&emsp;&emsp;Correct answer: " + correctAnswer[i] + "</font></p>"+ "<p><font size = 6 color=\"green\">&emsp;&emsp;Selected answer: " + selectedAnswer[i] + "</font></p>";
            }
            else{
                resultReview = resultReview + "<p>" + number++ + ". " + question[i] + "</p>" + "<p><font size = 6 color = \"blue\">&emsp;&emsp;Correct answer: " + correctAnswer[i] + "</font></p>"+ "<p><font size = 6 color=\"red\">&emsp;&emsp;Selected answer: " + selectedAnswer[i] + "</font></p>";
            }
        }
        resultLabel.setText("Mark: " + (mark * 10) + " ");
        resultReviewLabel.setText("<HTML>" + resultReview + "</HTML>");
    }

    public void saveAnswer() {        
        selectedAnswer[index] = selectedCheckBox;
        resetOf();
        on = false;
        timer.setText("00 ");
        second = 20;
        milliSecond = 0;
        index++;
        quesNo++;
        if(index == 9){
            save.setText("Submit");
        }
        if(index == 10){
            submit();
        }
        else{
            on = true;
            showQuestion();
        }

    }

    public void resetOf(){
        selectedCheckBox = "";
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
        option1.setSelected(false);
        option2.setSelected(false);
        option3.setSelected(false);
        option4.setSelected(false);
    }

    public void submit(){
        if(index < 10)
            selectedAnswer[index] = selectedCheckBox;
        save.setText("Save");
        on = false;
        timer.setText("00 ");
        timer.setForeground(Color.WHITE);
        second = 20;
        milliSecond = 0;
        setResult();
        card.show(middlePanel, "Result Panel");
        }

    public void run(){
        while(second > 0 && on == true){
            try{
                Thread.sleep(1);
                startWatch();
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    public void startWatch(){
        timer.setForeground(Color.MAGENTA);
        milliSecond++; 
        if (milliSecond == 500) { 
            milliSecond = 0; 
            second--; 
        }
        if(second < 10) {
            timer.setText("0" + second + " ");
        }
        else{
            timer.setText(second + " ");
        }
        if(second == 0){
            timer.setForeground(Color.RED);
            on = false; 
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            try{
                Thread.sleep(2000);
                }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
            saveAnswer();
        }   
    }
    
    public static void main(String args[]){
        new App();
    }
}