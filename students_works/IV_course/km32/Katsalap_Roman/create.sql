alter table CARDS
   drop constraint FK_CARDS_USER_CARD_USERS;

alter table PAYMENTS
   drop constraint FK_PAYMENTS_PAYMENT_C_CARDS;

alter table PAYMENTS
   drop constraint FK_PAYMENTS_PAYMENT_P_USERS_PH;

alter table USERS_ACCOUNT_STATUSES
   drop constraint FK_USERS_AC_ACCOUNT_S_USERS;

alter table USERS_ACCOUNT_STATUSES
   drop constraint FK_USERS_AC_ACCOUNTS__ACCOUNT_;

alter table USERS_PHONES
   drop constraint FK_USERS_PH_USER_PHON_USERS;

drop table ACCOUNT_STATUSES cascade constraints;

drop index user_card_fk;

drop table CARDS cascade constraints;

drop index payment_card_fk;

drop index payment_phone_fk;

drop table PAYMENTS cascade constraints;

drop table USERS cascade constraints;

drop index account_statuses_fk;

drop index accounts_statuses_fk;

drop table USERS_ACCOUNT_STATUSES cascade constraints;

drop index user_phone_fk;

drop table USERS_PHONES cascade constraints;

/*==============================================================*/
/* Table: ACCOUNT_STATUSES                                      */
/*==============================================================*/
create table ACCOUNT_STATUSES 
(
   status_name          VARCHAR2(12)         not null,
   status_description   CLOB,
   constraint PK_ACCOUNT_STATUSES primary key (status_name)
);

/*==============================================================*/
/* Table: CARDS                                                 */
/*==============================================================*/
create table CARDS 
(
   card_no              VARCHAR2(16)         not null,
   login                VARCHAR2(30)         not null,
   token                VARCHAR2(25)         not null,
   card_name            VARCHAR2(30),
   constraint PK_CARDS primary key (card_no)
);

/*==============================================================*/
/* Index: user_card_fk                                          */
/*==============================================================*/
create index user_card_fk on CARDS (
   login ASC
);

/*==============================================================*/
/* Table: PAYMENTS                                              */
/*==============================================================*/
create table PAYMENTS 
(
   payment_id           INTEGER              not null,
   login                VARCHAR2(30)         not null,
   phone_number         VARCHAR2(10)         not null,
   card_no              VARCHAR2(16)         not null,
   "date"               TIMESTAMP            not null,
   amount               NUMBER(4,2)          not null,
   constraint PK_PAYMENTS primary key (payment_id)
);

/*==============================================================*/
/* Index: payment_phone_fk                                      */
/*==============================================================*/
create index payment_phone_fk on PAYMENTS (
   login ASC,
   phone_number ASC
);

/*==============================================================*/
/* Index: payment_card_fk                                       */
/*==============================================================*/
create index payment_card_fk on PAYMENTS (
   card_no ASC
);

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS 
(
   login                VARCHAR2(30)         not null,
   email                VARCHAR2(50)         not null,
   hash_pass            VARCHAR2(60)         not null,
   salt                 VARCHAR2(30)         not null,
   hash_link            VARCHAR2(10),
   is_admin             SMALLINT,
   constraint PK_USERS primary key (login)
);

/*==============================================================*/
/* Table: USERS_ACCOUNT_STATUSES                                */
/*==============================================================*/
create table USERS_ACCOUNT_STATUSES 
(
   login                VARCHAR2(30)         not null,
   status_name          VARCHAR2(12)         not null,
   date_of_status       TIMESTAMP            not null,
   status_message       CLOB,
   constraint PK_USERS_ACCOUNT_STATUSES primary key (login, status_name, date_of_status)
);

/*==============================================================*/
/* Index: accounts_statuses_fk                                  */
/*==============================================================*/
create index accounts_statuses_fk on USERS_ACCOUNT_STATUSES (
   status_name ASC
);

/*==============================================================*/
/* Index: account_statuses_fk                                   */
/*==============================================================*/
create index account_statuses_fk on USERS_ACCOUNT_STATUSES (
   login ASC
);

/*==============================================================*/
/* Table: USERS_PHONES                                          */
/*==============================================================*/
create table USERS_PHONES 
(
   login                VARCHAR2(30)         not null,
   phone_number         VARCHAR2(10)         not null,
   phone_name           VARCHAR2(40),
   constraint PK_USERS_PHONES primary key (login, phone_number)
);

/*==============================================================*/
/* Index: user_phone_fk                                         */
/*==============================================================*/
create index user_phone_fk on USERS_PHONES (
   login ASC
);

alter table CARDS
   add constraint FK_CARDS_USER_CARD_USERS foreign key (login)
      references USERS (login);

alter table PAYMENTS
   add constraint FK_PAYMENTS_PAYMENT_C_CARDS foreign key (card_no)
      references CARDS (card_no);

alter table PAYMENTS
   add constraint FK_PAYMENTS_PAYMENT_P_USERS_PH foreign key (login, phone_number)
      references USERS_PHONES (login, phone_number);

alter table USERS_ACCOUNT_STATUSES
   add constraint FK_USERS_AC_ACCOUNT_S_USERS foreign key (login)
      references USERS (login);

alter table USERS_ACCOUNT_STATUSES
   add constraint FK_USERS_AC_ACCOUNTS__ACCOUNT_ foreign key (status_name)
      references ACCOUNT_STATUSES (status_name);

alter table USERS_PHONES
   add constraint FK_USERS_PH_USER_PHON_USERS foreign key (login)
      references USERS (login);
