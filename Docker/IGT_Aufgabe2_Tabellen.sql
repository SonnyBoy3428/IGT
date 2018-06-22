drop database if exists customerOrderManager;
create database if not exists customerOrderManager;
use customerOrderManager;

/*Tables representing the entities*/
create table if not exists WAREHOUSE(WarehouseId int not null auto_increment, Location varchar(50), Owner char(60), primary key(WarehouseId));
create table if not exists DISTRICT(DistrictId int not null auto_increment, DistrictName varchar(50), DistrictSize double default 0.0, WarehouseId int, primary key(DistrictId), foreign key(WarehouseId) references WAREHOUSE(WarehouseId));
create table if not exists CUSTOMER(CustomerId int not null auto_increment, FirstName varchar(30), LastName varchar(30), Address varchar(50), Telephone varchar(15), CreditCardNr varchar(12), DistrictId int,  primary key(CustomerId), foreign key(DistrictId) references DISTRICT(DistrictId));
create table if not exists CUSTOMER_ORDER(OrderId int not null auto_increment, OrderDate varchar(10), OrderCarriedOut boolean default false, TotalCost double default 0.0, CustomerId int, primary key(OrderId), foreign key(CustomerId) references CUSTOMER(CustomerId));
create table if not exists ITEM(ItemId int not null auto_increment, ItemName varchar(50), Price double default 0.0, primary key(ItemId));

/*Many-to-Many Relationships*/
create table if not exists ORDERLINE(ItemId int not null, OrderId int not null, Quantity int default 0, primary key(ItemId, OrderId), foreign key(ItemId) references ITEM(ItemId), foreign key(OrderId) references CUSTOMER_ORDER(OrderId));
create table if not exists STOCK(ItemId int not null, WarehouseId int not null, Quantity int default 0, primary key(ItemId, WarehouseId), foreign key(ItemId) references ITEM(ItemId), foreign key(WarehouseId) references WAREHOUSE(WarehouseId));

/*Views on the orders */
create view HISTORY as select OrderId, OrderDate, CustomerId from CUSTOMER_ORDER where OrderDate like '%2018%';
create view NEWORDER as select OrderId, OrderDate, CustomerId from CUSTOMER_ORDER where OrderDate like '%2018-06-%';