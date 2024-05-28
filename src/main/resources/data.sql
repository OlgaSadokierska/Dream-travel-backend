-- Wstawienie danych podróży
INSERT INTO travel (country, city, start_date, end_date, description, rate) VALUES
                                                                                ('Spain', 'Barcelona', '2024-06-01', '2024-06-10', 'A beautiful trip to Barcelona', 5),
                                                                                ('France', 'Paris', '2024-07-15', '2024-07-20', 'Visiting the Eiffel Tower and other attractions in Paris', 4),
                                                                                ('Italy', 'Rome', '2024-08-05', '2024-08-12', 'Exploring the historical sites of Rome', 5),
                                                                                ('Japan', 'Tokyo', '2024-09-10', '2024-09-17', 'Experiencing the culture and technology in Tokyo', 4),
                                                                                ('Australia', 'Sydney', '2024-10-20', '2024-10-30', 'Enjoying the beaches and landmarks in Sydney', 5);

-- Wstawienie danych użytkowników
INSERT INTO user (firstname, lastname, email, travel_id) VALUES
                                                             ('John', 'Doe', 'john.doe@example.com', 1),
                                                             ('Jane', 'Smith', 'jane.smith@example.com', 2),
                                                             ('Michael', 'Brown', 'michael.brown@example.com', 3),
                                                             ('Emily', 'Davis', 'emily.davis@example.com', 4),
                                                             ('Chris', 'Wilson', 'chris.wilson@example.com', 5),
                                                             ('Olga', 'Sadokierska', 'olga.sadokierska@op.pl', 5),
                                                             ('Iza', 'na', 'izakan75@gmail.com', 5);
