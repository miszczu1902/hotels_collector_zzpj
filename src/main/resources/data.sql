INSERT INTO users (id, username, email, password, is_active, is_enable, role) VALUES (1, 'TheHubert21', 'stary2111@gmail.com', '$2a$10$tuBH/t6MxHWdyBG.DV/Uw.23TkQWT4rVS/Bcm7qAe8RwGXqXz6yEi', true, true, 'CLIENT');

INSERT INTO hotels (id, name, longitude, latitude, city, street, number, additional_address_information, phone_number)
VALUES
    (-1,'Pan kiedyś stał nad hotelem', '19.4927', '49.8833', 'Wadowice', 'Żółta', '2137', 'Hotel Wadowice', '213721379'),
    (-2,'Gwiezdny Pałac', '18.9723', '50.0621', 'Kraków', 'Czerwona', '123', 'Obok Rynku Głównego', '123456789'),
    (-3,'Słoneczne Wzgórze', '20.0124', '50.2847', 'Warszawa', 'Niebieska', '456', 'Blisko Pałacu Kultury', '987654321'),
    (-4,'Morze i Słońce', '21.4546', '54.3521', 'Sopot', 'Pomorska', '789', 'W pobliżu molo', '741852963'),
    (-5,'Zielony Zakątek', '16.9132', '52.4066', 'Poznań', 'Zielona', '987', 'W parku miejskim', '369258147'),
    (-6,'Błękitny Horizont', '17.0382', '51.1079', 'Wrocław', 'Błękitna', '654', 'Obok Ostrów Tumski', '951753852'),
    (-7,'Górska Dolina', '15.5902', '49.7391', 'Zakopane', 'Górska', '321', 'Przy szlaku turystycznym', '586923147'),
    (-8,'Nad Jeziorem', '22.0034', '53.8008', 'Gdańsk', 'Jezioro', '741', 'W pobliżu plaży', '258369147'),
    (-9,'Pole Namiotowe', '19.9321', '50.2706', 'Katowice', 'Namiotowa', '159', 'Blisko Stadionu Śląskiego', '963852741'),
    (-10,'Winnica Pod Kasztanami', '21.2536', '52.2274', 'Łódź', 'Winna', '753', 'Wśród winnic', '123789456');

INSERT INTO rooms (id, room_size, maximum_guest_number, is_air_conditioning, is_soundproofing, description, facility, hotel_id)
VALUES
    (-1, 20, 2, true, true, 'Pokój z widokiem na ulice Żółtą', '["Upper_floors_accessible_by_elevator"]', -1),
    (-2, 25, 3, true, false, 'Pokój z łazienką', '["Free_WiFi", "TV", "Hairdryer"]', -2),
    (-3, 18, 1, false, true, 'Mały przytulny pokój', '["Minibar", "Coffee_maker", "Safe"]', -3),
    (-4, 30, 4, true, true, 'Duży luksusowy apartament', '["Balcony", "Kitchenette", "Room_service"]', -4),
    (-5, 15, 1, true, true, 'Pokój jednoosobowy', '["Free_WiFi", "TV", "Desk"]', -5),
    (-6, 22, 2, false, false, 'Standardowy pokój dwuosobowy', '["Minibar", "Safe"]', -6),
    (-7, 28, 3, true, true, 'Apartament z widokiem na morze', '["Balcony", "Kitchenette", "Room_service"]', -7),
    (-8, 12, 1, true, true, 'Mały pokój dla singla', '["Free_WiFi", "TV", "Desk"]', -8),
    (-9, 35, 4, true, true, 'Apartament rodzinny', '["Balcony", "Kitchenette", "Room_service", "Baby_cot"]', -9),
    (-10, 16, 1, true, false, 'Pokój z łazienką', '["Minibar", "Hairdryer"]', -1),
    (-11, 20, 2, true, true, 'Pokój z widokiem na ulice Żółtą', '["Upper_floors_accessible_by_elevator"]', -2),
    (-12, 25, 3, true, true, 'Apartament z widokiem na miasto', '["Balcony", "Kitchenette", "Room_service"]', -3),
    (-13, 18, 1, false, true, 'Mały przytulny pokój', '["Minibar", "Coffee_maker", "Safe"]', -4),
    (-14, 30, 4, true, false, 'Luksusowy apartament z jacuzzi', '["Balcony", "Kitchenette", "Room_service", "Jacuzzi"]', -5),
    (-15, 15, 1, true, true, 'Pokój jednoosobowy', '["Free_WiFi", "TV", "Desk"]', -6),
    (-16, 22, 2, false, true, 'Pokój dwuosobowy z łazienką', '["Minibar", "Hairdryer"]', -7),
    (-17, 28, 3, true, true, 'Apartament z widokiem na jezioro', '["Balcony", "Kitchenette", "Room_service"]', -8),
    (-18, 12, 1, true, false, 'Mały pokój dla singla', '["Free_WiFi", "TV", "Desk"]', -9),
    (-19, 35, 4, true, true, 'Apartament rodzinny', '["Balcony", "Kitchenette", "Room_service", "Baby_cot"]', -1),
    (-20, 16, 1, true, true, 'Pokój z widokiem na park', '["Minibar", "Coffee_maker", "Safe"]', -2);

