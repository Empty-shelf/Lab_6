package UI;

import Collection.Coordinates;
import Collection.Location;
import Collection.Route;
import Common.CommandShell;
import Connection.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class TablePanel extends JPanel implements Runnable {
    private static RoutesTableModel model = new RoutesTableModel();
    private JTable table;
    private Manager manager;
    private String login;

    private JButton exit;
    private JLabel user;
    private JLabel box_label;
    private String[] items_commands;
    private String[] items_fields;
    private JComboBox commands;
    private JComboBox sort;
    private JComboBox filter;
    private JComboBox sort_choose;
    private Panel top_panel;
    private JLabel sort_label;
    private JLabel filter_label;
    private JButton remove;
    private JButton visualize;

    public TablePanel(Manager manager, String login){
        this.manager = manager;
        this.login = login;
        model.updateData(manager);
        table = new JTable(model);
        setLayout(new GridBagLayout());
        (new Thread(this)).start();
    }


    private static Route getRoute(int row){
        Route route = new Route(Double.parseDouble((String) model.getValueAt(row, 1)),
                (String) model.getValueAt(row, 2),
                new Coordinates(Double.parseDouble((String) model.getValueAt(row, 3)),
                        Integer.valueOf((String)model.getValueAt(row, 4))), new Location(
                (String) model.getValueAt(row, 5), Double.parseDouble((String)
                model.getValueAt(row, 6)), Float.parseFloat((String)
                model.getValueAt(row, 7))), new Location(
                (String) model.getValueAt(row, 8), Double.parseDouble((String)
                model.getValueAt(row, 9)), Float.parseFloat((String)
                model.getValueAt(row, 10))), (String) model.getValueAt(row, 11));
        route.setId(Integer.parseInt((String) model.getValueAt(row, 0)));

        return route;
    }

    private void update(Route route){
        CommandShell com = new CommandShell("update");
        com.setLogin(login);
        com.setFirstArg(route);
        manager.getSender().send(com);
        AnswerWindow answer_window = new AnswerWindow(manager.check());
    }

    void init(){
        exit = new JButton("Exit");
        user = new JLabel("User: " + login);
        exit.addActionListener((e -> System.exit(0)));
        box_label = new JLabel("        Choose command:");

        items_commands = new String[]{"help", "info", "show", "add", "update", "remove_by_id", "clear", "execute_script",
                "remove_head", "add_if_min", "history", "group_counting_by_from", "filter_contains_name",
                "print_unique_distance"};
        commands = new JComboBox(items_commands);
        commands.addActionListener((e -> {
            manager.execute((String) commands.getSelectedItem());
        }));
        items_fields = new String[]{"id", "distance", "name", "coord_x", "coord_y", "loc_from", "from_x", "from_y",
        "loc_to", "to_x", "to_y", "owner"};

        sort = new JComboBox(items_fields);
        filter = new JComboBox(items_fields);

        sort_label = new JLabel("        Sort by field:");
        filter_label = new JLabel("        Filter by field:");
        sort_choose = new JComboBox(new String[]{"ascending", "descending"});
        sort_choose.setEnabled(false);

        sort.addActionListener((e -> {
            sort_choose.setEnabled(true);
        }));
        sort_choose.addActionListener((e1 -> {
            CommandShell com = new CommandShell("sort");
            com.setThirdArg(sort.getSelectedItem() + "," + sort_choose.getSelectedItem());
            com.setSecondArg(model.getColumnNumber((String) sort.getSelectedItem()));
            manager.getSender().send(com);
            InfoWindow w = new InfoWindow(" Sorted ", "info");
            sort_choose.setEnabled(false);
        }));

        filter.addActionListener((e -> {
            CommandShell com = new CommandShell("filter");
            SingleInputWindow in = new SingleInputWindow();
            in.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    CommandShell com = new CommandShell("get_routes");
                    manager.getSender().send(com);
                    ArrayList<String> res = new ArrayList<>();
                    ArrayList<String[]> f = model.filter((String) filter.getSelectedItem(), in.getString());
                    for (String[] s : f){
                            res.add(s[0]+", "+s[1]+", "+s[2]+", "+s[3]+", "+s[4]+", "+s[5]+", "+s[6]+", "+s[7]+", "+
                                    s[8]+", "+s[9]+", "+s[10]+", "+s[11]);
                    }
                    if(res.isEmpty()) {
                        InfoWindow info = new InfoWindow(" No matches found ", "info");
                    }else {
                        AnswerWindow w = new AnswerWindow(res);
                    }
                }
            });
        }));

        top_panel = new Panel();
        top_panel.setSize(1000, 50);
        top_panel.setLayout(new FlowLayout());
        top_panel.add(user);
        top_panel.add(box_label);
        top_panel.add(commands);
        top_panel.add(sort_label);
        top_panel.add(sort);
        top_panel.add(sort_choose);
        top_panel.add(filter_label);
        top_panel.add(filter);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public synchronized void mouseClicked(MouseEvent e) {
                try {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    if (!table.getValueAt(row, 11).equals(login)) {
                        InfoWindow info = new InfoWindow(" You aren't an owner ", "error");
                        return;
                    }
                    SingleInputWindow input = new SingleInputWindow();
                    input.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                String in = input.getString();
                                switch (column) {
                                    case 0: {
                                        InfoWindow info = new InfoWindow(" You can't change element's id", "info");
                                        return;
                                    }
                                    case 1: {
                                        double distance = Double.parseDouble(in);
                                        if (distance < 2) {
                                            InfoWindow w = new InfoWindow(" Distance have to be more than 1 ", "error");
                                            return;
                                        } else {
                                            Route route = getRoute(row);
                                            route.setDistance(distance);
                                            update(route);
                                        }
                                        break;
                                    }
                                    case 2: {
                                        if (in.trim().length() == 0) {
                                            InfoWindow w = new InfoWindow(" Route's name can't be empty ", "error");
                                            return;
                                        } else {
                                            Route route = getRoute(row);
                                            route.setName(in);
                                            update(route);
                                        }
                                        break;
                                    }
                                    case 3: {
                                        double x = Double.parseDouble(in);
                                        if (x <= -808) {
                                            InfoWindow w = new InfoWindow(" X coordinate have to be more than -808 ", "error");
                                            return;
                                        } else {
                                            Route route = getRoute(row);
                                            route.getCoordinates().setX(x);
                                            update(route);
                                        }
                                        break;
                                    }
                                    case 4: {
                                        Integer y = Integer.parseInt(in);
                                        Route route = getRoute(row);
                                        route.getCoordinates().setY(y);
                                        update(route);
                                        break;
                                    }

                                    case 5: {
                                        Route route = getRoute(row);
                                        route.getFrom().setName(in);
                                        update(route);
                                        break;
                                    }
                                    case 6: {
                                        Route route = getRoute(row);
                                        route.getFrom().setX(Double.parseDouble(in));
                                        update(route);
                                        break;
                                    }
                                    case 7: {
                                        Route route = getRoute(row);
                                        route.getFrom().setY(Float.parseFloat(in));
                                        update(route);
                                        break;
                                    }
                                    case 8: {
                                        Route route = getRoute(row);
                                        route.getTo().setName(in);
                                        update(route);
                                        break;
                                    }
                                    case 9: {
                                        Route route = getRoute(row);
                                        route.getTo().setX(Double.parseDouble(in));
                                        update(route);
                                        break;
                                    }
                                    case 10: {
                                        Route route = getRoute(row);
                                        route.getTo().setY(Float.parseFloat(in));
                                        update(route);
                                        break;
                                    }
                                    case 11: {
                                        InfoWindow info = new InfoWindow(" You can't change element's owner", "info");
                                        return;
                                    }
                                }
                            } catch (InputMismatchException | NumberFormatException er) {
                                InfoWindow w = new InfoWindow(" Input error, check it ", "error");
                            }
                        }
                    });
                }catch (ArrayIndexOutOfBoundsException r){
                    return;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(900, 100));

        add(top_panel, new GridBagConstraints(0, 0, 3, 1, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.SOUTH,
                new Insets(1, 1, 10, 1), 0, 0));

        add(scrollPane, new GridBagConstraints(0, 1, 3, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(2, 15, 1, 15), 0, 0));


        add(exit, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.EAST,
                new Insets(1, 1, 10, 15), 0, 0));
    }
    @Override
    public void run() {
        while (true) {
            try {
                model.updateData(manager);
                table.repaint();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
