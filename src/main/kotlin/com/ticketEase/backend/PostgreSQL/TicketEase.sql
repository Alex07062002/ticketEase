PGDMP     5    
                {         
   TicketEase    15.2    15.2 D    `           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            a           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            b           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            c           1262    16398 
   TicketEase    DATABASE     �   CREATE DATABASE "TicketEase" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "TicketEase";
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            d           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            i           1247    16558    City    TYPE     u   CREATE TYPE public."City" AS ENUM (
    'Воронеж',
    'Москва',
    'Санкт-Петербург'
);
    DROP TYPE public."City";
       public          postgres    false    4            {           1247    16633    StatusPlace    TYPE     �   CREATE TYPE public."StatusPlace" AS ENUM (
    'Свободно',
    'Занято',
    'На реконструкции'
);
     DROP TYPE public."StatusPlace";
       public          postgres    false    4            Q           1247    16403 
   event_type    TYPE     �   CREATE TYPE public.event_type AS ENUM (
    'Выставка',
    'Концерт',
    'Спектакль',
    'Экскурсия',
    'Праздник'
);
    DROP TYPE public.event_type;
       public          postgres    false    4            T           1247    16414 
   genre_type    TYPE     �  CREATE TYPE public.genre_type AS ENUM (
    'Утренник',
    'Фестиваль',
    'Опера',
    'Балет',
    'Мюзикл',
    'Театр',
    'Инструментальный концерт',
    'Вокальный концерт',
    'Танцевальный концерт',
    'Поэический вечер',
    'Картинная вытавка'
);
    DROP TYPE public.genre_type;
       public          postgres    false    4            r           1247    16592    statusEv    TYPE     r   CREATE TYPE public."statusEv" AS ENUM (
    'Создано',
    'Проведено',
    'Отменено'
);
    DROP TYPE public."statusEv";
       public          postgres    false    4            u           1247    16600 	   statusFav    TYPE     W   CREATE TYPE public."statusFav" AS ENUM (
    'Создано',
    'Удалено'
);
    DROP TYPE public."statusFav";
       public          postgres    false    4            l           1247    16575 	   statusOrg    TYPE     v   CREATE TYPE public."statusOrg" AS ENUM (
    'В рассмотрении',
    'Создан',
    'Удалён'
);
    DROP TYPE public."statusOrg";
       public          postgres    false    4            o           1247    16582 
   statusTick    TYPE     �   CREATE TYPE public."statusTick" AS ENUM (
    'В продаже',
    'Забронирован',
    'Выкуплен',
    'Отменён'
);
    DROP TYPE public."statusTick";
       public          postgres    false    4            �            1259    16437    buyer    TABLE     X  CREATE TABLE public.buyer (
    id bigint NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL,
    mobile integer,
    secret character varying NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    city character varying NOT NULL
);
    DROP TABLE public.buyer;
       public         heap    postgres    false    4            �            1259    16442    buyer_id_seq    SEQUENCE     �   ALTER TABLE public.buyer ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.buyer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    214            �            1259    16540    cart    TABLE     �   CREATE TABLE public.cart (
    ticket_id bigint NOT NULL,
    buyer_id bigint NOT NULL,
    "orderDate" timestamp without time zone,
    amount real NOT NULL
);
    DROP TABLE public.cart;
       public         heap    postgres    false    4            �            1259    16447    event    TABLE     0  CREATE TABLE public.event (
    id integer NOT NULL,
    "placeTime_id" integer,
    organizer_id integer NOT NULL,
    name character varying NOT NULL,
    genre public.genre_type NOT NULL,
    type public.event_type NOT NULL,
    description character varying,
    status character varying NOT NULL
);
    DROP TABLE public.event;
       public         heap    postgres    false    852    849    4            �            1259    16452    event_id_seq    SEQUENCE     �   ALTER TABLE public.event ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    216            �            1259    16565 	   favorites    TABLE     �   CREATE TABLE public.favorites (
    event_id bigint NOT NULL,
    buyer_id bigint NOT NULL,
    status character varying NOT NULL
);
    DROP TABLE public.favorites;
       public         heap    postgres    false    4            �            1259    16457 	   organizer    TABLE     �  CREATE TABLE public.organizer (
    id bigint NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL,
    mobile integer NOT NULL,
    city public."City" NOT NULL,
    secret character varying NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    status character varying NOT NULL
);
    DROP TABLE public.organizer;
       public         heap    postgres    false    4    873            �            1259    16462    organizer_id_seq    SEQUENCE     �   ALTER TABLE public.organizer ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.organizer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    218            �            1259    16463    place    TABLE     �   CREATE TABLE public.place (
    id bigint NOT NULL,
    name character varying(30) NOT NULL,
    capacity bigint NOT NULL,
    "numRow" integer,
    "numColumn" integer
);
    DROP TABLE public.place;
       public         heap    postgres    false    4            �            1259    16645 	   placeTime    TABLE     �   CREATE TABLE public."placeTime" (
    "placeTime_id" bigint NOT NULL,
    place_id integer NOT NULL,
    date timestamp without time zone NOT NULL,
    status character varying NOT NULL
);
    DROP TABLE public."placeTime";
       public         heap    postgres    false    4            �            1259    16466    place_id_seq    SEQUENCE     �   ALTER TABLE public.place ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.place_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220    4            �            1259    16467    ticket    TABLE     �   CREATE TABLE public.ticket (
    id bigint NOT NULL,
    event_id bigint NOT NULL,
    buyer_id bigint,
    "row" integer,
    "column" integer,
    price double precision NOT NULL,
    status character varying NOT NULL
);
    DROP TABLE public.ticket;
       public         heap    postgres    false    4            �            1259    16470    ticket_id_seq    SEQUENCE     �   ALTER TABLE public.ticket ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    222            Q          0    16437    buyer 
   TABLE DATA           `   COPY public.buyer (id, login, password, email, mobile, secret, name, surname, city) FROM stdin;
    public          postgres    false    214   RO       [          0    16540    cart 
   TABLE DATA           H   COPY public.cart (ticket_id, buyer_id, "orderDate", amount) FROM stdin;
    public          postgres    false    224   oO       S          0    16447    event 
   TABLE DATA           i   COPY public.event (id, "placeTime_id", organizer_id, name, genre, type, description, status) FROM stdin;
    public          postgres    false    216   �O       \          0    16565 	   favorites 
   TABLE DATA           ?   COPY public.favorites (event_id, buyer_id, status) FROM stdin;
    public          postgres    false    225   �O       U          0    16457 	   organizer 
   TABLE DATA           l   COPY public.organizer (id, login, password, email, mobile, city, secret, name, surname, status) FROM stdin;
    public          postgres    false    218   �O       W          0    16463    place 
   TABLE DATA           J   COPY public.place (id, name, capacity, "numRow", "numColumn") FROM stdin;
    public          postgres    false    220   �O       ]          0    16645 	   placeTime 
   TABLE DATA           M   COPY public."placeTime" ("placeTime_id", place_id, date, status) FROM stdin;
    public          postgres    false    226    P       Y          0    16467    ticket 
   TABLE DATA           X   COPY public.ticket (id, event_id, buyer_id, "row", "column", price, status) FROM stdin;
    public          postgres    false    222   P       e           0    0    buyer_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.buyer_id_seq', 6, true);
          public          postgres    false    215            f           0    0    event_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.event_id_seq', 1, false);
          public          postgres    false    217            g           0    0    organizer_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.organizer_id_seq', 1, false);
          public          postgres    false    219            h           0    0    place_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.place_id_seq', 17, true);
          public          postgres    false    221            i           0    0    ticket_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.ticket_id_seq', 1, false);
          public          postgres    false    223            �           2606    16838    buyer LoginUnique 
   CONSTRAINT     O   ALTER TABLE ONLY public.buyer
    ADD CONSTRAINT "LoginUnique" UNIQUE (login);
 =   ALTER TABLE ONLY public.buyer DROP CONSTRAINT "LoginUnique";
       public            postgres    false    214            �           2606    16624    ticket PriceMoreThenZero    CHECK CONSTRAINT     t   ALTER TABLE public.ticket
    ADD CONSTRAINT "PriceMoreThenZero" CHECK ((price > (0)::double precision)) NOT VALID;
 ?   ALTER TABLE public.ticket DROP CONSTRAINT "PriceMoreThenZero";
       public          postgres    false    222    222            �           2606    16740    buyer buyer_id 
   CONSTRAINT     L   ALTER TABLE ONLY public.buyer
    ADD CONSTRAINT buyer_id PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.buyer DROP CONSTRAINT buyer_id;
       public            postgres    false    214            �           2606    16725    cart cart2_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart2_pkey PRIMARY KEY (ticket_id, buyer_id);
 9   ALTER TABLE ONLY public.cart DROP CONSTRAINT cart2_pkey;
       public            postgres    false    224    224            �           2606    16476    event event_id 
   CONSTRAINT     L   ALTER TABLE ONLY public.event
    ADD CONSTRAINT event_id PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.event DROP CONSTRAINT event_id;
       public            postgres    false    216            �           2606    16697    favorites favorites2_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites2_pkey PRIMARY KEY (event_id, buyer_id);
 C   ALTER TABLE ONLY public.favorites DROP CONSTRAINT favorites2_pkey;
       public            postgres    false    225    225            �           2606    16763    organizer organizer_id 
   CONSTRAINT     T   ALTER TABLE ONLY public.organizer
    ADD CONSTRAINT organizer_id PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.organizer DROP CONSTRAINT organizer_id;
       public            postgres    false    218            �           2606    16663    placeTime placeDateUnique 
   CONSTRAINT     b   ALTER TABLE ONLY public."placeTime"
    ADD CONSTRAINT "placeDateUnique" UNIQUE (place_id, date);
 G   ALTER TABLE ONLY public."placeTime" DROP CONSTRAINT "placeDateUnique";
       public            postgres    false    226    226            �           2606    16786    placeTime placeTime_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public."placeTime"
    ADD CONSTRAINT "placeTime_pkey" PRIMARY KEY ("placeTime_id");
 F   ALTER TABLE ONLY public."placeTime" DROP CONSTRAINT "placeTime_pkey";
       public            postgres    false    226            �           2606    16775    place place_id 
   CONSTRAINT     L   ALTER TABLE ONLY public.place
    ADD CONSTRAINT place_id PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.place DROP CONSTRAINT place_id;
       public            postgres    false    220            �           2606    16800    ticket ticket_id 
   CONSTRAINT     N   ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_id PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.ticket DROP CONSTRAINT ticket_id;
       public            postgres    false    222            �           1259    16726    fki_CartToBuyer    INDEX     F   CREATE INDEX "fki_CartToBuyer" ON public.cart USING btree (buyer_id);
 %   DROP INDEX public."fki_CartToBuyer";
       public            postgres    false    224            �           1259    16712    fki_CartToTicket    INDEX     H   CREATE INDEX "fki_CartToTicket" ON public.cart USING btree (ticket_id);
 &   DROP INDEX public."fki_CartToTicket";
       public            postgres    false    224            �           1259    16698    fki_favoritesToBuyer    INDEX     P   CREATE INDEX "fki_favoritesToBuyer" ON public.favorites USING btree (buyer_id);
 *   DROP INDEX public."fki_favoritesToBuyer";
       public            postgres    false    225            �           1259    16684    fki_favoritesToEvent    INDEX     P   CREATE INDEX "fki_favoritesToEvent" ON public.favorites USING btree (event_id);
 *   DROP INDEX public."fki_favoritesToEvent";
       public            postgres    false    225            �           1259    16655 	   fki_place    INDEX     E   CREATE INDEX fki_place ON public."placeTime" USING btree (place_id);
    DROP INDEX public.fki_place;
       public            postgres    false    226            �           1259    16681    fki_placeTime    INDEX     K   CREATE INDEX "fki_placeTime" ON public.event USING btree ("placeTime_id");
 #   DROP INDEX public."fki_placeTime";
       public            postgres    false    216            �           1259    16675    fki_placeTime_id    INDEX     N   CREATE INDEX "fki_placeTime_id" ON public.event USING btree ("placeTime_id");
 &   DROP INDEX public."fki_placeTime_id";
       public            postgres    false    216            �           1259    16661    fki_t    INDEX     A   CREATE INDEX fki_t ON public.event USING btree ("placeTime_id");
    DROP INDEX public.fki_t;
       public            postgres    false    216            �           2606    16751    cart CartToBuyer    FK CONSTRAINT     |   ALTER TABLE ONLY public.cart
    ADD CONSTRAINT "CartToBuyer" FOREIGN KEY (buyer_id) REFERENCES public.buyer(id) NOT VALID;
 <   ALTER TABLE ONLY public.cart DROP CONSTRAINT "CartToBuyer";
       public          postgres    false    214    3233    224            �           2606    16801    cart CartToTicket    FK CONSTRAINT        ALTER TABLE ONLY public.cart
    ADD CONSTRAINT "CartToTicket" FOREIGN KEY (ticket_id) REFERENCES public.ticket(id) NOT VALID;
 =   ALTER TABLE ONLY public.cart DROP CONSTRAINT "CartToTicket";
       public          postgres    false    222    3244    224            �           2606    16819    ticket buyer_id    FK CONSTRAINT     y   ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT buyer_id FOREIGN KEY (buyer_id) REFERENCES public.buyer(id) NOT VALID;
 9   ALTER TABLE ONLY public.ticket DROP CONSTRAINT buyer_id;
       public          postgres    false    222    214    3233            �           2606    16810    ticket event_id    FK CONSTRAINT     o   ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT event_id FOREIGN KEY (event_id) REFERENCES public.event(id);
 9   ALTER TABLE ONLY public.ticket DROP CONSTRAINT event_id;
       public          postgres    false    216    3235    222            �           2606    16746    favorites favoritesToBuyer    FK CONSTRAINT     �   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT "favoritesToBuyer" FOREIGN KEY (buyer_id) REFERENCES public.buyer(id) NOT VALID;
 F   ALTER TABLE ONLY public.favorites DROP CONSTRAINT "favoritesToBuyer";
       public          postgres    false    214    225    3233            �           2606    16685    favorites favoritesToEvent    FK CONSTRAINT     �   ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT "favoritesToEvent" FOREIGN KEY (event_id) REFERENCES public.event(id) NOT VALID;
 F   ALTER TABLE ONLY public.favorites DROP CONSTRAINT "favoritesToEvent";
       public          postgres    false    225    216    3235            �           2606    16764    event organizer_id    FK CONSTRAINT     z   ALTER TABLE ONLY public.event
    ADD CONSTRAINT organizer_id FOREIGN KEY (organizer_id) REFERENCES public.organizer(id);
 <   ALTER TABLE ONLY public.event DROP CONSTRAINT organizer_id;
       public          postgres    false    216    3240    218            �           2606    16776    placeTime place    FK CONSTRAINT     {   ALTER TABLE ONLY public."placeTime"
    ADD CONSTRAINT place FOREIGN KEY (place_id) REFERENCES public.place(id) NOT VALID;
 ;   ALTER TABLE ONLY public."placeTime" DROP CONSTRAINT place;
       public          postgres    false    226    220    3242            �           2606    16787    event placeTime_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.event
    ADD CONSTRAINT "placeTime_id" FOREIGN KEY ("placeTime_id") REFERENCES public."placeTime"("placeTime_id") NOT VALID;
 >   ALTER TABLE ONLY public.event DROP CONSTRAINT "placeTime_id";
       public          postgres    false    216    226    3257            Q      x������ � �      [      x������ � �      S      x������ � �      \      x������ � �      U      x������ � �      W      x������ � �      ]      x������ � �      Y      x������ � �     