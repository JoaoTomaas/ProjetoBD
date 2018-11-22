import com.sun.corba.se.spi.orbutil.fsm.Guard;

import javax.swing.plaf.metal.MetalToolBarUI;
import java.sql.*;

class Metodos {

    void executa_login (String user, String pass, Connection connection) throws SQLException {

        String SQL = "select username, password from utilizador where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, user);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Esse mano ja existe na base de dados");

            if (pass.equals(rs.getString(2))) {
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

    void executa_registo (String user, String pass, Connection connection) throws SQLException {

        String SQL = "select username, password from utilizador where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, user);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()){
            System.out.println("Esse mano ja se encontra registado");
        }
        else{
            String SQLRegister = "insert into utilizador values (username, password) "; //TERMINAR
            PreparedStatement stmt = connection.prepareStatement(SQLRegister);
            //stmt.setString(1, user);
            rs = pstmt.executeQuery(); //Verificar se e executador //Verificar se e necessario inicializar a null o ResultSet

            System.out.println("Utilizador registado, welcome to DropMusic");
        }


    }

    void executa_search_music (String search_type, String word, Connection connection) throws SQLException{

        if (search_type.equals("all")) {
            String ALLSQL = "SELECT music_name FROM public.musica WHERE id_musica =" +
                                "(SELECT id_musica FROM public.musicas_de_album WHERE id_album = " +
                                    "(SELECT id_album FROM public.album WHERE album_name = ?))\n" +
                    "UNION\n" +

                    "SELECT music_name FROM public.musica WHERE music_name = ?\n" +
                    "UNION\n" +

                    "SELECT music_name FROM public.musica WHERE genre = ?\n" +
                    "UNION\n" +

                    "SELECT music_name FROM public.musica WHERE id_musica = " +
                        "(SELECT id_musica FROM public.musicas_de_artista WHERE id_artista = " +
                            "(SELECT id_artista FROM public.artista WHERE artist_name = ?))";

            PreparedStatement pstmt = connection.prepareStatement(ALLSQL);
            pstmt.setString(1, word);
            pstmt.setString(2, word);
            pstmt.setString(3, word);
            pstmt.setString(4, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
        }
        else if (search_type.equals("music")){
            String MUSICSQL = "SELECT music_name FROM public.musica WHERE music_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(MUSICSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
        }
        else if (search_type.equals("artist")){
            String ARTISTSQL = "SELECT music_name FROM public.musica WHERE id_musica = " +
                    "(SELECT id_musica FROM public.musicas_de_artista WHERE id_artista = " +
                    "(SELECT id_artista FROM public.artista WHERE artist_name = ?))";
            PreparedStatement pstmt = connection.prepareStatement(ARTISTSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
        }
        else if (search_type.equals("album")){
            String ALBUMSQL = "SELECT music_name FROM public.musica WHERE id_musica = " +
                                "(SELECT id_musica FROM public.musicas_de_album WHERE id_album = " +
                                    "(SELECT id_album FROM public.album WHERE album_name = ?))";
            PreparedStatement pstmt = connection.prepareStatement(ALBUMSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
        }
        else if (search_type.equals("genre")){
            String GENRESQL = "SELECT music_name FROM public.musica WHERE genre = ?";
            PreparedStatement pstmt = connection.prepareStatement(GENRESQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
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
