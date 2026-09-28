package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator translator = new JSONTranslator();

            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(new JLabel("Language:"));

            languagePanel.add(languageComboBox);

            JPanel countryPanel = new JPanel();

            CountryCodeConverter countryConverter = new CountryCodeConverter();

            String [] items = translator.getCountryCodes().toArray(new String[0]);
            for (int i = 0; i < items.length; i++) {
                items[i] = countryConverter.fromCountryCode(items[i]);
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(scrollPane, 1);


            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = countryConverter.fromCountry(list.getSelectedValue());

//                     for now, just using our simple translator, but
//                     we'll need to use the real JSON version later.

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
