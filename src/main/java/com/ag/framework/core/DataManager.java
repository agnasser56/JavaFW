package com.ag.framework.core;

import com.ag.framework.utilities.LogsGenerator;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.sun.rowset.CachedRowSetImpl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

public class DataManager {

    static Properties prop = new Properties();
    static InputStream input = null;
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static CachedRowSetImpl crs = null;
    static Recordset recordset = null;
    static String filePath = null;

    public static String getPropertyFile(String prptString) {
        String property = null;
        try {
            input = new FileInputStream("Configuration/config.properties");
            // load a properties file
            prop.load(input);
            property = prop.getProperty(prptString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return property;
        }
    }

    public static Hashtable<Integer, Hashtable<String, String>> GetExcelDataTable(String strQuery) throws SQLException {
        String filePath = null;
        Recordset recordset = null;
        com.codoid.products.fillo.Connection connection = null;
        // String filePath="D:\\My Docs\\QA Store\\Office\\Automation learning\\SeleniumFrameworkJava\\javadata.xlsx";
        String dir = System.getProperty("user.dir");
        filePath = dir + getPropertyFile("fileName");

        ArrayList list = null;
        int columns = 0;
        int rowNumber = 1;
        Hashtable<Integer, Hashtable<String, String>> DataTable = new Hashtable<Integer, Hashtable<String, String>>();

        try {
            //Import Folio liberary
            Fillo fillo = new Fillo();
            connection = fillo.getConnection(filePath);
            recordset = connection.executeQuery(strQuery);


            if (recordset.getCount() > 0) {
                ArrayList<String> fnames = recordset.getFieldNames();
                columns = fnames.size();
                while (recordset.next()) {
                    Hashtable<String, String> DataRow = new Hashtable<String, String>();
                    for (int i = 0; i < columns; i++) {
                        DataRow.put(recordset.getField(i).name(), recordset.getField(i).value());
                    }

                    DataTable.put(rowNumber, DataRow);
                    rowNumber++;
                }
            } else {
                System.out.println("No Record found");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                recordset.close();
            }
            if (connection != null)
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            return DataTable;
        }

    }


    public static Hashtable<String, String> GetExcelDictionary(String strQuery) throws SQLException {
        String filePath = null;
        Recordset recordset = null;
        com.codoid.products.fillo.Connection connection = null;
        // String filePath="D:\\My Docs\\QA Store\\Office\\Automation learning\\SeleniumFrameworkJava\\javadata.xlsx";
        String dir = System.getProperty("user.dir");
        filePath = dir + getPropertyFile("fileName");

        ArrayList list = null;
        int columns = 0;
        int rowNumber = 1;
        Hashtable<String, String> DataTable = new Hashtable<String, String>();

        try {
            //Import Folio liberary
            Fillo fillo = new Fillo();
            connection = fillo.getConnection(filePath);
            recordset = connection.executeQuery(strQuery);

            if (recordset.getCount() > 0) {

                while (recordset.next()) {

                    DataTable.put(recordset.getField(1).value(), recordset.getField(0).value());
                }

            } else {
                System.out.println("No Record found");

            }

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            LogsGenerator.error(e.getMessage());
        } finally {
            if (connection != null) {
                recordset.close();
            }
            if (connection != null)
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            return DataTable;
        }

    }


    //Insert/Update data in Excel
    public static String UpdateExcelData(String strQuery) throws Exception {
        String filePath = null;
        com.codoid.products.fillo.Connection connection = null;
        String status = null;
        //String filePath="D:\\My Docs\\QA Store\\Office\\Automation learning\\SeleniumFrameworkJava\\javadata.xlsx";
        String dir = System.getProperty("user.dir");
        filePath = dir + getPropertyFile("fileName");

        try {
            //Import Folio liberary
            Fillo fillo = new Fillo();
            connection = fillo.getConnection(filePath);
            //connection.executeUpdate(strQuery);
            int count = connection.executeUpdate(strQuery);
            if (count >= 0) {
                status = "Excel is updated successfully";
            } else {
                status = "No data updated!";
            }
        } catch (Exception e) {
            LogsGenerator.error(e.getMessage());
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return status;
    }

    //Insert/Update/Delete SQL
    public static String ExecuteSQLQuery(String sqlQuery) throws SQLException {
        String status = null;
        int count;
        try {

            String connectionUrl = getPropertyFile("dbConString");
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
            stmt = con.createStatement();
            count = stmt.executeUpdate(sqlQuery);
            if (count > 0) {
                status = "Successfully Executed!";

            } else {
                status = "No Data Updated";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return status;
        }
    }

    public static ResultSet GetDBResultSet(String sqlQuery) {
        try {

            String connectionUrl = getPropertyFile("dbConString");
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            crs = new CachedRowSetImpl();
            crs.populate(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return crs;
        }
    }


    //for array
    //Object[][]
    public static Object[][] dataArray(String strQuery) throws SQLException, FilloException {
        String filePath = null;
        Recordset recordset = null;
        com.codoid.products.fillo.Connection connection = null;
        // String filePath="D:\\My Docs\\QA Store\\Office\\Automation learning\\SeleniumFrameworkJava\\javadata.xlsx";
        String dir = System.getProperty("user.dir");
        filePath = dir + getPropertyFile("fileName");
        Object[][] DataTable = new Object[0][];

        try {
            //Import Folio liberary
            Fillo fillo = new Fillo();
            connection = fillo.getConnection(filePath);
            recordset = connection.executeQuery(strQuery);
            ArrayList<String> columns = recordset.getFieldNames();
            int rows = recordset.getCount();
            DataTable = new Object[rows][columns.size()];
            int i = 0;

            if (recordset.getCount() > 0) {
                while (recordset.next())

                {
                    //for(int i=0;i<rows ;i++)
                    // {
                    for (int j = 0; j < columns.size(); j++) {
                        DataTable[i][j] = recordset.getField(j).value();

                    }

                    //}
                    i++;


                }

                System.out.println(DataTable[0][0]);

            } else {
                System.out.println("No Record found");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                recordset.close();
            }
            if (connection != null)
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            return DataTable;
        }

    }
}
