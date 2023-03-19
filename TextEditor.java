 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class TextEditor implements ActionListener {

    JFrame f;
    JMenuBar menuBar;
    JMenu file,edit,themes,help;
    JTextArea textArea;
    JScrollPane scroll;
    JMenuItem darkTheme,defaultTheme,save,open,close,cut,copy,paste,New,selectAll,AboutHelp,fontSize;
    JPanel saveFileOptionWindow;
    JLabel Filename, directory;
    JTextField fileName, dirName;

    TextEditor(){
        f = new JFrame("Text Editor"); 
        menuBar = new JMenuBar();

        file = new JMenu("File");
        edit = new JMenu("Edit");
        themes = new JMenu("Themes");
        help = new JMenu("Help");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(themes);
        menuBar.add(help);
        f.setJMenuBar(menuBar);

        save = new JMenuItem("Save");
        open = new JMenuItem("Open");    
        New = new JMenuItem("New");
        close = new JMenuItem("Exit");
        file.add(open);
        file.add(New);
        file.add(save);
        file.add(close);

        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");     
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select all");
        fontSize = new JMenuItem("Font size");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(fontSize);

        darkTheme = new JMenuItem("Dark Theme");
        defaultTheme = new JMenuItem("Default Theme");
        themes.add(darkTheme);
        themes.add(defaultTheme);

        AboutHelp = new JMenuItem("About");
        help.add(AboutHelp);

        textArea = new JTextArea(32,88);
        f.add(textArea);

        scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scroll);

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        fontSize.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this); 
        New.addActionListener(this); 
        darkTheme.addActionListener(this);
        defaultTheme.addActionListener(this);
        AboutHelp.addActionListener(this);
        close.addActionListener(this); 

        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {}

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(f,"Do you want to exit?","Confirm Before Saving...",JOptionPane.YES_NO_OPTION);

                if (confirmExit == JOptionPane.YES_OPTION)
                    f.dispose();
                else if (confirmExit == JOptionPane.NO_OPTION)
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {}

            @Override
            public void windowIconified(WindowEvent windowEvent) {}

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {}

            @Override
            public void windowActivated(WindowEvent windowEvent) {}

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {}
        });

        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S && e.isControlDown())
                    saveTheFile();
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        };
        textArea.addKeyListener(k);
        f.setSize(1000,596);
        f.setResizable(true);
        f.setLocation(250,100);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==cut)
            textArea.cut();
        if (e.getSource()==copy)
            textArea.copy();
        if (e.getSource()==paste)
            textArea.paste();
        if (e.getSource()==selectAll)
            textArea.selectAll();
        if (e.getSource()==fontSize){

            String sizeOfFont = JOptionPane.showInputDialog(f,"Enter Font Size",JOptionPane.OK_CANCEL_OPTION);
                if (sizeOfFont != null){
                    int convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SANS_SERIF,Font.PLAIN,convertSizeOfFont);
                    textArea.setFont(font);
                }
        }

        if (e.getSource()==open){
            JFileChooser chooseFile = new JFileChooser();
            int i = chooseFile.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION){
                File file = chooseFile.getSelectedFile(); 
                String filePath = file.getPath();
                String fileNameToShow = file.getName();
                f.setTitle(fileNameToShow);

               try {
                   BufferedReader readFile = new BufferedReader(new FileReader(filePath));
                   String tempString1 = "";
                   String tempString2 = "";

                   while ((tempString1 = readFile.readLine()) != null)
                        tempString2 += tempString1 + "\n";

                   textArea.setText(tempString2);
                   readFile.close();
               }catch (Exception ae){
                   ae.printStackTrace();
               }
            }
        }

        if (e.getSource()==save) saveTheFile();

        if (e.getSource()==New) textArea.setText("");

        if (e.getSource()==close) System.exit(1);

        if (e.getSource()==darkTheme){
            textArea.setBackground(Color.DARK_GRAY);     
            textArea.setForeground(Color.WHITE);
        }

        if (e.getSource() == defaultTheme){
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.black);
        }

        if (e.getSource()==AboutHelp){
            try {
                textArea.setText("Let's Grow More\n \n Text Editor By Abhijeet Rathore\n \n ----- Thank You -----");
            }catch (Exception a){
                a.printStackTrace();
            }
        }

    }
    public void saveTheFile(){
        saveFileOptionWindow = new JPanel(new GridLayout(2,1));
        Filename = new JLabel("Filename      :- ");
        directory = new JLabel("Save File To :- ");
        fileName = new JTextField();
        dirName = new JTextField();

        saveFileOptionWindow.add(Filename);
        saveFileOptionWindow.add(fileName);
        saveFileOptionWindow.add(directory);
        saveFileOptionWindow.add(dirName);

        JOptionPane.showMessageDialog(f,saveFileOptionWindow); 
        String fileContent = textArea.getText();
        String filePath = dirName.getText();

        try {
            BufferedWriter writeContent = new BufferedWriter(new FileWriter(filePath));
            writeContent.write(fileContent);
            writeContent.close();
            JOptionPane.showMessageDialog(f,"File Successfully saved!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new TextEditor();
    }
}

