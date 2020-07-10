package UI;

import Common.CommandShell;
import Connection.Manager;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RoutesTableModel extends AbstractTableModel {

    private volatile static ArrayList<String[]> data;
    private int columnCount = 12;

    public static synchronized ArrayList<String[]> getData(){return data;}

    RoutesTableModel(){
        data = new ArrayList<>();
    }

    synchronized ArrayList<String[]> filter(String column, String str){
        ArrayList <String[]> data2 = data;
        ArrayList<String[]> res = new ArrayList<>();
        int i = getColumnNumber(column);
        for(String[] s : data2){
            if (s[i].contains(str)) res.add(s);
        }
        return res;
    }

    int getColumnNumber(String columnName){
        switch (columnName){
            case "id": return 0;
            case "distance": return 1;
            case "name": return 2;
            case "coord_x": return 3;
            case "coord_y": return 4;
            case "loc_from": return 5;
            case "from_x": return 6;
            case "from_y": return 7;
            case "loc_to": return 8;
            case "to_x": return 9;
            case "to_y": return 10;
            case "owner": return 11;
        }
        return -1;
    }


    void updateData(Manager manager) {
        ArrayList<String[]> data2 = new ArrayList<>();
        CommandShell com = new CommandShell("get_routes");
        manager.getSender().send(com);
        ArrayList<String> array = manager.check();
        for(String s : array){
            String[] str = s.split(",");
            data2.add(str);
        }
        data = data2;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String [] row = data.get(rowIndex);
        row [columnIndex] = aValue.toString();
        data.set(rowIndex, row);
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex){
            case 0: return "id";
            case 1: return "distance";
            case 2: return "name";
            case 3: return "coord_x";
            case 4: return "coord_y";
            case 5: return "loc_from";
            case 6: return "from_x";
            case 7: return "from_y";
            case 8: return "loc_to";
            case 9: return "to_x";
            case 10: return "to_y";
            case 11: return "owner";
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = data.get(rowIndex);
        return row[columnIndex];
    }
}
