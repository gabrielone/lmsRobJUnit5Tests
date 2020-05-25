---------------Database Script for Leave Management System--------------------
--
--CREATE DATABASE lms;
--
--\c lms
--
-----------------Newsletter table contains all news data-----------------
--
---- Table: public.newsletter
--
---- DROP TABLE public.newsletter;
--
CREATE TABLE newsletter
(
    id serial,
    content text COLLATE pg_catalog."default",
    created timestamp without time zone,
    image text COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT newsletter_pkey PRIMARY KEY (id)
); 
