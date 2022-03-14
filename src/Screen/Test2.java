package Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Test2 extends JFrame {

    public Test2() {
        // GUI
        setTitle("Test2");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // button
        JButton button = new JButton("Quit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton button2 = new JButton("Quit");
        JButton button3 = new JButton("Quit");
        JButton button4 = new JButton("Quit");
        JButton button5 = new JButton("Dialog");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(JOptionPane.showInputDialog(getContentPane(), "What is your name?"));
            }
        });

        //JLabel
        JLabel label = new JLabel("Hello World!");

        // JCheckBox
        JCheckBox cb = new JCheckBox("Ich bin eine JCheckBox");
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb_source = (JCheckBox) e.getSource();
                if (cb_source.isSelected()) {
                    // chang font
                    cb.setFont(new Font("Calibri", Font.ITALIC, 12));
                }
            }
        });

        //ComboBox
        String[] list = {"Auswahl1", "Auswahl12"};
        JComboBox<String> combo = new JComboBox<String>(list);
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    combo.addItem(e.getItem().toString());
                }
            }
        });

        // JProgressBar
        JProgressBar pb = new JProgressBar();
        pb.setValue(0);

        // JSlider
        JSlider slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                pb.setValue(value);
            }
        });

        //JtoggleBar
        JToggleButton tb = new JToggleButton("Ich bin togglebar");


        // JList
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        JScrollPane sp = new JScrollPane();
        JList<String> font_list = new JList<String>(ge.getAvailableFontFamilyNames());
        sp.setViewportView(font_list);

        //JTextArea
        JTextArea ta = new JTextArea("dsdhfdshdafsfh dg sfdg ");
        // erlaubt font und style bearbeitung

        // JTextPane
        JTextPane tp = new JTextPane();
        tp.setContentType("text/html");
        tp.setEditable(false);
        try {
            tp.setPage("https://www.google.com");
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Menubar
        ImageIcon icon = new ImageIcon("irgendwas.png");
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("open file", icon);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desk = Desktop.getDesktop();
                URL url = null;

                try {
                    url = new URL("https://www.youtube.com");
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }

                try {
                    desk.browse(url.toURI());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        file.add(open);

        menu.add(file);
        setJMenuBar(menu);

        //Layout
        Container pane = getContentPane();
        pane.setLayout(new FlowLayout());

        pane.add(label);
        pane.add(cb);

        pane.add(combo);

        pane.add(pb);
        pane.add(slider);

        pane.add(tb);

        pane.add(sp);

        pane.add(ta);
        pane.add(tp);

        pane.add(button);
        pane.add(button2);
        pane.add(button3);
        pane.add(button4);
        pane.add(button5);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Test2 m = new Test2();
                m.setVisible(true);
            }
        });
    }

}
