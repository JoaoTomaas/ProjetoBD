import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver"); // the postgresql driver string
            String url = "jdbc:postgresql://localhost:5432/dropmusic"; // the postgresql url

            // get the postgresql database connection
            connection = DriverManager.getConnection(url, "postgres", "postgresadmin1");

            // now do whatever you want to do with the connection
            //String SQL = "SELECT * from utilizador";
            //PreparedStatement pstmt = connection.prepareStatement(SQL);
            //rs = pstmt.executeQuery();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select username, password from utilizador where username = 'joao'"); //Tanto posso escrever public.utilizador como utilizador
            //while (rs.next())
            String pass_teste = "drogas";

            if (rs.next())
            {
                //System.out.println(rs.getString(1));
                //System.out.println(rs.getString(2));
                System.out.println("Esse mano ja existe na base de dados");

                if (pass_teste.equals(rs.getString(2))){
                    System.out.println("Password correta, welcome to DropMusic");
                }
                else{
                    System.out.println("Password errada, try again!");
                }
            }
            else {
                System.out.println("Podes-te registar mano");
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
