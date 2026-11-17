INSERT INTO users (name, email, phone) VALUES
('Arun Kumar', 'arun@test.com',    '9000000001'),
('Priya S',    'priya@test.com',   '9000000002'),
('Karthik R',  'karthik@test.com', '9000000003'),
('Meena V',    'meena@test.com',   '9000000004'),
('Rahul M',    'rahul@test.com',   '9000000005'),
('Divya K',    'divya@test.com',   '9000000006');

INSERT INTO courts (name, court_type, price_per_hour) VALUES
('Court A', 'SYNTHETIC', 400),
('Court B', 'SYNTHETIC', 400),
('Court C', 'WOODEN',    500),
('Court D', 'WOODEN',    500);

INSERT INTO bookings (user_id, court_id, booking_date, start_time, end_time, status) VALUES
(1, 1, '2026-11-10', '06:00', '07:00', 'CONFIRMED'),
(2, 1, '2026-11-10', '07:00', '08:00', 'CONFIRMED'),
(3, 2, '2026-11-10', '18:00', '20:00', 'CONFIRMED'),
(1, 3, '2026-11-11', '19:00', '20:00', 'CANCELLED'),
(4, 2, '2026-11-11', '06:00', '07:00', 'CONFIRMED'),
(5, 3, '2026-11-12', '20:00', '21:00', 'CONFIRMED'),
(2, 1, '2026-11-12', '18:00', '19:00', 'CONFIRMED'),
(5, 2, '2026-11-10', '19:00', '20:00', 'CONFIRMED');

INSERT INTO payments (booking_id, amount, method, status) VALUES
(1,    400, 'UPI',  'SUCCESS'),
(2,    400, 'CASH', 'SUCCESS'),
(3,    800, 'CARD', 'SUCCESS'),
(4,    500, 'UPI',  'REFUNDED'),
(5,    400, 'UPI',  'SUCCESS'),
(8,    400, 'UPI',  'SUCCESS'),
(NULL, 300, 'CASH', 'SUCCESS'),
(3,    800, 'UPI',  'SUCCESS');