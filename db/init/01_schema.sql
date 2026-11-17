CREATE TABLE users (
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(150) UNIQUE NOT NULL,
  phone       VARCHAR(15),
  created_at  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE courts (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(50) NOT NULL,
  court_type      VARCHAR(20) NOT NULL,
  price_per_hour  INT NOT NULL
);

CREATE TABLE bookings (
  id            SERIAL PRIMARY KEY,
  user_id       INT NOT NULL REFERENCES users(id),
  court_id      INT NOT NULL REFERENCES courts(id),
  booking_date  DATE NOT NULL,
  start_time    TIME NOT NULL,
  end_time      TIME NOT NULL,
  status        VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
  created_at    TIMESTAMP DEFAULT NOW()
);

CREATE TABLE payments (
  id          SERIAL PRIMARY KEY,
  booking_id  INT REFERENCES bookings(id),
  amount      INT NOT NULL,
  method      VARCHAR(20) NOT NULL,
  status      VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
  paid_at     TIMESTAMP DEFAULT NOW()
);