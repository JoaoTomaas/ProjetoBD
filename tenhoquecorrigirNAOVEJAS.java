import com.sun.corba.se.spi.orbutil.fsm.Guard;

import javax.swing.plaf.metal.MetalToolBarUI;
import java.sql.*;

class Metodos {

    void executa_login (String user, String password, Connection connection) throws SQLException {
        //Statement stmt = null;
        ResultSet rs = null;

        String SQL = "select username, password from utilizador where username = user";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        rs = pstmt.executeQuery();


        if (rs.next()) {
            System.out.println("Esse mano ja existe na base de dados");

            if (password.equals(rs.getString(2))) {
                System.out.println("Password correta, welcome to DropMusic");
            } else {
                System.out.println("Password errada, try again!");
            }
        } else {
            System.out.println("Tens que te registar mano");
        }
        rs.close();
        pstmt.close();
    }

}



public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        //Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver"); // the postgresql driver string
            String url = "jdbc:postgresql://localhost:5432/dropmusic"; // the postgresql url
            connection = DriverManager.getConnection(url, "postgres", "postgresadmin1"); // get the postgresql database connection

            // now do whatever you want to do with the connection

            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("select username, password from utilizador where username = 'joao'"); //Tanto posso escrever public.utilizador como utilizador

            //while (rs.next())

            Metodos m = new Metodos();

            m.executa_login("joao", "drogas", connection);


        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
