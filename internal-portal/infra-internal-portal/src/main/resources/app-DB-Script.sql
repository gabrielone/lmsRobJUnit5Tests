-------------Database Script for Leave Management System--------------------

CREATE DATABASE lms;

\c lms

---------------UserInfo table contains all users data-----------------

CREATE TABLE userinfo
(
  id serial,
  email character varying(50) NOT NULL,
  active boolean,
  last_name character varying(30),
  first_name character varying(30) NOT NULL,
  password character varying(255),
  role character varying(20),
  CONSTRAINT users_pkey PRIMARY KEY (id)
);



-------- Leave Details table-----------

CREATE TABLE leave_details
(
	id serial,
	username character varying(50) NOT NULL,
	employee_name character varying(50) NOT NULL,
	from_date timestamp without time zone NOT NULL,
	to_date timestamp without time zone NOT NULL,
	leave_type character varying(50) NOT NULL,
	reason character varying(300) NOT NULL,
	duration integer,
	accept_reject_flag boolean,
	active boolean,
	CONSTRAINT leave_pkey PRIMARY KEY (id)
);

CREATE TABLE yearly_time_off
(
	id serial,
	from_date timestamp without time zone NOT NULL,
	to_date timestamp without time zone NOT NULL,
	vacantion_type character varying(300) NOT NULL,
	duration integer,
	CONSTRAINT yearly_pkey PRIMARY KEY (id)
);

