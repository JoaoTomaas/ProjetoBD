import com.sun.corba.se.spi.orbutil.fsm.Guard;

import javax.swing.plaf.metal.MetalToolBarUI;
import java.sql.*;
import java.util.zip.CheckedInputStream;

import java.util.Scanner;

class Menu {

    void imprime_menu_opcoes (){

        System.out.println("1. Criar playlist");
        System.out.println("2. Adicionar musicas a playlist");
        System.out.println("3. Ver playlist");
        System.out.println("4. Adicionar musicas, albuns e artistas");
        System.out.println("5. Ver a info de um album"); //MENU INFO
        System.out.println("6. Ver a info de um artista"); //MENU INFO
        System.out.println("7. Ver a info de uma musica"); //MENU INFO
        System.out.println("8. Ver a info de um concerto"); //MENU INFO
        System.out.println("9. Partilha de musica");
        System.out.println("10. Upload de musica");
        System.out.println("11. Criar concerto");
        System.out.println("12. Associar artista a um concerto");
        System.out.println("13. Criar setlist de um concerto");
        System.out.println("14. Alterar ordem da setlist de um concerto");
    }


    void imprime_menu_inicial (){
        System.out.println("1. Registo do user");
        System.out.println("2. Login do user");
    }




}





class Metodos {

    void executa_login(String user, String pass, Connection connection) throws SQLException {

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

    void executa_registo(String user, String pass, Connection connection) throws SQLException {

        String SQL = "select username, password from utilizador where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, user);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Esse mano ja se encontra registado");
        } else {
            //Verificar se é o primeiro elemento a ser introduzido, para o nomear como editor ou admin, logo pergunto
            String SQLCONTA = "SELECT COUNT (*) FROM utilizador"; //Para verificar se a tabela está vazia ou não
            PreparedStatement ps = connection.prepareStatement(SQLCONTA);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                if (Integer.parseInt(r.getString(1)) == 0) {
                    String SQLRegAdmin = "insert into utilizador values (?, ?, ?)";
                    PreparedStatement pp = connection.prepareStatement(SQLRegAdmin);
                    pp.setString(1, user);
                    pp.setString(2, pass);
                    pp.setString(3, "admin");
                    pp.executeUpdate();
                    System.out.println("Utilizador registado como admin, welcome to DropMusic");
                    pp.close();
                } else {
                    String SQLRegNormal = "insert into utilizador values (?, ?, ?)";
                    PreparedStatement p = connection.prepareStatement(SQLRegNormal);
                    p.setString(1, user);
                    p.setString(2, pass);
                    p.setString(3, "normal");
                    p.executeUpdate();
                    System.out.println("Utilizador registado como normal, welcome to DropMusic");
                    p.close();
                }
            }

            ps.close();
            r.close();
            //Verificar se e necessario inicializar a null o ResultSet
        }

        rs.close();
        pstmt.close();
    }

    void executa_search_music(String search_type, String word, Connection connection) throws SQLException {

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
            while (rs.next()) {
                System.out.println("Nome da musica: " + rs.getString(1));
            }


        } else if (search_type.equals("music")) {
            String MUSICSQL = "SELECT music_name FROM public.musica WHERE music_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(MUSICSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                System.out.println("Nome da musica: " + rs.getString(1));
            }

        } else if (search_type.equals("artist")) {
            String ARTISTSQL = "SELECT music_name FROM public.musica WHERE id_musica = " +
                    "(SELECT id_musica FROM public.musicas_de_artista WHERE id_artista = " +
                    "(SELECT id_artista FROM public.artista WHERE artist_name = ?))";
            PreparedStatement pstmt = connection.prepareStatement(ARTISTSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                System.out.println("Nome da musica: " + rs.getString(1));
            }

        } else if (search_type.equals("album")) {
            String ALBUMSQL = "SELECT music_name FROM public.musica WHERE id_musica = " +
                    "(SELECT id_musica FROM public.musicas_de_album WHERE id_album = " +
                    "(SELECT id_album FROM public.album WHERE album_name = ?))";
            PreparedStatement pstmt = connection.prepareStatement(ALBUMSQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                System.out.println("Nome da musica: " + rs.getString(1));
            }

        } else if (search_type.equals("genre")) {
            String GENRESQL = "SELECT music_name FROM public.musica WHERE genre = ?";
            PreparedStatement pstmt = connection.prepareStatement(GENRESQL);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                System.out.println("Nome da musica: " + rs.getString(1));
            }

        }


    }

    void executa_search_info(String search_type, String word, Connection connection) throws SQLException {
        //Mostra detalhes sobre o album, artista e musica

        //Musica
        if (search_type.equals("music")) {
            //Info da musica
            String InfoMusicSQL = "SELECT music_name, artist_name, album_name, genre, duration FROM musica WHERE music_name = ?";
            PreparedStatement p = connection.prepareStatement(InfoMusicSQL);
            p.setString(1, word);
            ResultSet rst = p.executeQuery();

            //Lista de criticas da musica
            //TESTAR e VERIFICAR se e necessario inserir o atributo rating na musica tal como existe no album
            String MusicReviewList = "SELECT username, pontuacao, text FROM critica WHERE id_musica = " +
                    "(SELECT id_musica FROM musica WHERE music_name = ?)";
            PreparedStatement ps = connection.prepareStatement(MusicReviewList);
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();

            while (rst.next()) {
                System.out.println("Nome da musica: " + rst.getString(1));
                System.out.println("Nome do artista: " + rst.getString(2));
                System.out.println("Nome do album: " + rst.getString(3));
                System.out.println("Genero msuical: " + rst.getString(4));
                System.out.println("Duracao da musica: " + rst.getString(5));
            }

            System.out.println("Lista de criticas da musica: ");
            while (rs.next()) {
                System.out.println("Username: " + rs.getString(1) + "Pontuacao: " + rs.getString(2) +
                        "Text: " + rs.getString(3));
            }

            rst.close();
            rs.close();
            ps.close();
            p.close();
        }

        //Artista
        else if (search_type.equals("artist")) {
            //Info do artista
            String InfoArtistSQL = "SELECT artist_name, birth_date, biography, genre FROM artista WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(InfoArtistSQL);
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Nome do artista: " + rs.getString(1));
                System.out.println("Data de aniversario: " + rs.getString(2));
                System.out.println("Biografia: " + rs.getString(3));
                System.out.println("Genero musical:  " + rs.getString(4));
            }

            //Lista de musicas do artista
            String ArtistMusicList = "SELECT music_name, artist_name, album_name, genre, duration FROM musica WHERE id_musica = " +
                    "(SELECT id_musica FROM musicas_de_artista WHERE id_artista = " +
                    "(SELECT id_artista FROM artista  WHERE musica.artist_name = ?))";
            PreparedStatement p = connection.prepareStatement(ArtistMusicList);
            p.setString(1, word);
            ResultSet r = p.executeQuery();

            System.out.println("Lista de Musicas: ");
            while (r.next()) {
                System.out.println("Nome da musica: " + r.getString(1));
                System.out.println("Nome do artista: " + r.getString(2));
                System.out.println("Nome do album: " + r.getString(3));
                System.out.println("Genero musical: " + r.getString(4));
                System.out.println("Duracao da musica: " + r.getString(5));
            }

            //Lista de albuns do artista //TESTAR
            String ArtistAlbumList = "SELECT album_name, artist_name, description, release_date, genre, rating FROM album WHERE artist_name = ? AND rating = " +
                    "(SELECT AVG(sum_pontuacao) FROM " +
                    "(SELECT SUM(pontuacao) as sum_pontuacao FROM critica  WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE artist_name = ?)) as inner_query)";
            PreparedStatement pst = connection.prepareStatement(ArtistAlbumList);
            pst.setString(1, word);
            pst.setString(2, word);
            ResultSet rst = pst.executeQuery();

            System.out.println("Lista de Albuns: ");
            while (rst.next()) {
                System.out.println("Nome do album: " + r.getString(1));
                System.out.println("Nome do artista: " + r.getString(2));
                System.out.println("Descricao " + r.getString(3));
                System.out.println("Data de lancamento: " + r.getString(4));
                System.out.println("Genero musical: " + r.getString(5));
                System.out.println("Rating: " + r.getString(6));
            }

            p.close();
            ps.close();
            pst.close();
            r.close();
            rs.close();
            rst.close();
        }

        //Album
        else if (search_type.equals("album")) {
            //Info do album
            String InfoAlbumSQL = "SELECT album_name, artist_name, description, release_date, genre, rating FROM album WHERE album_name = ? AND rating = " +
                    "(SELECT AVG(sum_pontuacao) FROM " +
                    "(SELECT SUM(pontuacao) as sum_pontuacao FROM critica  WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE album_name = ?)) as inner_query)";
            PreparedStatement p = connection.prepareStatement(InfoAlbumSQL);
            p.setString(1, word);
            p.setString(2, word);
            ResultSet r = p.executeQuery();

            while (r.next()) {
                System.out.println("Nome do album: " + r.getString(1));
                System.out.println("Nome do artista: " + r.getString(2));
                System.out.println("Descricao " + r.getString(3));
                System.out.println("Data de lancamento: " + r.getString(4));
                System.out.println("Genero musical: " + r.getString(5));
                System.out.println("Rating : " + r.getString(6));
            }

            //Lista de musicas do album
            String AlbumMusicList = "SELECT music_name, artist_name, album_name, genre, duration FROM musica WHERE id_musica = " +
                    "(SELECT  id_musica FROM musicas_de_album WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE album_name = ?))";
            PreparedStatement ps = connection.prepareStatement(AlbumMusicList);
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Nome da musica: " + r.getString(1));
                System.out.println("Nome do artista: " + r.getString(2));
                System.out.println("Nome do album: " + r.getString(3));
                System.out.println("Genero musical: " + r.getString(4));
                System.out.println("Duracao da musica : " + r.getString(5));
            }

            //Lista de reviews do album
            String AlbumReviewList = "SELECT username, pontuacao, text FROM critica WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE album_name = ?)";
            PreparedStatement pst = connection.prepareStatement(AlbumReviewList);
            pst.setString(1, word);
            ResultSet rst = pst.executeQuery();

            while (rst.next()) {
                System.out.println("Username: " + rst.getString(1));
                System.out.println("Pontuacao: " + rst.getString(2));
                System.out.println("Texto: " + rst.getString(3));
            }
            p.close();
            ps.close();
            pst.close();
            r.close();
            rs.close();
            rst.close();
        }

    }

    void executa_album_review(String user, String album_title, String pontuacao, String text, Connection connection) throws SQLException {
        String AlbumReviewSQL = "INSERT INTO critica\n" +
                "VALUES (DEFAULT, null, (SELECT id_album FROM album WHERE album_name = ?), ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(AlbumReviewSQL);
        ps.setString(1, album_title);
        ps.setString(2, user);
        ps.setString(3, text);
        ps.setInt(4, Integer.parseInt(pontuacao));
        ps.executeUpdate();

        System.out.println("Review do album submetida com sucesso");
        ps.close();
    }

    void executa_music_review(String user, String music_title, String pontuacao, String text, Connection connection) throws SQLException {
        String MusicReviewSQL = "INSERT INTO critica " +
                                "VALUES (DEFAULT,  (SELECT id_musica FROM musica WHERE music_name = ?), NULL , ?, ?, ?)";
        PreparedStatement p = connection.prepareStatement(MusicReviewSQL);
        p.setString(1, music_title);
        p.setString(2, user);
        p.setString(3, text);
        p.setInt(4, Integer.parseInt(pontuacao));
        p.executeUpdate();

        System.out.println("Review da musica submetida com sucesso");
        p.close();
    }

    void executa_make_editor(String user, String novo_user, Connection connection) throws SQLException {
        //Verificar se o utilizador que está a querer tornar o outro num editor é também um editor
        String CheckPermissaoSQL = "SELECT user_type FROM utilizador WHERE username = ?";
        PreparedStatement p = connection.prepareStatement(CheckPermissaoSQL);
        p.setString(1, user);
        ResultSet r = p.executeQuery();

        //SE TEM PERMISSAO -> CONDICAO 1
        while (r.next()) {
            if (r.getString(1).equals("editor") || r.getString(1).equals("admin")) {
                System.out.println("Pode tornar editor");

                //Verificar se o novo_user existe na base de dados
                String NovoUserExisteSQL = "SELECT username FROM utilizador WHERE username = ?";
                PreparedStatement pstt = connection.prepareStatement(NovoUserExisteSQL);
                pstt.setString(1, novo_user);
                ResultSet rrst = pstt.executeQuery();

                if (rrst.next()) {
                    System.out.println("Utilizador existe");

                    //Fazer verificacao intermedia para ver se a pessoa ja e editora ou admin //TO DOOOOOOOOOOOOOO
                    String CheckIfEditSQL = "SELECT user_type FROM utilizador WHERE username = ?";
                    PreparedStatement pst = connection.prepareStatement(CheckIfEditSQL);
                    pst.setString(1, novo_user);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        if (rs.getString(1).equals("normal")) {
                            String MakeEditorSQL = "UPDATE utilizador SET user_type = 'editor' WHERE username = ?";
                            PreparedStatement ps = connection.prepareStatement(MakeEditorSQL);
                            ps.setString(1, novo_user);
                            ps.executeUpdate();
                        } else {
                            System.out.println("Esse mano ja tem permissoes de editor");
                        }
                    }
                }
                else {
                    System.out.println("Esse mano nao existe na nossa base de dados");
                }
            }
         //SE NAO TEM PERMISSAO -> CONDICAO 2
            else {
                System.out.println("Nao tem autorizacao");
                }


        }
    }

    void executa_cria_playlist(String user, String playlist_name, String privacy_type, Connection connection) throws SQLException {
        //Cria playlist, com playlist_name e privacy_type
        String CriaPlaySQL = "INSERT INTO playlist VALUES (DEFAULT, ?, ?)";
        PreparedStatement p = connection.prepareStatement(CriaPlaySQL);
        p.setString(1, playlist_name);
        p.setString(2, privacy_type);
        p.executeUpdate();

        //Associa a playlist ao utilizador que a criou (Visto que este pode ter várias)
        String AssociaPlaySQL = "INSERT INTO cria_playlist VALUES (?, (SELECT id_playlist FROM playlist WHERE playlist_name = ?))";
        PreparedStatement ps = connection.prepareStatement(AssociaPlaySQL);
        ps.setString(1, user);
        ps.setString(2, playlist_name);
        ps.executeUpdate();

        System.out.println("Playlist " + playlist_name + " do user " + user + " criada com sucesso");
    }

    void executa_opmusic_playlist(String user, String playlist_name, String op_type, String subop_type, String nome_musica, String nome_artista, String nome_album, Connection connection) throws SQLException {

        String CheckaPrivacySQL = "SELECT privacy_type FROM playlist WHERE playlist_name = ?";
        PreparedStatement p = connection.prepareStatement(CheckaPrivacySQL);
        p.setString(1, playlist_name);
        ResultSet r = p.executeQuery();

        //1. Se for private, só o owner pode ver e alterar
        if (r.getString(1).equals("private")){
            //vai checkar se esse user é o dono
            String checkaOwnerSQL = "SELECT username FROM cria_playlist WHERE id_playlist = " +
                    "(SELECT id_playlist FROM playlist WHERE playlist_name = ?)";
            PreparedStatement ps = connection.prepareStatement(checkaOwnerSQL);
            ps.setString(1, playlist_name);
            ResultSet rs = ps.executeQuery();

            if (rs.getString(1).equals(user)){
                System.out.println("Pode adicionar ou remover musicas a playlist");
                //QUERY PARA ADICIONAR OU REMOVER MUSICAS
                if (op_type.equals("inserir")){
                    if (subop_type.equals("single")) {
                        String insereMusicSQL = "INSERT INTO adiciona_musica VALUES ((SELECT id_playlist FROM playlist WHERE playlist_name = ?), " +
                                "(SELECT id_musica FROM musica WHERE music_name = ? AND artist_name = ? AND album_name = ?))";
                        PreparedStatement ppst = connection.prepareStatement(insereMusicSQL);
                        ppst.setString(1, playlist_name);
                        ppst.setString(2, nome_musica);
                        ppst.setString(3, nome_artista);
                        ppst.setString(4, nome_album);

                        ppst.executeUpdate();
                    }
                }
                else if (op_type.equals("remove")){
                    //ASK TOINO
                }

            }
            else{
                System.out.println("Grupo privado");
            }
        }

        //2. Se for publica, qualquer user pode ver, mas só o owner pode alterar
        else if (r.getString(1).equals("public")){
            System.out.println("ESTOU SO A BERE");
            //QUERY PARA VER SE E O OWNER
            String checkOwnerSQL = "SELECT username FROM cria_playlist WHERE id_playlist = " +
                    "(SELECT id_playlist FROM playlist WHERE playlist_name = ?)";
            PreparedStatement ppps = connection.prepareStatement(checkOwnerSQL);
            ppps.setString(1, playlist_name);
            ResultSet rrrs = ppps.executeQuery();

            if (rrrs.getString(1).equals(user)){
                System.out.println("Tem autorizacao para adicionar ou remover musicas a playlist");
                //QUERY PARA ADICIONAR OU REMOVER MUSICAS
                if (op_type.equals("inserir")){
                    if (subop_type.equals("single")) {
                        String insereMusicSQL = "INSERT INTO adiciona_musica VALUES ((SELECT id_playlist FROM playlist WHERE playlist_name = ?)," +
                                " (SELECT id_musica FROM musica WHERE music_name = ? AND artist_name = ? AND album_name = ?))";
                        PreparedStatement ppst = connection.prepareStatement(insereMusicSQL);
                        ppst.setString(1, playlist_name);
                        ppst.setString(2, nome_musica);
                        ppst.setString(3, nome_artista);
                        ppst.setString(4, nome_album);

                        ppst.executeUpdate();
                    }
                }
                else if (op_type.equals("remove")){
                    //ASK TOINO
                }

            }
            else{
                System.out.println("Pode ver, mas nao pode editar");
            }





            //QUERY PARA ADICIONAR OU REMOVER MUSICAS
        }

        //3. Se for collaborative, qualquer user pode ver e editar
        else if (r.getString(1).equals("collaborative")){
            //QUERY PARA ADICIONAR OU REMOVER MUSICAS
            if (op_type.equals("inserir")){
                if (subop_type.equals("single")) {
                    String insereMusicSQL = "INSERT INTO adiciona_musica VALUES ((SELECT id_playlist FROM playlist WHERE playlist_name = ?)," +
                            "(SELECT id_musica FROM musica WHERE music_name = ? AND artist_name = ? AND album_name = ?))";
                    PreparedStatement psttt = connection.prepareStatement(insereMusicSQL);
                    psttt.setString(1, playlist_name);
                    psttt.setString(2, nome_musica);
                    psttt.setString(3, nome_artista);
                    psttt.setString(4, nome_album);

                    psttt.executeUpdate();
                }
            }
            else if (op_type.equals("remove")){
                //ASK TOINO
            }
        }
    }

    void executa_ver_playlist(String user, String playlist_name, Connection connection) throws SQLException {
        //1. Ver qual e o tipo de privacidade da playlist
        String CheckaPrivacySQL = "SELECT privacy_type FROM playlist WHERE playlist_name = ?";
        PreparedStatement p = connection.prepareStatement(CheckaPrivacySQL);
        p.setString(1, playlist_name);
        ResultSet r = p.executeQuery();

        //1. Se for private, só o owner pode ver
        while (r.next()) {
            if (r.getString(1).equals("private")) {
                String checkaOwnerSQL = "SELECT username FROM cria_playlist WHERE id_playlist = " +
                        "(SELECT id_playlist FROM playlist WHERE playlist_name = ?)";
                PreparedStatement ps = connection.prepareStatement(checkaOwnerSQL);
                ps.setString(1, playlist_name);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getString(1).equals(user)) {
                        System.out.println("Pode ver a playlist");
                        //QUERY PARA VER AS MUSICAS DA PLAYLIST //EM FALTA
                        String MusicasPlaySQL = "SELECT music_name, artist_name, album_name, genre, duration, filename FROM musica WHERE id_musica = " +
                                "(SELECT id_musica FROM adiciona_musica WHERE id_playlist = " +
                                "(SELECT id_playlist FROM playlist WHERE playlist_name = ?))";
                        PreparedStatement pst = connection.prepareStatement(MusicasPlaySQL);
                        pst.setString(1, playlist_name);
                        ResultSet rst = pst.executeQuery();

                        while (rst.next()) {
                            System.out.println("Nome da musica: " + rst.getString(1));
                            System.out.println("Nome do artista: " + rst.getString(2));
                            System.out.println("Nome do album: " + rst.getString(3));
                            System.out.println("Genero msuical: " + rst.getString(4));
                            System.out.println("Duracao da musica: " + rst.getString(5));
                            System.out.println("Nome do ficheiro: " + rst.getString(6));
                        }

                    } else {
                        System.out.println("Grupo privado");
                    }
                }
            } else if ((r.getString(1).equals("public")) || (r.getString(1).equals("collaborative"))) {
                //QUERY PARA VER AS MUSICAS DA PLAYLIST
                String MusicasPlaySQL = "SELECT music_name, artist_name, album_name, genre, duration, filename FROM musica WHERE id_musica = " +
                        "(SELECT id_musica FROM adiciona_musica WHERE id_playlist = " +
                        "(SELECT id_playlist FROM playlist WHERE playlist_name = ?))";
                PreparedStatement pstt = connection.prepareStatement(MusicasPlaySQL);
                pstt.setString(1, playlist_name);
                ResultSet rstt = pstt.executeQuery();
                System.out.println("Podes ver mano");

                while (rstt.next()) {
                    System.out.println("Nome da musica: " + rstt.getString(1));
                    System.out.println("Nome do artista: " + rstt.getString(2));
                    System.out.println("Nome do album: " + rstt.getString(3));
                    System.out.println("Genero msuical: " + rstt.getString(4));
                    System.out.println("Duracao da musica: " + rstt.getString(5));
                    System.out.println("Nome do ficheiro:  " + rstt.getString(6));
                }

            }
        }
    }

    void executa_cria_concerto(String user, String local, String data, String lotacao, Connection connection) throws SQLException {
        String CriaConcertoSQL = "INSERT INTO concerto VALUES (DEFAULT, ?, to_date(?, 'DD/MM/YYYY'), ?)";
        PreparedStatement p = connection.prepareStatement(CriaConcertoSQL);
        p.setString(1, local);
        p.setString(2, data); //TESTAR (DEVE DAR ERRO)
        p.setInt(3, Integer.parseInt(lotacao));
        p.executeUpdate();
        System.out.println("Concerto criado");
        p.close();
    }

    void executa_addartist_concerto (String user, String artist_name, String local, String data, Connection connection) throws SQLException {

        //1.Verificar se o concerto existe
        String CheckConcertoSQL = "SELECT * FROM concerto WHERE local = ? AND data = ?";
        PreparedStatement p = connection.prepareStatement(CheckConcertoSQL);
        p.setString(1,local);
        p.setDate(2,java.sql.Date.valueOf(data)); //Tem que ser inserida no formato "2017-07-11"
        ResultSet r = p.executeQuery();

        if (r.next()){
            System.out.println("Esse concerto existe");
        }
        else{
            System.out.println("Mano tens que escolher um concerto que ja exista");
        }

        //2.Verificar se o artista existe
        String CheckArtistSQL = "SELECT * FROM artista WHERE artist_name = ?";
        PreparedStatement ps = connection.prepareStatement(CheckArtistSQL);
        ps.setString(1,artist_name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            System.out.println("Essa artista existe");
        }
        else{
            System.out.println("Mano essa artista nao existe, TA MAL");
        }

        if (r.next() && rs.next()) {
            String AddArtistSQL = "INSERT INTO dar_um_concerto\n" +
                    "VALUES ((SELECT id_artista FROM artista WHERE artist_name = ?), (SELECT id_concerto FROM concerto WHERE local = ? AND data = ?))";
            PreparedStatement pst = connection.prepareStatement(AddArtistSQL);
            pst.setString(1, artist_name);
            pst.setString(2, local);
            pst.setString(3, data);
            pst.executeUpdate();

            System.out.println("Artista adicionado");
            pst.close();
        }
    }

    void executa_alinhaconcerto (String user, String local, String data, String music_name, String ordem, Connection connection) throws SQLException{

        //1.Verificar se o concerto existe
        String CheckConcertoSQL = "SELECT * FROM concerto WHERE local = ? AND data = ?";
        PreparedStatement p = connection.prepareStatement(CheckConcertoSQL);
        p.setString(1,local);
        p.setDate(2,java.sql.Date.valueOf(data)); //Tem que ser inserida no formato "2017-07-11"
        ResultSet r = p.executeQuery();

        if (r.next()){
            System.out.println("Esse concerto existe");
        }
        else{
            System.out.println("Mano tens que escolher um concerto que ja exista");
        }

        //2.Verificar se a musica existe
        String CheckMusicSQL = "SELECT * FROM musica WHERE music_name = ?";
        PreparedStatement ps = connection.prepareStatement(CheckMusicSQL);
        ps.setString(1,music_name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            System.out.println("Essa musica existe");
        }
        else{
            System.out.println("Mano essa musica nao existe, TA MAL");
        }

        //3. Inserir a musica na setlist do concerto
        if (r.next() && rs.next()) {
            String InsereMusicSetlistSQL = "INSERT INTO alinhamento VALUES ((SELECT id_concerto FROM concerto WHERE local = ? and data = ?)," +
                    "(SELECT id_musica FROM musica WHERE music_name = ?), ?)";
            PreparedStatement pst = connection.prepareStatement(InsereMusicSetlistSQL);
            pst.setString(1, local);
            pst.setDate(2, java.sql.Date.valueOf(data));
            pst.setString(3, music_name);
            pst.setInt(4, Integer.parseInt(ordem));
            pst.executeUpdate();

            System.out.println("Musica inserida com sucesso na setlist do concerto");
        }
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

            Menu menu = new Menu();
            Metodos m = new Metodos();

            Scanner scan = new Scanner(System.in);

            String username = null;

            /************************************************** MENU INICIAL **************************************************************/
            menu.imprime_menu_inicial();
            System.out.println("Operacao pretendida: ");
            int num = scan.nextInt();
            if (num == 1){
                //REGISTO (Podemos adicionar um confirma password,se o tempo permitir)
                System.out.println("Nome pretendido: ");
                username = scan.next();

                System.out.println("Password pretendida: ");
                String password = scan.next();

                m.executa_registo(username, password, connection);
                menu.imprime_menu_inicial();
            }
            else if (num == 2){
                //LOGIN
                System.out.println("Username: ");
                username = scan.next();

                System.out.println("Password: ");
                String password = scan.next();
                m.executa_login(username, password, connection);
                menu.imprime_menu_opcoes();
            }


            /************************************************* MENU OPCOES **************************************************************/

            int opcao = scan.nextInt();

            if (opcao == 1){
                //Cria playlist
                System.out.println("Nome da playlist: ");
                String playlist_name = scan.next();

                System.out.println("Tipo de privacidade: ");
                String privacy_type = scan.next();

                m.executa_cria_playlist(username, playlist_name, privacy_type, connection);
            }

            else if (opcao == 2){
                //Adicionar musicas a playlist
                //É com a funcao do opmusic_list, mas ainda falta o DELETE a funfar
            }

            else if (opcao == 3){
                //Ver playlist
                System.out.println("Nome da playlist que pretende ver: ");
                String playslist_name = scan.next();

                m.executa_ver_playlist(username, playslist_name, connection);
            }

            else if (opcao == 4){
                //Editar tudo e mais alguma coisa
                //ESTA É TUA TOINO, É O MANAGE DATA
            }

            else if (opcao == 5){
                //Ver a info de um album
                //Vou precisar do nome do album, mas secalhar tambem vou previsar do nome do artista
                String search_type = "album";

                System.out.println("Nome do album: ");
                String album_name = scan.next();

                m.executa_search_info(search_type, album_name, connection);
            }

            else if (opcao == 6){
                //Ver a info de um artista
                //Ver se faltam argumentos por causa de ser uma artista unico
                String search_type = "artist";

                System.out.println("Nome do artista: ");
                String artist_name = scan.next();

                m.executa_search_info(search_type, artist_name, connection);
            }

            else if (opcao == 7){
                //Ver info de uma musica
                //Ver se faltam argumentos por causa de ser uma musica unica
                String search_type = "music";

                System.out.println("Nome da musica: ");
                String music_name = scan.next();

                m.executa_search_info(search_type, music_name, connection);
            }

            else if (opcao == 8){
                //Ver info de um concerto
                //Ver se faltam argumentos por causa de ser uma musica unica
                String search_type = "concert";

                System.out.println("Local do concerto: ");
                String local = scan.next();

                System.out.println("Data do concerto: "); //Especificar qual o formato obrigatorio
                String data = scan.next();

                //m.executa_search_info_concerto(local, data, connection); //FALTA IMPLEMENTAR
            }

            else if (opcao == 9){
                //Share music
                //ESTA É TUA TOINO

            }

            else if (opcao == 10){
                //Upload de musica
                //ESTA É TUA TOINO

            }

            else if (opcao == 11){
                //Criar concerto

                System.out.println("Local do concerto: "); //"Passeio Maritimo de Alges"
                String local = scan.next();

                System.out.println("Data (DD/MM/YYYY): "); //"11/07/2017"
                String data = scan.next();

                System.out.println("Lotacao: "); //"100"
                String lotacao = scan.next();

                m.executa_cria_concerto(username, local, data, lotacao, connection);
            }

            else if (opcao == 12){
                //Associar artista a um concerto
                //Ver se falta algum atributo, de forma a considerar o artista como unico

                System.out.println("Nome do artista: ");
                String nome_artista = scan.next();

                System.out.println("Local: ");
                String local = scan.next();

                System.out.println("Data: ");
                String data = scan.next();

                m.executa_addartist_concerto(username, nome_artista, local, data, connection); //FALTA CORRIGIR AS KEYS, PÔR AMBAS A FK
            }

            else if (opcao == 13){
                //Criar setlist de um concerto

                System.out.println("Local: ");
                String local = scan.next();

                System.out.println("Data: ");
                String data = scan.next();

                System.out.println("Nome da musica: ");
                String music_name = scan.next();

                System.out.println("Nome do artista: ");
                String artist_name = scan.next();

                System.out.println("Nome do album: ");
                String album_name = scan.next();

                System.out.println("Posicao: ");
                String posicao_alinha = scan.next();

                //FALTA ADICIONAR O NOME DO ALBUM E DO ARTISTA
                //m.executa_alinhaconcerto("joao","Passeio Maritimo de Alges", "2017-07-11", "Summertime Magic", "3", connection);
            }

            else if (opcao == 14){
                //Alterar a ordem da setlist de um concerto
                //FALTA IMPLEMENTAR


            }










            //m.executa_login("toni", "fifa", connection); //CHECK
            //m.executa_registo("toni", "fifa", connection); //CHECK
            //m.executa_make_editor("joao", "toni", connection); //CHECK
            //m.executa_music_review("toni", "Summertime Magic", "9", "ja ouvi pior e a pagar", connection); //CHECK
            //m.executa_album_review("toni", "Views", "8", "epa nao sei nao", connection); //CHECK
            //m.executa_cria_playlist("toni", "funk do bom", "public", connection); //CHECK
            //m.executa_ver_playlist("joao", "funk do bom", connection); //CHECK //VER DE NOVO QUANDO TIVER MÚSICAS NA PLAYLIST
            //m.executa_cria_concerto("joao", "Passeio Maritimo de Alges", "11/07/2017", "100", connection); //CHECK


            //m.executa_search_music("all", "Cenas", connection); //Esta a funcionar, falta testar a maior parte //music funciona
            //m.executa_search_info("album", "Views", connection); //Esta a funcionar, falta testar a maior parte //music funciona

            //FALTA TESTAR
            //m.executa_alinhaconcerto("joao","Passeio Maritimo de Alges", "2017-07-11", "Summertime Magic", "3", connection);
            //m.executa_addartist_concerto("joao", "Childish Gambino", "Passeio Maritimo de Alges", "2017-07-11", connection); //FALTA CORRIGIR AS KEYS, PÔR AMBAS A FK

            //FALTA TERMINAR
            //m.executa_opmusic_playlist

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
