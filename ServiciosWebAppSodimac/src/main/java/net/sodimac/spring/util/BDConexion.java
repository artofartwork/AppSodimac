package net.sodimac.spring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BDConexion 
    {
	
	
	public Connection getConexion(){
		 Connection con = null;
		   try
           {
			   System.out.println("CONECTADO");
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               String aa = "jdbc:sqlserver://localhost:1433;databaseName=BDSodimac;integratedSecurity=true";;
           con = DriverManager.getConnection(aa);

           
           } catch (Exception e)
           {  
               e.printStackTrace();
           }
		return con;
		
		
	}
        public static void main(String[] args)
        {
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String aa = "jdbc:sqlserver://localhost:1433;databaseName=BDSodimac;integratedSecurity=true";;
                Connection con = DriverManager.getConnection(aa);
                Statement s1 = con.createStatement();
                ResultSet rs = s1.executeQuery("SELECT  * FROM Sodimac.GesCliente");
              
                if(rs!=null){
                    while (rs.next()){
                        for(int i = 1; i <=10;i++)
                        {
                        
                            System.out.println(rs.getString(i));
                        
                        }
                    }
                }

                //String result = new result[20];

            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }


}
