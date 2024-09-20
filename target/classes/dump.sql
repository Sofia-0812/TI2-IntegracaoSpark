--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)
-- Dumped by pg_dump version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: id-livro; Type: SEQUENCE; Schema: public; Owner: sofiagrossi
--

CREATE SEQUENCE public."id-livro"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE public."id-livro" OWNER TO sofiagrossi;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: livro; Type: TABLE; Schema: public; Owner: sofiagrossi
--

CREATE TABLE public.livro (
    id integer DEFAULT nextval('public."id-livro"'::regclass) NOT NULL,
    autor text,
    titulo text,
    editora text,
    preco double precision,
    ano integer
);


ALTER TABLE public.livro OWNER TO sofiagrossi;

--
-- Name: livro livro_pkey; Type: CONSTRAINT; Schema: public; Owner: sofiagrossi
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--    