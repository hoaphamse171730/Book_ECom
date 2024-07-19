CREATE DATABASE [tbsd]
USE [tbsd]

CREATE TABLE [user] (
  [user_id] int NOT NULL IDENTITY(1, 1),
  [fullname] nvarchar(30) NOT NULL,
  [email] nvarchar(48) NOT NULL,
  [password] nvarchar(64) NOT NULL,
  [gender] nvarchar(10) NOT NULL,
  [address] nvarchar(150) NOT NULL,
  [phone_number] nvarchar(12) NOT NULL,
  [role] nvarchar(10) NOT NULL,
  [created_at] datetime NOT NULL,
  PRIMARY KEY ([user_id])
)
GO

CREATE TABLE [author] (
  [author_id] int NOT NULL IDENTITY(1, 1),
  [name] nvarchar(30) NOT NULL,
  PRIMARY KEY ([author_id])
)
GO

CREATE TABLE [authors_of_product] (
  [author_id] int NOT NULL,
  [product_id] int NOT NULL,
  PRIMARY KEY ([author_id], [product_id])
)
GO

CREATE TABLE [category] (
  [category_id] int NOT NULL IDENTITY(1, 1),
  [name] nvarchar(50) NOT NULL,
  PRIMARY KEY ([category_id])
)
GO

CREATE TABLE [product] (
  [product_id] int NOT NULL IDENTITY(1, 1),
  [category_id] int NOT NULL,
  [name] nvarchar(150) NOT NULL,
  [short_description] nvarchar(max) NOT NULL,
  [description] nvarchar(max) NOT NULL,
  [price] int,
  [manufacturer] nvarchar(50) NOT NULL DEFAULT '',
  [publisher] nvarchar(50) NOT NULL DEFAULT '',
  [size] nvarchar(30) NOT NULL DEFAULT '',
  [format] nvarchar(15) NOT NULL DEFAULT '',
  [pages] smallint,
  [remain] smallint DEFAULT (0),
  [publish_day] datetime NOT NULL,
  [created_at] datetime NOT NULL,
  PRIMARY KEY ([product_id])
)
GO

CREATE TABLE [favorite] (
  [product_id] int NOT NULL,
  [user_id] int NOT NULL,
  [created_at] datetime,
  PRIMARY KEY ([product_id], [user_id])
)
GO

CREATE TABLE [feedback] (
  [feedback_id] int NOT NULL IDENTITY(1, 1),
  [product_id] int NOT NULL,
  [user_id] int NOT NULL,
  [rating] int,
  [content] nvarchar(3000),
  [created_at] datetime,
  PRIMARY KEY ([feedback_id])
)
GO

CREATE TABLE [image] (
  [image_id] int NOT NULL IDENTITY(1, 1),
  [product_id] int NOT NULL,
  [url] nvarchar(max) NOT NULL,
  [created_at] datetime,
  PRIMARY KEY ([image_id])
)
GO

CREATE TABLE [notification] (
  [notification_id] int NOT NULL IDENTITY(1, 1),
  [user_id] int NOT NULL,
  [message] nvarchar(500),
  [type] nvarchar(30),
  [read] bit,
  [created_at] datetime,
  PRIMARY KEY ([notification_id])
)
GO

CREATE TABLE [order] (
  [order_id] int NOT NULL IDENTITY(1, 1),
  [user_id] int NOT NULL,
  [total_price] int NOT NULL,
  [status] nvarchar(15) NOT NULL,
  [created_at] datetime,
  PRIMARY KEY ([order_id])
)
GO

CREATE TABLE [order_detail] (
  [order_id] int NOT NULL,
  [product_id] int NOT NULL,
  [price] int NOT NULL,
  [amount] smallint NOT NULL,
  PRIMARY KEY ([order_id], [product_id])
)
GO

ALTER TABLE [authors_of_product] ADD FOREIGN KEY ([author_id]) REFERENCES [author] ([author_id])
GO

ALTER TABLE [authors_of_product] ADD FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [product] ADD FOREIGN KEY ([category_id]) REFERENCES [category] ([category_id])
GO

ALTER TABLE [favorite] ADD FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [favorite] ADD FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [feedback] ADD FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [feedback] ADD FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [image] ADD FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [notification] ADD FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [order] ADD FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [order_detail] ADD FOREIGN KEY ([order_id]) REFERENCES [order] ([order_id])
GO

ALTER TABLE [order_detail] ADD FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

 