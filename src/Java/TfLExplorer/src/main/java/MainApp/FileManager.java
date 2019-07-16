package MainApp;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * File Manager : Extract the values from the excel file "DistanceStation.xls" and put those values in the database
 */


public class FileManager {


    public static void main(String[] args){


        ArrayList<String> stations = new ArrayList<String>();
        Connection connection;
        Statement statement;


        try{

            InputStream input = new FileInputStream("src/Java/TfLExplorer/src/main/assets/DistanceStation.xls");
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/londontube?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "londontube", "");

            statement = connection.createStatement();

            while (rows.hasNext()){

                stations.clear();



                HSSFRow row = (HSSFRow) rows.next();

                Iterator cells = row.cellIterator();

                while (cells.hasNext()){

                    HSSFCell cell = (HSSFCell) cells.next();
                    if (CellType.NUMERIC == cell.getCellType())
                        stations.add(Double.toString(cell.getNumericCellValue()));
                    else if (CellType.STRING == cell.getCellType())
                        stations.add(cell.getStringCellValue());

                    System.out.println(stations);
                }


                //Insert values in the database
                try {


                    String sql = String
                            .format("INSERT INTO StationDistance(Line, Direction, StationFrom, StationTo, Distance, RunningTime, AMPeak, InterPeak) VALUES " +
                                            "('%s','%s','%s','%s','%s','%s','%s','%s')",
                                    stations.get(0), stations.get(1), stations.get(2), stations.get(3),
                                    stations.get(4), stations.get(5), stations.get(6) , stations.get(7));
                    int count = statement.executeUpdate(sql);

                    if (count > 0) {
                        System.out.println("Enregistrement terminé\n");
                    }


                }catch (Exception e){
                    System.out.println("hello c'est l'erreur");
                     e.printStackTrace();
                }

            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
