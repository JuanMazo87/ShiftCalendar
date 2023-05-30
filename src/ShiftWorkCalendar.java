import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class ShiftWorkCalendar {
    private LocalDate startDate;

    public ShiftWorkCalendar(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getShift(LocalDate date) {
        long daysDiff = ChronoUnit.DAYS.between(startDate, date);
        long remainder = daysDiff % 9;
        long dayOfShift = remainder % 3 + 1;

        String shiftType;
        if (remainder < 3) {
            shiftType = "Late";
        } else if (remainder < 6) {
            shiftType = "Off";
        } else {
            shiftType = "Early";
        }

        return String.format("%s shift, day %d", shiftType, dayOfShift);
    }

    public static void main(String[] args) {
        ShiftWorkCalendar calendar = new ShiftWorkCalendar(LocalDate.of(2023, 5, 16));

        JFrame frame = new JFrame("Shift Work Calendar");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, calendar);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, ShiftWorkCalendar calendar) {
        panel.setLayout(null);

        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(10, 20, 30, 25);
        panel.add(dayLabel);

        JTextField dayText = new JTextField(2);
        dayText.setBounds(40, 20, 30, 25);
        panel.add(dayText);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(80, 20, 50, 25);
        panel.add(monthLabel);

        JComboBox<Month> monthComboBox = new JComboBox<>(Month.values());
        monthComboBox.setBounds(130, 20, 100, 25);
        panel.add(monthComboBox);
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(240, 20, 30, 25);
        panel.add(yearLabel);

        JTextField yearText = new JTextField(4);
        yearText.setBounds(270, 20, 60, 25);
        panel.add(yearText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 80, 80, 25);
        panel.add(submitButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 80, 80, 25);
        panel.add(exitButton);

        JLabel infoLabel = new JLabel("");
        infoLabel.setBounds(10, 110, 300, 25);
        panel.add(infoLabel);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int day = Integer.parseInt(dayText.getText().trim());
                    Month month = (Month) monthComboBox.getSelectedItem();
                    int year = Integer.parseInt(yearText.getText().trim());

                    if (day < 1 || day > 31 || year < 2023 || year > 2030) {
                        throw new NumberFormatException();
                    }

                    LocalDate date = LocalDate.of(year, month, day);
                    String shift = calendar.getShift(date);
                    infoLabel.setText("Shift on " + date + ": " + shift);
                } catch (NumberFormatException exception) {
                    infoLabel.setText("Please enter a valid day (1-31) and year (2023-2030).");
                } catch (DateTimeException exception) {
                    infoLabel.setText("Invalid date. Please check the day and month.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

