INSERT INTO category(name, parent_id)
VALUES ('Смартфоны и гаджеты', null),
       ('Планшеты, электронные книги', null),
	   ('Компьютеры, комплектующие', null),
	   ('Фотоаппаратура', null),
	   ('Компьютеры, ноутбуки и ПО', null),
	   ('Комплектующие для ПК', null),
	   ('Периферия и аксессуары', null);

UPDATE category SET name = 'Смартфоны Xiaomi' WHERE id = '1';

INSERT INTO category(name, parent_id)
VALUES ('Смартфоны', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты'));

UPDATE category SET parent_id = (SELECT id FROM category WHERE name = 'Смартфоны') WHERE name = 'Смартфоны Xiaomi';

UPDATE category SET name = 'Планшеты Apple' WHERE id = '2';

INSERT INTO category(name, parent_id)
VALUES ('Планшеты', (SELECT id FROM category WHERE name = 'Планшеты, электронные книги'));

UPDATE category SET parent_id = (SELECT id FROM category WHERE name = 'Планшеты') WHERE name = 'Планшеты Apple';

UPDATE category SET name = 'Смартфоны Apple' WHERE id = '3';

UPDATE category SET parent_id = (SELECT id FROM category WHERE name = 'Смартфоны') WHERE id = '3';


INSERT INTO category(name, parent_id)
VALUES ('Планшеты Xiaomi', (SELECT id FROM category WHERE name = 'Планшеты')),
	   ('Планшеты Sumsung', (SELECT id FROM category WHERE name = 'Планшеты')),
	   ('Планшеты LG', (SELECT id FROM category WHERE name = 'Планшеты')),
	   ('Планшеты Asus', (SELECT id FROM category WHERE name = 'Планшеты')),
	   ('Планшеты Meizu', (SELECT id FROM category WHERE name = 'Планшеты'));

INSERT INTO category(name, parent_id)
VALUES ('Смартфоны Sumsung', (SELECT id FROM category WHERE name = 'Смартфоны')),
	   ('Смартфоны LG', (SELECT id FROM category WHERE name = 'Смартфоны')),
	   ('Смартфоны Asus', (SELECT id FROM category WHERE name = 'Смартфоны')),
	   ('Смартфоны Meizu', (SELECT id FROM category WHERE name = 'Смартфоны'));


INSERT INTO category(name, parent_id)
VALUES ('Смарт-часы и браслеты', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Сотовые телефоны', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Стационарные сотовые телефоны', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Радиостанции', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Аксессуары для смартфонов', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Аксессуары для смарт-часов и браслетов', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Сим-карты, услуги операторов', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты')),
	   ('Запчасти для смартфонов', (SELECT id FROM category WHERE name = 'Смартфоны и гаджеты'));



INSERT INTO category(name, parent_id)
VALUES ('Электронные книги', (SELECT id FROM category WHERE name = 'Планшеты, электронные книги')),
	   ('Аксессуары для планшетов', (SELECT id FROM category WHERE name = 'Планшеты, электронные книги')),
	   ('Аксессуары для электронных книг', (SELECT id FROM category WHERE name = 'Планшеты, электронные книги')),
	   ('Сим-карты', (SELECT id FROM category WHERE name = 'Планшеты, электронные книги'));



INSERT INTO category(name, parent_id)
VALUES ('Фотоаппараты', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Экшн-камеры и аксессуары', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Видеокамеры', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Объективы и аксессуары', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Штативы и стабилизаторы', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Световое оборудование и аксессуары', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Питание для фотоаппаратов', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Пульты ДУ для фотоаппаратов', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Микрофоны для фото-видеокамер', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Хранение и переноска', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Чистка оптики и матрицы', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Печать и хранение фотографий', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Приборы для наблюдений', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Аксессуары и доп. оборудование', (SELECT id FROM category WHERE name = 'Фотоаппаратура')),
	   ('Квадрокоптеры с камерами', (SELECT id FROM category WHERE name = 'Фотоаппаратура'));

INSERT INTO category(name, parent_id)
VALUES('Системные блоки', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Ноутбуки', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Моноблоки', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Неттопы', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Тонкие клиенты', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Микрокомпьютеры', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Платформы', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Серверы', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Серверные платформы', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('программное обеспечение', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Аксессуары для микрокомпьютеров', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Аксессуары для ноутбуков', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Запчасти и ПО для ноутбуков', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО')),
	  ('Комплектующие для ноутбуков', (SELECT id FROM category WHERE name = 'Компьютеры, ноутбуки и ПО'));

INSERT INTO category(name, parent_id)
VALUES('Процессоры', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
      ('Материнские платы', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Видеокарты', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Оперативная память', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Блоки питания', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Корпуса', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Комплектующие для сервера', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Охлаждение компьютера', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('SSD накопители', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Жесткие диски', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Аксессуары для накопителей', (SELECT id FROM category WHERE name = 'Комплектующие для ПК')),
	  ('Устройства расширения и апгрейд', (SELECT id FROM category WHERE name = 'Комплектующие для ПК'));

INSERT INTO category(name, parent_id)
VALUES('Мониторы', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
      ('Клавиатуры', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Мыши', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Комплект мышь+клавиатура', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Коврики для мыши', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Графические планшеты', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Аксессуары для графических планшетов', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Внешние накопители данных', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Веб-камеры', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('USB-разветвители', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Очки для работы за ПК', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Настольные крепления для мониторов', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Блоки питания для мониторов', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Полезные аксессуары', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Кабели и переходники', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Наушники', (SELECT id FROM category WHERE name = 'Периферия и аксессуары')),
	  ('Видеокабели и переходники', (SELECT id FROM category WHERE name = 'Периферия и аксессуары'));


