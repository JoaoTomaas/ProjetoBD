/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     24/11/2018 15:54:06                          */
/*==============================================================*/


drop index ACESSO_A_FICHEIRO2_FK;

drop index ACESSO_A_FICHEIRO_FK;

drop index ACESSO_A_FICHEIRO_PK;

drop table ACESSO_A_FICHEIRO;

drop index ADICIONA_MUSICA2_FK;

drop index ADICIONA_MUSICA_FK;

drop index ADICIONA_MUSICA_PK;

drop table ADICIONA_MUSICA;

drop index ALBUM_PK;

drop table ALBUM;

drop index ALBUNS_DE_ARTISTA2_FK;

drop index ALBUNS_DE_ARTISTA_FK;

drop index ALBUNS_DE_ARTISTA_PK;

drop table ALBUNS_DE_ARTISTA;

drop index ARTISTA_PK;

drop table ARTISTA;

drop index CONCERTO_PK;

drop table CONCERTO;

drop index CRIA_PLAYLIST2_FK;

drop index CRIA_PLAYLIST_FK;

drop index CRIA_PLAYLIST_PK;

drop table CRIA_PLAYLIST;

drop index RELATIONSHIP_20_FK;

drop index RELATIONSHIP_13_FK;

drop index RELATIONSHIP_12_FK;

drop index CRITICA_PK;

drop table CRITICA;

drop index DAR_UM_CONCERTO2_FK;

drop index DAR_UM_CONCERTO_FK;

drop index DAR_UM_CONCERTO_PK;

drop table DAR_UM_CONCERTO;

drop index ASSOCIA_FICHEIRO2_FK;

drop index MUSICA_PK;

drop table MUSICA;

drop index MUSICAS_DE_ALBUM2_FK;

drop index MUSICAS_DE_ALBUM_FK;

drop index MUSICAS_DE_ALBUM_PK;

drop table MUSICAS_DE_ALBUM;

drop index MUSICAS_DE_ARTISTA2_FK;

drop index MUSICAS_DE_ARTISTA_FK;

drop index MUSICAS_DE_ARTISTA_PK;

drop table MUSICAS_DE_ARTISTA;

drop index ASSOCIA_FICHEIRO_FK;

drop index MUSICFILE_PK;

drop table MUSICFILE;

drop index PERTENCA_PK;

drop table PERTENCA;

drop index PLAYLIST_PK;

drop table PLAYLIST;

drop index RELATIONSHIP_15_FK;

drop index RELATIONSHIP_14_FK;

drop index RELATIONSHIP_14_PK;

drop table RELATIONSHIP_14;

drop index UTILIZADOR_PK;

drop table UTILIZADOR;

/*==============================================================*/
/* Table: ACESSO_A_FICHEIRO                                     */
/*==============================================================*/
create table ACESSO_A_FICHEIRO (
   ATTRIBUTE_23         VARCHAR(20)          not null,
   FILENAME             VARCHAR(30)          not null,
   constraint PK_ACESSO_A_FICHEIRO primary key (ATTRIBUTE_23, FILENAME)
);

/*==============================================================*/
/* Index: ACESSO_A_FICHEIRO_PK                                  */
/*==============================================================*/
create unique index ACESSO_A_FICHEIRO_PK on ACESSO_A_FICHEIRO (
ATTRIBUTE_23,
FILENAME
);

/*==============================================================*/
/* Index: ACESSO_A_FICHEIRO_FK                                  */
/*==============================================================*/
create  index ACESSO_A_FICHEIRO_FK on ACESSO_A_FICHEIRO (
ATTRIBUTE_23
);

/*==============================================================*/
/* Index: ACESSO_A_FICHEIRO2_FK                                 */
/*==============================================================*/
create  index ACESSO_A_FICHEIRO2_FK on ACESSO_A_FICHEIRO (
FILENAME
);

/*==============================================================*/
/* Table: ADICIONA_MUSICA                                       */
/*==============================================================*/
create table ADICIONA_MUSICA (
   ID_PLAYLIST          INT4                 not null,
   ID_MUSICA            INT4                 not null,
   constraint PK_ADICIONA_MUSICA primary key (ID_PLAYLIST, ID_MUSICA)
);

/*==============================================================*/
/* Index: ADICIONA_MUSICA_PK                                    */
/*==============================================================*/
create unique index ADICIONA_MUSICA_PK on ADICIONA_MUSICA (
ID_PLAYLIST,
ID_MUSICA
);

/*==============================================================*/
/* Index: ADICIONA_MUSICA_FK                                    */
/*==============================================================*/
create  index ADICIONA_MUSICA_FK on ADICIONA_MUSICA (
ID_PLAYLIST
);

/*==============================================================*/
/* Index: ADICIONA_MUSICA2_FK                                   */
/*==============================================================*/
create  index ADICIONA_MUSICA2_FK on ADICIONA_MUSICA (
ID_MUSICA
);

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM (
   ID_ALBUM             SERIAL               not null,
   ALBUM_NAME           VARCHAR(30)          not null,
   DESCRIPTION          VARCHAR(100)         null,
   RELEASE_DATE         DATE                 null,
   ARTIST_NAME          VARCHAR(30)          not null,
   GENRE                VARCHAR(20)          null,
   RATING               FLOAT8               null,
   constraint PK_ALBUM primary key (ID_ALBUM)
);

/*==============================================================*/
/* Index: ALBUM_PK                                              */
/*==============================================================*/
create unique index ALBUM_PK on ALBUM (
ID_ALBUM
);

/*==============================================================*/
/* Table: ALBUNS_DE_ARTISTA                                     */
/*==============================================================*/
create table ALBUNS_DE_ARTISTA (
   ID_ALBUM             INT4                 not null,
   ID_ARTISTA           INT4                 not null,
   constraint PK_ALBUNS_DE_ARTISTA primary key (ID_ALBUM, ID_ARTISTA)
);

/*==============================================================*/
/* Index: ALBUNS_DE_ARTISTA_PK                                  */
/*==============================================================*/
create unique index ALBUNS_DE_ARTISTA_PK on ALBUNS_DE_ARTISTA (
ID_ALBUM,
ID_ARTISTA
);

/*==============================================================*/
/* Index: ALBUNS_DE_ARTISTA_FK                                  */
/*==============================================================*/
create  index ALBUNS_DE_ARTISTA_FK on ALBUNS_DE_ARTISTA (
ID_ALBUM
);

/*==============================================================*/
/* Index: ALBUNS_DE_ARTISTA2_FK                                 */
/*==============================================================*/
create  index ALBUNS_DE_ARTISTA2_FK on ALBUNS_DE_ARTISTA (
ID_ARTISTA
);

/*==============================================================*/
/* Table: ARTISTA                                               */
/*==============================================================*/
create table ARTISTA (
   ID_ARTISTA           INT4                 not null,
   GENRE                VARCHAR(20)          null,
   BIOGRAPHY            VARCHAR(100)         null,
   BIRTH_DATE           DATE                 null,
   ARTIST_NAME          VARCHAR(30)          not null,
   INDIVIDUO            BOOL                 null,
   GRUPO                BOOL                 null,
   COMPOSITOR           BOOL                 null,
   constraint PK_ARTISTA primary key (ID_ARTISTA)
);

/*==============================================================*/
/* Index: ARTISTA_PK                                            */
/*==============================================================*/
create unique index ARTISTA_PK on ARTISTA (
ID_ARTISTA
);

/*==============================================================*/
/* Table: CONCERTO                                              */
/*==============================================================*/
create table CONCERTO (
   ID_CONCERTO          SERIAL               not null,
   LOCAL                VARCHAR(30)          null,
   DATA                 DATE                 not null,
   LOTACAO              INT4                 null,
   constraint PK_CONCERTO primary key (ID_CONCERTO, DATA)
);

/*==============================================================*/
/* Index: CONCERTO_PK                                           */
/*==============================================================*/
create unique index CONCERTO_PK on CONCERTO (
ID_CONCERTO,
DATA
);

/*==============================================================*/
/* Table: CRIA_PLAYLIST                                         */
/*==============================================================*/
create table CRIA_PLAYLIST (
   ATTRIBUTE_23         VARCHAR(20)          not null,
   ID_PLAYLIST          INT4                 not null,
   constraint PK_CRIA_PLAYLIST primary key (ATTRIBUTE_23, ID_PLAYLIST)
);

/*==============================================================*/
/* Index: CRIA_PLAYLIST_PK                                      */
/*==============================================================*/
create unique index CRIA_PLAYLIST_PK on CRIA_PLAYLIST (
ATTRIBUTE_23,
ID_PLAYLIST
);

/*==============================================================*/
/* Index: CRIA_PLAYLIST_FK                                      */
/*==============================================================*/
create  index CRIA_PLAYLIST_FK on CRIA_PLAYLIST (
ATTRIBUTE_23
);

/*==============================================================*/
/* Index: CRIA_PLAYLIST2_FK                                     */
/*==============================================================*/
create  index CRIA_PLAYLIST2_FK on CRIA_PLAYLIST (
ID_PLAYLIST
);

/*==============================================================*/
/* Table: CRITICA                                               */
/*==============================================================*/
create table CRITICA (
   ID_CRITICA           SERIAL               not null,
   ID_MUSICA            INT4                 not null,
   ID_ALBUM             INT4                 not null,
   ATTRIBUTE_23         VARCHAR(20)          not null,
   TEXT                 VARCHAR(300)         null,
   PONTUACAO            INT4                 null,
   constraint PK_CRITICA primary key (ID_CRITICA)
);

/*==============================================================*/
/* Index: CRITICA_PK                                            */
/*==============================================================*/
create unique index CRITICA_PK on CRITICA (
ID_CRITICA
);

/*==============================================================*/
/* Index: RELATIONSHIP_12_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_12_FK on CRITICA (
ID_ALBUM
);

/*==============================================================*/
/* Index: RELATIONSHIP_13_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_13_FK on CRITICA (
ID_MUSICA
);

/*==============================================================*/
/* Index: RELATIONSHIP_20_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_20_FK on CRITICA (
ATTRIBUTE_23
);

/*==============================================================*/
/* Table: DAR_UM_CONCERTO                                       */
/*==============================================================*/
create table DAR_UM_CONCERTO (
   ID_ARTISTA           INT4                 not null,
   ID_CONCERTO          INT4                 not null,
   DATA                 DATE                 not null,
   constraint PK_DAR_UM_CONCERTO primary key (ID_ARTISTA, ID_CONCERTO, DATA)
);

/*==============================================================*/
/* Index: DAR_UM_CONCERTO_PK                                    */
/*==============================================================*/
create unique index DAR_UM_CONCERTO_PK on DAR_UM_CONCERTO (
ID_ARTISTA,
ID_CONCERTO,
DATA
);

/*==============================================================*/
/* Index: DAR_UM_CONCERTO_FK                                    */
/*==============================================================*/
create  index DAR_UM_CONCERTO_FK on DAR_UM_CONCERTO (
ID_ARTISTA
);

/*==============================================================*/
/* Index: DAR_UM_CONCERTO2_FK                                   */
/*==============================================================*/
create  index DAR_UM_CONCERTO2_FK on DAR_UM_CONCERTO (
ID_CONCERTO,
DATA
);

/*==============================================================*/
/* Table: MUSICA                                                */
/*==============================================================*/
create table MUSICA (
   ID_MUSICA            SERIAL               not null,
   FILENAME             VARCHAR(30)          null,
   MUSIC_NAME           VARCHAR(30)          not null,
   ARTIST_NAME          VARCHAR(30)          not null,
   ALBUM_NAME           VARCHAR(30)          null,
   GENRE                VARCHAR(20)          null,
   DURATION             INT4                 null,
   constraint PK_MUSICA primary key (ID_MUSICA)
);

/*==============================================================*/
/* Index: MUSICA_PK                                             */
/*==============================================================*/
create unique index MUSICA_PK on MUSICA (
ID_MUSICA
);

/*==============================================================*/
/* Index: ASSOCIA_FICHEIRO2_FK                                  */
/*==============================================================*/
create  index ASSOCIA_FICHEIRO2_FK on MUSICA (
FILENAME
);

/*==============================================================*/
/* Table: MUSICAS_DE_ALBUM                                      */
/*==============================================================*/
create table MUSICAS_DE_ALBUM (
   ID_ALBUM             INT4                 not null,
   ID_MUSICA            INT4                 not null,
   constraint PK_MUSICAS_DE_ALBUM primary key (ID_ALBUM, ID_MUSICA)
);

/*==============================================================*/
/* Index: MUSICAS_DE_ALBUM_PK                                   */
/*==============================================================*/
create unique index MUSICAS_DE_ALBUM_PK on MUSICAS_DE_ALBUM (
ID_ALBUM,
ID_MUSICA
);

/*==============================================================*/
/* Index: MUSICAS_DE_ALBUM_FK                                   */
/*==============================================================*/
create  index MUSICAS_DE_ALBUM_FK on MUSICAS_DE_ALBUM (
ID_ALBUM
);

/*==============================================================*/
/* Index: MUSICAS_DE_ALBUM2_FK                                  */
/*==============================================================*/
create  index MUSICAS_DE_ALBUM2_FK on MUSICAS_DE_ALBUM (
ID_MUSICA
);

/*==============================================================*/
/* Table: MUSICAS_DE_ARTISTA                                    */
/*==============================================================*/
create table MUSICAS_DE_ARTISTA (
   ID_MUSICA            INT4                 not null,
   ID_ARTISTA           INT4                 not null,
   constraint PK_MUSICAS_DE_ARTISTA primary key (ID_MUSICA, ID_ARTISTA)
);

/*==============================================================*/
/* Index: MUSICAS_DE_ARTISTA_PK                                 */
/*==============================================================*/
create unique index MUSICAS_DE_ARTISTA_PK on MUSICAS_DE_ARTISTA (
ID_MUSICA,
ID_ARTISTA
);

/*==============================================================*/
/* Index: MUSICAS_DE_ARTISTA_FK                                 */
/*==============================================================*/
create  index MUSICAS_DE_ARTISTA_FK on MUSICAS_DE_ARTISTA (
ID_MUSICA
);

/*==============================================================*/
/* Index: MUSICAS_DE_ARTISTA2_FK                                */
/*==============================================================*/
create  index MUSICAS_DE_ARTISTA2_FK on MUSICAS_DE_ARTISTA (
ID_ARTISTA
);

/*==============================================================*/
/* Table: MUSICFILE                                             */
/*==============================================================*/
create table MUSICFILE (
   FILENAME             VARCHAR(30)          not null,
   ID_MUSICA            INT4                 not null,
   DIRETORIA            VARCHAR(30)          not null,
   constraint PK_MUSICFILE primary key (FILENAME)
);

/*==============================================================*/
/* Index: MUSICFILE_PK                                          */
/*==============================================================*/
create unique index MUSICFILE_PK on MUSICFILE (
FILENAME
);

/*==============================================================*/
/* Index: ASSOCIA_FICHEIRO_FK                                   */
/*==============================================================*/
create  index ASSOCIA_FICHEIRO_FK on MUSICFILE (
ID_MUSICA
);

/*==============================================================*/
/* Table: PERTENCA                                              */
/*==============================================================*/
create table PERTENCA (
   ART_ID_ARTISTA       INT4                 not null,
   ID_ARTISTA           INT4                 null,
   constraint PK_PERTENCA primary key (ART_ID_ARTISTA)
);

/*==============================================================*/
/* Index: PERTENCA_PK                                           */
/*==============================================================*/
create unique index PERTENCA_PK on PERTENCA (
ART_ID_ARTISTA
);

/*==============================================================*/
/* Table: PLAYLIST                                              */
/*==============================================================*/
create table PLAYLIST (
   ID_PLAYLIST          SERIAL               not null,
   PLAYLIST_NAME        VARCHAR(30)          not null,
   PRIVACY_TYPE         VARCHAR(10)          null,
   constraint PK_PLAYLIST primary key (ID_PLAYLIST)
);

/*==============================================================*/
/* Index: PLAYLIST_PK                                           */
/*==============================================================*/
create unique index PLAYLIST_PK on PLAYLIST (
ID_PLAYLIST
);

/*==============================================================*/
/* Table: RELATIONSHIP_14                                       */
/*==============================================================*/
create table RELATIONSHIP_14 (
   ID_ARTISTA           INT4                 not null,
   ID_MUSICA            INT4                 not null,
   constraint PK_RELATIONSHIP_14 primary key (ID_ARTISTA, ID_MUSICA)
);

/*==============================================================*/
/* Index: RELATIONSHIP_14_PK                                    */
/*==============================================================*/
create unique index RELATIONSHIP_14_PK on RELATIONSHIP_14 (
ID_ARTISTA,
ID_MUSICA
);

/*==============================================================*/
/* Index: RELATIONSHIP_14_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_14_FK on RELATIONSHIP_14 (
ID_ARTISTA
);

/*==============================================================*/
/* Index: RELATIONSHIP_15_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_15_FK on RELATIONSHIP_14 (
ID_MUSICA
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR (
   ATTRIBUTE_23         VARCHAR(20)          not null,
   PASSWORD             VARCHAR(20)          not null,
   USER_TYPE            VARCHAR(10)          null,
   constraint PK_UTILIZADOR primary key (ATTRIBUTE_23)
);

/*==============================================================*/
/* Index: UTILIZADOR_PK                                         */
/*==============================================================*/
create unique index UTILIZADOR_PK on UTILIZADOR (
ATTRIBUTE_23
);

alter table ACESSO_A_FICHEIRO
   add constraint FK_ACESSO_A_ACESSO_A__UTILIZAD foreign key (ATTRIBUTE_23)
      references UTILIZADOR (ATTRIBUTE_23)
      on delete restrict on update restrict;

alter table ACESSO_A_FICHEIRO
   add constraint FK_ACESSO_A_ACESSO_A__MUSICFIL foreign key (FILENAME)
      references MUSICFILE (FILENAME)
      on delete restrict on update restrict;

alter table ADICIONA_MUSICA
   add constraint FK_ADICIONA_ADICIONA__PLAYLIST foreign key (ID_PLAYLIST)
      references PLAYLIST (ID_PLAYLIST)
      on delete restrict on update restrict;

alter table ADICIONA_MUSICA
   add constraint FK_ADICIONA_ADICIONA__MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

alter table ALBUNS_DE_ARTISTA
   add constraint FK_ALBUNS_D_ALBUNS_DE_ALBUM foreign key (ID_ALBUM)
      references ALBUM (ID_ALBUM)
      on delete restrict on update restrict;

alter table ALBUNS_DE_ARTISTA
   add constraint FK_ALBUNS_D_ALBUNS_DE_ARTISTA foreign key (ID_ARTISTA)
      references ARTISTA (ID_ARTISTA)
      on delete restrict on update restrict;

alter table ARTISTA
   add constraint FK_ARTISTA_RELATIONS_PERTENCA foreign key (ID_ARTISTA)
      references PERTENCA (ART_ID_ARTISTA)
      on delete restrict on update restrict;

alter table CRIA_PLAYLIST
   add constraint FK_CRIA_PLA_CRIA_PLAY_UTILIZAD foreign key (ATTRIBUTE_23)
      references UTILIZADOR (ATTRIBUTE_23)
      on delete restrict on update restrict;

alter table CRIA_PLAYLIST
   add constraint FK_CRIA_PLA_CRIA_PLAY_PLAYLIST foreign key (ID_PLAYLIST)
      references PLAYLIST (ID_PLAYLIST)
      on delete restrict on update restrict;

alter table CRITICA
   add constraint FK_CRITICA_RELATIONS_ALBUM foreign key (ID_ALBUM)
      references ALBUM (ID_ALBUM)
      on delete restrict on update restrict;

alter table CRITICA
   add constraint FK_CRITICA_RELATIONS_MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

alter table CRITICA
   add constraint FK_CRITICA_RELATIONS_UTILIZAD foreign key (ATTRIBUTE_23)
      references UTILIZADOR (ATTRIBUTE_23)
      on delete restrict on update restrict;

alter table DAR_UM_CONCERTO
   add constraint FK_DAR_UM_C_DAR_UM_CO_ARTISTA foreign key (ID_ARTISTA)
      references ARTISTA (ID_ARTISTA)
      on delete restrict on update restrict;

alter table DAR_UM_CONCERTO
   add constraint FK_DAR_UM_C_DAR_UM_CO_CONCERTO foreign key (ID_CONCERTO, DATA)
      references CONCERTO (ID_CONCERTO, DATA)
      on delete restrict on update restrict;

alter table MUSICA
   add constraint FK_MUSICA_ASSOCIA_F_MUSICFIL foreign key (FILENAME)
      references MUSICFILE (FILENAME)
      on delete restrict on update restrict;

alter table MUSICAS_DE_ALBUM
   add constraint FK_MUSICAS__MUSICAS_D_ALBUM foreign key (ID_ALBUM)
      references ALBUM (ID_ALBUM)
      on delete restrict on update restrict;

alter table MUSICAS_DE_ALBUM
   add constraint FK_MUSICAS__MUSICAS_D_MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

alter table MUSICAS_DE_ARTISTA
   add constraint FK_MUSICAS__MUSICAS_D_MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

alter table MUSICAS_DE_ARTISTA
   add constraint FK_MUSICAS__MUSICAS_D_ARTISTA foreign key (ID_ARTISTA)
      references ARTISTA (ID_ARTISTA)
      on delete restrict on update restrict;

alter table MUSICFILE
   add constraint FK_MUSICFIL_ASSOCIA_F_MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

alter table PERTENCA
   add constraint FK_PERTENCA_RELATIONS_ARTISTA foreign key (ID_ARTISTA)
      references ARTISTA (ID_ARTISTA)
      on delete restrict on update restrict;

alter table RELATIONSHIP_14
   add constraint FK_RELATION_RELATIONS_ARTISTA foreign key (ID_ARTISTA)
      references ARTISTA (ID_ARTISTA)
      on delete restrict on update restrict;

alter table RELATIONSHIP_14
   add constraint FK_RELATION_RELATIONS_MUSICA foreign key (ID_MUSICA)
      references MUSICA (ID_MUSICA)
      on delete restrict on update restrict;

