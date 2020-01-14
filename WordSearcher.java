import edu.princeton.cs.algs4.StdOut;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * WordSearcher/Shakespeare Concordance Application
 * Jan 13, 2020
 * Elven Shum
 */

public class WordSearcher {
    private JTextField wordSearch;
    private JPanel panel1;
    private JTextArea occurOutput;
    private JButton OKButton;
    private JButton resetButton;
    private static ConcHashTable currHash;


    public WordSearcher() {
        //searches for word!
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String searchedWord = wordSearch.getText();
                String occurrences = currHash.getStringVals(searchedWord);
                occurOutput.append("\n\"" + searchedWord + "\" occurrences:\n" + occurrences);
            }
        });

        //resets the text box
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                occurOutput.setText("");
            }
        });
    }

    public static void main(String[] args) {
        //create HashTable
        int currCapacity = 5 * 30000;
        currHash = new ConcHashTable(currCapacity);
        ConcHashTableRunner.loadText(currHash);
        currHash.consolePrintHash();

        //make WordSearcher Program
        JFrame frame = new JFrame("WordSearcher");
        frame.setContentPane(new WordSearcher().panel1);
//        frame.add(new JScrollPane());
        frame.setPreferredSize(new Dimension(400, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
