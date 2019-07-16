package service;

import MainApp.Node;

import java.sql.*;
import java.util.ArrayList;


/* ServiceSQLRequest class : Do the connection between the database and the application using an excel file.
 *
  * */

public  class ServiceSqlRequest {


    public Connection connection;
    public Statement statement;

    /* jdbc Connection : connection of the database
     *
     * */

    public void jdbcConnection(){
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/londontube?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "londontube", "");
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public double getTimeBetweenStations(String StationA, String StationB){
        jdbcConnection();

        String sql = String.format("SELECT RunningTime,StationFrom,StationTo,Line FROM stationdistance WHERE StationFrom = '%s' AND StationTo ='%s'",StationA.toUpperCase(),StationB.toUpperCase());


        double time = 0;
        try{
            ResultSet rs;
            rs = statement.executeQuery(sql);
            while(rs.next()){
                time = Double.parseDouble(rs.getString("RunningTime"));
                /*System.out.println(time + " : "+ rs.getString("StationFrom") + " to "+ rs.getString("StationTo"));*/
            }
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return time;
    }

    public ArrayList<Node> getListAllStation(){

        jdbcConnection();

        String sql = String.format("SELECT DISTINCT StationFrom,StationFrom FROM stationdistance");

        ArrayList<Node> listStation = new ArrayList<>();

        try{
            ResultSet rs;
            rs = statement.executeQuery(sql);
            while(rs.next()){
                listStation.add(new Node(rs.getString("StationFrom")));
            }
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listStation;
    }

    public ArrayList<String> getListNameAllStation(){

        jdbcConnection();

        String sql = String.format("SELECT DISTINCT StationFrom,StationFrom FROM stationdistance");

        ArrayList<String> listStation = new ArrayList<>();

        try{
            ResultSet rs;
            rs = statement.executeQuery(sql);
            while(rs.next()){
                listStation.add(rs.getString("StationFrom"));
            }
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listStation;
    }

    public String getLinebetweenStations(String StationA, String StationB){
        jdbcConnection();

        String sql = String.format("SELECT Line,StationFrom,StationTo FROM stationdistance WHERE StationFrom = '%s' AND StationTo ='%s'",StationA.toUpperCase(),StationB.toUpperCase());


        String line ="";
        try{
            ResultSet rs;
            rs = statement.executeQuery(sql);
            while(rs.next()){
                line = rs.getString("Line");
            }
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return line;
    }

    public ArrayList<String> getAdjacencies(String Station){
        jdbcConnection();

        String sql = String.format("SELECT StationTo FROM stationdistance WHERE StationFrom = '%s'",Station.toUpperCase());
        ArrayList<String> stationTo = new ArrayList<>();

        String line ="";
        try{
            ResultSet rs;
            rs = statement.executeQuery(sql);
            while(rs.next()){
                stationTo.add(rs.getString("StationTo"));
            }
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stationTo;
    }


    public static void main(String[] args) {
        //Test
        System.out.println(new ServiceSqlRequest().getAdjacencies("BAKER STREET"));
    }
}
